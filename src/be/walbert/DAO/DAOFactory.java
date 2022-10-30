package be.walbert.DAO;

import java.sql.Connection;

import be.walbert.classes.Player;
import be.walbert.classes.User;
import be.walbert.classes.VideoGame;

public class DAOFactory extends AbstractDAOFactory{
	protected static final Connection conn = be.walbert.connection.ProjectConnection.getInstance();
	
//	public DAO<Administrator> getAdministratorDAO(){
//		return new AdministratorDAO(conn);
//	}
//	
//	public DAO<Booking> getBookingDAO(){
//		return new BookingDAO(conn);
//	}
//	
//	public DAO<Copy> getCopyDAO(){
//		return new CopyDAO(conn);
//	}
	
//	public DAO<Loan> getLoanDAO(){
//	return new LoanDAO(conn);
//}
	public DAO<Player> getPlayerDAO(){
	return new PlayerDAO(conn);
}
	public DAO<User> getUserDAO(){
		return new UserDAO(conn);
	}
		
	public DAO<VideoGame> getVideoGameDAO(){
		return new VideoGameDAO(conn);
}
	
}
