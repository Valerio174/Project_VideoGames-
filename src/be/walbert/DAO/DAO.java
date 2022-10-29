package be.walbert.DAO;

import java.sql.Connection;

import be.walbert.classes.Player;

public abstract class DAO<T> {
	protected Connection connect = null;
	
	public DAO(Connection conn){
		this.connect = conn;
	}
	
	public abstract boolean create(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract boolean update(T obj);
	
	public abstract T find(T obj);

	public abstract boolean findAccount(Player player);

}
