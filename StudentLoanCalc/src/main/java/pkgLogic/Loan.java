package pkgLogic;

import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.poi.ss.formula.functions.FinanceLib;

public class Loan {
	private double LoanAmount;
	private double LoanBalanceEnd;
	private double InterestRate;
	private int LoanPaymentCnt;
	private boolean bCompoundingOption;
	private LocalDate StartDate;
	private double AdditionalPayment;
	private double Escrow;

	private ArrayList<Payment> loanPayments = new ArrayList<Payment>();

	public Loan(double loanAmount, double interestRate, int loanPaymentCnt, LocalDate startDate,
			double additionalPayment, double escrow) {
		super();
		LoanAmount = loanAmount;
		InterestRate = interestRate;
		LoanPaymentCnt = loanPaymentCnt * 12;
		StartDate = startDate;
		AdditionalPayment = additionalPayment;
		bCompoundingOption = false;
		LoanBalanceEnd = 0;
		this.Escrow = escrow;

		double RemainingBalance = LoanAmount;
		int PaymentCnt = 1;
		
		while (RemainingBalance >= (this.GetPMT() + AdditionalPayment)){
			//When checking if payment is last will account for the additional Payment
			Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, false);
			//"payment" becomes a function of the remaining balance the payment count we are on+1 and the start date
			RemainingBalance = payment.getEndingBalance();
			startDate = startDate.plusMonths(1);
			loanPayments.add(payment);
			
		}
		Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, false);
		loanPayments.add(payment);

	
	}

	public double GetPMT() {
		double PMT = 0;
		PMT = Math.abs(FinanceLib.pmt(this.getInterestRate()/12,this.getLoanPaymentCnt(),this.getLoanAmount(),this.getLoanBalanceEnd(),this.isbCompoundingOption()));
		// Calculating PMT 
		return Math.abs(PMT);
	}

	public double getTotalPayments() {
		double tot = 0;
		for(Payment i : this.loanPayments) {
			// Calculating total payments by calling getPayments and adding to the total for every time (i) in loanpayments
			tot += i.getPayment();
			
		}
		
		return tot;
	}

	public double getTotalInterest() {
		double interest = 0;
		for(Payment i : this.loanPayments) { 
			//calculating total interest by calling getPayments and adding to the total for every time (i) in loanpayments
			interest += i.getPayment();
		}
			return interest-this.LoanAmount; //subtracts the loan amount to just get the interest paid

	}

	public double getTotalEscrow() {
		double escrow = 0;
		for(Payment i : this.loanPayments) {
			//I am still not exactly sure what escrow is buttt this does the same as the fuctions above by iterating through loanPayments and adding to the total escrow the escrow payment
			escrow += i.getEscrowPayment();
		}
		
		return escrow;

	}

	public double getLoanAmount() {
		return LoanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		LoanAmount = loanAmount;
	}

	public double getLoanBalanceEnd() {
		return LoanBalanceEnd;
	}

	public void setLoanBalanceEnd(double loanBalanceEnd) {
		LoanBalanceEnd = loanBalanceEnd;
	}

	public double getInterestRate() {
		return InterestRate;
	}

	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}

	public int getLoanPaymentCnt() {
		return LoanPaymentCnt;
	}

	public void setLoanPaymentCnt(int loanPaymentCnt) {
		LoanPaymentCnt = loanPaymentCnt;
	}

	public boolean isbCompoundingOption() {
		return bCompoundingOption;
	}

	public void setbCompoundingOption(boolean bCompoundingOption) {
		this.bCompoundingOption = bCompoundingOption;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public double getAdditionalPayment() {
		return AdditionalPayment;
	}

	public void setAdditionalPayment(double additionalPayment) {
		AdditionalPayment = additionalPayment;
	}

	public ArrayList<Payment> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(ArrayList<Payment> loanPayments) {
		this.loanPayments = loanPayments;
	}

	public double getEscrow() {
		return Escrow;
	}

}