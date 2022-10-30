package be.walbert.DAO;

import java.sql.Connection;
import be.walbert.classes.VideoGame;

public class VideoGameDAO extends DAO<VideoGame>{

	public VideoGameDAO(Connection conn) {
		super(conn); 
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
