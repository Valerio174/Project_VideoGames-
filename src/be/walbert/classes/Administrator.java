package be.walbert.classes;

import java.io.Serializable;
import java.util.ArrayList;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.AdministratorDAO;
import be.walbert.DAO.DAO;
import be.walbert.DAO.VideoGameDAO;

public class Administrator extends User implements Serializable {
	
	/*Attributs*/
	private static final long serialVersionUID = 3523721586208348343L;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<Administrator> administratorDAO = adf.getAdministratorDAO();
	
	/*Getters/Setters*/
	

	/*Constructeurs*/
	public Administrator(int id_users, String username, String password) {
		super(id_users, username, password);
	}
	
	/*Méthodes*/
	
}