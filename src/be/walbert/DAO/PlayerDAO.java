package be.walbert.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import be.walbert.classes.Player;

public class PlayerDAO extends DAO<Player>{
	public PlayerDAO(Connection conn) {
		super(conn);
	}

	/*Récupérer Player correspondant */
	@Override
	public Player find(Player newplayer) {
		return newplayer;
	}

	@Override
	public boolean create(Player newplayer) {
		try{
			//connect.close();
			PreparedStatement ps = connect.prepareStatement("INSERT INTO Users(username, password,type) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, newplayer.getUsername());
			ps.setString(2, newplayer.getPassword());
			ps.setString(3, "Player");
			
			ps.execute();
			
			
			ps.close();
			return true;
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Player obj) { 
		return false;
	}

	@Override
	public boolean update(Player obj) { 
		return false;
	}
}
