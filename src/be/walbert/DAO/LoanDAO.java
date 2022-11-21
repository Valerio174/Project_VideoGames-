package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.walbert.classes.Loan;

public class LoanDAO extends DAO<Loan>{
	public LoanDAO(Connection conn) {
		super(conn); 
	} 

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
	public boolean delete(Loan obj) { 
		return false;
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
		return null;
	}
}
