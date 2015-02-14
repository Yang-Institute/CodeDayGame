package game;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.Particle;
import org.newdawn.slick.particles.ParticleSystem;

public class Bullet extends Particle{
//	private int damage;

	protected float velx;
	protected float vely;
	protected float x;
	protected float y;
	
	public Bullet(int xPlace, int yPlace) {
		super(new ExistingBullets());
		velx = 0;
		vely = 15;
		x = xPlace;
		y = yPlace;
	}
	
	public Bullet(ExistingBullets i) {
		super(i);
	}
	public void move() {
		x += velx;
		y += vely;
	}
	
}
