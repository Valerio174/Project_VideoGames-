package be.walbert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import be.walbert.classes.Administrator;

public class AdministratorDAO extends DAO<Administrator>{
	
	public AdministratorDAO(Connection conn) {
		super(conn);
	}
	
	/******Methodes communes (CRUD)*******/
	@Override
	public boolean create(Administrator admin) {
		try{
			/*Requete pour insérer les données dans la table Users*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Users(username, password,type) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, admin.getUsername());
			ps.setString(2, admin.getPassword());
			ps.setString(3, "Player");
			ps.execute();	/*Exécuter la requête*/
			
			ResultSet rs = ps.getGeneratedKeys();	/*Retourne une clé auto-généré lors d'un insertion (la plus récente)*/
			int generatedKey = 0;	/*Variable Integer initialisé à 0*/
			if (rs.next()) {	/*Si la PreparedStatement a retourné un enregistrement*/
			    generatedKey = rs.getInt(1);	 
			}
			
			/*Requete pour insérer les données dans la table Administrator*/
			PreparedStatement ps2 = connect.prepareStatement("INSERT INTO Administrator(id_users) VALUES(?)");
			ps2.setInt(1, generatedKey);
			ps2.executeUpdate();
			
			ps.close();
			ps2.close();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public boolean delete(Administrator admin) {
		try{
			/*Requete pour supprimer les données dans la table Administrator*/
			PreparedStatement ps = connect.prepareStatement("DELETE FROM Administrator WHERE id_Users=? ");
			ps.setInt(1, admin.getId_users());
			ps.execute();	/*Exécuter la requête*/
			ps.close();
			
			PreparedStatement ps2 = connect.prepareStatement("DELETE FROM Users WHERE id_Users=? ");
			ps2.setInt(1, admin.getId_users());
			ps2.execute();	/*Exécuter la 2e requête*/
			ps2.close();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}

	
	@Override
	public boolean update(Administrator obj) {
 		return false;
	}

	
	@Override
	public Administrator find(int id) {
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Users.id_users, Users.username, Users.password FROM Users WHERE Users.id_users="+id + " AND Type =\"Administrator\"");
			 
			if(result.first()) {
				Administrator a = new Administrator(result.getInt("id_users"),result.getString("username"), result.getString("password"));
				
				return a;
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
	public ArrayList<Administrator> findAll() {
		ArrayList<Administrator> all_administrators = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Users WHERE type = \"Administrator\"");
			 
			while(result.next()){
				Administrator newadmin = new Administrator(result.getInt("id_users"), result.getString("username"), result.getString("password"));
				all_administrators.add(newadmin);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_administrators;
	}
	
	/******METHODES PARTICULIERES POUR Administrator*******/

}