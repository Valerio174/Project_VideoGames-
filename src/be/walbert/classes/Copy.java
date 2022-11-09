
package be.walbert.classes;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.CopyDAO;
import be.walbert.DAO.DAO;
import be.walbert.DAO.LoanDAO; 

public class Copy{
	private static final long serialVersionUID = 1110485213455787467L;
	
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
		DAO<Loan> loan = (LoanDAO)loanDAO;
		
		loan.create(currentloan);
		
	}
	@Override
	public String toString() {
		return "Copy [id_copy=" + id_copy + ", owner=" + owner.getPseudo() + ", game=" + game + ", lend=" + lend + "]";
	}
	
	
}

