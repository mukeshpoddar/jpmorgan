package com.jpmorgan.test.model;

/**
 * Enumeration type representing all different trade reports.
 * 
 * @author Mukesh
 *
 */
public enum ReportType {

	// Representing the Sell/Incoming side report
	INCOMING_SETTLEMENT_AMOUNT,
	// Representing the Buy/Outgoing side report
	OUTGOING_SETLLEMENT_AMOUNT,
	// Stock rank report for the Sell/Incoming trades
	STOCK_INCOMING_RANK,
	// Stock rank report for the Buy/Outgoing Trades
	STOCK_OUTGOING_RANK

}
