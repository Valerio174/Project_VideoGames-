package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import be.walbert.classes.Administrator;
import be.walbert.classes.Booking;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;
 
public class BookingDAO extends DAO<Booking> {
	    
	
	public BookingDAO(Connection conn) {
		super(conn); 
	}
	
	/******Methodes communes (CRUD)*******/

	@Override
	public boolean create(Booking booking) {
		try{
			/*Requete pour insérer les données dans la table Booking*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Booking(bookingDate, id_VideoGame,id_users, number_of_weeks) VALUES(?,?,?,?)");
			ps.setDate(1,  Date.valueOf(booking.getBookingDate()));
			ps.setInt(2, booking.getGame().getId_videogame());  
			ps.setInt(3,  booking.getPlayer().getId_users());
			ps.setInt(4,  booking.getNumber_weeks());
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
	public boolean delete(Booking booking) {
		try{
			/*Requete pour supprimer les données de la table Booking*/
			PreparedStatement ps = connect.prepareStatement("DELETE FROM Booking WHERE id_Booking=? ");
			ps.setInt(1, booking.getId_booking());
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
	public boolean update(Booking obj) {
		return false;
	}

	@Override
	public Booking find(int id) {
		Booking booking = new Booking();
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT Booking.id_Booking, Booking.bookingDate, Booking.number_of_weeks, Booking.id_VideoGame, VideoGame.name, VideoGame.creditCost, Version.name_version, Console.name_console, Booking.id_users, Users.username, Users.password, Player.credit, Player.pseudo, Player.registrationDate, Player.dateOfBirth\r\n"
							+ "FROM Console INNER JOIN (Version INNER JOIN (VideoGame INNER JOIN (Users INNER JOIN (Player INNER JOIN Booking ON Player.id_users = Booking.id_users) ON Users.id_users = Player.id_users) ON VideoGame.id_VideoGame = Booking.id_VideoGame) ON Version.id_version = VideoGame.id_version) ON Console.id_console = Version.id_console\r\n"
							+ "WHERE Booking.id_Booking="+ id);
			
			 if(result.first()) {
				  booking = new Booking(result.getInt("id_Booking"), result.getDate("bookingDate").toLocalDate(), new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console")), new Player(result.getInt("id_users"),result.getString("username"), result.getString("password"), result.getInt("credit"), result.getString("pseudo"), result.getDate("registrationDate").toLocalDate(),
							result.getDate("dateOfBirth").toLocalDate()), result.getInt("number_of_weeks"));
			 }
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return booking;
		 
	}

	@Override
	public ArrayList<Booking> findAll() {
		ArrayList<Booking> all_bookings = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Booking");
			 
			while(result.next()){
				PlayerDAO playerDAO = new PlayerDAO(this.connect);
				VideoGameDAO videogameDAO =  new VideoGameDAO(this.connect);
				Booking newbooking = new Booking(result.getInt("id_Booking"), result.getDate("bookingDate").toLocalDate(), videogameDAO.find(result.getInt("id_VideoGame")), playerDAO.find(result.getInt("id_users")), result.getInt("number_of_weeks"));
				all_bookings.add(newbooking);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_bookings;
	}

	/******METHODES PARTICULIERES POUR Booking*******/
}
