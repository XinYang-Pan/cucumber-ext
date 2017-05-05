package cn.nextop.thorin.test.service.holder;

import static com.google.common.collect.Lists.transform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.collection.map.LongHashMap;
import cn.nextop.thorin.core.holding.HoldingStrategyAdministrator;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.BindBooksRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.BindBooksResponse;
import cn.nextop.thorin.core.holding.orm.po.BookPo;
import cn.nextop.thorin.core.holding.orm.po.BooksConfigPo;
import cn.nextop.thorin.core.holding.orm.po.BooksPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.util.HolderTestUtils;

@Service
@Lazy
public class BooksBindsService extends AbstractService {
	@Autowired
	private HoldingStrategyAdministrator admin;

	public List<BooksConfigPo> getBooksConfigPos(int booksId) {
		LongHashMap<BooksConfigPo> booksConfigs = admin.getBooksConfigs(booksId);
		return Lists.newArrayList(booksConfigs.values());
	}
	
	public BooksPo bindBooks(BooksPo booksPo, List<BookPo> bookPos) {
		List<BooksConfigPo> configs = Lists.newArrayList(transform(bookPos, bookPo -> HolderTestUtils.buildBooksConfigPo(booksPo, bookPo.getId())));
		BindBooksRequest request = new BindBooksRequest(booksPo.getCompanyId());
		request.setBooks(booksPo);
		request.setBooksConfigs(configs);
		log.debug("BindBooksRequest = {}", request);
		BindBooksResponse response = admin.bind(request);
		log.debug("BindBooksResponse = {}", response);
		ThorinAssertions.assertThat(response).isSuccessful();
		return Iterators.getOnlyElement(response.books());
	}

}
