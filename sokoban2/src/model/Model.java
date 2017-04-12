package model;

import java.io.FileNotFoundException;
import java.io.IOException;

import common.Level;
import model.DB.DBReciver;
import model.policy.Mover;
import model.saveReciever.Saver;

public interface Model {
	public Level getLevel();
	public void setLevel(Level level);
	public Mover getMover();
	public void setMover(Mover mover);
	public void move(String direction);
	public void saving(Saver saver,String path) throws FileNotFoundException, IOException;
	public void dataQuery(String queryType,String type, String name);
	public DBReciver getDBReciver();
	public String searchQuery(String Name);
	public void addData();

}
