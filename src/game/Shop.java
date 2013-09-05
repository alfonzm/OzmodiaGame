package game;

import game.Item.itemType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Shop extends BasicGameState{
	

	public String mouse = "";
	public String cost = "";
	Image shopBg;
	Image lockIcon;
	float posX = 300.0f; 
	float posY = 300.0f;
	int x,y;
	boolean sword1;
	boolean sword2;
	boolean sword3;
	boolean hoverS1;
	boolean hoverS2;
	boolean hoverS3;
	int sword1Price = 100;
	int sword2Price = 400;
	int sword3Price = 1000;
	
	public Shop(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		shopBg = new Image("res/shopState.png");
		lockIcon = new Image("res/lockIcon.png");
		sword1 = false;
		sword2 = false;
		sword3 = false;
		hoverS1 = false;
		hoverS2 = false;
		hoverS3 = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		shopBg.draw(0,0);
//		g.drawString(mouse,50,200);
		g.drawString(cost, 349, 438);
		if(!(Menu.playerChar.inventory[1] > 0))
			lockIcon.draw(120,0);
		if(!(Menu.playerChar.inventory[6] > 0))
			lockIcon.draw(120,163);
		if(!(Menu.playerChar.inventory[11] > 0))
			lockIcon.draw(120,307);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		
		Input input = gc.getInput();
		posY = gc.getHeight() - posY;
		mouse = "Xpos = "+posX+"  Ypos = "+posY;
		
		//HOME BUTTON
		if((posX > 27 && posX < 158) && (posY > 416) && posY < 472){ 
			if(input.isMousePressed(0)){
				sbg.enterState(2);
			}
		}
		
		//UNLOCK SWORD1 BUTTON
		if((posX > 120 && posX < 220) && (posY > 0) && posY < 92){ 
			cost = ""+sword1Price;
			if(input.isMousePressed(0)){
				if(sword1!=true && Menu.playerChar.getGold() >= sword1Price){
					sword1 = true;
					Menu.playerChar.setGold(Menu.playerChar.getGold() - sword1Price);
					Menu.playerChar.inventory[1] += 1;
					try {
						bought(1);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		}
		}
		//UNLOCK SWORD2 BUTTON
		if((posX > 120 && posX < 220) && (posY > 163) && posY < 255){ 
			cost = ""+sword2Price;
			if(input.isMousePressed(0)){
				if(sword2!=true && Menu.playerChar.getGold() >= sword2Price){
					sword2 = true;
					Menu.playerChar.setGold(Menu.playerChar.getGold() - sword2Price);
					Menu.playerChar.inventory[6] += 1;
					try {
						bought(6);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		//UNLOCK SWORD3 BUTTON
		if((posX >120 && posX < 220) && (posY > 309) && posY < 396){ 
			cost = ""+sword3Price;
			if(input.isMousePressed(0)){
				if(sword3!=true && Menu.playerChar.getGold()  >= sword3Price){
					sword3 = true;
					Menu.playerChar.setGold(Menu.playerChar.getGold() - sword3Price);
					Menu.playerChar.inventory[11] += 1;
					try {
						bought(11);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void bought(int itemID) throws SQLException{
		if(Menu.itemList.get(itemID).getType() == itemType.weapon){
			if(itemID > Menu.playerChar.equippedItems[0]){
				Menu.playerChar.equippedItems[0] = itemID;
			}
		}
		
		ResultSet result = null;
		PreparedStatement pst;
		
		boolean found = false;
		int newQuantity = 0;
		
		result = Game.st.executeQuery("SELECT * FROM `ozmodia`.`inventory` WHERE ch_ID="+Menu.charID);
		while(result.next() && found != true){
			if(result.getInt(1) == itemID){
				found = true;	
				newQuantity = result.getInt(3) + 1;
			}
		}
		
		if(!found){
			pst = Game.con.prepareStatement("INSERT INTO `ozmodia`.`inventory` SET it_ID='"+itemID+"', ch_ID='"+Menu.charID+"', quantity='1';");
			pst.executeUpdate();
			Menu.playerChar.inventory[itemID] += 1;
			System.out.println("inserted new item");
		}
		
	}
	
	@Override
	public int getID() {
		
		return 3;
	}

}
