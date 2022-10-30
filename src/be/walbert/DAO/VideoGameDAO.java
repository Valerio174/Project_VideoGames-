package be.walbert.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.walbert.classes.VideoGame;

public class VideoGameDAO extends DAO<VideoGame>{

	public VideoGameDAO(Connection conn) {
		super(conn); 
	}

	public ArrayList<VideoGame> findAll(){
		ArrayList<VideoGame> all_videogames = new ArrayList<>();
		
		try {
			ResultSet result = this.connect.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT vg.id_VideoGame, vg.name, vg.creditCost, v.name_version, c.name_console\r\n"
							+ "FROM ( VideoGame vg LEFT OUTER JOIN Version v ON vg.id_version = v.id_version ) LEFT OUTER JOIN Console c ON v.id_console= c.id_console");
			while(result.next()){
				VideoGame newvideogame = new VideoGame(result.getInt("id_VideoGame"),result.getString("name"),result.getInt("creditCost"),result.getString("name_version"),result.getString("name_console"));
				all_videogames.add(newvideogame);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return all_videogames;
		
	}
	@Override
	public boolean create(VideoGame obj) { 
		return false;
	}

	@Override
	public boolean delete(VideoGame obj) { 
		return false;
	}

	@Override
	public boolean update(VideoGame obj) { 
		return false;
	}

	@Override
	public VideoGame find(VideoGame obj) { 
		return null;
	}

}
