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
	private PlayerDAO playerDAO = new PlayerDAO(connect);
	private VideoGameDAO videogameDAO =  new VideoGameDAO(connect);
	
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
		try{
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Booking WHERE id_Booking ="+id);
			
			 if(result.first()) {
				 Booking new_booking = new Booking(result.getInt("id_Booking"), result.getDate("bookingDate").toLocalDate(), videogameDAO.find(result.getInt("id_VideoGame")), playerDAO.find(result.getInt("id_users")), result.getInt("number_of_weeks"));
				 return new_booking;
			 }
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ArrayList<Booking> findAll() {
		ArrayList<Booking> all_bookings = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Booking");
			 
			while(result.next()){
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
