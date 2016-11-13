package proj.state;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import proj.character.Player;
import proj.controller.MouseKeyboardPlayerController;
import proj.controller.PlayerController;
import proj.game.Game;
import proj.level.Level;

public class LevelState extends BasicGameState{
	
	Level level;
	String startingLevel;
	private Player player;
	private PlayerController playerController;

	public LevelState(String startingLevel){
		this.startingLevel = startingLevel;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		level = new Level(startingLevel);
		
		player = new Player(300,300);
        level.addCharacter(player);
        
        playerController = new MouseKeyboardPlayerController(player);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.scale(Game.SCALE, Game.SCALE);
        level.render();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		playerController.handleInput(gc.getInput(), delta);
	}

	//Exits game on ESCAPE press
	public void keyPressed(int key, char code){
        if(key == Input.KEY_ESCAPE){
            System.exit(0);
        }
    }
	
	public int getID() {
		return 0;
	}

}
