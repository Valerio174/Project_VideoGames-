
package be.walbert.classes;

import java.util.ArrayList;
import java.util.Random;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.CopyDAO;
import be.walbert.DAO.DAO;
import be.walbert.DAO.VideoGameDAO;

public class VideoGame{
	private static final long serialVersionUID = 1120585213455777467L;
	
	/*Attributs*/
	private int id_videogame;
	private String name;
	private int creditCost;
	private String console;
	private String version;
	private ArrayList<Copy> copy_list;
	private ArrayList<Booking> booking_list;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<VideoGame> videogameDAO = adf.getVideoGameDAO();
	DAO<Copy> copyDAO = adf.getCopyDAO();
	
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
	public VideoGame( int id_videogame, String name, int creditCost, String version, String console) {
		this.id_videogame = id_videogame;
		this.name=name;
		this.creditCost=creditCost;
		this.version=version;
		this.console=console;
		this.copy_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
	}
	public VideoGame(String name, int creditCost, String version, String console) {
		this.name=name;
		this.creditCost=creditCost;
		this.version=version;
		this.console=console;
		this.copy_list = new ArrayList<>();
		this.booking_list = new ArrayList<>();
	}
	
	/*Méthodes*/
	public static ArrayList<VideoGame> getAll(){
		VideoGameDAO vgDAO = (VideoGameDAO)(videogameDAO);
		return vgDAO.findAll();
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

	public Copy CopyAvailable(Player borrower) {
		CopyDAO copy = (CopyDAO)copyDAO;
		
		if(copy.CopyAvailable(this,borrower).size() != 0) {
		Random random = new Random();
		int nb;
		nb = random.nextInt(copy.CopyAvailable(this,borrower).size());
				
		Copy current_copy = copy.CopyAvailable(this,borrower).get(nb);
			
			return current_copy;
		}
			
		return null;
	}
	
	 
	public boolean ModifyCredits(int new_credits) {
		VideoGameDAO vgDAO = (VideoGameDAO)(videogameDAO);
		vgDAO.createHistoryCredits(this, new_credits);
		
		this.setCreditCost(new_credits);
			
		return videogameDAO.update(this);
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
	public boolean CreateVideoGame() {
		
		return videogameDAO.create(this);

	}
	@Override
	public String toString() {
		return "Id_videogame=" + id_videogame + ", name=" + name + ", creditCost=" + creditCost
				+ ", console=" + console + ", version=" + version;
	}
	
}
