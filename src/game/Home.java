package game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.lwjgl.input.Mouse;

public class Home extends BasicGameState{
	public String mouse = "";
	public String text = "";
	Image shopOpen;
	Image border;
	Image homeBg;
	Image inventoryButton;
	Image statButton;
	Image fightButton;
	float posX = 300.0f; 
	float posY = 300.0f;
	boolean battleEnd;
	int x,y;
	
	public Home(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		shopOpen = new Image("res/shopButton.png");
		border = new Image("res/menuBoarder.png");
		homeBg = new Image("res/homeBG.png");
		inventoryButton = new Image("res/inventorybutton.png");
		statButton = new Image("res/statButton.png");
		fightButton = new Image("res/fightButton.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		homeBg.draw(0,0);
		border.draw(0,0);
		shopOpen.draw(120,100);
		inventoryButton.draw(300,100);
		statButton.draw(480,100);
		fightButton.draw(230,230);
		g.drawString("Level: " + Menu.playerChar.getLevel(), 280, 320);
//		g.drawString(mouse,50,200);
		g.drawString(text,x,y);
		g.drawString("Gold: "+Menu.playerChar.getGold(), 280, 350);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		posY = gc.getHeight() - posY;
		mouse = "Xpos = "+posX+"  Ypos = "+posY;
//		 GO TO SHOP STATE 
		if((posX > 120 && posX < 171) && (posY > 101) && posY < 149){ 
			x = 125;
			y = 152;
			text = "Shop";
					if(Mouse.isButtonDown(0)){
						sbg.enterState(3);
					}
		}
//		GO TO INVENTORY STATE
		if((posX > 300 && posX < 350) && (posY > 101) && posY < 149){ 
			x = 285;
			y = 148;
			text = "Inventory";
			if(Mouse.isButtonDown(0)){
				sbg.enterState(4);
			}
		}
//		GO TO STAT STATE
		if((posX > 480 && posX < 531) && (posY > 101) && posY < 149){ 
			x = 485;
			y = 150;
			text = "Stat";
			if(Mouse.isButtonDown(0)){
				sbg.enterState(5);
			}
		}
//		GO TO BATTLE STATE
		if((posX > 230 && posX < 427) && (posY > 231) && posY < 311){ 
			if(Mouse.isButtonDown(0)){
				sbg.enterState(1);
			}
		}
		
	}

	@Override
	public int getID() {
		
		return 2;
	}
}

	