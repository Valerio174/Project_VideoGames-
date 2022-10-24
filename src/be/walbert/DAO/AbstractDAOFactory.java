package be.walbert.DAO;

import be.walbert.classes.User;

public abstract class AbstractDAOFactory {
	public static final int DAO_FACTORY = 0;
	public static final int XML_DAO_FACTORY = 1;

//	public abstract DAO<Administrator> getAdministratorDAO();
//	
//	public abstract DAO<Booking> getBookingDAO();
//		
//	public abstract DAO<Copy> getCopyDAO();
	
//	public abstract DAO<Loan> getLoanDAO();
	
//	public abstract DAO<Player> getPlayerDAO();
	
	public abstract DAO<User> getUserDAO();
	
//	public abstract DAO<VideoGames> getVideoGamesDAO();
	
	
	public static AbstractDAOFactory getFactory(int type){
		switch(type){
		case DAO_FACTORY:
			return new DAOFactory();
			default:
				return null;
		}
	}
}
