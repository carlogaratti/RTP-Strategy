package com.industriallogic.loans;

public class CapitalStrategyAdviseLine extends CapitalStrategy {
	
	public double capital(Loan loan) {
		return loan.getCommitment() * loan.getUnusedPercentage() * loan.duration() * riskFactor(loan);
	}

}
