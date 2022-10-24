package be.walbert.classes;

import java.time.LocalDate;

public class Loan{

	/*Attributs*/
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean ongoing;
	private Player borrower;
	private Player lender;
	private Copy copy;
	
	
	/*Getters/Setters*/
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public boolean isOngoing() {
		return ongoing;
	}
	public void setOngoing(boolean ongoing) {
		this.ongoing = ongoing;
	}
	public Player getBorrower() {
		return borrower;
	}
	public void setBorrower(Player borrower) {
		this.borrower = borrower;
	}
	public Player getLender() {
		return lender;
	}
	public void setLender(Player lender) {
		this.lender = lender;
	}
	public Copy getCopy() {
		return copy;
	}
	public void setCopy(Copy copy) {
		this.copy = copy;
	}
	/*Constructeurs*/
	public Loan(LocalDate startDate, LocalDate endDate, boolean ongoing, Player borrower, Player lender, Copy copy) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.ongoing = ongoing;
		this.borrower  = borrower;
		this.lender = lender;
		this.copy = copy;
	}
	
	/*Méthodes*/
	public void CalculateBalance() {}
	
	public void EndLoan() {}
	
}
