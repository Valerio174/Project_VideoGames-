package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import be.walbert.classes.Booking;
import be.walbert.classes.Player;
import be.walbert.classes.VideoGame;

public class BookingDAO extends DAO<Booking> {
	private PlayerDAO playerDAO;
	private VideoGameDAO videogameDAO;
	
	public BookingDAO(Connection conn) {
		super(conn); 
	}

	@Override
	public boolean create(Booking booking) {
		try{
			/*Requete pour insérer les données dans la table Booking*/
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Booking(bookingDate, id_VideoGame,id_users) VALUES(?,?,?)");
			ps.setDate(1,  Date.valueOf(booking.getBookingDate()));
			ps.setInt(2, booking.getGame().getId_videogame());  
			ps.setInt(3,  booking.getPlayer().getId_users());
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
			/*Requete pour insérer les données dans la table Booking*/
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
					ResultSet.CONCUR_READ_ONLY).executeQuery("");
			 if(result.first()) {
				 Booking new_booking = new Booking(result.getInt("id_booking"), result.getDate("bookingDate").toLocalDate(), videogameDAO.find(result.getInt("id_VideoGame")), playerDAO.find(result.getInt("id_users")));
				 return new_booking;
			 }
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}

}
