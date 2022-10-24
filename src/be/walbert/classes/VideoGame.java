
package be.walbert.classes;

import java.util.ArrayList;

public class VideoGame{

	/*Attributs*/
	private ArrayList<Copy> copy_list;
	private ArrayList<Booking> booking_list;

	
	/*Getters/Setters*/
	public ArrayList<Copy> getList_copy() {
		return copy_list;
	}

	public void setList_copy(ArrayList<Copy> list_copy) {
		this.copy_list = list_copy;
	}
	
	public ArrayList<Booking> getBooking_list() {
		return booking_list;
	}

	public void setBooking_list(ArrayList<Booking> booking_list) {
		this.booking_list = booking_list;
	}

	/*Constructeurs*/
	public VideoGame() {
		this.copy_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
	}
	
	/*Méthodes*/
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
