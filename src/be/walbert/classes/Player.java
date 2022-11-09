package be.walbert.classes;

import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.CopyDAO;
import be.walbert.DAO.DAO;
import be.walbert.DAO.PlayerDAO;

public class Player extends User{
	private static final long serialVersionUID = 1920585118455777467L;
	
	/*Attributs*/
	private int credits;
	private String pseudo;
	private LocalDate registrationDate;
	private LocalDate dateOfBirth;
	private ArrayList<Loan> lender_list;
	private ArrayList<Loan> borrow_list;
	private ArrayList<Copy> copy_list;
	private ArrayList<Booking> booking_list;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Player> playerDAO = adf.getPlayerDAO();
	DAO<Copy> copyDAO = adf.getCopyDAO();
	
	/*Getters/Setters*/
	public int getCredits() {
		return credits;
	}
	public void setCredits(int credits) {
		this.credits = credits;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/*Constructeurs*/
	public Player(int id_users, String username, String password, int credits, String pseudo, LocalDate registrationDate,
			LocalDate dateOfBirth) {
		super(id_users, username, password);
		this.credits = credits;
		this.pseudo = pseudo;
		this.registrationDate = registrationDate;
		this.dateOfBirth = dateOfBirth;
		this.lender_list = new ArrayList<>();
		this.borrow_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
		this.copy_list = new ArrayList<>();
		this.copy_list = ((CopyDAO) copyDAO).OwnCopy(this);
	}
	public Player(  String username, String password, int credits, String pseudo, LocalDate registrationDate,
			LocalDate dateOfBirth) {
		super( username, password);
		this.credits = credits;
		this.pseudo = pseudo;
		this.registrationDate = registrationDate;
		this.dateOfBirth = dateOfBirth;
		this.lender_list = new ArrayList<>();
		this.borrow_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
		this.copy_list = new ArrayList<>();
		this.copy_list = ((CopyDAO) copyDAO).OwnCopy(this);
	}
	public Player(int id_users, String username, String password) {
		super(id_users, username, password);
		this.lender_list = new ArrayList<>();
		this.borrow_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
		this.copy_list = new ArrayList<>();
		this.copy_list = ((CopyDAO) copyDAO).OwnCopy(this);
	}

	/*Méthodes*/
	public boolean SignIn() {
		
		boolean success= playerDAO.create(this);
		
		return success;
	}
	
	public boolean CheckUsername() {
		PlayerDAO p =(PlayerDAO)playerDAO;
		 
		return p.findAccount(this);
	}

	public boolean LoanAllowed() {
		boolean success = false;
				
		return success;
	}
	
	public void AddBirthdayBonus() {}
	
	public void AddLender(Loan newloan) {
		try {
			lender_list.add(newloan);
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
	}
	
	public void AddBorrow(Loan newborrow) {
		try {
			borrow_list.add(newborrow);
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
	}
	
	public void AddCopy(Copy newcopy) {
		try {
			copy_list.add(newcopy);
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
	}
	
	public void AddBooking(Booking newbooking) {
		try {
			booking_list.add(newbooking);
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
	}
	
}