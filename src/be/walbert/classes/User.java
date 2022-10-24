package be.walbert.classes;


public class User{
	
	/*Attributs*/
	private String username;
	private String password;
 

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
	 
}