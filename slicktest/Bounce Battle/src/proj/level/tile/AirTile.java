package proj.level.tile;

import proj.physics.AABoundingRect;

public class AirTile extends Tile{
	
	public AirTile(int x, int y) {
        super(x, y);
        new AABoundingRect(x*32,y*32,32,32);
    }
	
}
