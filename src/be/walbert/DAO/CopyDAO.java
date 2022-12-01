package be.walbert.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.classes.Copy;
import be.walbert.classes.Loan;
import be.walbert.classes.Player; 
import be.walbert.classes.VideoGame;

public class CopyDAO extends DAO<Copy> {

	public CopyDAO(Connection conn) {
		super(conn); 
	}
	
	/******Methodes communes (CRUD)*******/

	@Override
	public boolean create(Copy copy) { 
		try{
			/*Requete pour insérer les données dans la table Copy*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Copy(id_VideoGame,id_users_lender) VALUES(?,?)");
			ps.setInt(1,  copy.getGame().getId_videogame());
			ps.setInt(2, copy.getOwner().getId_users());  
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
	public boolean delete(Copy obj) { 
		return false;
	}

	@Override
	public boolean update(Copy obj) { 
		return false;
	}

	@Override
	public Copy find(int id) { 
		Copy copy = new Copy();
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, Copy.id_VideoGame, Copy.id_users_lender, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Users.username, Users.password, Player.credit, Player.pseudo, Player.birthday_bonus, Player.registrationDate, Player.dateOfBirth, Loan.id_Loan\r\n"
							+ "FROM ((Console INNER JOIN Version ON Console.id_console = Version.id_console) INNER JOIN (Users INNER JOIN (VideoGame INNER JOIN (Player INNER JOIN Copy ON Player.id_users = Copy.id_users_lender) ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Users.id_users = Player.id_users) ON Version.id_version = VideoGame.id_version) LEFT JOIN Loan ON Copy.id_copy = Loan.id_copy\r\n"
							+ "WHERE Copy.id_copy ="+id+" AND Loan.id_Loan Is Null");
			
			if(result.first()) {
				copy = new Copy(result.getInt("id_copy"), new Player(result.getInt("id_users_lender"),result.getString("username"), 
						result.getString("password"), result.getInt("credit"), result.getString("pseudo"),
						result.getBoolean("birthday_bonus"),
						result.getDate("registrationDate").toLocalDate(),
						result.getDate("dateOfBirth").toLocalDate()), 
						new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console")));		
			}
			else {
				result = this.connect.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, Loan.id_Loan, Loan.startDate, Loan.endDate, Loan.ongoing, Loan.id_users_borrower, Users.username, Users.password, Player.credit, Player.pseudo, Player.birthday_bonus, Player.registrationDate, Player.dateOfBirth, VideoGame.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console\r\n"
								+ "FROM Console INNER JOIN (Version INNER JOIN (VideoGame INNER JOIN (Users INNER JOIN (Player INNER JOIN (Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy) ON Player.id_users = Loan.id_users_borrower) ON Users.id_users = Player.id_users) ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Version.id_version = VideoGame.id_version) ON Console.id_console = Version.id_console\r\n"
								+ "WHERE Copy.id_copy="+id+" AND Loan.ongoing<>True");
				if(result.next()) {
				copy = new Copy(result.getInt("id_copy"), new Player(result.getInt("id_users_borrower"),result.getString("username"), 
						result.getString("password"), result.getInt("credit"), result.getString("pseudo"),
						result.getBoolean("birthday_bonus"),
						result.getDate("registrationDate").toLocalDate(),
						result.getDate("dateOfBirth").toLocalDate()), 
						new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console")));		
				} 
					result = this.connect.createStatement(
							ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, Loan.id_Loan, Loan.startDate, Loan.endDate, Loan.ongoing, Loan.id_users_borrower, Users.username, Users.password, Player.credit, Player.pseudo, Player.birthday_bonus, Player.registrationDate, Player.dateOfBirth, VideoGame.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console\r\n"
									+ "FROM Console INNER JOIN (Version INNER JOIN (VideoGame INNER JOIN (Users INNER JOIN (Player INNER JOIN (Copy INNER JOIN Loan ON Copy.id_copy = Loan.id_copy) ON Player.id_users = Loan.id_users_borrower) ON Users.id_users = Player.id_users) ON VideoGame.id_VideoGame = Copy.id_VideoGame) ON Version.id_version = VideoGame.id_version) ON Console.id_console = Version.id_console\r\n"
									+ "WHERE Copy.id_copy="+id+" AND Loan.ongoing<>False");
						if(result.next()) {
							copy = new Copy(result.getInt("id_copy"), new Player(result.getInt("id_users_borrower"),result.getString("username"), 
									result.getString("password"), result.getInt("credit"), result.getString("pseudo"),
									result.getBoolean("birthday_bonus"),
									result.getDate("registrationDate").toLocalDate(),
									result.getDate("dateOfBirth").toLocalDate()), 
									new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console")));		
							
							Loan lend = new Loan(result.getInt("id_Loan"), result.getDate("startDate").toLocalDate(), result.getDate("endDate").toLocalDate(),result.getBoolean("ongoing"), 
									new Player(result.getInt("id_users_borrower"),result.getString("username"), 
											result.getString("password"), result.getInt("credit"), result.getString("pseudo"),
											result.getBoolean("birthday_bonus"),
											result.getDate("registrationDate").toLocalDate(),
											result.getDate("dateOfBirth").toLocalDate()), 
									copy.getOwner(), 
									copy);
							copy.setLend(lend);
						}
				
			}
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return copy;
	}
	
	public ArrayList<Copy> findAll(){
		ArrayList<Copy> list_copy = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, Copy.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Users.id_users, Users.Users.username, Users.password, Player.credit, Player.pseudo, Player.birthday_bonus, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM ((((Copy LEFT JOIN VideoGame ON Copy.id_VideoGame = VideoGame.id_VideoGame) "
							+ "LEFT JOIN Version ON VideoGame.id_version = Version.id_version) "
							+ "LEFT JOIN Console ON Version.id_console = Console.id_console) "
							+ "LEFT JOIN Player ON Copy.id_users_lender = Player.id_users) "
							+ "LEFT JOIN Users ON Player.id_users = Users.id_users ");
			while(result.next()){
				VideoGameDAO videogameDAO = new VideoGameDAO(this.connect);
				Copy newcopy = new Copy(result.getInt("id_copy"), new Player(result.getInt("id_users"),result.getString("username"),result.getString("password"),
						result.getInt("credit"), result.getString("pseudo"), result.getBoolean("birthday_bonus"),result.getDate("registrationDate").toLocalDate(),
						result.getDate("dateOfBirth").toLocalDate()), videogameDAO.find(result.getInt("id_VideoGame")));
				list_copy.add(newcopy);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list_copy;
	}
}