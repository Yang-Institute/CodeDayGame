package game;

import org.newdawn.slick.SlickException;

public class NonPlayer extends Player{

	public int edeg = 90;	
	
	public NonPlayer(int x,int y, int h,String t,String b) throws SlickException{
		super(x,y,t,b);
		health = h;
		angle = 90;
	}
	
}
