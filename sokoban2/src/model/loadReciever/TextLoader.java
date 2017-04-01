package model.loadReciever;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import common.Level;
import model.data.MyTextLevelLoader;
/**
 *
 * @author Adin Epstein
 * @since 23/12/2016
 * The class creates a text loader
 *
 */
public class TextLoader extends AbstractLoader {

	@Override
	public Level load(String file) throws IOException {
		Path p=Paths.get(file);
		String name=p.getFileName().toString();
		String [] levelName=file.split(".");
		
		FileInputStream fos=new FileInputStream(file);
		MyTextLevelLoader txtloader=new MyTextLevelLoader(levelName[0]);
		return txtloader.loadLevel(fos);

	}

}
