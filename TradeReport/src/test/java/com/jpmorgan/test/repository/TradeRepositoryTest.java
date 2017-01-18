package com.jpmorgan.test.repository;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.test.exceptions.InvalidTradeDataException;
import com.jpmorgan.test.model.Currency;
import com.jpmorgan.test.model.Trade;
import com.jpmorgan.test.model.TradeType;
import com.jpmorgan.test.repository.TradeRepository;

public class TradeRepositoryTest {

	private TradeRepository tradeRepo;

	@Before
	public void setUP() {
		tradeRepo = TradeRepository.getInstance();

	}

	@Test(expected = InvalidTradeDataException.class)
	public void testNullTradeException() throws InvalidTradeDataException {
		tradeRepo.addTrade(null);
	}

	@Test(expected = InvalidTradeDataException.class)
	public void testTradeWithInvalidCurrencyException()
			throws InvalidTradeDataException {
		tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0.5, null, 200,
				100.25));
	}

	@Test(expected = InvalidTradeDataException.class)
	public void testTradeWithInvalidEntityException()
			throws InvalidTradeDataException {
		tradeRepo.addTrade(new Trade(null, TradeType.BUY, 0.5, null, 200,
				100.25));
	}

	@Test(expected = InvalidTradeDataException.class)
	public void testTradeWithInvalidTradeTypeException()
			throws InvalidTradeDataException {
		tradeRepo.addTrade(new Trade("foo", null, 0.5, null, 200, 100.25));
	}

	@Test(expected = InvalidTradeDataException.class)
	public void testTradeWithInvalidQuantityyException()
			throws InvalidTradeDataException {
		tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0.5, new Currency(
				"SGP", "Singapore Dollars"), -100, 100.25));
	}

	@Test(expected = InvalidTradeDataException.class)
	public void testTradeWithInvalidAgreedFxException()
			throws InvalidTradeDataException {
		tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0, new Currency(
				"SGP", "Singapore Dollars"), 200, 100.25));
	}

	@Test(expected = InvalidTradeDataException.class)
	public void testTradeWithInvalidSettlementDateException()
			throws InvalidTradeDataException {
		tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0, new Currency(
				"SGP", "Singapore Dollars"), 200, 100.25, new Date(), null));
	}

	@Test(expected = InvalidTradeDataException.class)
	public void testTradeWithInvalidInstructionDateException()
			throws InvalidTradeDataException {
		tradeRepo.addTrade(new Trade("foo", TradeType.BUY, 0, new Currency(
				"SGP", "Singapore Dollars"), 200, 100.25, null, new Date()));
	}

}
