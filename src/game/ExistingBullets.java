package game;

import org.newdawn.slick.particles.ParticleSystem;
import java.util.ArrayList;

public class ExistingBullets extends ParticleSystem {
	
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	public ExistingBullets() {
		super("img/bullet.png");
	}
	
	public ExistingBullets(String i) {
		super(i);
	}
	
	public void createBullet() {
		
	}
}
