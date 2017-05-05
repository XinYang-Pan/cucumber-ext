package cn.nextop.thorin.test.service.holder;

import static com.google.common.base.Predicates.not;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.collection.map.LongHashMap;
import cn.nextop.thorin.core.holding.HoldingStrategyAdministrator;
import cn.nextop.thorin.core.holding.domain.pipeline.request.impl.BindBookRequest;
import cn.nextop.thorin.core.holding.domain.pipeline.response.impl.BindBookResponse;
import cn.nextop.thorin.core.holding.orm.po.BookPo;
import cn.nextop.thorin.core.holding.orm.po.BookQuoteConfigPo;
import cn.nextop.thorin.test.core.assertj.hard.ThorinAssertions;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.util.HolderTestUtils;

@Service
@Lazy
public class BookBindsService extends AbstractService {
	@Autowired
	private HoldingStrategyAdministrator admin;

	public List<BookQuoteConfigPo> getBookQuoteConfigPos(int bookId) {
		LongHashMap<BookQuoteConfigPo> bookQuoteConfigs = admin.getBookQuoteConfigs(bookId);
		return Lists.newArrayList(bookQuoteConfigs.values());
	}

	public BookPo bindBook(BookPo bookPo, List<String> itNames) {
		return bindBook1(bookPo, itNames).getValue0();
	}

	private Pair<BookPo, List<BookQuoteConfigPo>> bindBook1(BookPo bookPo, List<String> itNames) {
		List<Short> lpIds = Lists.transform(itNames, commonService::getDefaultLpId);
		//
		List<BookQuoteConfigPo> bookQuoteConfigPos = Lists.newArrayList(Lists.transform(lpIds, lpId -> HolderTestUtils.buildBookQuoteConfigPo(bookPo, lpId)));
		// 
		BindBookRequest request = new BindBookRequest(bookPo.getCompanyId());
		request.setBook(bookPo);
		request.setBookQuoteConfigs(bookQuoteConfigPos);
		log.debug("BindBookRequest = {}", request);
		BindBookResponse response = admin.bind(request);
		ThorinAssertions.assertThat(response).isSuccessful();
		Iterator<BookQuoteConfigPo> bookQuoteConfigs = Iterators.filter(response.bookQuoteConfigs(), not(BookQuoteConfigPo::isDeleted));
		log.debug("BindBookResponse = {}", response);
		// 
		ThorinAssertions.assertThat(response).as("ref - %s", response).isSuccessful();
		assertThat(response.book()).hasSize(1);
		assertThat(bookQuoteConfigs).hasSameSizeAs(itNames);
		return Pair.with(response.book().next(), Lists.newArrayList(bookQuoteConfigs));
	}

}
