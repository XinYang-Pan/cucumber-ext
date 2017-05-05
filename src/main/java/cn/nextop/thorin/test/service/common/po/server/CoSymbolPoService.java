package cn.nextop.thorin.test.service.common.po.server;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Converter;

import cn.nextop.thorin.core.common.CompanyAdministrator;
import cn.nextop.thorin.core.common.orm.po.CoSymbolPo;
import cn.nextop.thorin.test.vo.id.has.compound.HasCoSymbolId;
import cn.nextop.thorin.test.vo.id.obj.compound.CoSymbolId;

@Service
public class CoSymbolPoService extends AbstractServerPoService<Integer, CoSymbolPo, HasCoSymbolId> {

	@Autowired
	protected CompanyAdministrator companyAdministrator;

	@Override
	protected Integer getId(CoSymbolPo po) {
		return po.getId();
	}

	@Override
	protected Converter<Integer, HasCoSymbolId> getConverter() {
		return new Converter<Integer, HasCoSymbolId>() {

			@Override
			protected HasCoSymbolId doForward(Integer a) {
				return CoSymbolId.toCoSymbolId(a);
			}

			@Override
			protected Integer doBackward(HasCoSymbolId b) {
				return b.coSymbolId();
			}
		};
	}

	@Override
	protected CoSymbolPo doGetByUniqueKey(HasCoSymbolId uniqueKey) {
		return companyAdministrator.getCoSymbol(uniqueKey.coSymbolId());
	}

	@Override
	protected Map<Integer, CoSymbolPo> update(Map<Integer, CoSymbolPo> valueMap) {
		return companyAdministrator.updateCoSymbols(valueMap);
	}

}
