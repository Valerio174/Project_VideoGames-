
package be.walbert.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.CopyDAO;
import be.walbert.DAO.DAO;
import be.walbert.DAO.VideoGameDAO;

public class VideoGame implements Serializable{
	private static final long serialVersionUID = 1120585213455777467L;
	
	/*Attributs*/
	private int id_videogame;
	private String name;
	private int creditCost;
	private String console;
	private String version;
	private ArrayList<Copy> copy_list;
	private ArrayList<Booking> booking_list;
	private ArrayList<HistoryCredits> historycredits_list;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<VideoGame> videogameDAO = adf.getVideoGameDAO();
	
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
	
	public ArrayList<HistoryCredits> getHistorycredits_list() {
		return historycredits_list;
	}
	public void setHistorycredits_list(ArrayList<HistoryCredits> historycredits_list) {
		this.historycredits_list = historycredits_list;
	}
	/*Constructeurs*/
	public VideoGame( ) {}
	public VideoGame( int id_videogame, String name, int creditCost, String version, String console) {
		this.id_videogame = id_videogame;
		this.name=name;
		this.creditCost=creditCost;
		this.version=version;
		this.console=console;
		this.copy_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
		this.historycredits_list = new ArrayList<>();
	}
	public VideoGame(String name, int creditCost, String version, String console) {
		this.name=name;
		this.creditCost=creditCost;
		this.version=version;
		this.console=console;
		this.copy_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
		this.historycredits_list = new ArrayList<>();
	}
	
	/*Méthodes*/
	public VideoGame GetVideoGame() {
		return videogameDAO.find(this.getId_videogame());
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
	public void AddHistoryCredits(HistoryCredits history) {
		try {
			historycredits_list.add(history);
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		}
	}
	public Copy CopyAvailable(Player player) {
		Copy current_copy = new Copy();
		
		Random random = new Random();
		int nb;
		nb = random.nextInt(this.getList_copy().size());
		
		if(this.getList_copy().size() != 0) {
			for (int i = 0; i < this.getList_copy().size(); i++) {
				if(this.getList_copy().get(nb).getOwner().getId_users() != player.getId_users()) {
					current_copy = this.getList_copy().get(nb);
					if(current_copy.IsAvailable()) {
						return current_copy;
					}
				}
			}
			return null;
		}
		
		return null;
	}
	
	public Booking SelectBooking() {
		if(booking_list.size() != 0) {
			Booking temp = booking_list.get(0);
			
			for (int i = 1; i < booking_list.size(); i++) {
				if(temp.getPlayer().getCredits() <booking_list.get(i).getPlayer().getCredits()) {
					 temp = booking_list.get(i);
				} 
				else if(temp.getPlayer().getCredits() == booking_list.get(i).getPlayer().getCredits()) {
					 if(temp.getBookingDate().isAfter(booking_list.get(i).getBookingDate())) {
						 temp = booking_list.get(i);
					 }
					 else if(temp.getBookingDate().equals(booking_list.get(i).getBookingDate())) {
						 if(temp.getPlayer().getRegistrationDate().isAfter(booking_list.get(i).getPlayer().getRegistrationDate())) {
							 temp = booking_list.get(i);
						 }
						 else if(temp.getPlayer().getRegistrationDate().equals(booking_list.get(i).getPlayer().getRegistrationDate()))
						 	 if(temp.getPlayer().getDateOfBirth().isAfter(booking_list.get(i).getPlayer().getDateOfBirth()))
							 temp = booking_list.get(i);
						 	 else if(temp.getPlayer().getDateOfBirth().equals(booking_list.get(i).getPlayer().getDateOfBirth())) {
						 		 Random random = new Random();
						 		 boolean random_int = random.nextBoolean();
						 		 temp= random_int?temp:booking_list.get(i); 
	 					 }
					 }
				}
			}
			return temp; 
		}
		else {
			return null;
		}
		
		
	}
	 
	public boolean CreateVideoGame() {
		return videogameDAO.create(this);
	}
	public boolean UpdateGame() {
		return videogameDAO.update(this);
	}
	
	/*Méthodes statiques*/
	public static ArrayList<VideoGame> GetAll(){
		return  videogameDAO.findAll();
	}
	public static ArrayList<String> GetAllConsoles(){
		VideoGameDAO videogame = (VideoGameDAO) videogameDAO;
		
		return videogame.AllConsoles();
		
	}
	public static ArrayList<String> GetAllVersions(String console){
		VideoGameDAO videogame = (VideoGameDAO) videogameDAO;
		
		return videogame.AllVersion(console);
		
	}
	public static boolean CreateConsole(String name_console) {
		VideoGameDAO videogame = (VideoGameDAO) videogameDAO;
		
		return videogame.CreateConsole(name_console)?true:false;
	}
	public static boolean CreateVersion(String name_console, String name_version) {
		VideoGameDAO videogame = (VideoGameDAO) videogameDAO;
		
		return videogame.CreateVersion(name_console, name_version)?true:false;
	}

	@Override
	public String toString() {
		return "Id_videogame=" + id_videogame + ", name=" + name + ", creditCost=" + creditCost
				+ ", console=" + console + ", version=" + version;
	}
}
