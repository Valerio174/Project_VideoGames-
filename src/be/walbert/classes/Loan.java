package be.walbert.classes;

import java.io.Serializable;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;

public class Loan implements Serializable{

	private static final long serialVersionUID = 7637435341275591831L;
	
	/*Attributs*/
	private int id_loan;
	private LocalDate startDate;
	private LocalDate endDate;
	private boolean ongoing;
	private Player borrower;
	private Player lender;
	private Copy copy;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<Loan> loanDAO = adf.getLoanDAO(); 
	
	/*Getters/Setters*/
	public int getId_loan() {
		return id_loan;
	}
	public void setId_loan(int id_loan) {
		this.id_loan = id_loan;
	}
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
	public Loan() {}
	public Loan(int id_loan, LocalDate startDate, LocalDate endDate, boolean ongoing, Player borrower, Player lender, Copy copy) {
		this.id_loan = id_loan;
		this.startDate = startDate;
		this.endDate = endDate;
		this.ongoing = ongoing;
		this.borrower  = borrower;
		this.lender = lender;
		this.copy = copy;
	}
	public Loan(LocalDate startDate, LocalDate endDate, boolean ongoing, Player borrower, Player lender, Copy copy) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.ongoing = ongoing;
		this.borrower  = borrower;
		this.lender = lender;
		this.copy = copy;
	}
	
	/*Méthodes*/
	public void CalculateBalance() {
		int regular_days = (int) (ChronoUnit.DAYS.between(startDate, endDate));
		int day_in_late= (int) (ChronoUnit.DAYS.between(endDate, LocalDate.now()));
		int weeks = regular_days/7;
		int weeks_in_late= day_in_late/7;
		int total_creditCost=0;
		if(regular_days%7!=0) {
			weeks = (regular_days/7)+1;
		}
		if(ongoing==true) {
			if(LocalDate.now().isAfter(endDate)) {
				if(day_in_late%7!=0) {
					weeks_in_late = (day_in_late/7)+1;
				}
				total_creditCost= weeks*this.getCopy().getGame().getCreditCost()+(5*day_in_late)+(weeks_in_late*this.getCopy().getGame().getCreditCost());
				this.borrower.setCredits(this.borrower.getCredits()-total_creditCost);
				this.borrower.UpdatePlayer();
				this.lender.setCredits(this.lender.getCredits()+total_creditCost);
				this.lender.UpdatePlayer();
			}
			else {
				total_creditCost = weeks*this.getCopy().getGame().getCreditCost();
				this.borrower.setCredits(this.borrower.getCredits()-total_creditCost);
				this.borrower.UpdatePlayer();
				this.lender.setCredits(this.lender.getCredits()+total_creditCost);
				this.lender.UpdatePlayer();
		 	}
		}
		
		
	}
	

	public boolean EndLoan() {
		this.CalculateBalance();
		this.getCopy().ReleaseCopy();
		return loanDAO.update(this);
	}
	
	@Override
	public String toString() {
		return "Loan [id_loan=" + id_loan + ", startDate=" + startDate + ", endDate=" + endDate + ", ongoing=" + ongoing
				+ ", borrower=" + borrower + ", lender=" + lender + ", copy=" + copy + "]";
	}
}
