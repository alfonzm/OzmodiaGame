package game;

import java.sql.SQLException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Status  extends BasicGameState {

	public String mouse = "";
	public String remainingPoints = "";
	float posX = 300.0f; 
	float posY = 300.0f;
	int x,y;
	public static int points;
	Image statBg;
	Input input;

	public Status(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		statBg = new Image("res/upGradeState.png");
		input = gc.getInput();
		points = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		statBg.draw(0,0);
		g.drawString(""+points, 300, 71);
		
		g.drawString(""+Menu.playerChar.getHealth(), 440, 130);
		g.drawString(""+Menu.playerChar.getStr(), 440, 185);
		g.drawString(""+Menu.playerChar.getDex(), 440, 243);
		g.drawString(""+Menu.playerChar.getIntl(), 440, 298);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		float posX = Mouse.getX();
		float posY = Mouse.getY();

		posY = gc.getHeight() - posY;
		mouse = "Xpos = "+posX+"  Ypos = "+posY;
		
		
		if(points > 0){
			//HP
			if((posX > 375 && posX < 424) && (posY > 121) && posY < 158){ 
				if(input.isMousePressed(0)){
					System.out.println("increaseHP");
					Menu.playerChar.setHealth(Menu.playerChar.getHealth()+5);
					points--;
				}
			}
			
			//STR
			if((posX > 375 && posX < 424) && (posY > 174) && posY < 214){ 
				if(input.isMousePressed(0)){
					System.out.println("increaseSTR");
					Menu.playerChar.setStr(Menu.playerChar.getStr()+1);
					points--;
				}
			}
			//DEX
			if((posX > 375 && posX < 424) && (posY > 230) && posY < 267){ 
				if(input.isMousePressed(0)){
					System.out.println("increaseDEX");
					Menu.playerChar.setDex(Menu.playerChar.getDex()+1);
					points--;
				}
			}	
			//INT
			if((posX > 375 && posX < 424) && (posY > 287) && posY < 323){ 
				if(input.isMousePressed(0)){
					System.out.println("increaseINT");
					Menu.playerChar.setIntl(Menu.playerChar.getIntl()+1);
					points--;
				}
			}
		}
		
		//HOME
		if((points <= 0) && (posX > 27 && posX < 158) && (posY > 416) && posY < 472){ 
			if(input.isMousePressed(0)){
				try {
					Play.save(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sbg.enterState(2);
			}
		}
	}

	@Override
	public int getID() {
		
		return 5;
	}
	
}
