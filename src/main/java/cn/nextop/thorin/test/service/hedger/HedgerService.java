package cn.nextop.thorin.test.service.hedger;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.nextop.thorin.common.messaging.Channel;
import cn.nextop.thorin.common.util.marshaller.Marshallable;
import cn.nextop.thorin.core.common.domain.pipeline.Request;
import cn.nextop.thorin.core.common.domain.pipeline.Requests;
import cn.nextop.thorin.core.common.glossary.HedgingSessionType;
import cn.nextop.thorin.core.common.orm.po.LiquidityProviderPo;
import cn.nextop.thorin.core.hedging.HedgingSessionAdministrator;
import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.FeedLpQuoteRequest;
import cn.nextop.thorin.test.emulator.AbstractEmulator;
import cn.nextop.thorin.test.emulator.bo.GwGroup;
import cn.nextop.thorin.test.emulator.bo.GwQuote;
import cn.nextop.thorin.test.emulator.bo.GwQuoteFull;
import cn.nextop.thorin.test.emulator.bo.GwQuoteIncr;
import cn.nextop.thorin.test.emulator.impl.ads.AdsEmulator;
import cn.nextop.thorin.test.emulator.impl.gkgoh.GkgohEmulator;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;

@Service
@Lazy
public class HedgerService extends AbstractService {
	@Autowired
	private HedgingSessionAdministrator hedgingSessionAdministrator;
	// Emulator Config
	private final List<String> its = Lists.newArrayList("ADS", "GKGOH");
	private final List<String> symbols = Lists.newArrayList("USD/JPY");
	// Init values by init()
	private final Map<String, Integer> portMap = Maps.newHashMap();
	private final Map<String, AbstractEmulator<?>> emulatorMap = Maps.newHashMap();
	// Lp Quotes Cache
	private final List<LpQuote> lpQuotes = Lists.newCopyOnWriteArrayList();
	
	@PostConstruct
	public void init() {
		portMap.put("ADS", 20590);
		portMap.put("GKGOH", 6789);
		this.addListenerForLpQuotes();
		this.start(its, symbols);
	}
	
	private void addListenerForLpQuotes() {
		Channel channel = messenger.getChannel("pricing.server");
		messenger.addListener(channel, m -> {
			Marshallable payload = m.getPayload();
			log.debug("Receiving request class {}, payload {}.", payload.getClass(), payload);
			if (payload instanceof Requests) {
				List<? extends Request> requests = ((Requests) payload).getRequests();
				for (Request request : requests) {
					if (request instanceof FeedLpQuoteRequest) {
						FeedLpQuoteRequest req = (FeedLpQuoteRequest) request;
						lpQuotes.add(req.getLpQuote());
					}
				}
			}
		});
	}
	
	private void start(List<String> itNames, List<String> symbolNames) {
		try {
			for (String itName : itNames) {
				this.updateLpConfigForServerInfo(itName, portMap.get(itName));
				for (String symbolName : symbolNames) {
					this.subscribe(symbolName, itName);
				}
				AbstractEmulator<?> emulator = this.startEmulator(itName);
				emulatorMap.put(itName, emulator);
				this.sendConnectMsgToHedge(itName);
				emulator.waitUtilReady();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void reconnect(String itName) {
		try {
			this.sendDisconnectMsgToHedge(itName);
			Thread.sleep(1000);
			this.sendConnectMsgToHedge(itName);
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void send(GwQuote gwQuote) {
		try {
			validate(gwQuote);
			AbstractEmulator<?> emulator = emulatorMap.get(gwQuote.getItName());
			Assert.notNull(emulator);
			if (gwQuote.getDelay() > 0) {
				Thread.sleep(gwQuote.getDelay() * 1000);
			}
			emulator.sendGwQuote(gwQuote);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void validate(GwQuote gwQuote) {
		Assert.notNull(gwQuote);
		assertThat(emulatorMap).as("Has this emulator.").containsKey(gwQuote.getItName());
		// 
		if (gwQuote instanceof GwQuoteFull) {
			String symbolName = ((GwQuoteFull) gwQuote).getSymbolName();
			assertThat(symbols).as("Has this symbol.").contains(symbolName);
		} else if (gwQuote instanceof GwQuoteIncr) {
			List<String> symbols = Lists.newArrayList(Lists.transform(((GwQuoteIncr) gwQuote).getGroups(), GwGroup::getSymbol));
			assertThat(symbols).as("Has these symbols.").containsAll(symbols);
		}
	}

	@PreDestroy
	public void stopAll() {
		for (Entry<String, AbstractEmulator<?>> e : emulatorMap.entrySet()) {
			try {
				this.sendDisconnectMsgToHedge(e.getKey());
				Thread.sleep(1000);
				e.getValue().stop();
			} catch (InterruptedException e1) {
				throw new RuntimeException(e1);
			}
		}
	}
	
	private AbstractEmulator<?> startEmulator(String itName) {
		if ("ADS".equals(itName)) {
			AdsEmulator adsEmulator = new AdsEmulator();
			adsEmulator.start();
			return adsEmulator;
		} else if ("GKGOH".equals(itName)) {
			GkgohEmulator gkgohEmulator = new GkgohEmulator();
			gkgohEmulator.start();
			return gkgohEmulator;
		} else {
			throw new IllegalArgumentException(String.format("Not supported itName=%s", itName));
		}
	}
	
	private void updateLpConfigForServerInfo(String itName, int port) throws UnknownHostException  {
		short lpId = commonService.getDefaultLpId(itName);
		LiquidityProviderPo liquidityProvider = liquidityProviderPoService.get(lpId);
		liquidityProvider.setFixPricingEnabled(true);
		liquidityProvider.setFixPricingHost(InetAddress.getLocalHost().getHostAddress());
		liquidityProvider.setFixTradingHost(InetAddress.getLocalHost().getHostAddress());
		liquidityProvider.setFixPricingSenderId("seanpan");
		liquidityProvider.setFixTradingSenderId("seanpan");
		liquidityProvider.setFixPricingPort(port);
		// 
		liquidityProviderPoService.update(liquidityProvider);
		log.debug("updating liquidityProvider. ref={}", liquidityProvider);
	}

	private void subscribe(String symbolName, String itName) {
		LpSymbolId lpSymbolId = commonService.getDefaultLpSymbolId(itName, symbolName);
		lpSymbolPoService.updateByUniqueKey(lpSymbolId, lpSymbolPo -> lpSymbolPo.setQuoteSubscription(true));
	}

	private void sendConnectMsgToHedge(String itName) {
		short lpId = commonService.getDefaultLpId(itName);
		hedgingSessionAdministrator.connect(lpId, HedgingSessionType.PRICE);
	}

	private void sendDisconnectMsgToHedge(String itName) {
		short lpId = commonService.getDefaultLpId(itName);
		hedgingSessionAdministrator.disconnect(lpId, HedgingSessionType.PRICE);
	}

	public void clearAll() {
		this.lpQuotes.clear();
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	public List<LpQuote> getLpQuotes() {
		return lpQuotes;
	}

}
