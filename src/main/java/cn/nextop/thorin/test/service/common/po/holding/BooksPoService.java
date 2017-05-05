package cn.nextop.thorin.test.service.common.po.holding;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.Objects;
import cn.nextop.thorin.common.util.collection.map.IntHashMap;
import cn.nextop.thorin.core.holding.domain.entity.HoldingStrategy;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.DeleteBooksRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.ModifyStrategyRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.DeleteBooksResponse;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.ModifyStrategyResponse;
import cn.nextop.thorin.core.holding.orm.po.BooksPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;

@Service
public class BooksPoService extends AbstractHoldingService<BooksPo> {

	@Override
	protected Integer getId(BooksPo po) {
		return po.getId();
	}

	@Override
	protected BooksPo doGet(Integer id) {
		return admin.getBooks(id);
	}

	@Override
	protected BooksPo doUpdate(BooksPo input) {
		ModifyStrategyRequest request = new ModifyStrategyRequest(input.getCompanyId());
		request.setBooks(Lists.newArrayList(input));
		ModifyStrategyResponse response = admin.modify(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Iterators.getOnlyElement(response.books());
	}

	public void delete(BooksPo input) {
		Assert.notNull(input);
		// 
		DeleteBooksRequest request = new DeleteBooksRequest(input.getCompanyId());
		request.setBooks(input);
		DeleteBooksResponse response = admin.delete(request);
		ThorinAssertions.assertThat(response).isSuccessful();
	}

	public void deleteAll(byte coId) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<BooksPo> map = strategy.getBooks();
		// 
		for (BooksPo input : map.values()) {
			this.delete(input);
		}
	}

	public BooksPo get(byte coId, String abbr) {
		HoldingStrategy strategy = admin.getStrategy(coId);
		IntHashMap<BooksPo> map = strategy.getBooks();
		// 
		Collection<BooksPo> collection = Collections2.filter(map.values(), t -> Objects.isEquals(t.getAbbreviation(), abbr));
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		} else {
			return Iterables.getOnlyElement(collection);
		}
	}

}
