package com.industriallogic.loans;

import java.util.Date;
import java.util.Iterator;

import com.industriallogic.loans.Loan.Payment;

public abstract class CapitalStrategy {
	
	private static final int MILLIS_PER_DAY = 86400000;
	private static final int DAYS_PER_YEAR = 365;

	public abstract double capital(Loan loan);
	
	protected double riskFactor(Loan loan) {
		return RiskFactor.getFactors().forRating(loan.getRiskRating());
	}
	
	protected double unusedRiskFactor(Loan loan) {
		return UnusedRiskFactors.getFactors().forRating(loan.getRiskRating());
	}
	
	public double duration(Loan loan) {
		if (loan.getExpiry() == null && loan.getMaturity() != null)
		   return weightedAverageDuration(loan);
		else if (loan.getExpiry() != null && loan.getMaturity() == null)
		   return yearsTo(loan.getExpiry(), loan);
		return 0.0;
	}
	
	private double weightedAverageDuration(Loan loan) {
		double duration = 0.0;
		double weightedAverage = 0.0;
		double sumOfPayments = 0.0;
		Iterator loanPayments = loan.getPayments().iterator();
		while (loanPayments.hasNext()) {
			Payment payment = (Payment)loanPayments.next();
			sumOfPayments += payment.amount();
			weightedAverage += yearsTo(payment.date(), loan) * payment.amount();
		}
		if (loan.getCommitment() != 0.0)
			duration = weightedAverage / sumOfPayments;
		return duration;
	}
	
	double yearsTo(Object object, Loan loan) {
		Date beginDate = (Date) (loan.getToday() == null ? loan.getStart() : loan.getToday());
		return ((((Date) object).getTime() - beginDate.getTime()) / MILLIS_PER_DAY) / DAYS_PER_YEAR; 
	}


}
