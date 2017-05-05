package cn.nextop.thorin.test.service.common.po.server;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.common.CompanyAdministrator;
import cn.nextop.thorin.core.common.orm.po.LpSymbolPo;
import cn.nextop.thorin.test.vo.id.has.compound.HasLpSymbolId;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;

@Service
public class LpSymbolPoService extends AbstractServerPoService<Integer, LpSymbolPo, HasLpSymbolId> {

	@Autowired
	protected CompanyAdministrator companyAdministrator;

	@Override
	protected Integer getId(LpSymbolPo po) {
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
	protected LpSymbolPo doGetByUniqueKey(HasLpSymbolId uniqueKey) {
		return companyAdministrator.getLpSymbol(uniqueKey.lpSymbolId());
	}

	@Override
	protected Map<Integer, LpSymbolPo> update(Map<Integer, LpSymbolPo> valueMap) {
		return companyAdministrator.updateLpSymbols(valueMap);
	}

	public List<LpSymbolPo> getAll() {
		return newArrayList(transform(companyAdministrator.getLpSymbols().values(), LpSymbolPo::copy));
	}
	
}
