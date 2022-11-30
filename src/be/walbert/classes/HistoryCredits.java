package be.walbert.classes;

import java.io.Serializable;
import java.time.LocalDate;

import be.walbert.DAO.AbstractDAOFactory;
import be.walbert.DAO.DAO;
import be.walbert.DAO.VideoGameDAO;

public class HistoryCredits implements Serializable{

	private static final long serialVersionUID = -851300193647142222L;
	
	/*Attributs*/
	private int id_history;
	private LocalDate modification_date;
	private int old_creditCost;
	private int new_creditCost;
	VideoGame videogame;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<HistoryCredits> historycreditsDAO = adf.getHistoryCredits();
	
	/*Getters/Setters*/
	public int getId_history() {
		return id_history;
	}
	public void setId_history(int id_history) {
		this.id_history = id_history;
	}
	
	public LocalDate getModification_date() {
		return modification_date;
	}
	public void setModification_date(LocalDate modification_date) {
		this.modification_date = modification_date;
	}
	
	public int getOld_creditCost() {
		return old_creditCost;
	}
	public void setOld_creditCost(int old_creditCost) {
		this.old_creditCost = old_creditCost;
	}
	
	public int getNew_creditCost() {
		return new_creditCost;
	}
	public void setNew_creditCost(int new_creditCost) {
		this.new_creditCost = new_creditCost;
	}
	
	public VideoGame getVideogame() {
		return videogame;
	}
	public void setVideogame(VideoGame videogame) {
		this.videogame = videogame;
	}
	
	/*Constructeurs*/
	public HistoryCredits() {}
		 
	public HistoryCredits(int id_history, LocalDate modification_date, int old_creditCost, int new_creditCost,
			VideoGame videogame) {
 		this.id_history = id_history;
		this.modification_date = modification_date;
		this.old_creditCost = old_creditCost;
		this.new_creditCost = new_creditCost;
		this.videogame = videogame;
	}	
	public HistoryCredits( LocalDate modification_date, int old_creditCost, int new_creditCost,
			VideoGame videogame) {
		this.modification_date = modification_date;
		this.old_creditCost = old_creditCost;
		this.new_creditCost = new_creditCost;
		this.videogame = videogame;
	}	
 	
	/*Méthodes*/
	public boolean ModifyCredits() {
		return historycreditsDAO.create(this);
	}
}
