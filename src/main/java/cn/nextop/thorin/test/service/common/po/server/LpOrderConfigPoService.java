package cn.nextop.thorin.test.service.common.po.server;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.common.CompanyAdministrator;
import cn.nextop.thorin.core.common.orm.po.LpOrderConfigPo;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;

@Service
public class LpOrderConfigPoService extends AbstractServerPoService<Integer, LpOrderConfigPo, HasLpSymbolId> {

	@Autowired
	protected CompanyAdministrator companyAdministrator;

	@Override
	protected Integer getId(LpOrderConfigPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Integer, HasLpSymbolId> getConverter() {
		return new Converter<Integer, HasLpSymbolId>() {
			
			@Override
			protected HasLpSymbolId doForward(Integer a) {
				return LpSymbolId.toLpSymbolId(a);
			}
			
			@Override
			protected Integer doBackward(HasLpSymbolId b) {
				return b.lpSymbolId();
			}
		};
	}

	@Override
	protected LpOrderConfigPo doGetByUniqueKey(HasLpSymbolId uniqueKey) {
		return companyAdministrator.getLpOrderConfig(uniqueKey.lpSymbolId());
	}

	@Override
	protected Map<Integer, LpOrderConfigPo> update(Map<Integer, LpOrderConfigPo> valueMap) {
		return companyAdministrator.updateLpOrderConfigs(valueMap);
	}

}
