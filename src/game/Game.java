package game;

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

public class Game extends BasicGame
{
	public static final int WIDTH   = 500;
    public static final int HEIGHT  = 540;
    public static final int FPS     = 60;
    public static final double VERSION = 0.01;
	
    Player player;
    
	public Game(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		player = new Player(400,320,"img/psprite1.png");
		
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		
		Input input = gc.getInput();
		/*
		if(input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_LEFT)){
	        	
			player.ypos -= 1;
	        player.xpos -= 1;
	        	
	    }
	    else if(input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_RIGHT)){
	        	
	        player.ypos -= 1;
	        player.xpos += 1;
	        	
	    }
	    else if(input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_LEFT)){
	        	
	        player.ypos += 1;
	        player.xpos -= 1;
	        	
	    }
	    else if(input.isKeyDown(Input.KEY_DOWN) && input.isKeyDown(Input.KEY_RIGHT)){
	        	
	        player.ypos += 1;
	        player.xpos += 1;
	        	
	    }
	    */
	    if (input.isKeyDown(Input.KEY_UP)){
        	
        	player.ypos -= 2;
        	
        }
        if (input.isKeyDown(Input.KEY_DOWN)){
        	
            player.ypos += 2;
            
        }
        if (input.isKeyDown(Input.KEY_LEFT)){
        	
            player.xpos -= 2;
            
        }
        if (input.isKeyDown(Input.KEY_RIGHT)){
        	
            player.xpos += 2;
            
        }
      
		
		
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		player.img.draw(player.xpos,player.ypos);
		//g.drawString("Hello World", 50, 50);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new Game("Game v." + VERSION ));
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
			appgc.setTargetFrameRate(FPS);
            appgc.setShowFPS(true);
            appgc.setAlwaysRender(true);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}