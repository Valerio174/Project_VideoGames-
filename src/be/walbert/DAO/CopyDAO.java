package be.walbert.DAO;

import java.sql.Connection;

import be.walbert.classes.Copy;

public class CopyDAO extends DAO<Copy> {

	public CopyDAO(Connection conn) {
		super(conn); 
	}

	@Override
	public boolean create(Copy obj) { 
		return false;
	}

	@Override
	public boolean delete(Copy obj) { 
		return false;
	}

	@Override
	public boolean update(Copy obj) { 
		return false;
	}

	@Override
	public Copy find(Copy obj) { 
		return null;
	}

	
}
