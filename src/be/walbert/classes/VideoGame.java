
package be.walbert.classes;

import java.util.ArrayList;

public class VideoGame{

	/*Attributs*/
	private int id_videogame;
	private String name;
	private int creditCost;
	private String console;
	private String version;
	private ArrayList<Copy> copy_list;
	private ArrayList<Booking> booking_list;

	
	/*Getters/Setters*/
	public int getId_videogame() {
		return id_videogame;
	}

	public void setId_videogame(int id_videogame) {
		this.id_videogame = id_videogame;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCreditCost() {
		return creditCost;
	}

	public void setCreditCost(int creditCost) {
		this.creditCost = creditCost;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

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
	public VideoGame( int id_videogame, String name, int creditCost, String console, String version) {
		this.id_videogame = id_videogame;
		this.name=name;
		this.creditCost=creditCost;
		this.console=console;
		this.version=version;
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
