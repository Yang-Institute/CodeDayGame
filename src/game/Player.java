package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Player {
	
	public int xpos;
	public int ypos;
	public Image img;
	
	public Player(int x, int y, String i) throws SlickException{
		
		xpos = x;
		ypos = y;
		img = new Image(i);
		
	}
	
	
}