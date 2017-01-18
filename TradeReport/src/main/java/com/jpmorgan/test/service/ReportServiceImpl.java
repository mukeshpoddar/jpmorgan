package com.jpmorgan.test.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jpmorgan.test.model.ReportType;
import com.jpmorgan.test.model.Trade;
import com.jpmorgan.test.model.TradeType;
import com.jpmorgan.test.repository.TradeRepository;

/**
 * This class
 * 
 * @author Mukesh
 *
 */
public final class ReportServiceImpl implements ReportService {

	private static TradeRepository tradeRepo = TradeRepository.getInstance();

	/**
	 * This method generates the trade report of the passed report type with
	 * today settlement date.
	 * 
	 * @param reportType
	 *            The enumeration representing the report type
	 */
	public void generateReport(ReportType reportType) {
		generateReport(reportType, new java.util.Date());
	}

	/**
	 * This method generates the trade report based on the report type
	 * enumeration value for the passed settlement date.
	 * 
	 * If the no report type is passed the default report will be generated for
	 * INCOMING_SETTLEMENT_AMOUNT.
	 * 
	 * @param reportType
	 *            The enumeration representing the report type
	 */

	public void generateReport(ReportType reportType, Date reportDate) {
		List<Trade> tradeList = tradeRepo.getTradesBySettlementDate(reportDate);
		if (reportType == null) {
			reportType = ReportType.INCOMING_SETTLEMENT_AMOUNT;
		}
		switch (reportType) {
		case INCOMING_SETTLEMENT_AMOUNT:
			generateIncomingTradeReport(reportDate, tradeList);
			break;
		case OUTGOING_SETLLEMENT_AMOUNT:
			generateOutgoingTradeReport(reportDate, tradeList);
			break;
		case STOCK_INCOMING_RANK:
			generateIncomingTradeReportByEntity(reportDate, tradeList);
			break;
		case STOCK_OUTGOING_RANK:
			generateOutgoingTradeReportByEntity(reportDate, tradeList);
			break;
		}

	}

	private void generateOutgoingTradeReportByEntity(Date reportDate,
			List<Trade> tradeList) {
		List<Trade> filterTradeList = getTradeListByType(tradeList,
				TradeType.BUY);
		Map<String, Double> entityRankMap = getEntityMapByRank(filterTradeList);

		System.out
				.println("Outgoing Settlement Trade report by Entity Rank(USD Amount)");
		System.out.println("Trade Settlement Date :" + reportDate.toString());
		for (Map.Entry<String, Double> entry : entityRankMap.entrySet()) {
			String key = entry.getKey().toString();
			Double value = entry.getValue();
			System.out.println("Entity : " + key + ", Total Amount in USD : "
					+ value);
		}
	}

	private void generateIncomingTradeReportByEntity(Date reportDate,
			List<Trade> tradeList) {
		List<Trade> filterTradeList = getTradeListByType(tradeList,
				TradeType.SELL);

		Map<String, Double> entityRankMap = getEntityMapByRank(filterTradeList);

		System.out
				.println("Incoming Settlement Trade report by Entity Rank(USD Amount)");
		System.out.println("Trade Settlement Date : " + reportDate.toString());
		for (Map.Entry<String, Double> entry : entityRankMap.entrySet()) {
			String key = entry.getKey().toString();
			Double value = entry.getValue();
			System.out.println("Entity : " + key + ", Total Amount in USD : "
					+ value);
		}
	}

	private void generateOutgoingTradeReport(final Date reportDate,
			final List<Trade> tradeList) {
		List<Trade> filterTradeList = getTradeListByType(tradeList,
				TradeType.BUY);
		System.out.println("OutGoing Settlement Trade report");
		System.out.println("Trade Settlement Date :" + reportDate.toString());
		System.out.println("Total Outgoing Amount in USD :"
				+ getTotalAmountInUSD(filterTradeList));

	}

	private void generateIncomingTradeReport(Date reportDate,
			final List<Trade> tradeList) {
		List<Trade> filterTradeList = getTradeListByType(tradeList,
				TradeType.SELL);

		System.out.println("Incoming Settlement Trade report");
		System.out.println("Trade Settlement Date :" + reportDate.toString());
		System.out.println("Total Incoming Amount in USD :"
				+ getTotalAmountInUSD(filterTradeList));
	}

	/**
	 * This method will filter the trades based on the trade type passed.
	 * 
	 * @param tradeList
	 *            The List of trades which needs to be filtered
	 * @param tradeType
	 *            The tradeType used for filtering the trades
	 * @return <code>List<Trades><code> The list of trades matching the trade type.
	 */
	public List<Trade> getTradeListByType(List<Trade> tradeList,
			TradeType tradeType) {
		return tradeList.stream().filter(t -> t.getTradeType() == tradeType)
				.collect(Collectors.<Trade> toList());

	}

	/**
	 * This method will calculate the Total amount of the trades based on the
	 * FxPrice, Quantity and Units
	 * 
	 * @param tradeList
	 *            The list of trade objects from which the total USD amount
	 *            needs to be calculated
	 * @return <code> double <code> The Total UD amount.
	 */
	public double getTotalAmountInUSD(List<Trade> tradeList) {
		double result = 0;
		if (tradeList != null) {
			return tradeList.stream().mapToDouble(t -> t.getTradeAmountinUSD())
					.sum();
		}
		return result;
	}

	/**
	 * This method will return a LinkedHashMap containing the entity as the key
	 * and total USD traded amount for that entity in the decreasing order of
	 * the total USD amount.
	 * 
	 * @param filterTradeList
	 * @return
	 */
	public LinkedHashMap<String, Double> getEntityMapByRank(
			List<Trade> filterTradeList) {
		LinkedHashMap<String, Double> entityRankMap = new LinkedHashMap<String, Double>();
		Map<String, Double> tradeMap = filterTradeList.stream().collect(
				Collectors.groupingBy(Trade::getEntity,
						Collectors.summingDouble(Trade::getTradeAmountinUSD)));

		tradeMap.entrySet()
				.stream()
				.sorted(Map.Entry.<String, Double> comparingByValue()
						.reversed())
				.forEachOrdered(
						entry -> entityRankMap.put(entry.getKey(),
								entry.getValue()));
		return entityRankMap;
	}
}
