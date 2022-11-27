package be.walbert.classes;

import java.io.Serializable;
import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;

public class Copy implements Serializable{
 	
	private static final long serialVersionUID = 6781608518275076469L;
	/*Attributs*/
	private int id_copy;
	private Player owner;
	private VideoGame game;
	private Loan lend;
	static AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	static DAO<Copy> copyDAO = adf.getCopyDAO();
	DAO<Loan> loanDAO = adf.getLoanDAO();
	
	
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
	public Copy(int id_copy, Player owner, VideoGame game) {
		this.id_copy = id_copy;
		this.owner = owner;
		this.game = game;
	}	
	public Copy( Player owner, VideoGame game) {
		this.owner = owner;
		this.game = game;
	}	
	
	/*Méthodes*/

	public Copy() {
 	}
	public boolean CreateCopy() {
		return copyDAO.create(this);
	}
	public boolean IsAvailable(VideoGame game) {
//		CopyDAO copy = (CopyDAO)copyDAO;
//		 
//		if(copy.CopyAvailable(game).size() == 0) {
//			return false;
//		}
//		else {
//			return true;
//		}
		return false;
	}
	
	public void Borrow(Loan currentloan) {
	 	
		loanDAO.create(currentloan);
		
	}
	@Override
	public String toString() {
		return "Copy [id_copy=" + id_copy + ", owner=" + owner.getPseudo() + ", game=" + game + ", lend=" + lend + "]";
	}
	
	
}

