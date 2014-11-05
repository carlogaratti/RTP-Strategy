package com.industriallogic.loans;

public class CapitalStrategyTermLoan extends CapitalStrategy {
	
	public double capital(Loan loan) {
		   return loan.getCommitment() * loan.duration() * riskFactor(loan);
	}

}
