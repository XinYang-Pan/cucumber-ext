package cn.nextop.thorin.test.core.cucumber;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.Iterables;

import cn.nextop.test.cucumber.IgnoreMapperWrapper;
import cn.nextop.test.cucumber.converter.EmptyToNullBooleanConverter;
import cn.nextop.test.cucumber.converter.EmptyToNullLongConverter;
import cn.nextop.test.cucumber.converter.EmptyToNullShortConverter;
import cn.nextop.thorin.test.core.cucumber.converter.HedgingOrderTypeConfigConverter;
import cn.nextop.thorin.trader.engine.gateway.support.rpc.acceptor.proto.TradingModelProto.ExecuteType;
import cucumber.api.DataTable;
import cucumber.deps.com.thoughtworks.xstream.XStream;
import cucumber.deps.com.thoughtworks.xstream.converters.javabean.JavaBeanConverter;
import cucumber.runtime.table.TableConverter;
import cucumber.runtime.xstream.LocalizedXStreams;
import cucumber.runtime.xstream.LocalizedXStreams.LocalizedXStream;

public class CucumberUtils {
	private static final LocalizedXStreams LOCALIZED_XSTREAMS = new LocalizedXStreams(Thread.currentThread().getContextClassLoader());
	public static final LocalizedXStream localizedXStream = LOCALIZED_XSTREAMS.get(Locale.US);

	static {
		// extra converters
		localizedXStream.registerConverter(new EmptyToNullShortConverter(), XStream.PRIORITY_VERY_HIGH);
		localizedXStream.registerConverter(new EmptyToNullLongConverter(), XStream.PRIORITY_VERY_HIGH);
		localizedXStream.registerConverter(new EmptyToNullBooleanConverter(), XStream.PRIORITY_VERY_HIGH);
//		localizedXStream.registerConverter(new PricingBandStatusConverter(), XStream.PRIORITY_VERY_HIGH);
		localizedXStream.registerConverter(new HedgingOrderTypeConfigConverter(), XStream.PRIORITY_VERY_HIGH);
		localizedXStream.registerConverter(new JavaBeanConverter(new IgnoreMapperWrapper(localizedXStream.getMapper())), XStream.PRIORITY_VERY_LOW);
		localizedXStream.alias("executeType", ExecuteType.class);
	}

	public static <T> T getOnlyElement(DataTable dataTable, Class<T> clazz) {
		return Iterables.getOnlyElement(getElements(dataTable, clazz));
	}

	public static <T> List<T> getElements(DataTable dataTable, Class<T> clazz) {
		TableConverter tableConverterCopy = new TableConverter(CucumberUtils.localizedXStream, null);
		DataTable dataTableCopy = new DataTable(dataTable.getGherkinRows(), tableConverterCopy);
		return dataTableCopy.asList(clazz);
	}

}
