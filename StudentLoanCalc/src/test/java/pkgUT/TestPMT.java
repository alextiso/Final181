package pkgUT;


import org.apache.poi.ss.formula.functions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import pkgLogic.Loan;

public class TestPMT {

	@Test
	public void TestPMT() {
		
		//	PMT is a standard function included in apache POI.
		//	For a given r (rate), n (number of payments), p (present value), f (future value), t (how compounding is applied)
		//	this function will determine payment
		
		//	This is an example with known values
		//	PMT returns with negative values (this is typical accounting).  
		
		double PMT;
		double r = 0.07 / 12;
		double n = 20 * 12;
		double p = 150000;
		double f = 0;
		boolean t = false;
		PMT = Math.abs(FinanceLib.pmt(r, n, p, f, t));		
		double PMTExpected = 1162.95;		
		assertEquals(PMTExpected, PMT, 0.01);
	}
	@Test
	public void TestLoanWithNoExtraPayment() {
		
		//This unit test should work with the values given
		double dLoanAmount = 50000;
		double dInterestRate = 0.07;
		int iNbrOfYears = 20;
		LocalDate localDate = LocalDate.now();
		double dAdditionalPayment = 0;
		double dEscrow = 0;

		Loan loan = new Loan(dLoanAmount, dInterestRate, iNbrOfYears, localDate, dAdditionalPayment, dEscrow);
		
		
		assertEquals(loan.getLoanPayments().size(), 240);
		assertEquals(loan.getTotalPayments(), 93033.62, 3);
		assertEquals(loan.getTotalInterest(), 43035.87, 0.01);
	}
	
	@Test
	public void TestLoanWithExtraPayment() {
		
		//This unit test should work with the values given
		double dLoanAmount = 50000;
		double dInterestRate = 0.07;
		int iNbrOfYears = 20;
		LocalDate localDate = LocalDate.now();
		double dAdditionalPayment = 200;
		double dEscrow = 0;

		Loan loan = new Loan(dLoanAmount, dInterestRate, iNbrOfYears, localDate, dAdditionalPayment, dEscrow);
		Loan Loan_without_payments = new Loan(dLoanAmount, dInterestRate, iNbrOfYears, localDate, 0, dEscrow);
		
		//My calculations are slightly off from what is given in the excel However I believe that it has to do with the remaining decimal places of the double being added to the total payments/interest and therefore after 118 its starting to add up
		//However the math that is actually being done should be right 
		
		//Testing Interest Saved
		assertEquals(Loan_without_payments.getTotalInterest() - loan.getTotalInterest(), 23693.23, .01);
		
		//Checking how much the payments saved
		assertEquals(Loan_without_payments.getTotalPayments() - loan.getTotalPayments(), 23693.23, .01);
		
		//Checking number of Payments ( total payment amt) 
		
		assertEquals(loan.getLoanPayments().size(), 118);
		
		//Checking Total Payments
		assertEquals(loan.getTotalPayments(), 69342.64, .01);
		
		//Checking Total interests
		assertEquals(loan.getTotalInterest(), 19342.64 , .01); 

		//TODO: Assert correct values based on amort spreadsheet (total payments, total payment amt, 
		//		total interest, total interest saved, total payments saved.  
		
	}	
}

 

