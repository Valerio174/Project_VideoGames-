package be.walbert.classes;

import java.io.Serializable;
import java.time.LocalDate;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;

public class Booking implements Serializable{

	private static final long serialVersionUID = 4447518906972680337L;
	/*Attributs*/
	private int id_booking;
	private LocalDate bookingDate;
	private VideoGame game;
	private Player player;
	private int number_weeks;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Booking> bookingDAO = adf.getBookingDAO();
	
	/*Getters/Setters*/
	
	public int getId_booking() {
		return id_booking;
	}
	
	public void setId_booking(int id_booking) {
		this.id_booking = id_booking;
	}
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	public VideoGame getGame() {
		return game;
	}

	public void setGame(VideoGame game) {
		this.game = game;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getNumber_weeks() {
		return number_weeks;
	}
	public void setNumber_weeks(int number_weeks) {
		this.number_weeks = number_weeks;
	}
	/*Constructeurs*/
	public Booking() {}
	public Booking(LocalDate bookingDate, VideoGame game, Player player,int number_weeks) {
		this.bookingDate = bookingDate;
		this.game = game;
		this.player = player;
		this.number_weeks = number_weeks;
	}	
	public Booking(int id_booking, LocalDate bookingDate, VideoGame game, Player player,int number_weeks) {
		this.id_booking = id_booking;
		this.bookingDate = bookingDate;
		this.game = game;
		this.player = player;
		this.number_weeks = number_weeks;

	}	
	
	public boolean CreateBooking() {
		return bookingDAO.create(this);
	}
	/*Méthodes*/
	public void Delete() {
		
	}
}

