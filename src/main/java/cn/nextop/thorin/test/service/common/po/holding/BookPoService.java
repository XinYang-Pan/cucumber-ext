package cn.nextop.thorin.test.service.common.po.holding;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.jfree.util.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.Objects;
import cn.nextop.thorin.common.util.collection.map.IntHashMap;
import cn.nextop.thorin.core.common.glossary.BookType;
import cn.nextop.thorin.core.holding.domain.entity.HoldingStrategy;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.DeleteBookRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.ModifyStrategyRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.DeleteBookResponse;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.ModifyStrategyResponse;
import cn.nextop.thorin.core.holding.orm.po.BookPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class BookPoService extends AbstractHoldingService<BookPo> {

	@Override
	protected Integer getId(BookPo po) {
		return po.getId();
	}

	@Override
	protected BookPo doGet(Integer id) {
		return admin.getBook(id);
	}

	private ModifyStrategyResponse requestUpdate(BookPo input) {
		ModifyStrategyRequest request = new ModifyStrategyRequest(input.getCompanyId());
		request.setBook(Lists.newArrayList(input));
		ModifyStrategyResponse response = admin.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return response;
	}
	
	@Override
	protected BookPo doUpdate(BookPo input) {
		ModifyStrategyResponse response = requestUpdate(input);
		return Iterators.getOnlyElement(response.book());
	}

	public void delete(BookPo bookPo) {
		Assert.notNull(bookPo);
		// 
		DeleteBookRequest deleteBookRequest = new DeleteBookRequest(bookPo.getCompanyId());
		deleteBookRequest.setBook(bookPo);
		DeleteBookResponse deleteBookResponse = admin.delete(deleteBookRequest);
		ThorinAssertions.assertThat(deleteBookResponse).isSuccessful();
	}

	public void deleteAll(byte coId) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<BookPo> book = strategy.getBook();
		// 
		for (BookPo bookPo : book.values()) {
			this.delete(bookPo);
		}
	}

	public BookPo getForDefaultCompanyId(String abbr) {
		return this.get(this.commonService.getDefaultCompanyId(), abbr);
	}

	public BookPo get(byte coId, String abbr) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<BookPo> bookMap = strategy.getBook();
		// 
		Collection<BookPo> collection = Collections2.filter(bookMap.values(), bookPo -> Objects.isEquals(bookPo.getAbbreviation(), abbr));
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		} else {
			return Iterables.getOnlyElement(collection);
		}
	}

	public int getBookId(byte coId, String abbr) {
		return this.get(coId, abbr).getId();
	}

	public BookPo updateToDefault(BookPo input) {
		if (input.getType() == BookType.DEFAULT) {
			Log.warn("Already Default.");
			return input;
		}
		input.setType(BookType.DEFAULT);
		ModifyStrategyResponse response = this.requestUpdate(input);
		assertThat(response.book()).hasSize(2);
		Iterator<BookPo> filtered = Iterators.filter(response.book(), bookPo -> BookType.DEFAULT.equals(bookPo.getType()));
		BookPo result = Iterators.getOnlyElement(filtered);
		this.updateAssert(result, input);
		return result;
	}

}
