package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.walbert.classes.Administrator;
import be.walbert.classes.Player;

public class PlayerDAO extends DAO<Player>{
	public PlayerDAO(Connection conn) {
		super(conn);
	}

	/*Trouver si un compte existe deja avec un username ou un pseudo donne*/
	public boolean findAccount(Player newplayer) {
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT username FROM Users WHERE username = \"" + newplayer.getUsername() +"\"");
			
			ResultSet result2 = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT pseudo FROM Users u LEFT OUTER JOIN Player p ON u.id_users = p.id_users "
							+ "WHERE pseudo = \"" + newplayer.getPseudo() +"\"");
			
			if(result.first() || result2.first()) {
					return false;
				}
			else
				return true;
		
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return false;
		
	}

	@Override
	public boolean create(Player newplayer) {
		try{
			/*Requete pour insérer les données dans la table Users*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Users(username, password,type) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, newplayer.getUsername());
			ps.setString(2, newplayer.getPassword());
			ps.setString(3, "Player");
			ps.execute();	/*Exécuter la requête*/
			
			ResultSet rs = ps.getGeneratedKeys();	/*Retourne une clé auto-généré lors d'un insertion (la plus récente)*/
			int generatedKey = 0;	/*Variable Integer initialisé à 0*/
			if (rs.next()) {	/*Si la PreparedStatement a retourné un enregistrement*/
			    generatedKey = rs.getInt(1);	 
			}
			
			/*Requete pour insérer les données dans la table Player*/
			PreparedStatement ps2 = connect.prepareStatement("INSERT INTO Player(id_users,credit, pseudo,registrationDate,dateOfBirth) VALUES(?,?,?,?,?)");
			ps2.setInt(1, generatedKey);
			ps2.setInt(2, 10);
			ps2.setString(3, newplayer.getPseudo());
			ps2.setDate(4, Date.valueOf(newplayer.getRegistrationDate()));
			ps2.setDate(5, Date.valueOf(newplayer.getDateOfBirth()));
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
	public boolean delete(Player obj) { 
		return false;
	}

	@Override
	public boolean update(Player obj) { 
		return false;
	}

	@Override
	public boolean find(Player obj) { 
		return false;
	}
}
