package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import be.walbert.classes.Booking;
import be.walbert.classes.Loan;

public class LoanDAO extends DAO<Loan>{
	private PlayerDAO playerDAO = new PlayerDAO(connect);
	private CopyDAO copyDAO = new CopyDAO(connect);;
	
	public LoanDAO(Connection conn) {
		super(conn); 
	} 

	/******Methodes communes (CRUD)*******/

	@Override
	public boolean create(Loan loan) {
		try{
			/*Requete pour insérer les données dans la table Loan*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Loan(startDate, endDate,ongoing,id_users_borrower,id_copy) VALUES(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, Date.valueOf(loan.getStartDate()));
			ps.setDate(2,Date.valueOf(loan.getEndDate()));
			ps.setBoolean(3, true);
			ps.setInt(4, loan.getBorrower().getId_users());	//Ajouter un champ id_users dans la classe Users 
			ps.setInt(5,loan.getCopy().getId_copy());
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
	public boolean delete(Loan loan) { 
		try{
			/*Requete pour supprimer les données de la table Loan*/
			PreparedStatement ps = connect.prepareStatement("DELETE FROM Loan WHERE id_Loan=? ");
			ps.setInt(1, loan.getId_loan());
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
	public boolean update(Loan loan) { 
		try{
			/*Requete pour mettre à jour les données dans la table Loan*/
			PreparedStatement ps = connect.prepareStatement("UPDATE Loan SET ongoing = ? WHERE id_loan = ?");
			ps.setBoolean(1, false);
			ps.setInt(2, loan.getId_loan());
			
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
	public Loan find(int id) { 
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELEC* FROM Loan WHERE id_Loan=" + id);
			 if(result.first()) {
				 Loan new_loan = new Loan(result.getInt("id_Loan"), result.getDate("startDate").toLocalDate(),result.getDate("endDate").toLocalDate(), result.getBoolean("ongoing"),  playerDAO.find(result.getInt("id_users_borrower")), copyDAO.find(result.getInt("id_copy")).getOwner(), copyDAO.find(result.getInt("id_copy")));
 				 return new_loan;
			 }
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Loan> findAll() {
		ArrayList<Loan> all_loans = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Loan");
			 
			while(result.next()){
				 Loan new_loan = new Loan(result.getInt("id_Loan"), result.getDate("startDate").toLocalDate(),result.getDate("endDate").toLocalDate(), result.getBoolean("ongoing"),  playerDAO.find(result.getInt("id_users_borrower")), copyDAO.find(result.getInt("id_copy")).getOwner(), copyDAO.find(result.getInt("id_copy")));
				all_loans.add(new_loan);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_loans;	}
	
	/******METHODES PARTICULIERES POUR Loan*******/

}