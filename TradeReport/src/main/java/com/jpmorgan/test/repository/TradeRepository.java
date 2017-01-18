package com.jpmorgan.test.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jpmorgan.test.exceptions.InvalidTradeDataException;
import com.jpmorgan.test.model.Currency;
import com.jpmorgan.test.model.Trade;

/**
 * The Trade repository class will hold all the reported trades to the
 * repository in map based on the actual settlement Date.
 * 
 * @author Mukesh
 *
 */
public class TradeRepository {

	private static TradeRepository instance;
	private Map<String, List<Trade>> repository;

	/** The date format in dd MMM YYYY format */
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"dd MMM YYYY");

	private TradeRepository() {
		this.repository = new HashMap<String, List<Trade>>();
	}

	/**
	 * Add the trade to the repository map based on the actual settlement date
	 * 
	 * @param trade
	 */
	public void addTrade(Trade trade) throws InvalidTradeDataException {
		validateTrade(trade);
		Date actSettDate = getValidSettlementDate(trade);
		String settlementDateStr = dateFormat.format(actSettDate);
		if (repository.containsKey(settlementDateStr)) {
			List<Trade> tradeList = repository.get(settlementDateStr);
			tradeList.add(trade);
			repository.put(settlementDateStr, tradeList);
		} else {
			List<Trade> tradeList = new ArrayList<Trade>();
			tradeList.add(trade);
			repository.put(settlementDateStr, tradeList);
		}
	}

	public void clearTrades() {
		repository.clear();
	}

	/**
	 * This method will calculate the valid Settlement Date based on the trade
	 * currency working days.
	 * 
	 * @param trade
	 *            The trade Object for which the settlement date needs to be
	 *            calculated.
	 * @return <code> Date <Code> The valid settlement date of the trade based on it's currency of trade
	 */

	private Date getValidSettlementDate(Trade trade) {
		Currency currency = trade.getCurrency();
		Date trSettDate = trade.getSettlementDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(trSettDate);
		int dayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
		while (currency.getNonWorkingDays().contains(dayofWeek)) {
			calendar.add(Calendar.DATE, 1);
			dayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
		}
		Date newSettlementDate = calendar.getTime();
		return newSettlementDate;
	}

	/**
	 * This method will returns all the trades associated with the passed
	 * settlement date.
	 * 
	 * @param settlementDate
	 *            The Settlement Date.
	 * @return List<Trade> The list of trades associated with the settlement
	 *         date if any, otherwise empty List of trades
	 */
	public List<Trade> getTradesBySettlementDate(Date settlementDate) {
		List<Trade> tradeList = new ArrayList<Trade>();
		if (settlementDate != null) {
			String settlementDateStr = dateFormat.format(settlementDate);
			if (repository.containsKey(settlementDateStr)) {
				if (repository.get(settlementDateStr) != null) {
					tradeList = repository.get(settlementDateStr);
				}
			}
		}
		return tradeList;
	}

	/**
	 * Returns the instance of the Trade repository
	 * 
	 * @return
	 */

	public static TradeRepository getInstance() {
		if (instance == null) {
			instance = new TradeRepository();
		}
		return instance;
	}

	/**
	 * Prints the Trade repository by Actual Settlement Date
	 */
	public void prettyPrint() {
		for (Map.Entry<String, List<Trade>> entry : repository.entrySet()) {
			String key = entry.getKey().toString();
			List<Trade> value = entry.getValue();
			System.out.println("Trade Settlement Date : " + key + " Trades :[");
			for (Trade trade : value) {
				System.out.println(trade);
			}
			System.out.println("]");
		}
	}

	/**
	 * This method validated the trade object before passing to the repository
	 * 
	 * @param trade
	 *            The trade object that needs to be validated
	 * @throws InvalidTradeDataException
	 */
	private void validateTrade(Trade trade) throws InvalidTradeDataException {
		if (trade == null) {
			throw new InvalidTradeDataException("Trade Object cannot be null");
		} else if (trade.getEntity() == null || trade.getEntity().isEmpty()) {
			throw new InvalidTradeDataException(
					"Trade enttity cannot be null or empty");
		} else if (trade.getFxPrice() <= 0) {
			throw new InvalidTradeDataException(
					"Trade Fx Agreed cannot be Zero or Negative");
		} else if (trade.getQuantity() <= 0) {
			throw new InvalidTradeDataException(
					"Trade Quantity cannot be Zero or Negative");
		} else if (trade.getPrice() <= 0) {
			throw new InvalidTradeDataException(
					"Trade Price cannot be Zero or Negative");
		} else if (trade.getSettlementDate() == null) {
			throw new InvalidTradeDataException(
					"Trade Settlement Date cannot be null");
		} else if (trade.getTradeType() == null) {
			throw new InvalidTradeDataException("Trade type cannot be null");
		} else if (trade.getCurrency() == null) {
			throw new InvalidTradeDataException("Currency Cannot be null");
		} else if (trade.getSettlementDate() == null) {
			throw new InvalidTradeDataException(
					"Settlement Date cannot be null");
		}
	}

}
