package com.jpmorgan.tradereport;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jpmorgan.test.exceptions.InvalidTradeDataException;
import com.jpmorgan.test.model.Currency;
import com.jpmorgan.test.model.ReportType;
import com.jpmorgan.test.model.Trade;
import com.jpmorgan.test.model.TradeType;
import com.jpmorgan.test.repository.TradeRepository;
import com.jpmorgan.test.service.ReportService;
import com.jpmorgan.test.service.ReportServiceImpl;

/**
 *
 *
 */
public class TradeReportingEngineApp {

	public static void main(String[] args) {

		List<Integer> nonWorkingDays = Arrays.asList(Calendar.FRIDAY,
				Calendar.SATURDAY);

		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		// Add the default settlement data as +1
		c.add(Calendar.DATE, 1);

		Date settlementDate = c.getTime();

		TradeRepository tradeRepo = TradeRepository.getInstance();
		try {
			tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0.5,
					new Currency("SGP", "Singapore Dollars"), 200, 100.25, now,
					settlementDate));
			tradeRepo.addTrade(new Trade("bar", TradeType.SELL, 0.22,
					new Currency("AED", "UAE Dirham", nonWorkingDays) {
					}, 450, 150.5));
			tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0.5,
					new Currency("USD", "US Dollars"), 200, 200));
			tradeRepo.addTrade(new Trade("foo", TradeType.SELL, 0.5,
					new Currency("GBP", "British Pound"), 200, 250));
			tradeRepo.addTrade(new Trade("bar", TradeType.BUY, 0.5,
					new Currency("AUD", "Australian dollar"), 200, 180));
			tradeRepo.addTrade(new Trade("bar", TradeType.SELL, 0.5,
					new Currency("EUR", "Euro"), 200, 100.25, settlementDate,
					settlementDate));
			tradeRepo.addTrade(new Trade("Y", TradeType.BUY, 0.20,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 300, 150));
			tradeRepo.addTrade(new Trade("foo", TradeType.SELL, 0.10,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 10, 150, now, settlementDate));

			tradeRepo.addTrade(new Trade("bar", TradeType.SELL, 0.12,
					new Currency("AED", "UAE Dirham", nonWorkingDays) {
					}, 45, 150.5));
			tradeRepo.addTrade(new Trade("X", TradeType.BUY, 0.51,
					new Currency("USD", "US Dollars"), 13, 200));
			tradeRepo.addTrade(new Trade("foo", TradeType.SELL, 0.52,
					new Currency("GBP", "British Pound"), 21, 250));
			tradeRepo.addTrade(new Trade("bar", TradeType.BUY, 0.25,
					new Currency("AUD", "Australian dollar"), 12, 180));
			tradeRepo.addTrade(new Trade("bar", TradeType.SELL, 0.45,
					new Currency("EUR", "Euro"), 21, 100.25));
			tradeRepo.addTrade(new Trade("bar", TradeType.BUY, 0.60,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 10, 150));
			tradeRepo.addTrade(new Trade("foo", TradeType.SELL, 0.20,
					new Currency("SAR", "Saudi Arab Riyal", nonWorkingDays) {
					}, 300, 150));
			;
			tradeRepo.prettyPrint();

		} catch (InvalidTradeDataException ex) {
			System.out.println("The trade data passed in invalid");
		}

		ReportService reportService = new ReportServiceImpl();
		System.out.println("Report for today");
		reportService.generateReport(null);
		reportService.generateReport(ReportType.OUTGOING_SETLLEMENT_AMOUNT);
		reportService.generateReport(ReportType.STOCK_INCOMING_RANK);
		reportService.generateReport(ReportType.STOCK_OUTGOING_RANK);

		System.out.println("Report for Monday");
		reportService.generateReport(ReportType.INCOMING_SETTLEMENT_AMOUNT,
				settlementDate);
		reportService.generateReport(ReportType.OUTGOING_SETLLEMENT_AMOUNT,
				settlementDate);
		reportService.generateReport(ReportType.STOCK_INCOMING_RANK,
				settlementDate);
		reportService.generateReport(ReportType.STOCK_OUTGOING_RANK,
				settlementDate);

	}
}
