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
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	public static final int FPS = 60;
	public static final double VERSION = 0.3;

	public static int deg = 90;
	public static int level = 1;
	// public static int tankdeg = 90;

	Player player;

	public ArrayList<NonPlayer> enemies = new ArrayList<NonPlayer>();
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Game(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		player = new Player(400, 320, "img/toptank.png", "img/bottank.png");

	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {

		Input input = gc.getInput();
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {

			int bulletX = player.pos.x + 25;
			int bulletY = player.pos.y + 22;
			int tankX = player.pos.x + 25;
			int tankY = player.pos.y + 22;
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			double radians = 0;
			double distance = (int) Math.sqrt(Math.pow(mouseX - tankX, 2)
					+ Math.pow(mouseY - tankY, 2));

			if (mouseX > tankX) {
				radians = Math.asin((tankY - mouseY) / distance);
			} else {
				radians = 3.14 / 2 + Math.acos((tankY - mouseY) / distance);
			}

			bulletX = bulletX + (int) Math.round(25 * Math.cos(radians));
			bulletY = bulletY - (int) Math.round(22 * Math.sin(radians));

			double mod = 10 / Math.sqrt(Math.pow(mouseX - bulletX, 2)
					+ Math.pow(mouseY - bulletY, 2));

			try {
				bullets.add(new Bullet(bulletX, bulletY,
						(int) Math.round((mod * (mouseX - bulletX))),
						(int) Math.round((mod * (mouseY - bulletY))), "img/bullet2.png"));

			} catch (SlickException e) {
				e.printStackTrace();
			}

		}

		if (input.isKeyDown(Input.KEY_W)) {

			player.pos.x += Math.round(3 * Math.cos(Math
					.toRadians(player.angle)));
			player.pos.y -= Math.round(3 * Math.sin(Math
					.toRadians(player.angle)));

		}
		if (input.isKeyDown(Input.KEY_S)) {

			player.pos.x -= Math.round(3 * Math.cos(Math
					.toRadians(player.angle)));
			player.pos.y += Math.round(3 * Math.sin(Math
					.toRadians(player.angle)));

		}
		if (input.isKeyDown(Input.KEY_A)) {

			player.angle += 4;
			player.imgBottom.rotate(-4);
			if (player.angle > 360)
				player.angle -= 360;

		}
		if (input.isKeyDown(Input.KEY_D)) {

			player.angle -= 4;
			player.imgBottom.rotate(4);
			if (player.angle < 0)
				player.angle += 360;

		}

		for (int j = 0; j < bullets.size(); j++) { // Bullets collison with
													// player
			Bullet b = bullets.get(j);
			if (Math.abs(player.pos.x + 25 - b.pos.x) <= 8
					&& Math.abs(player.pos.y + 22 - b.pos.y) <= 15) {
				player.health -= 10;
				bullets.remove(j);
				j--;
			}
		}

		for (int r = 0; r < bullets.size(); r++) { // Bullets collison with
													// player
			Bullet b = bullets.get(r);
			if (b.pos.x <= 0 || b.pos.x >= WIDTH) {
				b.vel.x = -b.vel.x;
				b.bounce--;
				if (b.bounce <= 0) {
					bullets.remove(r);
					r--;
				}
			}
			if (b.pos.y <= 0 || b.pos.y >= HEIGHT) {
				b.vel.y = -b.vel.y;
				b.bounce--;
				if (b.bounce <= 0) {
					bullets.remove(r);
					r--;
				}
			}

		}

		for (int tt = 0; tt < enemies.size(); tt++) {
			NonPlayer e = enemies.get(tt);
			for (int a = 0; a < bullets.size(); a++) { // Bullets collision with
														// enemy
				Bullet bb = bullets.get(a);
				if (Math.abs(e.pos.x + 25 - bb.pos.x) <= 8
						&& Math.abs(e.pos.y + 22 - bb.pos.y) <= 15) {
					e.health -= 10;
					if (e.health <= 0)
						enemies.remove(tt);
					tt--;
					bullets.remove(a);
					a--;

				}
			}
		}

		if (enemies.isEmpty()) {
			for (int e = 0; e < level; e++) {
				enemies.add(new NonPlayer(
						(int) (Math.random() * WIDTH - 50) + 25, (int) (Math
								.random() * HEIGHT - 50) + 25, 20,
						"img/toptank.png", "img/bottank.png"));
			}
			level++;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		Input input = gc.getInput();

		int tankX = player.pos.x + 31;
		int tankY = player.pos.y + 31;
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		double distance = (int) Math.sqrt(Math.pow(mouseX - tankX, 2)
				+ Math.pow(mouseY - tankY, 2));

		double radians;
		if (mouseX > tankX) {
			radians = Math.asin((tankY - mouseY) / distance);
		} else {
			radians = 3.14 / 2 + Math.acos((tankY - mouseY) / distance);
		}
		int degrees = (int) (180 / 3.14 * radians);

		player.imgTop.rotate(deg - degrees);

		deg = degrees;

		g.drawString("Health: " + player.health, 0, HEIGHT - 20);
		g.drawString("Level: " + (level - 1), 0, HEIGHT - 40);

		player.imgBottom.draw(player.pos.x, player.pos.y);
		player.imgTop.draw(player.pos.x, player.pos.y);

		for (Bullet b : bullets) {
			b.pos.x += b.vel.x;
			b.pos.y += b.vel.y;
			b.img.draw(b.pos.x, b.pos.y);

		}

		for (NonPlayer t : enemies) {
			t.imgBottom.draw(t.pos.x, t.pos.y);
			t.imgTop.draw(t.pos.x, t.pos.y);
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