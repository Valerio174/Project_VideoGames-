package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import be.walbert.classes.Copy;
import be.walbert.classes.Loan;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;

public class LoanDAO extends DAO<Loan>{

	
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
		Loan loan = new Loan();
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Loan.id_Loan, Loan.startDate, Loan.endDate, Loan.ongoing, Loan.id_copy, Copy.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Copy.id_users_lender, Users.username, Users.password, Player.credit, Player.pseudo, Player.birthday_bonus, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Users INNER JOIN (Player INNER JOIN (Console INNER JOIN (Version INNER JOIN (VideoGame INNER JOIN (Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy) ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Version.id_version = VideoGame.id_version) ON Console.id_console = Version.id_console) ON Player.id_users = Copy.id_users_lender) ON Users.id_users = Player.id_users\r\n"
							+ "WHERE Loan.id_Loan="+id);
			ResultSet result2 = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Loan.id_Loan, Loan.startDate, Loan.endDate, Loan.ongoing, Loan.id_users_borrower, Users.username, Users.password, Player.credit, Player.pseudo, Player.birthday_bonus, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Users INNER JOIN (Player INNER JOIN Loan ON Player.id_users = Loan.id_users_borrower) ON Users.id_users = Player.id_users\r\n"
							+ "WHERE Loan.id_Loan="+id);
			 
			if(result.next() && result2.next()) {
				VideoGameDAO videogameDAO = new VideoGameDAO(this.connect);
				 loan = new Loan(result.getInt("id_Loan"), result.getDate("startDate").toLocalDate(), result.getDate("endDate").toLocalDate(),result.getBoolean("ongoing"),
						 new Player(result2.getInt("id_users_borrower"), result2.getString("username"), result2.getString("password"), result2.getInt("credit"), result2.getString("pseudo"), result2.getBoolean("birthday_bonus") , result2.getDate("registrationDate").toLocalDate(), result2.getDate("dateOfBirth").toLocalDate()), 
						 
						 new Player(result.getInt("id_users_lender"), result.getString("username"), result.getString("password"), result.getInt("credit"), result.getString("pseudo"), result.getBoolean("birthday_bonus") , result.getDate("registrationDate").toLocalDate(), result.getDate("dateOfBirth").toLocalDate()), 
						 
						 new Copy( result.getInt("id_copy"), 
								 loan.getLender(), videogameDAO.find(result.getInt("id_VideoGame"))));
 			 }
 			 
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return loan;
	}

	@Override
	public ArrayList<Loan> findAll() {
		ArrayList<Loan> all_loans = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Loan");
			 
			while(result.next()){
				 PlayerDAO playerDAO = new PlayerDAO(connect);
				 CopyDAO copyDAO = new CopyDAO(connect);;
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