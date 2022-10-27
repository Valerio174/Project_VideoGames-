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
			
			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			
			PreparedStatement ps2 = connect.prepareStatement("INSERT INTO Player(id_users,credit, pseudo,registrationDate,dateOfBirth) VALUES(?,?,?,?,?)");
			ps2.setInt(1, generatedKey);
			ps2.setInt(2, 10);
			ps2.setString(3, newplayer.getPseudo());
			ps2.setDate(4, Date.valueOf(newplayer.getRegistrationDate()));
			ps2.setDate(5, Date.valueOf(newplayer.getDateOfBirth()));
			ps2.executeUpdate();
			
			ps.close();
			ps2.close();
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
