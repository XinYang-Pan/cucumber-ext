package cn.nextop.thorin.test.service.common.po.server;

import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.common.CompanyAdministrator;
import cn.nextop.thorin.core.common.orm.po.BkSymbolPo;
import cn.nextop.thorin.test.vo.id.has.compound.HasBkSymbolId;
import cn.nextop.thorin.test.vo.id.obj.compound.BkSymbolId;

@Service
public class BkSymbolPoService extends AbstractServerPoService<Integer, BkSymbolPo, HasBkSymbolId> {

	@Autowired
	protected CompanyAdministrator companyAdministrator;

	@Override
	protected Integer getId(BkSymbolPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Integer, HasBkSymbolId> getConverter() {
		return new Converter<Integer, HasBkSymbolId>() {

			@Override
			protected HasBkSymbolId doForward(Integer a) {
				return BkSymbolId.toBkSymbolId(a);
			}

			@Override
			protected Integer doBackward(HasBkSymbolId b) {
				return b.bkSymbolId();
			}
		};
	}

	@Override
	protected BkSymbolPo doGetByUniqueKey(HasBkSymbolId uniqueKey) {
		return companyAdministrator.getBkSymbol(uniqueKey.bkSymbolId());
	}

	@Override
	protected Map<Integer, BkSymbolPo> update(Map<Integer, BkSymbolPo> valueMap) {
		return companyAdministrator.updateBkSymbols(valueMap);
	}

	public List<BkSymbolPo> getAll() {
		return newArrayList(transform(companyAdministrator.getBkSymbols().values(), BkSymbolPo::copy));
	}

}
