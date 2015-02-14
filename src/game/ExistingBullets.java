package game;

import org.newdawn.slick.particles.ParticleSystem;

public class ExistingBullets extends ParticleSystem {
	
	public ExistingBullets() {
		super("img/bullet.png");
	}
	
	public ExistingBullets(String i) {
		super(i);
	}
}
