package cn.nextop.thorin.test.service.dealerapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import cn.nextop.thorin.api.dealer.proto.HoldingServiceProtoV1.HoldingEvent;
import cn.nextop.thorin.api.dealer.proto.HoldingServiceProtoV1.SubscribeHoldingEventResponse;
import cn.nextop.thorin.core.holding.HoldingStrategyManager;
import cn.nextop.thorin.test.vo.dlrapi.HoldingData;
import support.shell.dlrapi.service.HoldingService;

@Service
@Lazy
public class DealerApiHoldingService extends DealerApiAbstractService {

	@Autowired
	private HoldingService holdingService;
	@Autowired
	private HoldingStrategyManager holdingStrategyManager;

	@PostConstruct
	public void init() {
		super.init();
		// 
		SubscribeHoldingEventResponse subHolding = holdingService.subscribeHoldingEvent();
		assertThat(subHolding.getResult()).as(subHolding.toString()).isEqualTo(SubscribeHoldingEventResponse.Result.SUCCESS);
	}

	public HoldingData next() {
		HoldingEvent holdingEvent = holdingService.nextHoldingEvent();
		HoldingData holdingData = HoldingData.from(holdingEvent);
		log.debug("nextHoldingDataFromDealerApi - {}", holdingData);
		return holdingData;
	}

	public HoldingData get(Set<Byte> companyIds) {
		// 
		HoldingData holdingData = new HoldingData();
		holdingData.setBookPos(flatAndToList(companyIds, companyId -> holdingStrategyManager.getBook(companyId).values().stream()));
		holdingData.setBooksPos(flatAndToList(companyIds, companyId -> holdingStrategyManager.getBooks(companyId).values().stream()));
		holdingData.setAccountsPos(flatAndToList(companyIds, companyId -> holdingStrategyManager.getAccounts(companyId).values().stream()));
		holdingData.setAllocationPos(flatAndToList(companyIds, companyId -> holdingStrategyManager.getAllocation(companyId).values().stream()));
		holdingData.setHoldingStrategyPos(flatAndToList(companyIds, companyId ->  Stream.of(holdingStrategyManager.getStrategy(companyId).getSummary())));

		holdingData.setBooksConfigPos(flatAndToList(holdingData.getBooksPos(), booksPo -> holdingStrategyManager.getBooksConfigs(booksPo.getId()).values().stream()));
		holdingData.setAccountsConfigPos(flatAndToList(holdingData.getAccountsPos(), accountsPo -> holdingStrategyManager.getAccountsConfigs(accountsPo.getId()).values().stream()));
		holdingData.setBookQuoteConfigPos(flatAndToList(holdingData.getBookPos(), bookPo -> holdingStrategyManager.getBookQuoteConfigs(bookPo.getId()).values().stream()));

		holdingData.setAllocationBookConfigPos(flatAndToList(holdingData.getAllocationPos(), allocationPo -> holdingStrategyManager.getAllocationBookConfigs(allocationPo.getId()).values().stream()));
		holdingData.setAllocationBrokerConfigPos(flatAndToList(holdingData.getAllocationPos(), allocationPo -> holdingStrategyManager.getAllocationBrokerConfigs(allocationPo.getId()).values().stream()));
		holdingData.setAllocationSymbolConfigPos(flatAndToList(holdingData.getAllocationPos(), allocationPo -> holdingStrategyManager.getAllocationSymbolConfigs(allocationPo.getId()).values().stream()));
		holdingData.setAllocationAccountsConfigPos(flatAndToList(holdingData.getAllocationPos(), allocationPo -> holdingStrategyManager.getAllocationAccountsConfigs(allocationPo.getId()).values().stream()));
		return holdingData;
	}

	private <S, T> List<T> flatAndToList(Collection<S> companyIds, Function<? super S, ? extends Stream<? extends T>> mapper) {
		return companyIds.stream().flatMap(mapper).filter(Objects::nonNull).collect(Collectors.toList());
	}

}
