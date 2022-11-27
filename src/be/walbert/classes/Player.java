package be.walbert.classes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;
import be.walbert.DAO.PlayerDAO;
import be.walbert.DAO.UserDAO;

public class Player extends User implements Serializable{
 	
	private static final long serialVersionUID = -3257763287714633366L;
	
	/*Attributs*/
	private int credits;
	private String pseudo;
	private LocalDate registrationDate;
	private LocalDate dateOfBirth;
	private ArrayList<Loan> lender_list;
	private ArrayList<Loan> borrow_list;
	private ArrayList<Copy> copy_list;
	private ArrayList<Booking> booking_list;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<Player> playerDAO = adf.getPlayerDAO();
	
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
	
	public ArrayList<Loan> getBorrow_list() {
		return borrow_list;
	}
	public void setBorrow_list(ArrayList<Loan> borrow_list) {
		this.borrow_list = borrow_list;
	}
	public ArrayList<Loan> getLender_list() {
		return lender_list;
	}
	public void setLender_list(ArrayList<Loan> lender_list) {
		this.lender_list = lender_list;
	}
	public ArrayList<Copy> getCopy_list() {
		return copy_list;
	}
	public void setCopy_list(ArrayList<Copy> copy_list) {
		this.copy_list = copy_list;
	}
	public ArrayList<Booking> getBooking_list() {
		return booking_list;
	}
	public void setBooking_list(ArrayList<Booking> booking_list) {
		this.booking_list = booking_list;
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
		
		if(getCredits() > 0) {
			return true;
		}
		return false;

	}
	public Player GetPlayer(Player player) {
		PlayerDAO p =(PlayerDAO)playerDAO;
		
		return p.find(player.getId_users());
	}
	public boolean UpdatePlayer() {
		return playerDAO.update(this);
	}
	public void AddBirthdayBonus() {}
	
	public void AddLender(Loan newloan) {
		try {
			lender_list.add(newloan);
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
	}
	public void UpdateLoan(Loan new_loan) {
		for (Loan loan : lender_list) {
			if (loan.getId_loan() == new_loan.getId_loan()) {
				loan.setOngoing(false);
			}
		}
	}
	public boolean CheckBookingAlreadyDone(int id_videogame) {
		for (Booking booking : booking_list) {
			if(booking.getGame().getId_videogame() == id_videogame)
				return true;
		}
		return false;
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