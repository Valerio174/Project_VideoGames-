package be.walbert.classes;

import java.io.Serializable;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;

public class Administrator extends User implements Serializable{
	
	/*Attributs*/
	private static final long serialVersionUID = -7428898100132995109L;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<Administrator> administratorDAO = adf.getAdministratorDAO();
	
	/*Getters/Setters*/
	
 

	/*Constructeurs*/
	public Administrator(int id_users, String username, String password) {
		super(id_users, username, password);
	}
	
	/*Méthodes*/

}

