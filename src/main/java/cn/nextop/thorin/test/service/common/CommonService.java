package cn.nextop.thorin.test.service.common;

import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Collections2;
import com.google.common.collect.Maps;

import cn.nextop.thorin.common.util.Sets;
import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.CompanyManager;
import cn.nextop.thorin.core.common.MarketManager;
import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cn.nextop.thorin.core.common.glossary.CurrencyCode;
import cn.nextop.thorin.core.common.orm.po.BrokerPo;
import cn.nextop.thorin.core.common.orm.po.CompanyPo;
import cn.nextop.thorin.core.common.orm.po.InstitutionPo;
import cn.nextop.thorin.core.common.orm.po.LiquidityProviderPo;
import cn.nextop.thorin.core.common.orm.po.SymbolPo;
import cn.nextop.thorin.test.vo.id.has.single.HasSymbolId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;
import cn.nextop.thorin.test.vo.id.obj.derivative.CounterpartyId;
import cn.nextop.thorin.test.vo.id.obj.derivative.LadderId;
import cn.nextop.thorin.test.vo.id.obj.derivative.PositionId;

@Service
public class CommonService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonService.class);

	@Autowired
	private CompanyManager companyManager;
	@Autowired
	private MarketManager marketManager;
	@Autowired
	@Qualifier("common.datasource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	// default
	private final byte defaultCompanyId = (byte) 1;
	private final byte defaultLpSeq = (byte) 0;

	// Internal use
	private Map<String, InstitutionPo> name2InstitutionPo;
	private Map<String, SymbolPo> name2SymbolPo;

	@PostConstruct
	public void init() {
		try {
			// 
			name2InstitutionPo = Maps.uniqueIndex(companyManager.getInstitutions().values(), InstitutionPo::getAbbreviation);
			name2SymbolPo = Maps.uniqueIndex(marketManager.getSymbols().values(), SymbolPo::getName);
			// 
			jdbcTemplate = new JdbcTemplate(dataSource);
			// 
			this.isTimeDiffOk();
		} catch (Throwable e) {
			e.printStackTrace();
			LOGGER.error("Error in init", e);
		}
	}

	private void isTimeDiffOk() throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			if (isTimeDiffOkForOnce()) {
				return;
			}
			Thread.sleep(333);
		}
		System.err.println("Time Diff Error.");
		LOGGER.error("Time Diff Error.");
	}

	private boolean isTimeDiffOkForOnce() {
		long nowInDbInSec = jdbcTemplate.queryForObject("select unix_timestamp(now())", Long.class);
		long nowInMs = System.currentTimeMillis();
		LOGGER.debug("DB Time: {}. APP Time: {}. diff={}", nowInDbInSec * 1000, nowInMs, nowInMs - nowInDbInSec * 1000);
		return Math.abs(nowInMs - nowInDbInSec * 1000) <= 1000;
	}

	public TradingDate currentTradingDate() {
		return marketManager.getTradingDate();
	}

	public TradingDate getTradingDate(int tday) {
		return marketManager.getTradingDate().add(tday);
	}
	
	public Set<Short> getAllBrokerIds() {
		Supplier<Set<Short>> supplier = Suppliers.memoize(() -> Sets.newHashSet(Collections2.transform(companyManager.getBrokers().values(), BrokerPo::getId)));
		return supplier.get();
	}

	public Set<Short> getAllLpIds() {
		Supplier<Set<Short>> supplier = Suppliers.memoize(() -> Sets.newHashSet(Collections2.transform(companyManager.getLiquidityProviders().values(), LiquidityProviderPo::getId)));
		return supplier.get();
	}

	public Set<Byte> getAllCompanyIds() {
		Supplier<Set<Byte>> supplier = Suppliers.memoize(() -> Sets.newHashSet(Collections2.transform(companyManager.getCompanies().values(), CompanyPo::getId)));
		return supplier.get();
	}

	public byte getItId(String itName) {
		Assert.notNull(itName);
		itName = itName.trim();
		Assert.hasText(itName);
		InstitutionPo institutionPo = name2InstitutionPo.get(itName);
		Assert.notNull(institutionPo, String.format("No Institution Found for %s", itName));
		byte id = institutionPo.getId();
		return id;
	}

	public short getDefaultLpId(String itName) {
		byte itId = this.getItId(itName);
		return CommonGenerator.toLpId(defaultCompanyId, itId, defaultLpSeq);
	}

	public short getDefaultLpId(String itName, byte coId) {
		byte itId = this.getItId(itName);
		return CommonGenerator.toLpId(coId, itId, defaultLpSeq);
	}

	public LpSymbolId getDefaultLpSymbolId(String itName, String symbolName) {
		short lpId = this.getDefaultLpId(itName);
		short symbolId = this.getSymbolId(symbolName);
		int lpSymbolId = CommonGenerator.toLpSymbolId(lpId, symbolId);
		return LpSymbolId.toLpSymbolId(lpSymbolId);
	}

	public short getSymbolId(String symbolName) {
		Assert.hasText(symbolName);
		SymbolPo symbolPo = name2SymbolPo.get(symbolName);
		Assert.notNull(symbolPo, String.format("No Symbol Found for %s", symbolName));
		short symbolId = symbolPo.getId();
		return symbolId;
	}

	public byte getCurrencyValue(String currencyName) {
		Assert.hasText(currencyName);
		return CurrencyCode.parse(currencyName).getValue();
	}

	public LpSymbolId getDefaultLpSymbolId(String itName, HasSymbolId symbolId) {
		short lpId = this.getDefaultLpId(itName);
		LpSymbolId lpSymbolId = LpSymbolId.toLpSymbolId(lpId, symbolId.symbolId());
		return lpSymbolId;
	}

	public LadderId toLadderId(int tday, String itName, String symbolName) {
		LpSymbolId lpSymbolId = this.getDefaultLpSymbolId(itName, symbolName);
		TradingDate tradingDate = marketManager.getTradingDate();
		tradingDate = tradingDate.add(tday);
		LadderId ladderId = LadderId.toLadderId(tradingDate, lpSymbolId);
		return ladderId;
	}

	public CounterpartyId toCounterpartyId(int tday, String itName, String symbolName) {
		LpSymbolId lpSymbolId = this.getDefaultLpSymbolId(itName, symbolName);
		TradingDate tradingDate = marketManager.getTradingDate().add(tday);
		return CounterpartyId.toCounterpartyId(tradingDate, lpSymbolId);
	}

	public PositionId toPositionId(int bookId, String symbolName) {
		short symbolId = this.getSymbolId(symbolName);
		PositionId positionId = PositionId.toPositionId(bookId, symbolId);
		return positionId;
	}

	public byte getDefaultCompanyId() {
		return defaultCompanyId;
	}

}
