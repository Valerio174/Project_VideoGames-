
package be.walbert.classes;

public class Copy{

	/*Attributs*/
	private Player owner;
	private VideoGame game;
	private Loan lend;
	
	/*Getters/Setters*/
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
	public Copy(Player owner, VideoGame game, Loan lend) {
		this.owner = owner;
		this.game = game;
		this.lend = lend;
	}	
	public Copy(Player owner, VideoGame game) {
		this.owner = owner;
		this.game = game;
	}	
	
	/*Méthodes*/
	
}
