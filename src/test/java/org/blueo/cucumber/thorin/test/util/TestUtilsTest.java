package org.blueo.cucumber.thorin.test.util;

public class TestUtilsTest {
//
//	@Test
//	public void test_getTargetObject() throws Exception {
//		// prepare
//		LpBandImpl ask = new LpBandImpl();
//		ask.setPrice(new Price("100.000"));
//		List<LpBand> list = Lists.newArrayList(ask);
//		// test target
//		Pair<Object, Class<?>> target = ParseUtils.getFieldValueAndType(list, "0.price");
//		// assert
//		assertThat(target.getValue0()).isEqualTo(new Price("100.000"));
//		assertThat(target.getValue1()).isEqualTo(Price.class);
//	}
//
//	@Test
//	public void test_populateFields_list_object() throws Exception {
//		// prepare
//		LpBandImpl ask = new LpBandImpl();
//		ArrayList<LpBandImpl> asks = Lists.newArrayList(ask);
//		Map<String, String> map = ImmutableMap.of("0.price", "11.22");
//		// test target
//		ParseUtils.populateFields(map, asks);
//		// assert
//		assertThat(asks.get(0).getPrice()).isEqualTo(new Price("11.22"));
//	}
//
//	@Test
//	public void test_populateFields_object_list_object() throws Exception {
//		// prepare
//		LpQuoteImpl lpQuoteImpl = new LpQuoteImpl();
//		LpBandImpl ask = new LpBandImpl();
//		List<LpBand> asks = Lists.newArrayList(ask);
//		lpQuoteImpl.setAsks(asks);
//		// 
//		Map<String, String> map = ImmutableMap.of("asks.0.price", "11.22");
//		// test target
//		ParseUtils.populateFields(map, lpQuoteImpl);
//		// assert
//		assertThat(lpQuoteImpl.getAsks().get(0).getPrice()).isEqualTo(new Price("11.22"));
//	}
//
//	@Test
//	public void test_populateFields2() throws Exception {
//		// prepare
//		LpBandImpl ask = new LpBandImpl();
//		Map<String, String> map = ImmutableMap.of("price", "11.22");
//		// test target
//		ParseUtils.populateFields(map, ask);
//		// assert
//		assertThat(ask.getPrice()).isEqualTo(new Price("11.22"));
//	}
//
//	@Test
//	public void test_populateFields4() throws Exception {
//		// prepare
//		Map<String, String> parameterMap = ImmutableMap.of("executeType.stopEnabled", "true");
//		// test target
//		AllocationPo t = new AllocationPo();
//		t.setExecuteType(new TradingExecuteTypeConfig());
//		AllocationPo actual = ParseUtils.populateFields(parameterMap, t);
//		// expected
//		AllocationPo expected = new AllocationPo();
//		expected.setExecuteType(new TradingExecuteTypeConfig());
//		expected.getExecuteType().setStopEnabled(true);
//		// assert
//		assertThat(actual).isEqualTo(expected);
//	}

}
