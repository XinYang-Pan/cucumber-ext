package io.github.xinyangpan.cucumber.element;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.assertj.core.util.Sets;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cucumber.api.DataTable;
import io.github.xinyangpan.cucumber.util.ElementUtils;

public class ElementData<E extends BaseElement> {
	// 
	private final List<E> baseElements = Lists.newArrayList();
	// -----------------------------
	// ----- Constructors
	// -----------------------------

	public ElementData(DataTable dataTable, Function<Map<String, String>, E> newElement) {
		this(dataTable.asMaps(String.class, String.class), null, newElement);
	}
	
	public ElementData(DataTable dataTable, E scenarioVariable, Function<Map<String, String>, E> newElement) {
		this(dataTable.asMaps(String.class, String.class), scenarioVariable, newElement);
	}

	public ElementData(List<Map<String, String>> elementMaps, E scenarioVariable, Function<Map<String, String>, E> newElement) {
		for (Map<String, String> elementMap : elementMaps) {
			E baseElement = newElement.apply(applyVariable(elementMap, scenarioVariable));
			if (baseElement.isNotIgnoreRow()) {
				this.baseElements.add(baseElement);
			}
		}
	}

	// Only Support Element Single Value
	public Map<String, String> applyVariable(Map<String, String> elementMap, E scenarioVariable) {
		if (scenarioVariable == null) {
			return elementMap;
		}
		if (scenarioVariable.dataMap().isEmpty()) {
			return elementMap;
		}
		Map<String, String> maps = Maps.newHashMap(elementMap);
		for (Entry<String, String> singleVar : scenarioVariable.dataMap().entrySet()) {
			String variableName = "$" + singleVar.getKey();
			String variableValue = singleVar.getValue();
			for (Entry<String, String> e : maps.entrySet()) {
				if (Objects.equals(variableName, e.getValue())) {
					e.setValue(variableValue);
				}
			}
		}
		return maps;
	}

	// -----------------------------
	// ----- public Non-Static Methods
	// -----------------------------

	public void convert(Map<String, Function<String, ?>> funcName2FunctionMap) {
		Set<String> fieldNames = this.getElementKeys();
		for (String fieldName : fieldNames) {
			if (fieldName.contains("@")) {
				String[] fieldNameArray = fieldName.split("@");
				String newFieldName = fieldNameArray[0];
				String functionName;
				if (fieldNameArray.length == 1) {
					functionName = newFieldName;
				} else {
					functionName = fieldNameArray[1];
				}
				this.convert(fieldName, newFieldName, funcName2FunctionMap.get(functionName));
			}
		}
	}

	public ElementData<E> convert(String oldKey, String newKey, Function<String, ?> valueConverter) {
		if (!this.getElementKeys().contains(oldKey)) {
			return this;
		}
		for (E baseElement : baseElements) {
			baseElement.convert(oldKey, newKey, valueConverter);
		}
		return this;
	}

	public List<E> getElements() {
		return baseElements;
	}

	public <T> List<T> getElements(Supplier<T> elementCreator) {
		List<T> values = Lists.newArrayList();
		for (E baseElement : baseElements) {
			T target = elementCreator.get();
			baseElement.putValuesTo(target);
			values.add(target);
		}
		return values;
	}

	public <T> List<T> getElements(Class<T> clazz) {
		return getElements(() -> ElementUtils.newInstance(clazz));
	}

	public E getOnlyElement() {
		return Iterables.getOnlyElement(baseElements, null);
	}

	public <T> T getOnlyElement(Class<T> clazz) {
		E baseElement = this.getOnlyElement();
		T t = ElementUtils.newInstance(clazz);
		baseElement.putValuesTo(t);
		return t;
	}

	public boolean isEmpty() {
		return baseElements.isEmpty();
	}

	protected Set<String> getElementKeys() {
		if (baseElements.isEmpty()) {
			return Sets.newHashSet();
		}
		Set<String> keySet = Sets.newHashSet();
		for (E element : baseElements) {
			keySet.addAll(element.getKeySet());
		}
		return keySet;
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataTableWrapper [valueMaps=");
		builder.append(baseElements);
		builder.append("]");
		return builder.toString();
	}

}
