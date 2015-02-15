package game;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;
import org.newdawn.slick.particles.ParticleSystem;
import org.omg.CORBA.BAD_POLICY_VALUE;

public class Game extends BasicGame {
	public static final int WIDTH = 500;
	public static final int HEIGHT = 540;
	public static final int FPS = 60;
	public static final double VERSION = 0.01;

	Player player;
	
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Game(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		player = new Player(400, 320, "img/psprite1.png", "img/bullet2.png");
		
	}
	


	@Override
	public void update(GameContainer gc, int i) throws SlickException {

		Input input = gc.getInput();
		if (input.isKeyPressed(Input.MOUSE_LEFT_BUTTON)){
			try{
				bullets.add(new Bullet(player.pos.x + 24, player.pos.y,
													xvelfiller, yvelfiller, "img/bullet2.png"));
			}
			catch (SlickException e){
				e.printStackTrace();
			}
		}
		
		if (input.isKeyDown(Input.KEY_W)) {

			player.pos.y -= 2;

		}
		if (input.isKeyDown(Input.KEY_S)) {

			player.pos.y += 2;

		}
		if (input.isKeyDown(Input.KEY_A)) {

			player.pos.x -= 2;

		}
		if (input.isKeyDown(Input.KEY_D)) {

			player.pos.x += 2;

		}

		for (int j = 0; j < bullets.size(); j++) {
			Bullet b = bullets.get(j);
			if (Math.abs(player.pos.x + 25 - b.pos.x) <= 8
					&& Math.abs(player.pos.y + 22 - b.pos.y) <= 15) {
				player.health -= 10;
				bullets.remove(j);
				j--;
			}
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.drawString("Health: " + player.health, 0, 520);
		
		player.img.draw(player.pos.x, player.pos.y);
		
		for (Bullet b : bullets) {
			b.pos.x += b.vel.x;
			b.pos.y += b.vel.y;
			b.img.draw(b.pos.x, b.pos.y);


		}
		// g.drawString("Hello World", 50, 50);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Game v." + VERSION));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(FPS);
			appgc.setShowFPS(true);
			appgc.setAlwaysRender(true);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}


}