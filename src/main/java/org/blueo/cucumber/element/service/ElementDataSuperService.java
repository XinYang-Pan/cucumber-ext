package org.blueo.cucumber.element.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.blueo.cucumber.element.Element;
import org.blueo.cucumber.element.ElementData;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cucumber.api.DataTable;

public class ElementDataSuperService {
	// 
	protected Map<String, Function<String, ?>> funcName2FunctionMap = Maps.newHashMap();
	protected Element scenarioVariable;

	public ElementDataSuperService() {
		funcName2FunctionMap.put("now", this::now);
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

	// -----------------------------
	// ----- function
	// -----------------------------

	public long now(String seconds) {
		if (!StringUtils.hasText(seconds)) {
			return 0;
		}
		return System.currentTimeMillis() + (Long.valueOf(seconds) * 1000L);
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
