package model.data;

import java.awt.Point;

import common.Level;
import common.Position;
import model.data.character.Floor;
import model.data.character.GameCharacters;
import model.data.character.Player;
import model.data.character.Target;

/**
 *
 * @author Adin Epstein
 * @since 25/12/2016
 * The class is in charge of moving the player and pushing the boxes
 */
public class Move {
	private Level level;
	private Position playerPos;
	private int xPlayer;
	private int yPlayer;

	public Move(Level level) {
		this.level=level;
		playerPos=level.getPlayerPosition();
		xPlayer=playerPos.getRow();
		yPlayer=playerPos.getCol();
		//level.getMap().get(xPlayer).get(yPlayer);
	}
	/**
	 * 	The method pushes a box to the position next to him
	 * @param characterNext the character that is in the position that the player is moving to
	 * @param characterNextNext the character that is in the position that is 2 places from the player
	 */
	public void push(GameCharacters characterNext, GameCharacters characterNextNext){
		boolean floorNextNext=characterNextNext.isFloorFlag();
		boolean floorNext=characterNext.isFloorFlag();
		int xNext=(int)characterNext.getPosition().getRow();
		int yNext=(int)characterNext.getPosition().getCol();
		int xNextNext=(int)characterNextNext.getPosition().getRow();
		int yNextNext=(int)characterNextNext.getPosition().getCol();

		characterNext.setPosition(characterNextNext.getPosition());
		characterNext.setFloorFlag(floorNextNext);
		characterNext.setTargetFlag(!floorNextNext);
		level.getMap().get(xNextNext).set(yNextNext, characterNext);
		if(floorNext){
			level.getMap().get(xNext).set(yNext, new Floor(xNext,yNext));
		}
		else{
			level.getMap().get(xNext).set(yNext, new Target(xNext,yNext));
			level.ReduceBoxOnTarget();
		}
		if(!floorNextNext){
			level.addBoxOnTarget();
		}

	}
	/**
	 * The method moves the player to the next position
	 * @param player the player object
	 * @param characterNext the character that is in the position that the player is moving to
	 */
	public void move(Player player, GameCharacters characterNext){
		boolean floorNext=characterNext.isFloorFlag();
		boolean floorPrev=player.isFloorFlag();
		int xPlayer=player.getPosition().getRow();
		int yPlayer=player.getPosition().getCol();
		int xNext=characterNext.getPosition().getRow();
		int yNext=characterNext.getPosition().getCol();

		player.setPosition(characterNext.getPosition());
		player.setFloorFlag(floorNext);
		player.setTargetFlag(!floorNext);
		level.getMap().get(xNext).set(yNext, player);
		level.setPlayerPosition(player.getPosition());

		if(floorPrev){
			level.getMap().get(xPlayer).set(yPlayer, new Floor(xPlayer,yPlayer));
		}
		else{
			level.getMap().get(xPlayer).set(yPlayer, new Target(xPlayer,yPlayer));
		}
		level.setNumOfSteps(level.getNumOfSteps()+1);
	}


}
