package com.industriallogic.loans;

public class CapitalStrategyRevolver extends CapitalStrategy {
	
	public double capital(Loan loan) {  
			return 
				  (loan.outstandingRiskAmount() * loan.duration() * riskFactor(loan))
				+ (loan.unusedRiskAmount() * loan.duration() * unusedRiskFactor(loan));
	}

}
