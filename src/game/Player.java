package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player {
	
	public Vector pos = new Vector(0,0);
	public int health;
	public Image img;
	public Image bulletImg;
	public Vector bvel = new Vector(0,-1);
	
	public Player(int xx, int yy, String i, String b) throws SlickException{
		
		pos.x = xx;
		pos.y = yy;
		img = new Image(i);
		bulletImg = new Image(b);
		health = 100;
		
		
	}
	
	public Bullet shoot() throws SlickException{
		return new Bullet(pos,bvel,bulletImg);
		
	}
	

}
