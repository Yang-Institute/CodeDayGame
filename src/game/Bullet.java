package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Bullet {
	
	public Vector pos = new Vector(0,0);
	public Vector vel = new Vector(0,0);
	
	public int bounce = 3;
	public Image img;
	
	
	public Bullet(int xx,int yy,int xxx,int yyy, String s) throws SlickException{
		
		pos.x = xx;
		pos.y = yy;
		vel.x = xxx;
		vel.y = yyy;
		img = new Image(s);
		
	}
	
	public Bullet(int xx,int yy,int xxx,int yyy, Image i) throws SlickException{
		
		pos.x = xx;
		pos.y = yy;
		vel.x = xxx;
		vel.y = yyy;
		img = i;
		
	}
	
	public Bullet(Vector poss, Vector vell, Image i) throws SlickException{
		pos = poss;
		vel = vell;
		img = i;
	}
	
}
