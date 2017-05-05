package cn.nextop.thorin.test.service.common.po.pricing;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;

import cn.nextop.thorin.common.glossary.Versionable;
import cn.nextop.thorin.common.persistence.jpa.PersistentObject;
import cn.nextop.thorin.core.pricing.PricingQuotationAdministrator;
import cn.nextop.thorin.core.pricing.domain.pipeline.request.impl.ModifyQuotationRequest;
import cn.nextop.thorin.core.pricing.domain.pipeline.response.impl.ModifyQuotationResponse;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.po.IdCalculableService;
import cn.nextop.thorin.test.vo.id.has.single.HasCoId;
import cn.nextop.thorin.test.vo.id.has.single.HasSymbolId;

public abstract class AbstractConfigPoService<K, P extends Versionable & PersistentObject, U extends HasCoId & HasSymbolId> extends IdCalculableService<K, P, U> {

	@Autowired
	protected PricingQuotationAdministrator pricingQuotationAdministrator;

	@Override
	protected final Map<K, P> update(Map<K, P> valueMap) {
		Map<Pair<Byte, Short>, Collection<P>> map = Multimaps.index(valueMap.values(), this::getShadeId).asMap();
		List<P> returnList = Lists.newArrayList();
		for (Entry<Pair<Byte, Short>, Collection<P>> e : map.entrySet()) {
			Pair<Byte, Short> key = e.getKey();
			Collection<P> value = e.getValue();
			// 
			ModifyQuotationRequest request = new ModifyQuotationRequest(key.getValue0(), key.getValue1());
			this.setRequest(request, Lists.newArrayList(value));
			ModifyQuotationResponse response = pricingQuotationAdministrator.modify(request);
			// 
			ThorinAssertions.assertThat(response).isSuccessful();
			Iterators.addAll(returnList, this.getResponse(response));
		}
		Map<K, P> returnMap = Maps.uniqueIndex(returnList, this::getId);
		ThorinAssertions.assertThat(returnMap.size()).isEqualTo(valueMap.size());
		return returnMap;
	}

	private Pair<Byte, Short> getShadeId(P po) {
		U uniqueKey = this.getConverter().convert(this.getId(po));
		return Pair.with(uniqueKey.coId(), uniqueKey.symbolId());
	}

	protected abstract void setRequest(ModifyQuotationRequest request, List<P> value);

	protected abstract Iterator<P> getResponse(ModifyQuotationResponse response);

}
