package com.jpmorgan.test.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jpmorgan.test.exceptions.InvalidTradeDataException;
import com.jpmorgan.test.model.Currency;
import com.jpmorgan.test.model.Trade;
import com.jpmorgan.test.model.TradeType;
import com.jpmorgan.test.repository.TradeRepository;

public class ReportServiceImplTest {

	private static TradeRepository tradeRepo;

	/** Reporting service instance */
	private ReportServiceImpl reportService = new ReportServiceImpl();

	@BeforeClass
	public static void setUP() {
		tradeRepo = TradeRepository.getInstance();
		TradeRepository tradeRepo = TradeRepository.getInstance();
		List<Integer> nonWorkingDays = Arrays.asList(Calendar.FRIDAY,
				Calendar.SATURDAY);

		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		// Add the default settlement data as +1
		c.add(Calendar.DATE, 1);

		Date settlementDate = c.getTime();
		try {
			tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0.5,
					new Currency("SGP", "Singapore Dollars"), 200, 100, now,
					settlementDate));
			tradeRepo.addTrade(new Trade("Y", TradeType.SELL, 0.20,
					new Currency("AED", "UAE Dirham", nonWorkingDays) {
					}, 450, 150));
			tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0.5,
					new Currency("USD", "US Dollars"), 200, 200));
			tradeRepo.addTrade(new Trade("Z", TradeType.SELL, 0.5,
					new Currency("GBP", "British Pound"), 200, 250));
			tradeRepo.addTrade(new Trade("bar", TradeType.BUY, 0.5,
					new Currency("AUD", "Australian dollar"), 200, 180));
			tradeRepo.addTrade(new Trade("M", TradeType.SELL, 0.5,
					new Currency("EUR", "Euro"), 200, 100.25, settlementDate,
					settlementDate));
			tradeRepo.addTrade(new Trade("Y", TradeType.BUY, 0.20,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 300, 150));
			tradeRepo.addTrade(new Trade("foo", TradeType.SELL, 0.10,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 10, 150, now, settlementDate));
			tradeRepo.addTrade(new Trade("bar", TradeType.BUY, 0.60,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 10, 150));
			tradeRepo.addTrade(new Trade("foo", TradeType.SELL, 0.20,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 300, 150));

		} catch (InvalidTradeDataException ex) {
			System.out.println("The trade data passed in invalid");
		}

	}

	@Test
	public void testFilterTestBuy() {
		List<Trade> tradeList = tradeRepo.getTradesBySettlementDate(new Date());
		List<Trade> filterTradeList = reportService.getTradeListByType(
				tradeList, TradeType.BUY);
		Assert.assertEquals(4, filterTradeList.size());

	}

	@Test
	public void testFilterTestSell() {
		List<Trade> tradeList = tradeRepo.getTradesBySettlementDate(new Date());
		List<Trade> filterTradeList = reportService.getTradeListByType(
				tradeList, TradeType.SELL);
		Assert.assertEquals(3, filterTradeList.size());

	}

	@Test
	public void testEntityRankUSDAmountBuy() {
		List<Trade> tradeList = tradeRepo.getTradesBySettlementDate(new Date());
		List<Trade> filterTradeList = reportService.getTradeListByType(
				tradeList, TradeType.BUY);
		LinkedHashMap<String, Double> entityRankMap = reportService
				.getEntityMapByRank(filterTradeList);
		Assert.assertEquals(3, entityRankMap.size());
		Assert.assertEquals(3, entityRankMap.size());
		Assert.assertTrue(entityRankMap.containsKey("foo"));
		Assert.assertEquals(20000.0, entityRankMap.get("foo"), 0.0);
		Assert.assertTrue(entityRankMap.containsKey("bar"));
		Assert.assertEquals(18900.0, entityRankMap.get("bar"), 0.0);
		Assert.assertTrue(entityRankMap.containsKey("Y"));
		Assert.assertEquals(9000.0, entityRankMap.get("Y"), 0.0);

	}

	@Test
	public void testEntityRankUSDAmountSell() {
		List<Trade> tradeList = tradeRepo.getTradesBySettlementDate(new Date());
		List<Trade> filterTradeList = reportService.getTradeListByType(
				tradeList, TradeType.SELL);
		LinkedHashMap<String, Double> entityRankMap = reportService
				.getEntityMapByRank(filterTradeList);
		Assert.assertEquals(3, entityRankMap.size());
		Assert.assertTrue(entityRankMap.containsKey("Z"));
		Assert.assertEquals(25000.0, entityRankMap.get("Z"), 0.0);
		Assert.assertTrue(entityRankMap.containsKey("Y"));
		Assert.assertEquals(13500.0, entityRankMap.get("Y"), 0.0);
		Assert.assertTrue(entityRankMap.containsKey("foo"));
		Assert.assertEquals(9000.0, entityRankMap.get("foo"), 0.0);

	}

}
