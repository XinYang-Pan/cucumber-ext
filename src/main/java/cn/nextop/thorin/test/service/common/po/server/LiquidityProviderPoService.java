package cn.nextop.thorin.test.service.common.po.server;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.common.CompanyAdministrator;
import cn.nextop.thorin.core.common.orm.po.LiquidityProviderPo;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpId;

@Service
public class LiquidityProviderPoService extends AbstractServerPoService<Short, LiquidityProviderPo, HasLpId> {

	@Autowired
	protected CompanyAdministrator companyAdministrator;

	@Override
	protected Short getId(LiquidityProviderPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Short, HasLpId> getConverter() {
		return new Converter<Short, HasLpId>() {
			@Override
			protected HasLpId doForward(Short a) {
				return LpId.toLpId(a);
			}

			@Override
			protected Short doBackward(HasLpId b) {
				return b.lpId();
			}
		};
	}

	@Override
	protected LiquidityProviderPo doGetByUniqueKey(HasLpId uniqueKey) {
		return companyAdministrator.getLiquidityProvider(uniqueKey.lpId());
	}

	@Override
	protected Map<Short, LiquidityProviderPo> update(Map<Short, LiquidityProviderPo> valueMap) {
		return companyAdministrator.updateLiquidityProviders(valueMap);
	}

	public List<LiquidityProviderPo> getAll() {
		return newArrayList(transform(companyAdministrator.getLiquidityProviders().values(), LiquidityProviderPo::copy));
	}

}
