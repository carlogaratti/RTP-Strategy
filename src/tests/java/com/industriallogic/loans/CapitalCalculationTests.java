/// ***************************************************************************
/// Copyright (c) 2008, Industrial Logic, Inc., All Rights Reserved.
///
/// This code is the exclusive property of Industrial Logic, Inc. It may ONLY be
/// used by students during Industrial Logic's workshops or by individuals
/// who are being coached by Industrial Logic on a project.
///
/// This code may NOT be copied or used for any other purpose without the prior
/// written consent of Industrial Logic, Inc.
/// ****************************************************************************


//$CopyrightHeader()$

package com.industriallogic.loans;

import java.util.Date;

import junit.framework.TestCase;

public class CapitalCalculationTests extends TestCase {
	private static double LOAN_AMOUNT = 3000.0;
	private static double PENNY_PRECISION = 0.01;
	private static int HIGH_RISK_RATING = 4;
	private static int LOW_RISK_RATING = 1;
	
	public CapitalCalculationTests(String name) {
		super(name);
	}
	
	private Date november(int day, int year) {
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(year, java.util.Calendar.NOVEMBER, day);
		return calendar.getTime();
	}
	
	public void testTermLoanSamePayments() {
		Date start = november(20, 2003);
		Date maturity = november(20, 2006);
		Loan termLoan = Loan.newTermLoan(LOAN_AMOUNT, start, maturity, HIGH_RISK_RATING);
		termLoan.payment(1000.00, november(20, 2004));
		termLoan.payment(1000.00, november(20, 2005));
		termLoan.payment(1000.00, november(20, 2006));
		assertEquals("duration", 2.0, termLoan.duration(), PENNY_PRECISION);
		assertEquals("capital", 210.00, termLoan.capital(), PENNY_PRECISION);
	}
	
	public void testTermLoanDifferentPayments() {
		Date start = november(20, 2003);
		Date maturity = november(20, 2006);
		Loan termLoan = Loan.newTermLoan(LOAN_AMOUNT, start, maturity, HIGH_RISK_RATING);
		termLoan.payment(500.00, november(20, 2004));
		termLoan.payment(1500.00, november(20, 2005));
		termLoan.payment(1000.00, november(20, 2006));
		assertEquals("duration", 2.16, termLoan.duration(), PENNY_PRECISION);
		assertEquals("capital", 227.50, termLoan.capital(), PENNY_PRECISION);
	}
	
	public void testRevolverNoPayments() {
		Date start = november(20, 2003);
		Date expiry = november(20, 2010);
		Loan revolver = Loan.newRevolver(LOAN_AMOUNT, start, expiry, HIGH_RISK_RATING); 
		assertEquals("duration", 7.0, revolver.duration(), PENNY_PRECISION);
		assertEquals("capital", 609.00, revolver.capital(), PENNY_PRECISION);
	}
	
	public void testRevolverWithPayments() {
		Date start = november(20, 2003);
		Date expiry = november(20, 2010);
		Loan revolver = Loan.newRevolver(LOAN_AMOUNT, start, expiry, HIGH_RISK_RATING);
		revolver.setOutstanding(1000.00);
		assertEquals("duration", 7.0, revolver.duration(), PENNY_PRECISION);
		assertEquals("capital", 651.00, revolver.capital(), PENNY_PRECISION);
	}
	
	public void testAdvisedLineMustHaveLowRiskRating() {
		Date start = november(20, 2003);
		Date expiry = november(20, 2010);
		Loan advisedLine = Loan.newAdvisedLine(LOAN_AMOUNT, start, expiry, HIGH_RISK_RATING);
		assertNull("null loan because not at right risk level", advisedLine);
	}
	
	public void testAdvisedLine() {
		Date start = november(20, 2003);
		Date expiry = november(20, 2010);
		Loan advisedLine = Loan.newAdvisedLine(LOAN_AMOUNT, start, expiry, LOW_RISK_RATING);
		assertEquals("duration", 7.0, advisedLine.duration(), PENNY_PRECISION);
		assertEquals("capital", 15.75, advisedLine.capital(), PENNY_PRECISION);
	}
	
}
