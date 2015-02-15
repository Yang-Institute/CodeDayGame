package game;

import org.newdawn.slick.SlickException;

public class NonPlayer extends Player {

	public int edeg = 90;
	//public Vector vel;

	public NonPlayer(int x, int y, int h, String t, String b)
			throws SlickException {
		super(x, y, t, b);
		health = h;
		angle = 90;
		//angle = (int) Math.random() * 360;
		//vel = new Vector((int) (3 * Math.cos(Math.toRadians(angle))),
		//		(int) (3 * Math.sin(Math.toRadians(angle))));
	}

}
