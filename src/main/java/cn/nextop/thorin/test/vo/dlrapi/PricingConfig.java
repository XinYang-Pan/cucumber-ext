package cn.nextop.thorin.test.vo.dlrapi;

import static com.google.common.collect.Lists.transform;

import java.util.List;
import java.util.Set;

import org.assertj.core.util.Sets;

import com.google.common.base.Suppliers;

import cn.nextop.thorin.api.dealer.proto.PricingServiceProtoV1.PricingEvent;
import cn.nextop.thorin.api.dealer.service.pricing.v1.model.BkDeviationConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.pricing.v1.model.BkPickingConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.pricing.v1.model.BkSymbolConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.pricing.v1.model.BkTuningConfigV1Factory;
import cn.nextop.thorin.api.dealer.service.pricing.v1.model.LpDeviationConfigV1Factory;
import cn.nextop.thorin.core.pricing.orm.po.BkDeviationConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.BkPickingConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.BkSymbolConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.BkTuningConfigPo;
import cn.nextop.thorin.core.pricing.orm.po.LpDeviationConfigPo;

public class PricingConfig {
	private List<BkDeviationConfigPo> bkDeviationConfigPos;
	private List<BkPickingConfigPo> bkPickingConfigPos;
	private List<BkSymbolConfigPo> bkSymbolConfigPos;
	private List<BkTuningConfigPo> bkTuningConfigPos;
	private List<LpDeviationConfigPo> lpDeviationConfigPos;

	public static PricingConfig from(PricingEvent pricingEvent) {
		PricingConfig pricingConfig = new PricingConfig();
		pricingConfig.setBkDeviationConfigPos(transform(pricingEvent.getBkDeviationsList(), BkDeviationConfigV1Factory::toBkDeviationConfig));
		pricingConfig.setBkPickingConfigPos(transform(pricingEvent.getBkPickingsList(), BkPickingConfigV1Factory::toBkPickingConfig));
		pricingConfig.setBkSymbolConfigPos(transform(pricingEvent.getBkSymbolsList(), BkSymbolConfigV1Factory::toBkSymbolConfig));
		pricingConfig.setBkTuningConfigPos(transform(pricingEvent.getBkTuningsList(), BkTuningConfigV1Factory::toBkTuningConfig));
		pricingConfig.setLpDeviationConfigPos(transform(pricingEvent.getLpDeviationsList(), LpDeviationConfigV1Factory::toLpDeviationConfig));
		return pricingConfig;
	}

	public Set<Short> getBrokerIds() {
		return Suppliers.memoize(this::doGetBrokerIds).get();
	}

	private Set<Short> doGetBrokerIds() {
		Set<Short> brokerIds = Sets.newHashSet();
		brokerIds.addAll(transform(bkDeviationConfigPos, BkDeviationConfigPo::getBrokerId));
		brokerIds.addAll(transform(bkPickingConfigPos, BkPickingConfigPo::getBrokerId));
		brokerIds.addAll(transform(bkSymbolConfigPos, BkSymbolConfigPo::getBrokerId));
		brokerIds.addAll(transform(bkTuningConfigPos, BkTuningConfigPo::getBrokerId));
		return brokerIds;
	}

	public Set<Short> getLpIds() {
		return Suppliers.memoize(this::doGetLpIds).get();
	}

	private Set<Short> doGetLpIds() {
		Set<Short> lpIds = Sets.newHashSet();
		lpIds.addAll(transform(lpDeviationConfigPos, LpDeviationConfigPo::getLpId));
		return lpIds;
	}

	// -----------------------------
	// ----- Get Set ToString HashCode Equals
	// -----------------------------

	public String toIdsString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PricingConfig [\n\tbkDeviationConfigPos=");
		builder.append(transform(bkDeviationConfigPos, BkDeviationConfigPo::getId));
		builder.append(", \n\tbkPickingConfigPos=");
		builder.append(transform(bkPickingConfigPos, BkPickingConfigPo::getId));
		builder.append(", \n\tbkSymbolConfigPos=");
		builder.append(transform(bkSymbolConfigPos, BkSymbolConfigPo::getId));
		builder.append(", \n\tbkTuningConfigPos=");
		builder.append(transform(bkTuningConfigPos, BkTuningConfigPo::getId));
		builder.append(", \n\tlpDeviationConfigPos=");
		builder.append(transform(lpDeviationConfigPos, LpDeviationConfigPo::getId));
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PricingConfig [\n\tbkDeviationConfigPos=");
		builder.append(bkDeviationConfigPos);
		builder.append(", \n\tbkPickingConfigPos=");
		builder.append(bkPickingConfigPos);
		builder.append(", \n\tbkSymbolConfigPos=");
		builder.append(bkSymbolConfigPos);
		builder.append(", \n\tbkTuningConfigPos=");
		builder.append(bkTuningConfigPos);
		builder.append(", \n\tlpDeviationConfigPos=");
		builder.append(lpDeviationConfigPos);
		builder.append("]");
		return builder.toString();
	}

	public List<BkDeviationConfigPo> getBkDeviationConfigPos() {
		return bkDeviationConfigPos;
	}

	public void setBkDeviationConfigPos(List<BkDeviationConfigPo> bkDeviationConfigPos) {
		this.bkDeviationConfigPos = bkDeviationConfigPos;
	}

	public List<BkPickingConfigPo> getBkPickingConfigPos() {
		return bkPickingConfigPos;
	}

	public void setBkPickingConfigPos(List<BkPickingConfigPo> bkPickingConfigPos) {
		this.bkPickingConfigPos = bkPickingConfigPos;
	}

	public List<BkSymbolConfigPo> getBkSymbolConfigPos() {
		return bkSymbolConfigPos;
	}

	public void setBkSymbolConfigPos(List<BkSymbolConfigPo> bkSymbolConfigPos) {
		this.bkSymbolConfigPos = bkSymbolConfigPos;
	}

	public List<BkTuningConfigPo> getBkTuningConfigPos() {
		return bkTuningConfigPos;
	}

	public void setBkTuningConfigPos(List<BkTuningConfigPo> bkTuningConfigPos) {
		this.bkTuningConfigPos = bkTuningConfigPos;
	}

	public List<LpDeviationConfigPo> getLpDeviationConfigPos() {
		return lpDeviationConfigPos;
	}

	public void setLpDeviationConfigPos(List<LpDeviationConfigPo> lpDeviationConfigPos) {
		this.lpDeviationConfigPos = lpDeviationConfigPos;
	}

}
