package com.jpmorgan.test.service;

import java.util.Date;

import com.jpmorgan.test.model.ReportType;

/**
 * 
 * Interface representing the Report service and all the operation that can be
 * performed.
 * 
 * @author Mukesh
 *
 */
public interface ReportService {

	/**
	 * Generates the trade report for the passed enumeration Report type with
	 * settlement date as today date.
	 * 
	 * @param reportType
	 *            The enumeration representing the report type
	 */
	void generateReport(ReportType reportType);

	/**
	 * Generates the trade report for the passed enumeration Report type and
	 * settlement date.
	 * 
	 * @param reportType
	 *            The enumeration representing the report type
	 */
	void generateReport(ReportType reportType, Date reportDate);

}
