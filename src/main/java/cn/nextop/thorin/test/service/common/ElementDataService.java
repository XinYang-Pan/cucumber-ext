package cn.nextop.thorin.test.service.common;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.nextop.test.cucumber.Element;
import cn.nextop.test.cucumber.ElementData;
import cn.nextop.thorin.core.common.MarketManager;
import cn.nextop.thorin.core.common.domain.CommonGenerator;
import cucumber.api.DataTable;

@Service
public class ElementDataService {
	@Autowired
	protected MarketManager marketManager;
	@Autowired
	protected CommonService commonService;
	@Autowired
	protected ParseService parseService;
	// 
	private Map<String, Function<String, ?>> funcName2FunctionMap = Maps.newHashMap();
	private Element scenarioVariable;

	@PostConstruct
	public void init() {
		// 
		funcName2FunctionMap.put("valueDate", t -> marketManager.getTradingDate().add(Integer.parseInt(t)));
		funcName2FunctionMap.put("tradingDate", t -> marketManager.getTradingDate().add(Integer.parseInt(t)));
		funcName2FunctionMap.put("symbolId", commonService::getSymbolId);
		funcName2FunctionMap.put("lpId", parseService::toLpId);
		funcName2FunctionMap.put("bookId", parseService::toBookId);
		funcName2FunctionMap.put("triggerId", parseService::toTriggerId);
		funcName2FunctionMap.put("addSeq", this::addSeq);
		funcName2FunctionMap.put("now", parseService::toTimestamp);
	}

	public ElementData from(DataTable dataTable) {
		ElementData elementData = ElementData.of(dataTable, scenarioVariable);
		elementData.convert(funcName2FunctionMap);
		return elementData;
	}

	public ElementData from(List<Map<String, String>> valueMaps) {
		ElementData elementData = ElementData.of(valueMaps);
		elementData.convert(funcName2FunctionMap);
		return elementData;
	}

	public ElementData from(Map<String, String> valueMap) {
		return from(Lists.newArrayList(valueMap));
	}

	private String addSeq(String value) {
		long next = CommonGenerator.getDefault().next();
		return String.format("%s@%s", value, next);
	}

	// -----------------------------
	// ----- Scenario Variables
	// -----------------------------

	public Element initScenarioVariable(Map<String, String> scenarioVariableMap) {
		return scenarioVariable = this.from(scenarioVariableMap).getOnlyElement();
	}

	public Element getScenarioVariable() {
		return Objects.requireNonNull(scenarioVariable);
	}

	public void clearScenarioVariable() {
		scenarioVariable = null;
	}

}
