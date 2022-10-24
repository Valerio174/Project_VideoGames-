

package be.walbert.classes;

import java.time.LocalDate;

public class Booking{

	/*Attributs*/
	private LocalDate bookingDate;
	private VideoGame game;
	
	/*Getters/Setters*/
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

	/*Constructeurs*/
	public Booking(LocalDate bookingDate, VideoGame game) {
		this.bookingDate = bookingDate;
		this.game = game;
	}	
	
	/*Méthodes*/
	public void Delete() {
		
	}
}

