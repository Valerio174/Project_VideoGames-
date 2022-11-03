package be.walbert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.classes.Copy;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;

public class CopyDAO extends DAO<Copy> {

	public CopyDAO(Connection conn) {
		super(conn); 
	}

	@Override
	public boolean create(Copy obj) { 
		return false;
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
	public Copy find(Copy obj) { 
		return null;
	}
	
	public ArrayList<Copy> findAllCopy(VideoGame videogame){
		ArrayList<Copy> list_copy = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Copy.id_copy, Copy.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Users.username, Users.password, Player.credit, Player.pseudo, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM ((((Copy LEFT JOIN VideoGame ON Copy.id_VideoGame = VideoGame.id_VideoGame) "
							+ "LEFT JOIN Version ON VideoGame.id_version = Version.id_version) "
							+ "LEFT JOIN Console ON Version.id_console = Console.id_console) "
							+ "LEFT JOIN Player ON Copy.id_users_lender = Player.id_users) "
							+ "LEFT JOIN Users ON Player.id_users = Users.id_users WHERE Copy.id_VideoGame = " + videogame.getId_videogame());
			while(result.next()){
				Copy newcopy = new Copy(result.getInt("id_copy"), new Player(result.getString("username"),result.getString("password"),
						result.getInt("credit"), result.getString("pseudo"), result.getDate("registrationDate").toLocalDate(),
						result.getDate("dateOfBirth").toLocalDate()), videogame);
				list_copy.add(newcopy);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list_copy;
		
	}

	
}
