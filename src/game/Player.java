package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player {
	
	public Vector pos = new Vector(0,0);
	public int angle;
	public int health;
	public Image imgTop;
	public Image imgBottom;
	//public Vector bvel = new Vector(0,-10);
	
	public Player(int xx, int yy, String t, String b) throws SlickException{
		
		pos.x = xx;
		pos.y = yy;
		imgTop = new Image(t);
		imgBottom = new Image(b);
		health = 100;
		angle = 90;
		
		
	}
	/* Depreciated
	public Bullet shoot() throws SlickException{
		return new Bullet(pos.x + 24, pos.y,bvel.x,bvel.y,bulletImg);
		
	}
	*/

}
