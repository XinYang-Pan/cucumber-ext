package cn.nextop.thorin.test.core.cucumber.converter;

import static java.lang.String.format;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cucumber.deps.com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public abstract class AbstractTypeConfigConverter<T, C> extends AbstractSingleValueConverter {

	private Map<String, T> abbr2TypeMap = Maps.newHashMap();

	public AbstractTypeConfigConverter() {
		this.initMap(abbr2TypeMap);
	}

	protected abstract void initMap(Map<String, T> abbr2TypeMap);

	protected abstract C newConfig();

	protected abstract void toSetEnabled(C typeConfig, T type);
	
	@Override
	public C fromString(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		} else {
			C typeConfig = newConfig();
			Set<String> abbrs = Sets.newHashSet(str.split("/"));
			for (String abbr : abbrs) {
				T hedgingOrderType = abbr2TypeMap.get(abbr);
				Assert.notNull(hedgingOrderType, format("%s not valid value", abbr));
				toSetEnabled(typeConfig, hedgingOrderType);
			}
			return typeConfig;
		}
	}


}
