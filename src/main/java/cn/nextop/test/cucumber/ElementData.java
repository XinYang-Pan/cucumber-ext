package cn.nextop.test.cucumber;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.assertj.core.util.Sets;
import org.springframework.beans.BeanUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cucumber.api.DataTable;

public class ElementData {
	// 
	private final List<Element> elements = Lists.newArrayList();

	// -----------------------------
	// ----- Constructors
	// -----------------------------

	private ElementData(List<Map<String, String>> elementMaps, Element scenarioVariable) {
		for (Map<String, String> elementMap : elementMaps) {
			Element element = new Element(applyVariable(elementMap, scenarioVariable));
			if (element.isNotIgnoreRow()) {
				this.elements.add(element);
			}
		}
	}
	
	// Only Support Element Single Value
	private Map<String, String> applyVariable(Map<String, String> elementMap, Element scenarioVariable) {
		if (scenarioVariable == null) {
			return elementMap;
		}
		if (scenarioVariable.dataMap().isEmpty()) {
			return elementMap;
		}
		Map<String, String> maps = Maps.newHashMap(elementMap);
		for (Entry<String, String> singleVar : scenarioVariable.dataMap().entrySet()) {
			String variableName = "$"+singleVar.getKey();
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
	// ----- Static Methods
	// -----------------------------
	public static ElementData of(DataTable dataTable) {
		return new ElementData(toMapList(dataTable), null);
	}
	
	public static ElementData of(DataTable dataTable, Element scenarioVariable) {
		return new ElementData(toMapList(dataTable), scenarioVariable);
	}

	public static ElementData of(List<Map<String, String>> valueMaps) {
		return new ElementData(valueMaps, null);
	}

	private static List<Map<String, String>> toMapList(DataTable dataTable) {
		return dataTable.asMaps(String.class, String.class);
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

	public ElementData convert(String oldKey, String newKey, Function<String, ?> valueConverter) {
		if (!this.getElementKeys().contains(oldKey)) {
			return this;
		}
		for (Element element : elements) {
			element.convert(oldKey, newKey, valueConverter);
		}
		return this;
	}

	public List<Element> getElements() {
		return elements;
	}

	public <T> List<T> getElements(Supplier<T> elementCreator) {
		List<T> values = Lists.newArrayList();
		for (Element element : elements) {
			T target = elementCreator.get();
			element.putValuesTo(target);
			values.add(target);
		}
		return values;
	}

	public <T> List<T> getElements(Class<T> clazz) {
		return getElements(() -> this.newInstance(clazz));
	}

	public Element getOnlyElement() {
		return Iterables.getOnlyElement(elements, null);
	}

	public <T> T getOnlyElement(Class<T> clazz) {
		Element element = this.getOnlyElement();
		T t = newInstance(clazz);
		element.putValuesTo(t);
		return t;
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	// -----------------------------
	// ----- private
	// -----------------------------
	
	private Set<String> getElementKeys() {
		if (elements.isEmpty()) {
			return Sets.newHashSet();
		}
		return elements.get(0).getKeySet();
	}

	private <T> T newInstance(Class<T> clazz) {
		T t = BeanUtils.instantiate(clazz);
		return t;
	}
	
	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataTableWrapper [valueMaps=");
		builder.append(elements);
		builder.append("]");
		return builder.toString();
	}

}
