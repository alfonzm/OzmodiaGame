package game;
import game.Item.itemType;
import game.Spell.spellEffect;
import game.Spell.spellTarget;
import game.Spell.spellType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState{
	
	public static boolean isLogged;
	
	public String mouse = "";
	boolean hoverPlay; 
	boolean hoverExit;
	boolean hoverLogin; 
	boolean inUserText;
	boolean textFieldclick2;
	Image playNow;
	Image playNowHover;
	Image exit;
	Image exitNowHover;
	Image backGroundImage;
	Image menuBorder;
	Image login;
	Image clickTextField;
	public static Player playerChar;
	public static ArrayList<Spell> skillList;
	public static ArrayList<Item> itemList;
	
	public static int charID;	
	String username="";
	String password="";
	
	public static int userID;
	
	public Menu(int state){
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		isLogged = false;
		backGroundImage = new Image("res/menuBg.png");
		playNowHover = new Image("res/newtxt.png");
		exitNowHover = new Image("res/exittxtHover.png");
		menuBorder = new Image("res/menuBoarder.png");
		login = new Image("res/loginClick.png");
		clickTextField = new Image("res/hoverText.png");
		
		skillList = new ArrayList<Spell>();
		itemList = new ArrayList<Item>();
		
		skillList.add(new Spell("No Skill",0,spellType.damage,spellTarget.enemy,spellEffect.noEffect));//High base damage
		skillList.add(new Spell("Fireball",40,spellType.damage,spellTarget.enemy,spellEffect.burn));//High base damage
        skillList.add(new Spell("Lightning Bolt",10,spellType.damage,spellTarget.enemy,spellEffect.shock));//low base damage high crit chance
        skillList.add(new Spell("Ice Spear",20,spellType.damage,spellTarget.enemy,spellEffect.freeze));//average base damage high crit damage
        skillList.add(new Spell("Poison Sting",10,spellType.damage,spellTarget.enemy,spellEffect.poison));//chance to instakill
		itemList.add(new Item("noitem",0,itemType.weapon, 0));
        itemList.add(new Item("Bronze Sword",10,itemType.weapon, 100));
        itemList.add(new Item("Bronze Helm",10,itemType.helm, 100));
        itemList.add(new Item("Bronze Shield",10,itemType.shield, 100));
        itemList.add(new Item("Bronze Armor",10,itemType.armor, 100));
        itemList.add(new Item("Bronze Greaves",10,itemType.greaves, 100));
        itemList.add(new Item("Silver Sword",25,itemType.weapon, 400));
        itemList.add(new Item("Silver Helm",25,itemType.helm, 400));
        itemList.add(new Item("Silver Shield",25,itemType.shield, 400));
        itemList.add(new Item("Silver Armor",25,itemType.armor, 400));
        itemList.add(new Item("Silver Greaves",25,itemType.greaves, 400));
        itemList.add(new Item("Gold Sword",25,itemType.weapon, 1000));
        itemList.add(new Item("Gold Helm",50,itemType.armor, 1000));
        itemList.add(new Item("Gold Shield",50,itemType.shield, 1000));
        itemList.add(new Item("Gold Armor",50,itemType.armor, 1000));
        itemList.add(new Item("Gold Greaves",50,itemType.greaves, 1000));
	}
	
	public static void initPlayer(int userID){
		
		ResultSet rsChar = null;
		ResultSet rsInventory = null;
		ResultSet rsSkills = null;
		
		String charName = "";
		System.out.println(userID);
		
		try {
			System.out.println(userID);
			
			rsChar = Game.st.executeQuery("SELECT * FROM `ozmodia`.`character` WHERE us_ID=" + userID);
			rsChar.next();
			charName = rsChar.getString(3);
			
			charID = rsChar.getInt(1);
			
			// read character stats from DB
			int level = rsChar.getInt(10);
			int health = rsChar.getInt(5);
			int str = rsChar.getInt(7);
			int dex = rsChar.getInt(8);
			int intl = rsChar.getInt(9);
			int gold = rsChar.getInt(6);
			
			int[] inventory = new int[itemList.size()];
			rsInventory = Game.st.executeQuery("SELECT * FROM `ozmodia`.`inventory` WHERE ch_ID=" + charID);
			while(rsInventory.next()){
				inventory[rsInventory.getInt(1)] = rsInventory.getInt(3);
			}
			
			int[] skills = new int[3];
			rsSkills = Game.st.executeQuery("SELECT * FROM `ozmodia`.`skills` WHERE ch_ID=" + charID);
			for(int i = 0; i < 3 ; i++){
				if(rsSkills.next())
					skills[i] = rsSkills.getInt(1);
				else
					skills[i] = 0;
			}
			
			int[] equips = new int[5];
			
			playerChar = new Player(level, health, 100, str, dex, intl, skills, inventory, gold, equips, charName);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("read");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		g.drawString("Astral",490,50);
		backGroundImage.draw(0,0);
		menuBorder.draw(0,0);
//		g.drawString(mouse,50,50);		
		
		if(hoverLogin == false){
			login.draw(307,349);
		}
		if(inUserText == true){
			clickTextField.draw(251, 215);
		}
		if(textFieldclick2 == true){
			clickTextField.draw(251, 284);
		}
		
		g.setColor(Color.black);
		g.drawString(""+username, 260, 220);
		g.drawString(""+password, 260, 288);
		g.setColor(Color.white);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		float posX = Mouse.getX();
		float posY = Mouse.getY();		
		posY = gc.getHeight() - posY;
		mouse = "Xpos = "+posX+"  Ypos = "+posY;
		hoverLogin = false;
		
		Input input = gc.getInput();
		
		if((posX > 307 && posX < 370) && (posY > 349) && posY < 384){ //hoverPlayButton
			hoverLogin = true;
			if(Mouse.isButtonDown(0)){
//				PUT CONDITION TO CHECK IF USER AND PASS IS CORRECT!!!!!!!faggot
				try {
					Game.st = Game.con.createStatement();
					ResultSet rsUser;
					rsUser = Game.st.executeQuery("SELECT * FROM `ozmodia`.`users` WHERE username='"+username+"' AND password='"+password+"';");
					rsUser.next();
					userID = rsUser.getInt(1);
					if(true){
						System.out.println("correct");

						initPlayer(userID);
						
						sbg.addState(new Play(Game.play));
						sbg.addState(new Home(Game.home));
						sbg.addState(new Shop(Game.shop));
						sbg.addState(new Inventory(Game.inventory));
						sbg.addState(new Status(Game.stat));
						
						sbg.getState(Game.play).init(gc,sbg);
						sbg.getState(Game.home).init(gc, sbg);
						sbg.getState(Game.shop).init(gc, sbg);
						sbg.getState(Game.inventory).init(gc, sbg);
						sbg.getState(Game.stat).init(gc, sbg);
						
						sbg.enterState(Game.home);
					}
					else
						System.out.println("incorrect");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				sbg.enterState(2);
			}
		}
		
		
		if((posX > 251 && posX < 420) && (posY > 215) && posY < 241){//hoverExitButton
			if(Mouse.isButtonDown(0)){
				inUserText = true;
				textFieldclick2 = false;
			}
		}
	
		if((posX > 251 && posX < 420) && (posY > 284) && posY < 310){//hoverExitButton
			if(Mouse.isButtonDown(0)){
				textFieldclick2 = true;
				inUserText = false;
			}
		}
		
		if((inUserText == true && username.length() < 16) || (inUserText == false && password.length() < 16)){
			if(input.isKeyPressed(Input.KEY_TAB)){
				inUserText = !inUserText;
			}
			if(input.isKeyPressed(Input.KEY_BACK)){
				if(inUserText==true){
					if(username.length() > 0){
						username = username.substring(0, username.length()-1);
					}
				}
				else{
					if(password.length() > 0){
						password = password.substring(0, password.length()-1);
					}
				}
			}
			//lowercase
			if(input.isKeyPressed(Input.KEY_A)){
				if(inUserText==true)
					username+="a";
				else
					password+="a";
			}
			if(input.isKeyPressed(Input.KEY_B)){
				if(inUserText==true)
					username+="b";
				else
					password+="b";
			}
			if(input.isKeyPressed(Input.KEY_C)){
				if(inUserText==true)
					username+="c";
				else
					password+="c";
			}if(input.isKeyPressed(Input.KEY_D)){
				if(inUserText==true)
					username+="d";
				else
					password+="d";
			}if(input.isKeyPressed(Input.KEY_E)){
				if(inUserText==true)
					username+="e";
				else
					password+="e";
			}
			if(input.isKeyPressed(Input.KEY_F)){
				if(inUserText==true)
					username+="f";
				else
					password+="f";
			}
			if(input.isKeyPressed(Input.KEY_G)){
				if(inUserText==true)
					username+="g";
				else
					password+="g";
			}if(input.isKeyPressed(Input.KEY_H)){
				if(inUserText==true)
					username+="h";
				else
					password+="h";
			}if(input.isKeyPressed(Input.KEY_I)){
				if(inUserText==true)
					username+="i";
				else
					password+="i";
			}if(input.isKeyPressed(Input.KEY_J)){
				if(inUserText==true)
					username+="j";
				else
					password+="j";
			}if(input.isKeyPressed(Input.KEY_K)){
				if(inUserText==true)
					username+="k";
				else
					password+="k";
			}if(input.isKeyPressed(Input.KEY_L)){
				if(inUserText==true)
					username+="l";
				else
					password+="l";
			}if(input.isKeyPressed(Input.KEY_M)){
				if(inUserText==true)
					username+="m";
				else
					password+="m";
			}if(input.isKeyPressed(Input.KEY_N)){
				if(inUserText==true)
					username+="n";
				else
					password+="n";
			}if(input.isKeyPressed(Input.KEY_O)){
				if(inUserText==true)
					username+="o";
				else
					password+="o";
			}if(input.isKeyPressed(Input.KEY_P)){
				if(inUserText==true)
					username+="p";
				else
					password+="p";
			}if(input.isKeyPressed(Input.KEY_Q)){
				if(inUserText==true)
					username+="q";
				else
					password+="q";
			}if(input.isKeyPressed(Input.KEY_R)){
				if(inUserText==true)
					username+="r";
				else
					password+="r";
			}if(input.isKeyPressed(Input.KEY_S)){
				if(inUserText==true)
					username+="s";
				else
					password+="s";
			}if(input.isKeyPressed(Input.KEY_T)){
				if(inUserText==true)
					username+="t";
				else
					password+="t";
			}if(input.isKeyPressed(Input.KEY_U)){
				if(inUserText==true)
					username+="u";
				else
					password+="u";
			}if(input.isKeyPressed(Input.KEY_V)){
				if(inUserText==true)
					username+="v";
				else
					password+="v";
			}if(input.isKeyPressed(Input.KEY_W)){
				if(inUserText==true)
					username+="w";
				else
					password+="w";
			}if(input.isKeyPressed(Input.KEY_X)){
				if(inUserText==true)
					username+="x";
				else
					password+="x";
			}if(input.isKeyPressed(Input.KEY_Y)){
				if(inUserText==true)
					username+="y";
				else
					password+="y";
			}if(input.isKeyPressed(Input.KEY_Z)){
				if(inUserText==true)
					username+="z";
				else
					password+="z";
			}
			//uppercase
			if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_A)){
				if(inUserText==true)
					username+="A";
				else
					password+="A";
			}
			if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_B)){
				if(inUserText==true)
					username+="B";
				else
					password+="B";
			}
			if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_C)){
				if(inUserText==true)
					username+="C";
				else
					password+="C";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_D)){
				if(inUserText==true)
					username+="D";
				else
					password+="D";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_E)){
				if(inUserText==true)
					username+="E";
				else
					password+="E";
			}
			if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_F)){
				if(inUserText==true)
					username+="F";
				else
					password+="F";
			}
			if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_G)){
				if(inUserText==true)
					username+="G";
				else
					password+="G";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_H)){
				if(inUserText==true)
					username+="H";
				else
					password+="H";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_I)){
				if(inUserText==true)
					username+="I";
				else
					password+="I";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_J)){
				if(inUserText==true)
					username+="J";
				else
					password+="J";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_K)){
				if(inUserText==true)
					username+="K";
				else
					password+="K";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_L)){
				if(inUserText==true)
					username+="L";
				else
					password+="L";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_M)){
				if(inUserText==true)
					username+="M";
				else
					password+="M";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_N)){
				if(inUserText==true)
					username+="N";
				else
					password+="N";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_O)){
				if(inUserText==true)
					username+="O";
				else
					password+="O";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_P)){
				if(inUserText==true)
					username+="P";
				else
					password+="P";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_Q)){
				if(inUserText==true)
					username+="Q";
				else
					password+="Q";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_R)){
				if(inUserText==true)
					username+="R";
				else
					password+="R";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_S)){
				if(inUserText==true)
					username+="S";
				else
					password+="S";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_T)){
				if(inUserText==true)
					username+="T";
				else
					password+="T";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_U)){
				if(inUserText==true)
					username+="U";
				else
					password+="U";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_V)){
				if(inUserText==true)
					username+="V";
				else
					password+="V";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_W)){
				if(inUserText==true)
					username+="W";
				else
					password+="W";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_X)){
				if(inUserText==true)
					username+="X";
				else
					password+="X";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_Y)){
				if(inUserText==true)
					username+="Y";
				else
					password+="Y";
			}if((input.isKeyPressed(Input.KEY_LSHIFT) ||  input.isKeyPressed(Input.KEY_RSHIFT)) && input.isKeyPressed(Input.KEY_Z)){
				if(inUserText==true)
					username+="Z";
				else
					password+="Z";
			}
			
			
			if(input.isKeyPressed(Input.KEY_1)){
				if(inUserText==true)
					username+="1";
				else
					password+="1";
			}if(input.isKeyPressed(Input.KEY_2)){
				if(inUserText==true)
					username+="2";
				else
					password+="2";
			}if(input.isKeyPressed(Input.KEY_3)){
				if(inUserText==true)
					username+="3";
				else
					password+="3";
			}if(input.isKeyPressed(Input.KEY_4)){
				if(inUserText==true)
					username+="4";
				else
					password+="4";
			}if(input.isKeyPressed(Input.KEY_5)){
				if(inUserText==true)
					username+="5";
				else
					password+="5";
			}if(input.isKeyPressed(Input.KEY_6)){
				if(inUserText==true)
					username+="6";
				else
					password+="6";
			}if(input.isKeyPressed(Input.KEY_7)){
				if(inUserText==true)
					username+="7";
				else
					password+="7";
			}if(input.isKeyPressed(Input.KEY_8)){
				if(inUserText==true)
					username+="8";
				else
					password+="8";
			}if(input.isKeyPressed(Input.KEY_9)){
				if(inUserText==true)
					username+="9";
				else
					password+="9";
			}if(input.isKeyPressed(Input.KEY_0)){
				if(inUserText==true)
					username+="0";
				else
					password+="0";
			}
		}

	}
	
	
	public int getID(){
		return 0;
	}
	
}
