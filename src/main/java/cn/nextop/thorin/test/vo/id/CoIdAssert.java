package cn.nextop.thorin.test.vo.id;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.springframework.util.Assert;

import cn.nextop.thorin.test.vo.id.has.single.HasCoId;

public class CoIdAssert implements HasCoId {
	private Byte coId;

	private CoIdAssert() {
		super();
	}

	private CoIdAssert(byte coId) {
		super();
		this.coId = coId;
	}
	
	public static CoIdAssert emptyCoId() {
		return new CoIdAssert();
	}
	
	public static CoIdAssert of(byte coId) {
		return new CoIdAssert(coId);
	}
	
	public byte asserts(byte companyId) {
		if (coId == null) {
			coId = companyId;
		} else {
			assertThat(coId).as("Must be same company").isEqualTo(companyId);
		}
		return this.coId;
	}
	
	public byte asserts(HasCoId hasCoId) {
		return this.asserts(hasCoId.coId());
	}
	
	public byte asserts(List<? extends HasCoId> hasCoIds) {
		Assert.notEmpty(hasCoIds);
		for (HasCoId hasCoId : hasCoIds) {
			asserts(hasCoId);
		}
		return this.coId();
	}
	
	@Override
	public byte coId() {
		return coId;
	}
	
}
