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
 
public class HistoryCreditsDAO extends DAO<HistoryCredits>{

	public HistoryCreditsDAO(Connection conn) {
		super(conn);
	}

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
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM HistoryCredit WHERE id_history="+id);
			 
			if(result.next()) {
				VideoGameDAO videogameDAO = new VideoGameDAO(this.connect);
				history = new HistoryCredits(result.getInt("id_history"), result.getDate("modification_date").toLocalDate(), 
						result.getInt("old_creditCost"), result.getInt("new_creditCost"),videogameDAO.find(result.getInt("id_videogame")));
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
}
