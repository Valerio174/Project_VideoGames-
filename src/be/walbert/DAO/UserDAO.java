package be.walbert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import be.walbert.classes.Administrator;
import be.walbert.classes.Player;
import be.walbert.classes.User;

public class UserDAO extends DAO<User>{
	private PlayerDAO playerDAO = new PlayerDAO(connect);
	private AdministratorDAO administratorDAO = new AdministratorDAO(connect);
	
	public UserDAO(Connection conn) {
		super(conn); 
	}
	/******Methodes communes (CRUD)*******/

	/*Methodes communes (CRUD)*/
	@Override
	public boolean create(User user) { 
		try{
			/*Requete pour insérer les données dans la table Users*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Users(username, password,type) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, "Player");
			ps.execute();	/*Exécuter la requête*/
			ps.close();
			
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(User user) { 
		try{
			/*Requete pour supprimer les données dans la table Player*/
			PreparedStatement ps = connect.prepareStatement("DELETE FROM Users WHERE id_users=? ");
			ps.setInt(1, user.getId_users());
			ps.execute();	/*Exécuter la requête*/
			ps.close();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(User obj) { 
		return false;
	} 
	
	@Override
	public User find(int id) { 
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Users.id_users, Users.username, Users.password, Users.type, Player.credit, Player.pseudo, Player.birthday_bonus ,Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Users INNER JOIN Player ON Users.id_users = Player.id_users WHERE Users.id_users="+id);
			if(result.first()){
				if(result.getString("type").equals("Player")) {
					Player newuser = new Player(result.getInt("id_users"), result.getString("username"), result.getString("password"), result.getInt("credit"), result.getString("pseudo"), result.getBoolean("birthday_bonus"),result.getDate("registrationDate").toLocalDate(), result.getDate("dateOfBirth").toLocalDate());
					return newuser;
				}
				else {
					Administrator newuser = new Administrator(result.getInt("id_users"), result.getString("username"), result.getString("password"));
					return newuser;
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ArrayList<User> findAll(){
		ArrayList<User> all_users = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Users.id_users, Users.username, Users.password, Users.type, Player.credit, Player.pseudo, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Users INNER JOIN Player ON Users.id_users = Player.id_users\r\n");
			while(result.next()){
				if(result.getString("type")=="Player") {
					Player newuser = playerDAO.find(result.getInt("id_users"));
					all_users.add(newuser);
				}
				else {
					Administrator newuser = administratorDAO.find(result.getInt("id_users"));
					all_users.add(newuser);
				}
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_users;
		
	}
	
	/******METHODES PARTICULIERES POUR User*******/
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
					PlayerDAO playerDAO = new PlayerDAO(this.connect);
					Player player = playerDAO.find(result.getInt("id_users"));
					return player;
				}
				else {
					AdministratorDAO administratorDAO = new AdministratorDAO(this.connect);
					Administrator admin = administratorDAO.find(result.getInt("id_users"));    
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

	
}