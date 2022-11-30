package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.walbert.classes.Copy;
import be.walbert.classes.HistoryCredits;
import be.walbert.classes.Loan;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;
 
public class HistoryCreditsDAO extends DAO<HistoryCredits>{

	public HistoryCreditsDAO(Connection conn) {
		super(conn);
	}

	/******Methodes communes (CRUD)*******/
	@Override
	public boolean create(HistoryCredits historycredits) {
		try{
			/*Requete pour insérer les données dans la table Copy*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO HistoryCredits(modification_date, old_creditCost, new_creditCost, id_videogame) VALUES(?,?,?,?)");
			ps.setDate(1,  Date.valueOf(historycredits.getModification_date()));
			ps.setInt(2, historycredits.getOld_creditCost()); 
			ps.setInt(3, historycredits.getNew_creditCost()); 
			ps.setInt(4, historycredits.getVideogame().getId_videogame()); 
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
	public boolean delete(HistoryCredits historycredits) {
		try{
			/*Requete pour supprimer les données de la table Loan*/
			PreparedStatement ps = connect.prepareStatement("DELETE FROM HistoryCredits WHERE id_history=? ");
			ps.setInt(1, historycredits.getId_history());
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
	public boolean update(HistoryCredits historycredits) {
 		return false;
	}

	@Override
	public HistoryCredits find(int id) {
		HistoryCredits history = new HistoryCredits();
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT HistoryCredits.id_history, HistoryCredits.modification_date, HistoryCredits.old_creditCost, HistoryCredits.new_creditCost, VideoGame.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console\r\n"
							+ "FROM Console INNER JOIN (Version INNER JOIN (VideoGame INNER JOIN HistoryCredits ON VideoGame.id_VideoGame = HistoryCredits.id_videogame) ON Version.id_version = VideoGame.id_version) ON Console.id_console = Version.id_console\r\n"
							+ "WHERE HistoryCredits.id_history="+id);
			 
			if(result.next()) {
				history = new HistoryCredits(result.getInt("id_history"), result.getDate("modification_date").toLocalDate(), 
						result.getInt("old_creditCost"), result.getInt("new_creditCost"),
						 new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console")));
 			 }
 			 
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return history;
	}

	@Override
	public ArrayList<HistoryCredits> findAll() {
		ArrayList<HistoryCredits> all_historycredits = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM HistoryCredits");
			 
			while(result.next()){
				VideoGameDAO videogameDAO = new VideoGameDAO(this.connect);
				HistoryCredits new_history = new HistoryCredits(result.getInt("id_history"), result.getDate("modification_date").toLocalDate(), 
						result.getInt("old_creditCost"), result.getInt("new_creditCost"),videogameDAO.find(result.getInt("id_videogame")));
 				 all_historycredits.add(new_history);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_historycredits;	
	}
	
	/******METHODES PARTICULIERES POUR HistoryCredits*******/
	 
}
