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
		
		while(RemainingBalance >= (this.GetPMT()+AdditionalPayment)){
			//When checking if payment is last will account for the additional Payment
			Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, false);
			RemainingBalance = payment.getEndingBalance();
			startDate = startDate.plusMonths(1);
			loanPayments.add(payment);
			//COMMENT THIS BITCH
		}
		Payment payment = new Payment(RemainingBalance, PaymentCnt++, startDate, this, false);
		loanPayments.add(payment);

		//TODO: Create final payment (last payment might b	e partial payment)QUESTIONABLE
	}

	public double GetPMT() {
		double PMT = 0;
		PMT = Math.abs(FinanceLib.pmt(this.getInterestRate()/12,this.getLoanPaymentCnt(),this.getLoanAmount(),this.getLoanBalanceEnd(),this.isbCompoundingOption()));
		//TODO: Calculate PMT (use FinanceLib.pmt) FINISHHHHHHHED BUT COMMENTS 
		return Math.abs(PMT);
	}

	public double getTotalPayments() {
		double tot = 0;
		for(Payment i : this.loanPayments) {
			tot += i.getPayment();
		}
		//TODO: Calculate total payments FINISHED BUT COMMENT
		return tot;
	}

	public double getTotalInterest() {
		double interest = 0;
		for(Payment i : this.loanPayments) {
			interest += i.getPayment();
		}
		//TODO: Calculate total Interest FINISHEDDDDDDD
		return interest-this.LoanAmount;

	}

	public double getTotalEscrow() {
		double escrow = 0;
		for(Payment i : this.loanPayments) {
			escrow += i.getEscrowPayment();
		}
		//TODO: Calculate total escrow  
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