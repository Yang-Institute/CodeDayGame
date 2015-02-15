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
				bullets.add(new Bullet(bulletX, bulletY, (int) Math
						.round((mod * (mouseX - bulletX))), (int) Math
						.round((mod * (mouseY - bulletY))), "img/bullet2.png"));

			} catch (SlickException e) {
				e.printStackTrace();
			}

		}

		if (input.isKeyDown(Input.KEY_W)) {
			int tempX = player.pos.x
					+ (int) Math.round(3 * Math.cos(Math
							.toRadians(player.angle)));

			int tempY = player.pos.y
					- (int) Math.round(3 * Math.sin(Math
							.toRadians(player.angle)));
			for (NonPlayer e : enemies) {
				if (!(Math.abs(tempX - e.pos.x) < 20 && Math.abs(tempY
						- e.pos.y) < 25)) {
					if (tempX >= -20 && tempX <= WIDTH - 42) {

						player.pos.x = tempX;
					}
					if (tempY >= -20 && tempY <= HEIGHT - 42) {
						player.pos.y = tempY;
					}

				} else if (Math.abs(player.pos.x - e.pos.x) < 20) {
					if (tempX >= -20 && tempX <= WIDTH - 42) {
						player.pos.x = tempX;
					}
				} else if (Math.abs(player.pos.y - e.pos.y) < 25) {
					if (tempY >= -20 && tempY <= WIDTH - 42) {
						player.pos.y = tempY;
					}
				}
			}

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

		for (int r = 0; r < bullets.size(); r++) {

			Bullet b = bullets.get(r);
			if (b.pos.x <= 0 || b.pos.x >= WIDTH) {
				b.vel.x = -b.vel.x;
				b.bounce--;
				if (b.bounce <= 0) {
					bullets.remove(r);
					r--;
					continue;
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
					if (e.health <= 0){
						enemies.remove(tt);
						tt--;
					}	
					bullets.remove(a);
					a--;
					continue;

				}
			}
		}

		if (enemies.isEmpty()) {
			for (int e = 0; e < level; e++) {
				enemies.add(new NonPlayer(
						(int) (Math.random() * WIDTH - 50) + 25, (int) (Math
								.random() * HEIGHT - 50) + 25, 20,
						"img/toptankblue.png", "img/bottankblue.png"));
			}
			level++;
		}

		for (int j = 0; j < enemies.size(); j++) {
			artifiacalizedMANNNGGGG(enemies.get(j));
		}
	}

	public void artifiacalizedMANNNGGGG(NonPlayer enemy) { // are you fucking
															// srs?

		if (!isInBulletPath(enemy)) {
			if (Math.random() * (60 / Math.log(level * 3)) <= 1) {
				enemyShoot(enemy);
			}
		}

	}

	public void enemyShoot(NonPlayer enemy) {
		int bulletX = enemy.pos.x + 25;
		int bulletY = enemy.pos.y + 22;
		int tankX = enemy.pos.x + 25;
		int tankY = enemy.pos.y + 22;
		int playerX = player.pos.x;
		int playerY = player.pos.y;
		double radians = 0;
		double distance = (int) Math.sqrt(Math.pow(playerX - tankX, 2)
				+ Math.pow(playerY - tankY, 2));

		if (playerX > tankX) {
			radians = Math.asin((tankY - playerY) / distance);
		} else {
			radians = 3.14 / 2 + Math.acos((tankY - playerY) / distance);
		}

		bulletX = bulletX + (int) Math.round(25 * Math.cos(radians));
		bulletY = bulletY - (int) Math.round(22 * Math.sin(radians));

		double mod = 10 / Math.sqrt(Math.pow(playerX - bulletX, 2)
				+ Math.pow(playerX - bulletY, 2));

		try {
			bullets.add(new Bullet(bulletX, bulletY, (int) Math
					.round((mod * (playerX - bulletX))), (int) Math
					.round((mod * (playerX - bulletY))), "img/bullet2.png"));

		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public boolean isInBulletPath(NonPlayer enemy) {

		for (Bullet b : bullets) {

			for (int i = 1; i < 15; i++) {
				int bulletX = b.pos.x + i * b.vel.x;

				int bulletY = b.pos.y + b.vel.y;
				if (Math.abs(bulletX - enemy.pos.x) < 60
						&& Math.abs(bulletY - enemy.pos.y) < 22) {
					return true;
				}
			}
		}
		return false;
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

		for (NonPlayer tt : enemies) {
			tt.imgBottom.draw(tt.pos.x, tt.pos.y);
			int etankX = tt.pos.x + 31;
			int etankY = tt.pos.y + 31;
			int emouseX = player.pos.x;
			int emouseY = player.pos.y;
			double edistance = (int) Math.sqrt(Math.pow(emouseX - etankX, 2)
					+ Math.pow(emouseY - etankY, 2));

			double eradians;
			if (emouseX > etankX) {
				eradians = Math.asin((etankY - emouseY) / edistance);
			} else {
				eradians = 3.14 / 2 + Math.acos((etankY - emouseY) / edistance);
			}
			int edegrees = (int) (180 / 3.14 * eradians);

			tt.imgTop.rotate(tt.edeg - edegrees);
			tt.imgTop.draw(tt.pos.x, tt.pos.y);

			tt.edeg = edegrees;
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