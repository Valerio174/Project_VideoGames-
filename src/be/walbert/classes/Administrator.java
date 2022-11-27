package be.walbert.classes;

import java.io.Serializable;

public class Administrator extends User implements Serializable{
	
	/*Attributs*/
	private static final long serialVersionUID = -7428898100132995109L;

	
	/*Getters/Setters*/
	
 

	/*Constructeurs*/
	public Administrator(int id_users, String username, String password) {
		super(id_users, username, password);
	}
	
	
	/*Méthodes*/

}

