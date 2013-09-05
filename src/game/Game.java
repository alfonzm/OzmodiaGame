package game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{
	
	public static final String gamename = "Ozmodia";
	public static final int menu = 0;
	public static final int play = 1;
	public static final int home = 2;
	public static final int shop = 3;
	public static final int inventory = 4;
	public static final int stat = 5;
	
	// DB THINGS
	static Connection con = null;
	static Statement st = null;
	static ResultSet rs = null;
 	
	public Game(){
		super("Game");
		this.addState(new Menu(menu));
//		this.addState(new Play(play));
//		this.addState(new Home(home));
//		this.addState(new Shop(shop));
//		this.addState(new Inventory(inventory));
//		this.addState(new Status(stat));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException{
		this.getState(menu).init(gc,this);
		this.enterState(Game.menu);
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "");
		
		try{
			AppGameContainer appgc = new AppGameContainer(new Game());
			appgc.setDisplayMode(640, 480, false);
			appgc.setTargetFrameRate(50);
			appgc.setShowFPS(false);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

}
