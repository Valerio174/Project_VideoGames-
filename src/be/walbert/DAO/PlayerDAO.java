package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import be.walbert.classes.Player;
 
 
public class PlayerDAO extends DAO<Player>{
  	
	public PlayerDAO(Connection conn) {
		super(conn);
	}

	/******Methodes communes (CRUD)*******/
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
	public boolean delete(Player newplayer) { 
		try{
			/*Requete pour supprimer les données dans la table Player*/
			PreparedStatement ps = connect.prepareStatement("DELETE FROM Player WHERE id_users=? ");
			ps.setInt(1, newplayer.getId_users());
			ps.execute();	/*Exécuter la requête*/
			ps.close();
			
			PreparedStatement ps2 = connect.prepareStatement("DELETE FROM Users WHERE id_users=? ");
			ps2.setInt(1, newplayer.getId_users());
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
	public boolean update(Player newplayer) { 
		try{
			/*Requete pour mettre à jour les données dans la table Player*/
			PreparedStatement ps = connect.prepareStatement("UPDATE Player SET credit = ?, birthday_bonus = ? WHERE id_users = ?");
			ps.setInt(1, newplayer.getCredits());
			ps.setBoolean(2, newplayer.GetBirthday_bonus());
			ps.setInt(3, newplayer.getId_users());
			
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
	public Player find(int id_users) { 
		UserDAO userDAO = new UserDAO(this.connect);
		Player p = (Player) userDAO.find(id_users);
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Player WHERE id_users="+id_users);
			if(result.first()) {
				result = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Copy WHERE id_users_lender="+id_users);
				CopyDAO copyDAO =new CopyDAO(this.connect);
				while(result.next())
					p.AddCopy(copyDAO.find(result.getInt("id_copy")));
					LoanDAO loanDAO = new LoanDAO(this.connect);
					result = this.connect.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Loan WHERE id_users_borrower="+id_users);
				while(result.next())
					p.AddBorrow(loanDAO.find(result.getInt("id_Loan")));
					result = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT *\r\n"
								+ "FROM Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy\r\n"
								+ "WHERE Copy.id_users_lender="+id_users);
				while(result.next())
					p.AddLender(loanDAO.find(result.getInt("id_Loan")));
					BookingDAO bookingDAO = new BookingDAO(this.connect);
					result = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Booking WHERE id_users ="+id_users);
				while(result.next())
					p.AddBooking(bookingDAO.find(result.getInt("id_Booking")));
	 			
			}
		} 
		catch(SQLException e){
			e.printStackTrace();
		}
		return p;
	
	}
	
	@Override
	public ArrayList<Player> findAll() {
		ArrayList<Player> all_players = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Users.id_users, Users.username, Users.password, Player.credit, Player.pseudo, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Users INNER JOIN Player ON Users.id_users = Player.id_users");
			 
			while(result.next()){
				Player newplayer =  this.find(result.getInt("id_users"));
				all_players.add(newplayer);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_players;
	}
	
	/******METHODES PARTICULIERES POUR Player*******/
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


}