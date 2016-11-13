package proj.game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import proj.state.LevelState;

public class Game extends StateBasedGame{

	//Game info
	public static final String GAME_NAME = "Bounce Battle";
	public static final int GAME_WIDTH = 1080;
	public static final int GAME_HEIGHT = GAME_WIDTH / 16 * 9; //ensures a 16:9 aspect ratio
	public static final float SCALE = (float)(1.25*((double)GAME_WIDTH/1280));
	public static final boolean GAME_FULLSCREEN = false;
	
	public Game() {
		super(GAME_NAME);
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		
		 //create a level state, this state will do the whole logic and rendering for individual levels
        addState(new LevelState("level_0"));
        this.enterState(0);
	}
	

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game());
		
		app.setDisplayMode(GAME_WIDTH, GAME_HEIGHT, GAME_FULLSCREEN);
		app.setTargetFrameRate(60);
		
		app.start();
	}

}
