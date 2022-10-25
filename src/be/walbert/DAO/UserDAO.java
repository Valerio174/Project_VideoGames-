package be.walbert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.walbert.classes.User;

public class UserDAO extends DAO<User>{

	public UserDAO(Connection conn) {
		super(conn); 
	}

	@Override
	public boolean create(User obj) { 
		return false;
	}

	@Override
	public boolean delete(User obj) { 
		return false;
	}

	@Override
	public boolean update(User obj) { 
		return false;
	}

	/*Récupérer User correspondant */
	@Override
	public User find(User newuser) {
		 
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Users WHERE username = \"" + newuser.getUsername() +"\"" 
					+ " AND password = \"" + newuser.getPassword() +"\"");
			if(result.first())
				return newuser;
			else
				return null;
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return newuser;
	}

}
