package be.walbert.classes;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;

public class User{
	private static final long serialVersionUID = 1900585338455777467L;
	
	/*Attributs*/
	private String username;
	private String password;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<User> userDAO = adf.getUserDAO();
	

	/*Constructeurs*/
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	public User() {}
	
	/*Getters/Setters*/
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
	public boolean Login(User u){
		
		boolean success = false;
		User user = userDAO.find(u);
		
		if(user == null) {
			success = true;
			return success;
			
		}
		return success;
		
	}	
}