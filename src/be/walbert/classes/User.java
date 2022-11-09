package be.walbert.classes;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;
import be.walbert.DAO.UserDAO;

public abstract class User{
	private static final long serialVersionUID = 1900585338455777467L;
	
	/*Attributs*/
	private int id_users;
	private String username;
	private String password;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<User> userDAO = adf.getUserDAO();
	

	/*Constructeurs*/
	public User(int id_users, String username, String password) {
		this.id_users = id_users;
		this.username = username;
		this.password = password;
	}
	public User( String username, String password) {
		this.username = username;
		this.password = password;
	}

	
	/*Getters/Setters*/
	public int getId_users() {
		return id_users;
	}
	public void setId_users(int id_users) {
		this.id_users = id_users;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/*Méthodes*/
	public static User GetUser(String username, String password) {
		UserDAO user =(UserDAO)userDAO;
		
		if(user.GetUser(username, password) != null) {
			return (user.GetUser(username, password));
		}
		return null;
	}
	public int Login(){
		
		int success= -1;
		User user = userDAO.find(this);
		
		if(user != null) {
			if(user instanceof Player) {
				success = 1;
				return success;
			} 
			if(user instanceof Administrator){
				success = 0;
				return success;
			}
		}
		
		return success;
	}	
}