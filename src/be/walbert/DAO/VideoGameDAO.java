package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import be.walbert.classes.VideoGame;

public class VideoGameDAO extends DAO<VideoGame>{

	public VideoGameDAO(Connection conn) {
		super(conn); 
	}

	public ArrayList<VideoGame> findAll(){
		ArrayList<VideoGame> all_videogames = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT vg.id_VideoGame, vg.name, vg.creditCost, v.name_version, c.name_console\r\n"
							+ "FROM ( VideoGame vg LEFT OUTER JOIN Version v ON vg.id_version = v.id_version ) LEFT OUTER JOIN Console c ON v.id_console= c.id_console");
			while(result.next()){
				VideoGame newvideogame = new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console"));
				all_videogames.add(newvideogame);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_videogames;
		
	}
	
	@Override
	public boolean create(VideoGame videogame) { 
		try{
			/*Requete pour insérer les données dans la table VideoGame*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO VideoGame(name, creditCost,id_version) VALUES(?,?,?)");
			ps.setString(1, videogame.getName() );
			ps.setInt(2, videogame.getCreditCost()); 
			ps.setInt(3, this.GetVersion(videogame.getVersion()));
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
	public boolean delete(VideoGame obj) { 
		return false;
	}

	@Override
	public boolean update(VideoGame videogame) { 
		try{
			/*Requete pour mettre à jour les données dans la table VideoGame*/
			PreparedStatement ps = connect.prepareStatement("UPDATE VideoGame SET creditCost = ? WHERE id_videogame = ?");
			ps.setInt(1, videogame.getCreditCost());
			ps.setInt(2, videogame.getId_videogame());
			
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
	public VideoGame find(VideoGame obj) { 
		return null;
	}
	
	public VideoGame GetVideoGame(String name, String version) {
		ArrayList<VideoGame> all_videogames = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT VideoGame.id_VideoGame, VideoGame.name, VideoGame.creditCost, Console.name_console, Version.name_version\r\n"
							+ "FROM Console INNER JOIN (Version INNER JOIN VideoGame ON Version.id_version = VideoGame.id_version) ON Console.id_console = Version.id_console\r\n"
							+ "WHERE (((VideoGame.name)=\""+name+"\") AND ((Version.name_version)=\""+version+"\"));\r\n"
							+ "");
			if(result.first()) {
				VideoGame game = new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console"));
				return game;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public ArrayList<String> AllConsoles(){
		ArrayList<String> all_consoles = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Console");
			while(result.next()){
 				all_consoles.add(result.getString("name_console"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_consoles;
		
	}
	public ArrayList<String> AllVersion(String console){
		ArrayList<String> all_versions = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Version.name_version\r\n"
							+ "FROM Console INNER JOIN Version ON Console.id_console = Version.id_console\r\n"
							+ "WHERE (((Console.name_console)=\""+console+"\"))");
			while(result.next()){
				all_versions.add(result.getString("name_version"));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_versions;
		
	}
	
	
	public int GetVersion(String version) {
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Version WHERE name_version=\""+version+"\"");
			while(result.next()){
				return result.getInt("id_version");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int GetConsole(String name_console) {
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Console WHERE name_console=\""+name_console+"\"");
			while(result.next()){
				return result.getInt("id_console");
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public boolean CreateConsole(String name_console) {
		try{
			/*Requete pour insérer les données dans la table VideoGame*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Console(name_console) VALUES(?)");
			ps.setString(1, name_console); 
			ps.execute();	/*Exécuter la requête*/
			
			ps.close();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean CreateVersion(String name_console, String name_version) {
		try{
			/*Requete pour insérer les données dans la table VideoGame*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Version(name_version,id_console) VALUES(?,?)");
			ps.setString(1, name_version); 
			ps.setInt(2, this.GetConsole(name_console));
			ps.execute();	/*Exécuter la requête*/
			
			ps.close();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	
}
