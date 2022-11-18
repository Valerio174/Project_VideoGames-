package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.classes.Copy;
import be.walbert.classes.Loan;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;

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
	public Player find(int id_users) { 
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Users.id_users, Users.username, Users.password, Player.credit, Player.pseudo, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Users INNER JOIN Player ON Users.id_users = Player.id_users\r\n"
							+ "WHERE (((Users.id_users)=\""+id_users+"\"));\r\n"
							+ "");
			 
			if(result.first()) {
				Player p = new Player(result.getInt("id_users"),result.getString("username"), result.getString("password"), result.getInt("id_users"), result.getString("pseudo"), result.getDate("registrationDate").toLocalDate(),
						result.getDate("dateOfBirth").toLocalDate());
				p.setCopy_list(GetAllCopy(p));
				p.setBorrow_list(GetAllBorrows(p));
				p.setLender_list(GetAllLoans(p));
				return p;
			}
			else
				return null;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<Copy> GetAllCopy(Player player){
		ArrayList<Copy> all_copy = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, VideoGame.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Copy.id_users_lender\r\n"
							+ "FROM (Console INNER JOIN Version ON Console.id_console = Version.id_console) INNER JOIN (VideoGame INNER JOIN Copy ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Version.id_version = VideoGame.id_version\r\n"
							+ "WHERE (((Copy.id_users_lender)="+player.getId_users()+"));\r\n"
							+ "");
			while(result.next()){
				Copy newcopy = new Copy(result.getInt("id_copy"),player, new VideoGame(result.getInt("id_VideoGame"),result.getString("name"), result.getInt("creditCost"), result.getString("name_version"), result.getString("name_console")));
				all_copy.add(newcopy);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_copy;
	}
	
	public ArrayList<Loan> GetAllBorrows(Player borrower){
		ArrayList<Loan> all_borrows = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Loan.startDate, Loan.endDate, Loan.ongoing FROM Console INNER JOIN (Version INNER JOIN (VideoGame INNER JOIN (Users INNER JOIN (Player INNER JOIN "
							+ "(Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy) ON Player.id_users = Loan.id_users_borrower) "
							+ "ON Users.id_users = Player.id_users) ON VideoGame.id_VideoGame = Copy.id_VideoGame) "
							+ "ON Version.id_version = VideoGame.id_version) ON Console.id_console = Version.id_console WHERE id_users_borrower="+borrower.getId_users());
			ResultSet result2 = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, Copy.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Copy.id_users_lender, Users.username, Users.password, Player.credit, Player.pseudo, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Users INNER JOIN ((Console INNER JOIN Version ON Console.id_console = Version.id_console) INNER JOIN (VideoGame INNER JOIN (Player INNER JOIN (Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy) ON Player.id_users = Copy.id_users_lender) ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Version.id_version = VideoGame.id_version) ON Users.id_users = Player.id_users\r\n"
							+ "WHERE Loan.id_users_borrower ="+borrower.getId_users());
			
			while(result.next() && result2.next()){
				Player lender = new Player(result2.getInt("id_users_lender"), result2.getString("username") , result2.getString("password"), result2.getInt("credit"), result2.getString("pseudo"), result2.getDate("registrationDate").toLocalDate(),
						result2.getDate("dateOfBirth").toLocalDate());
				Copy copy = new Copy(result2.getInt("id_copy"),lender,new VideoGame(result2.getInt("id_VideoGame"), result2.getString("name") ,  result2.getInt("creditCost"),  result2.getString("name_version"), result2.getString("name_console")));
				Loan newloan = new Loan(result.getDate("startDate").toLocalDate(), result.getDate("endDate").toLocalDate(), result.getBoolean("ongoing"), borrower, lender, copy);
				all_borrows.add(newloan);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_borrows;
	}
	
	public ArrayList<Loan> GetAllLoans(Player lender){
		ArrayList<Loan> all_loans = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Loan.startDate, Loan.endDate, Loan.ongoing, Loan.id_copy,"
							+ " Copy.id_users_lender, Users.username, Users.password, Player.credit, Player.pseudo, Player.registrationDate, "
							+ "Player.dateOfBirth, Copy.id_VideoGame, VideoGame.name, VideoGame.creditCost, "
							+ "Version.name_version, Console.name_console\r\n"
							+ "FROM Console INNER JOIN (Version INNER JOIN (VideoGame INNER JOIN "
							+ "(Users INNER JOIN (Player INNER JOIN (Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy) "
							+ "ON Player.id_users = Copy.id_users_lender) ON Users.id_users = Player.id_users) "
							+ "ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Version.id_version = VideoGame.id_version)"
							+ " ON Console.id_console = Version.id_console\r\n"
							+ "WHERE (((Copy.id_users_lender)="+lender.getId_users()+"))");
			ResultSet result2 = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, Copy.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Copy.id_users_lender, Users.username, Users.password, Player.credit, Player.pseudo, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "	FROM Users INNER JOIN ((Console INNER JOIN Version ON Console.id_console = Version.id_console) INNER JOIN (VideoGame INNER JOIN (Player INNER JOIN (Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy) ON Player.id_users = Copy.id_users_lender) ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Version.id_version = VideoGame.id_version) ON Users.id_users = Player.id_users\r\n"
							+ "	WHERE Loan.id_users_borrower ="+lender.getId_users());
			
			while(result.next() && result2.next()){
				Player borrower = new Player(result2.getInt("id_users_lender"), result2.getString("username") , result2.getString("password"), result2.getInt("credit"), result2.getString("pseudo"), result2.getDate("registrationDate").toLocalDate(),
						result2.getDate("dateOfBirth").toLocalDate());
				Copy copy = new Copy(result2.getInt("id_copy"),lender,new VideoGame(result2.getInt("id_VideoGame"), result2.getString("name") ,  result2.getInt("creditCost"),  result2.getString("name_version"), result2.getString("name_console")));
				Loan newloan = new Loan(result.getDate("startDate").toLocalDate(), result.getDate("endDate").toLocalDate(), result.getBoolean("ongoing"), borrower, lender, copy);
				all_loans.add(newloan);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_loans;
	}
	

}