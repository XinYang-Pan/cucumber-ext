package cn.nextop.thorin.test.service.core;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import cn.nextop.thorin.common.util.datetime.TradingDate;
import cn.nextop.thorin.core.common.domain.glossary.decimal.Price;
import cn.nextop.thorin.core.common.glossary.BidAsk;
import cn.nextop.thorin.core.common.glossary.PricingBandStatus;
import cn.nextop.thorin.core.common.glossary.PricingBandType;
import cn.nextop.thorin.core.common.orm.po.LpSymbolPo;
import cn.nextop.thorin.core.pricing.domain.entity.LpQuote;
import cn.nextop.thorin.core.pricing.domain.entity.impl.LpQuoteImpl;
import cn.nextop.thorin.test.service.common.AbstractService;
import cn.nextop.thorin.test.util.LpQuoteUtils;
import cn.nextop.thorin.test.vo.id.obj.compound.LpSymbolId;
import cn.nextop.thorin.test.vo.quote.LpQuoteWrapper;
import jxl.Sheet;
import jxl.Workbook;

@Service
@Lazy
public class LpQuoteWrapperService extends AbstractService {

	public List<LpQuoteWrapper> getLpQuoteWrappers(String pathname) {
		return this.getLpQuoteWrappers(new File(pathname));
	}

	public List<LpQuoteWrapper> getLpQuoteWrappers(File file) {
		try {
			Workbook workbook = Workbook.getWorkbook(file);
			Sheet[] sheets = workbook.getSheets();
			Sheet sheet = sheets[0];
			// 
			int totalRows = sheet.getRows();
			// start point row-3 column-1
			List<LpQuoteWrapper> wrappers = Lists.newArrayList();
			for (int row = 3; row < totalRows; row++) {
				String itName = sheet.getCell(1, row).getContents();
				if (StringUtils.hasText(itName)) {
					String symbolName = sheet.getCell(2, row).getContents();
					String delay = sheet.getCell(3, row).getContents();
					// 
					LpSymbolId lpSymbolId = commonService.getDefaultLpSymbolId(itName, symbolName);
					LpSymbolPo lpSymbolPo = lpSymbolPoService.getByUniqueKey(lpSymbolId);
					TradingDate tradingDate = commonService.currentTradingDate();
					LpQuoteImpl lpQuoteImpl = LpQuoteUtils.buildLpQuoteImpl(lpSymbolPo, tradingDate);
					LpQuoteWrapper lpQuoteWrapper = new LpQuoteWrapper();
					lpQuoteWrapper.setLpQuote(lpQuoteImpl);
					if (!StringUtils.isEmpty(delay)) {
						lpQuoteWrapper.setDelay(Long.parseLong(delay));
					}
					wrappers.add(lpQuoteWrapper);
				}
				// 
				String bidAskStr = sheet.getCell(4, row).getContents();
				if (StringUtils.isEmpty(bidAskStr)) {
					break;
				}
				String typeStr = sheet.getCell(5, row).getContents();
				String priceStr = sheet.getCell(6, row).getContents();
				String volumeStr = sheet.getCell(7, row).getContents();
				String recoveredStr = sheet.getCell(8, row).getContents();
				String suspendedStr = sheet.getCell(9, row).getContents();
				String duplicatedStr = sheet.getCell(10, row).getContents();
				String activeStr = sheet.getCell(11, row).getContents();
				// 
				LpQuoteWrapper lpQuoteWrapper = Iterables.getLast(wrappers);
				LpQuote lpQuote = lpQuoteWrapper.getLpQuote();
				LpSymbolPo lpSymbolPo = lpSymbolPoService.get(lpQuote.getLpSymbolId());
				// 
				BidAsk side = this.getSide(bidAskStr);
				PricingBandType bandType = this.getBandType(typeStr);
				Price price = this.getPrice(priceStr);
				Integer volume = this.getVolume(volumeStr);
				PricingBandStatus status = new PricingBandStatus();
				status.setRecovered(BooleanUtils.toBoolean(recoveredStr));
				status.setSuspended(BooleanUtils.toBoolean(suspendedStr));
				status.setDuplicate(BooleanUtils.toBoolean(duplicatedStr));
				status.setActive(BooleanUtils.toBoolean(activeStr));
				// 
				LpQuoteUtils.buildLpBandImpl(lpQuote, lpSymbolPo, side, bandType, price, volume, status, lpQuoteWrapper.getMessageId());
			}
			// 
			return wrappers;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private BidAsk getSide(String bidAskStr) {
		return BidAsk.valueOf(bidAskStr);
	}

	private Integer getVolume(String volumnStr) {
		if (StringUtils.isEmpty(volumnStr)) {
			return null;
		}
		return Integer.valueOf(volumnStr);
	}

	private PricingBandType getBandType(String bandStr) {
		if (StringUtils.isEmpty(bandStr)) {
			return null;
		}
		return PricingBandType.valueOf(bandStr);
	}

	private Price getPrice(String priceStr) {
		if (StringUtils.isEmpty(priceStr)) {
			return null;
		}
		return new Price(priceStr.trim());
	}

}
