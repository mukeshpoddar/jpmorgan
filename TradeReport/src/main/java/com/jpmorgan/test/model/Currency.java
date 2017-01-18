package com.jpmorgan.test.model;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Model class for keeping the currency details like code, currency description
 * and currency non working day of the week
 *
 */
public class Currency {

	// currency code of the currency
	private String code;

	// currency detailed description
	private String description;

	// Default Non Working Day for the settlement of any currency is Sunday and
	// Saturday
	private List<Integer> nonWorkingDays = Arrays.asList(Calendar.SUNDAY,
			Calendar.SATURDAY);

	/**
	 * Constructs the Currency object
	 * 
	 * @param code
	 *            The Code for the currency
	 * @param desc
	 *            The description of the Currency
	 */
	public Currency(String code, String desc) {
		this.code = code;
		this.description = desc;
	}

	/**
	 * Constructs the Currency object
	 * 
	 * @param code
	 *            The Code for the currency
	 * @param desc
	 *            The description of the Currency
	 * @param nonWorkingDays
	 *            The List of non working days for the currency
	 */
	public Currency(String code, String desc, List<Integer> nonWorkingDays) {
		this(code, desc);
		this.nonWorkingDays = nonWorkingDays;
	}

	/**
	 * Gets the Currency code
	 * 
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set the Currency code
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the description of the currency
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the Description of the currency
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the List of Non Working day associated with the currency
	 * 
	 * @return
	 */
	public List<Integer> getNonWorkingDays() {
		return nonWorkingDays;
	}

	/**
	 * Sets the List of Non Working day associated with the currency see See the
	 * See Calendar.DAY_OF_WEEK for the possible days and values.
	 * 
	 * @return
	 */
	public void setNonWorkingDays(List<Integer> nonWorkingDays) {
		this.nonWorkingDays = nonWorkingDays;

	}

	/**
	 *  Returns a string representation of the currency Object.
	 */
	@Override
	public String toString() {
		return "Currency [code=" + code + ", nonWorkingDays=" + nonWorkingDays
				+ "]";
	}

}
