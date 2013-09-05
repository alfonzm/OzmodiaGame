package game;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Inventory extends BasicGameState {
	
	Image bg, border, home;
	String text;
	
	ArrayList<String> items;

	public Inventory(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		bg = new Image("res/homeBG.png");
		border = new Image("res/menuBoarder.png");
		home = new Image("res/homeButton.png");
		
		items = new ArrayList<String>();
		
		for(int i = 1; i < Menu.playerChar.inventory.length; i++){
			if(Menu.playerChar.inventory[i] > 0){
				items.add((Menu.itemList.get(i).getName() + " x " + Menu.playerChar.inventory[i]));
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(bg, 0, 0);
		g.drawImage(border, 0, 0);
		g.drawImage(home, 27, 416);
		
		for(int i = 0; i < items.size(); i++){
			g.drawString(items.get(i), 200, 100 + (i*30));
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		posY = gc.getHeight() - posY;
		Input input = gc.getInput();
		

		//HOME BUTTON
		if((posX > 27 && posX < 158) && (posY > 416) && posY < 472){ 
			if(input.isMousePressed(0)){
				sbg.enterState(2);
			}
		}
	}

	@Override
	public int getID() {
		
		return 4;
	}
}
