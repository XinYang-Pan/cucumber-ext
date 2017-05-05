//package cn.nextop.thorin.test.core.assertj.asserts.response;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//import java.util.Map;
//
//import com.google.common.collect.Maps;
//
//import cn.nextop.thorin.core.dealing.domain.entity.LpRate;
//import cn.nextop.thorin.core.pricing.domain.dom.pipeline.response.impl.AcquireLqtyResponse;
//
//public class AcquireLqtyResponseAssert extends ResponseAssert<AcquireLqtyResponseAssert, AcquireLqtyResponse> {
//
//	public AcquireLqtyResponseAssert(AcquireLqtyResponse actual) {
//		super(actual, AcquireLqtyResponseAssert.class);
//	}
//
//	public AcquireLqtyResponseAssert lpRatesIs(List<? extends LpRate> lpRates) {
//		this.isNotNull();
//		// same size
//		assertThat(actual.getRates()).as("ref - %s", actual).hasSameSizeAs(lpRates);
//		// 
//		Map<Long, LpRate> actualLpRates = Maps.uniqueIndex(actual.getRates(), LpRate::getBandId);
//		Map<Long, ? extends LpRate> expectedLpRates = Maps.uniqueIndex(lpRates, LpRate::getBandId);
//		for (LpRate actualLpRate : actualLpRates.values()) {
//			LpRate expectedLpRate = expectedLpRates.get(actualLpRate.getBandId());
//			assertThat(actualLpRate).isEqualToIgnoringGivenFields(expectedLpRate, "quoteId");
//		}
//		return this;
//	}
//
//}
