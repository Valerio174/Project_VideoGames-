package be.walbert.classes;

import java.io.Serializable;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;
import be.walbert.DAO.UserDAO;

public abstract class User implements Serializable{
	private static final long serialVersionUID = -3943080872431286010L;
	
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
		UserDAO user =(UserDAO)userDAO;
		int success= -1;
		
		if(user.find(getId_users()) != null) {
			if(user.find(getId_users()) instanceof Player) {
				success = 1;
				return success;
			} 
			if(user.find(getId_users()) instanceof Administrator){
				success = 0;
				return success;
			}
		}
		
		return success;
	}	
}