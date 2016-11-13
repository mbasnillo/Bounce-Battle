package proj.level;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import proj.character.Character;
import proj.level.tile.AirTile;
import proj.level.tile.SolidTile;
import proj.level.tile.Tile;

public class Level {
	private TiledMap map;
	
	//list of all characters
	public ArrayList<Character> characters;
	
	private Tile[][] tiles;
	
	public Level(String level) throws SlickException{
		map = new TiledMap("res/levels/" + level + ".tmx", "res/img/tiles/");
		characters = new ArrayList<Character>();
		
		loadTileMap();
	}
	
	public void loadTileMap(){
		tiles = new Tile[map.getWidth()][map.getHeight()];
		
		int layerIndex = map.getLayerIndex("CollisionLayer");
		
		if(layerIndex == -1){
            System.err.println("Map does not have the layer \"CollisionLayer\"");
            System.exit(0);
        }
		
		//loop through the whole map
        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y < map.getHeight(); y++){
 
                //get the tile
                int tileID = map.getTileId(x, y, layerIndex);
 
                Tile tile = null;
 
                //and check what kind of tile it is (
                switch(map.getTileProperty(tileID, "tileType", "solid")){
                    case "air":
                        tile = new AirTile(x,y);
                        break;
                    default:
                        tile = new SolidTile(x,y);
                        break;
                }
                tiles[x][y] = tile;
            }
        }
	}
		
	public void addCharacter(Character c){
        characters.add(c);
    }
	
	public ArrayList<Character> getCharacters(){
        return characters;
    }
 
    public Tile[][] getTiles(){
        return tiles;
    }
	
	public void render(){
		map.render(0, 0);
		
		for(Character c : characters){
			c.render();
		}
	}
}
