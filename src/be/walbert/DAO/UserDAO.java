package be.walbert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.walbert.classes.Administrator;
import be.walbert.classes.Player;
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
	public User GetUser(User newuser) {
		 
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT id_users, username, password, "
							+ "type, credit, pseudo, registrationDate, dateOfBirth "
							+ "FROM Users u LEFT OUTER JOIN Player p ON u.id_users = p.id_users "
							+ "WHERE username = \"" + newuser.getUsername() +"\"" 
							+ " AND password = \"" + newuser.getPassword() +"\"");
			if(result.first())
				if (result.getString("type").equals("Player")) {
					Player player = new Player(result.getInt("id_users"), result.getString("username"),result.getString("password"),
							result.getInt("credit"),result.getString("pseudo"),
							result.getDate("registrationDate").toLocalDate(),
							result.getDate("dateOfBirth").toLocalDate());
					return player;
				}
				else {
					Administrator admin = new Administrator(result.getInt("id_users"),result.getString("username"),result.getString("password"));
					return admin;
				}
			else
				return null;
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return newuser;
	}

	public User GetUser(String username, String password) {
		 
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT id_users, username, password, "
							+ "type, credit, pseudo, registrationDate, dateOfBirth "
							+ "FROM Users u LEFT OUTER JOIN Player p ON u.id_users = p.id_users "
							+ "WHERE username = \"" + username +"\"" 
							+ " AND password = \"" + password +"\"");
			if(result.first())
				if (result.getString("type").equals("Player")) {
					Player player = new Player(result.getInt("id_users"), result.getString("username"),result.getString("password"),
							result.getInt("credit"),result.getString("pseudo"),
							result.getDate("registrationDate").toLocalDate(),
							result.getDate("dateOfBirth").toLocalDate());
					return player;
				}
				else {
					Administrator admin = new Administrator(result.getInt("id_users"),result.getString("username"),result.getString("password"));
					return admin;
				}
			else
				return null;
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User find(int id) { 
		return null;
	}
}
