package com.jpmorgan.test.model;


import org.junit.Assert;
import org.junit.Test;

import com.jpmorgan.test.exceptions.InvalidTradeDataException;

/**
 * Test class for testing the derived field of the trade object
 * 
 * @author Mukesh
 *
 */
public class TradeTest {

	@Test
	public void testTradeUSDAmount() throws InvalidTradeDataException {
		Trade trade = new Trade("foo", TradeType.BUY, 1, new Currency("SGP",
				"Singapore Dollars"), 200, 100.00);
		Assert.assertEquals(20000.0, trade.getTradeAmountinUSD(), 0.0);
	}

	@Test
	public void testTradeUSDAmountNegative() throws InvalidTradeDataException {
		Trade trade = new Trade("foo", TradeType.BUY, 1, new Currency("SGP",
				"Singapore Dollars"), 200, 100.00);
		Assert.assertNotEquals(4000.0, trade.getTradeAmountinUSD(), 0.0);
	}

}
