package io.github.xinyangpan.cucumber.element.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cucumber.api.DataTable;
import io.github.xinyangpan.cucumber.element.BaseElement;
import io.github.xinyangpan.cucumber.element.ElementData;

public class ElementDataSuperService<E extends BaseElement> {
	// 
	protected Map<String, Function<String, ?>> funcName2FunctionMap = Maps.newHashMap();
	protected E scenarioVariable;
	private Function<Map<String, String>, E> newElement;

	public ElementDataSuperService() {
		this(null);
	}
	
	public ElementDataSuperService(Function<Map<String, String>, E> newElement) {
		this.newElement = newElement;
		//
		funcName2FunctionMap.put("now", this::now);
	}

	public ElementData<E> from(DataTable dataTable) {
		ElementData<E> elementData = new ElementData<E>(dataTable, scenarioVariable, newElement);
		elementData.convert(funcName2FunctionMap);
		return elementData;
	}

	public ElementData<E> from(List<Map<String, String>> valueMaps) {
		ElementData<E> elementData = new ElementData<>(valueMaps, scenarioVariable, newElement);
		elementData.convert(funcName2FunctionMap);
		return elementData;
	}

	public ElementData<E> from(Map<String, String> valueMap) {
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

	public E initScenarioVariable(Map<String, String> scenarioVariableMap) {
		return scenarioVariable = this.from(scenarioVariableMap).getOnlyElement();
	}

	public E getScenarioVariable() {
		return Objects.requireNonNull(scenarioVariable);
	}

	public void clearScenarioVariable() {
		scenarioVariable = null;
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public Function<Map<String, String>, E> getNewElement() {
		return newElement;
	}

	public void setNewElement(Function<Map<String, String>, E> newElement) {
		this.newElement = newElement;
	}

}
