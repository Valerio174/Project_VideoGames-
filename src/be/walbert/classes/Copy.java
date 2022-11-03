
package be.walbert.classes;

import java.util.ArrayList;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.CopyDAO;
import be.walbert.DAO.DAO; 

public class Copy{
	private static final long serialVersionUID = 1110485213455787467L;
	
	/*Attributs*/
	private int id_copy;
	private Player owner;
	private VideoGame game;
	private Loan lend;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<Copy> copyDAO = adf.getCopyDAO();
	
	
	/*Getters/Setters*/
	public int getId_copy() {
		return id_copy;
	}
	public void setId_copy(int id_copy) {
		this.id_copy = id_copy;
	}
	public Player getOwner() {
		return owner;
	}
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	public VideoGame getGame() {
		return game;
	}
	public void setGame(VideoGame game) {
		this.game = game;
	}
	public Loan getLend() {
		return lend;
	}
	public void setLend(Loan lend) {
		this.lend = lend;
	}

	/*Constructeurs*/
	public Copy(int id_copy, Player owner, VideoGame game, Loan lend) {
		this.id_copy = id_copy;
		this.owner = owner;
		this.game = game;
		this.lend = lend;
	}	
	public Copy(int id_copy, Player owner, VideoGame game) {
		this.id_copy = id_copy;
		this.owner = owner;
		this.game = game;
	}	
	
	/*Méthodes*/
	public static ArrayList<Copy> getAll(VideoGame videogame){
		CopyDAO copy = (CopyDAO)(copyDAO);
		return copy.findAllCopy(videogame);
	}
}
