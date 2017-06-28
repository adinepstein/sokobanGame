package common;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import model.data.character.Box;
import model.data.character.GameCharacters;
import model.data.character.Target;

public class Level implements Serializable {

	/**
	 * @author Adin Epstein
	 * @since 20/12/2016 This class holds all the data of the level
	 */
	private static final long serialVersionUID = 4341899718648579988L;
	private ArrayList<ArrayList<GameCharacters>> map;
	private Position startPoint;
	private Position playerPosition;
	private int numOfTargets;
	private int numOfBoxOnTarget;
	private int height;
	private int width;
	private int numOfSteps;
	private int finishTime;
	private String playerName;
	private String LevelName;
	private ArrayList<Box> boxList;
	private ArrayList<Target> targetList;

	public Level() {
		map = new ArrayList<ArrayList<GameCharacters>>();
		startPoint = new Position();
		playerPosition = new Position();
		numOfTargets = 0;
		numOfBoxOnTarget = 0;
		numOfSteps = 0;
		boxList= new ArrayList<Box>();
		targetList= new ArrayList<Target>();
	}

	/**
	 *
	 * @return the number of targets in the level
	 */
	public int getNumOfTargets() {
		return numOfTargets;
	}

	public ArrayList<Box> getBoxList() {
		return boxList;
	}

	public void setBoxList(ArrayList<Box> boxList) {
		this.boxList = boxList;
	}

	public ArrayList<Target> getTargetList() {
		return targetList;
	}

	public void setTargetList(ArrayList<Target> targetList) {
		this.targetList = targetList;
	}

	/**
	 *
	 * @param sets
	 *            the number of targets in the level- used only for the
	 *            serializing dose'nt change the setting on the map
	 */
	public void setNumOfTargets(int numOfTargets) {
		this.numOfTargets = numOfTargets;
	}

	/**
	 *
	 * @return the start point of the player
	 */
	public Position getStartPoint() {
		return startPoint;
	}

	/**
	 *
	 * @param sets
	 *            the start point- used only for the serializing dose'nt change
	 *            the setting on the map
	 */
	public void setStartPoint(Position startPoint) {
		this.startPoint = startPoint;// to check if does a problem
	}

	/**
	 *
	 * @return the position of the player using the point class
	 */
	public Position getPlayerPosition() {
		return playerPosition;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getLevelName() {
		return LevelName;
	}

	public void setLevelName(String levelName) {
		LevelName = levelName;
	}

	/**
	 *
	 * @param sets
	 *            the player position in the level- used only for the
	 *            serializing dose'nt change the setting on the map
	 */
	public void setPlayerPosition(Position player) {
		this.playerPosition = player;
	}

	/**
	 *
	 * @return the map of the game
	 */
	public ArrayList<ArrayList<GameCharacters>> getMap() {
		return map;
	}

	/**
	 *
	 * @param map
	 *            the map of the game-used only for the serializing dose'nt
	 *            change the setting on the map
	 */
	public void setMap(ArrayList<ArrayList<GameCharacters>> map) {
		this.map = map;
	}

	/**
	 * adds one to the number of boxes in target
	 */
	public void addBoxOnTarget() {
		numOfBoxOnTarget++;
	}

	/**
	 * reduces one to the number of boxes in target
	 */
	public void ReduceBoxOnTarget() {
		numOfBoxOnTarget--;
	}

	/**
	 *
	 * @return number of boxes on target
	 */
	public int getNumOfBoxOnTarget() {
		return numOfBoxOnTarget;
	}

	/**
	 *
	 * @param numOfBoxOnTarget
	 *            used only for the serializing
	 */
	public void setNumOfBoxOnTarget(int numOfBoxOnTarget) {
		this.numOfBoxOnTarget = numOfBoxOnTarget;
	}

	/**
	 * adding a target to the count
	 */
	public void addTarget() {
		numOfTargets++;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getNumOfSteps() {
		return numOfSteps;
	}

	public void setNumOfSteps(int numOfSteps) {
		this.numOfSteps = numOfSteps;
	}

	public char[][] getCharMap() {
		char charMap[][] = new char[getHeight()][getWidth()];
		for (int i = 0; i < map.size(); i++) {
			int size = map.get(i).size();
			for (int j = 0; j < size; j++)
				charMap[i][j] = map.get(i).get(j).getRepresent();

		}
		return charMap;
	}

	

}
