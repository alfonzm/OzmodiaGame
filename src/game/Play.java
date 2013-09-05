package game;

import game.Spell.spellEffect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState{
	Random rand = new Random();
	
	public String mouse = "";
	public String text = "";
	public String text2 = "";
	public String floor = "";
	public String castSkill = "";
	
	int turnDelay;
	public static int earnedGold;
	
	Image battlemap;
	Image attackButton;
	Image itemsButton;
	Image cursor;
	Image skillsButton;
	Image infoButton;
	Image runButton;
	Image menuWindow;
	Image knightSprite; //------------------------------------------Knight Sprite
	Image mob1;
	Image shopOpen;
	Image shopClose;
	Image healthBar;
	Image defeat, victory;
	Image next, nextWhite;
	Image returnMainMenu, returnMainMenuWhite;
	Image skillBack;
	Image[] skills;
	Image[] mobSprites;
	
	Monster monster;
	static Player player;
	Player playerTemp;
	int playerHP, monsterHP;
	boolean playerIsAlive, monsterIsAlive;
	
	boolean allowBuy;
	boolean nextHover;
	boolean skillsWindow;
	boolean battleEnd;
	boolean hoverAttack;
	boolean hoverRun;
	boolean hoverItems;
	boolean hoverSkills;
	boolean paused;
	boolean incLife;
	float posX = 200.0f; 
	float posY = 252.0f;
	
//	public player playerObj;
	
	
	public Play(int state){
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		battlemap = new Image("res/playBg.png");
		cursor = new Image("res/cursor.png");  //cursorImg
		attackButton = new Image("res/AttackButton.png"); //attackButtonImg
		itemsButton = new Image("res/itemsButton-2.png");
		skillsButton = new Image("res/skillsButton.png");
		runButton = new Image("res/runButton.png");
		
		menuWindow = new Image("res/Pause.png");
		shopOpen = new Image("res/shopButton.png");
		shopClose = new Image("res/unclickableShopeButton.png");
		healthBar = new Image("res/HealthBar.png");
		
		knightSprite = new Image("res/KnightSprite.png");
		
		mobSprites = new Image[4];
		mobSprites[0] = new Image("res/mob1.png");
		mobSprites[1] = new Image("res/mobDex.png");
		mobSprites[2]= new Image("res/mobStr.png");
		mobSprites[3]= new Image("res/mobDef.png");
		
		victory = new Image("res/victory.png");
		defeat= new Image("res/defeat.png");
		next = new Image("res/next.png");
		nextWhite = new Image("res/nextWhite.png");
		returnMainMenu = new Image("res/returnMainMenu.png");
		returnMainMenuWhite = new Image("res/returnMainMenuWhite.png");
		
		skills = new Image[4];
		
		skillBack = new Image("res/BackButton.png");
		skills[1] = new Image("res/fireBall.png");
		skills[2] = new Image("res/lightingBolt.png");
		skills[3] = new Image("res/iceSpear.png");
				
		player = Menu.playerChar;
		playerHP = player.getHealth();
		
		int lvl = player.getLevel();
		
		monster = new Monster(lvl,100+(rand.nextInt(10)+(5*lvl)),50,5+lvl,5+lvl,5+lvl, mobSprites[rand.nextInt(4)], "Monster");
		monsterHP = monster.getHealth();
		
		playerIsAlive = true;
		monsterIsAlive = true;
		
		turnDelay = 1000;
		
		nextHover = false;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		battlemap.draw(0,0);
//		g.drawString(mouse,50,200);
		if(!skillsWindow){
			attackButton.draw(55,370);
			itemsButton.draw(55, 420);
			skillsButton.draw(200,370);
			runButton.draw(200,420);
		}
		else if(skillsWindow){
			
			for(int i = 0, x; i < 3; i++){
				x = Menu.playerChar.learnedSkills[i];
				switch(x){
				case 1:
					skills[x].draw(55,370);
					break;
				
				case 2:
					skills[x].draw(55, 420);
					break;
					
				case 3:
					skills[x].draw(200,370);
					break;
					
				default:
					break;
					
				}
			}
			skillBack.draw(200,420);
		}
		
		// health bar
		g.setColor(Color.red);
		
		if(playerIsAlive)
			g.fillRect(38, 30, ((float)playerHP/(float)player.getHealth()) * 250f, 33);
		
		// monster health bar
		if(monsterIsAlive)
			g.fillRect(355, 30, ((float)monsterHP/(float)monster.getHealth()) * 250f, 33);
		
		g.setColor(Color.white);
		healthBar.draw(10,5);
		
		g.drawString(text, 365, 363);
		g.drawString(text2, 365, 383);
		
		g.drawString(""+player.getLevel(), 311, 25);
		knightSprite.draw(100,220);
		monster.sprite.draw(400,100);
		
		g.drawString(castSkill, 100, 180);
			
		
		if(hoverAttack == true){
			cursor.draw(40,370);
		}
		if(hoverItems == true){
			cursor.draw(40,423);
		}
		if(hoverSkills == true){
			cursor.draw(185,370);
		}
		if(hoverRun == true){
			cursor.draw(185,423);
		}
		
		
		if(allowBuy == true){
			shopOpen.draw(360,420);
		}
		
		
		if(paused==true){
			menuWindow.draw(200,50);
			g.drawString("Resume (R)", 260,240);
			g.drawString("Main Menu (M)", 260,200);
			g.drawString("Quit (Q)", 260, 160);

			if(paused==false){
				g.clear();
			}
		}
		
		// END BATTLE BUTTONS
		if(!playerIsAlive || !monsterIsAlive){
			text = ""; text2 = "";
			if(!playerIsAlive){
				g.drawImage(defeat, gc.getWidth()/2 - defeat.getWidth()/2, 103);
				if(nextHover)
					g.drawImage(returnMainMenuWhite, 18, gc.getHeight()-124);
				else
					g.drawImage(returnMainMenu, 18, gc.getHeight()-124);
			}
			else if(!monsterIsAlive){
				g.drawImage(victory, gc.getWidth()/2 - victory.getWidth()/2, 103);
				if(nextHover)
					g.drawImage(nextWhite, 18, gc.getHeight()-124);
				else
					g.drawImage(next, 18, gc.getHeight()-124);
			}
		}
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		Input input = gc.getInput();
		float posX = Mouse.getX();
		float posY = Mouse.getY();
		floor = "10";
		mouse = "Xpos = "+posX+"  Ypos = "+posY;
		hoverAttack = false;
		hoverItems = false;
		hoverSkills = false;
		hoverItems = false;
		hoverRun = false;
		battleEnd = false;
		
		if(playerIsAlive && monsterIsAlive){
			if(!skillsWindow){
				// ATTACK BUTTON
				if((posX > 24 && posX < 125) && (posY > 72) && posY < 125){
					if(paused == false)
					hoverAttack = true;
					if(input.isMousePressed(0) && !paused && (monsterIsAlive && playerIsAlive)){

						int damage = attack(player.getStr(), player.getDex(), player.equippedItems[0]);
						fight(damage);
					}
				}
				// ITEM BUTTON
				else if((posX > 24 && posX < 111) && (posY > 20) && posY < 111){
					if(paused == false)
					hoverItems = true;
					if(input.isMousePressed(0)){
						System.out.println("item");
					}
				}
				// SKILLS BUTTON
				else if((posX > 200 && posX < 340) && (posY > 72) && posY < 112){
					if(paused == false)
					hoverSkills = true;
					if(input.isMousePressed(0)){
						skillsWindow = true;
					}
					
				}
				// RUN BUTTON
				else if((posX > 200 && posX < 234) && (posY > 20) && posY < 62){
					if(paused == false)
					hoverRun = true;
				}
			}//end !skillsWindow
			// START SKILLS WINDOW
			else{
				if((posX > 24 && posX < 125) && (posY > 72) && posY < 125){
					if(input.isMousePressed(0) &&  player.learnedSkills[0] > 0){
						int damage = spell(player.getStr(), player.getDex(), player.getIntl(), player.learnedSkills[0]);
						fight(damage);
						castSkill = Menu.skillList.get(player.learnedSkills[0]).getName() + "!";
						skillsWindow = false;
					}
					hoverAttack = true;
				}
				else if((posX > 24 && posX < 111) && (posY > 20) && posY < 111){
					hoverItems = true;
					if(input.isMousePressed(0) && player.learnedSkills[1] > 0){
						int damage = spell(player.getStr(), player.getDex(), player.getIntl(), player.learnedSkills[1]);
						fight(damage);
						castSkill = Menu.skillList.get(player.learnedSkills[0]).getName() + "!";
						skillsWindow = false;
					}
				}
				else if((posX > 200 && posX < 340) && (posY > 72) && posY < 112){
					hoverSkills = true;
					if(input.isMousePressed(0) && player.learnedSkills[2] > 0){
						int damage = spell(player.getStr(), player.getDex(), player.getIntl(), player.learnedSkills[2]);
						fight(damage);
						castSkill = Menu.skillList.get(player.learnedSkills[0]).getName() + "!";
						skillsWindow = false;
					}
				}
				else if((posX > 200 && posX < 234) && (posY > 20) && posY < 62){
					hoverRun = true;
					if(input.isMousePressed(0)){
						skillsWindow = false;
					}
				}					
			}
			
		}
		// NEXT BUTTON (END BATTLE, PROCEED TO HOME STATE)
		else{
			if((posX > 18 && posX < 18 + next.getWidth()) && (posY > 16 && posY < 125)){
				nextHover = true;
				if(input.isMousePressed(0)){
					if(!monsterIsAlive){
						sbg.enterState(5);
						Menu.playerChar.setLevel(Menu.playerChar.getLevel()+1);
						Status.points += 5;
						earnedGold = 10 * Menu.playerChar.getLevel() + (rand.nextInt(10));
						Menu.playerChar.setGold(Menu.playerChar.getGold() + earnedGold);
						
						try {
							save(false);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						this.init(gc, sbg);
					}
					else if(!playerIsAlive){
						// RESET TO LEVEL 1
						try	{ save(true); } //update DB to reset to lv1 
						catch (SQLException e) { e.printStackTrace(); }
						sbg.enterState(2);
						Status.points = 0;
						Menu.initPlayer(1);
						this.init(gc, sbg);
					}
					
				}
			}
			else{
				nextHover = false;
			}
		}
			
				//when the menu is up
				if(input.isKeyDown(Input.KEY_ESCAPE)){
					paused = true;
				}
				
				if(paused == true){
					if(input.isKeyDown(Input.KEY_R)){
						paused = false;
					}
					if(input.isKeyDown(Input.KEY_M)){
						sbg.enterState(0);
						paused = false;
					}
					if(input.isKeyDown(Input.KEY_Q)){
						System.exit(0);
					}
				}
		
	}
	
	public boolean isAlive(int health){
		if(health <= 0){
			return false;
		}
		else
			return true;
	}
	
	public int getID(){
		return 1;
	}
	
	public int attack(int str,int dex,int wepID){
		int damage = str + rand.nextInt(10) + Menu.itemList.get(wepID).getValue();
		if(rand.nextInt(100)==dex){
			damage*=2;			
		}
		return damage;
	}
	
	public int spell(int str, int dex, int intl,int spellId){
        Random rand = new Random();
        int damage=0;
        int critMultiplier=2;
        if(Menu.skillList.get(spellId).getEffect()==spellEffect.burn){
                damage=Menu.skillList.get(spellId).getBaseDamage()+(intl/2)+str;
        }
        else if(Menu.skillList.get(spellId).getEffect()==spellEffect.freeze){
                critMultiplier=4;
                damage=Menu.skillList.get(spellId).getBaseDamage()+intl;
        }
        else if(Menu.skillList.get(spellId).getEffect()==spellEffect.shock){
                dex*=2;
                damage=Menu.skillList.get(spellId).getBaseDamage()+(intl*2);
        }
        else if(Menu.skillList.get(spellId).getEffect()==spellEffect.poison){
                dex/=3;
                critMultiplier=999;
                damage=Menu.skillList.get(spellId).getBaseDamage();
        }
        if(rand.nextInt(100)==dex)
                damage*=critMultiplier;
        return damage+rand.nextInt(10);
	}
	
	public static void save(boolean defeat) throws SQLException{
		PreparedStatement pst;

		player = Menu.playerChar;
		
		int level = 0, health = 0, str = 0, dex = 0, intl = 0, gold = 0;

		if(!defeat){
			level = player.getLevel();
			health = player.getHealth();
			str = player.getStr();
			dex = player.getDex();
			intl = player.getIntl();
			gold = player.getGold();
		}
		else if(defeat){
			// BACK TO LEVEL 1
			level = 1;
			health = 100;
			str = 10;
			dex = 10;
			intl = 10;
			gold = 10;
			pst = Game.con.prepareStatement("DELETE FROM `ozmodia`.`skills` WHERE ch_ID="+Menu.charID);
			pst.executeUpdate();
			pst = Game.con.prepareStatement("DELETE FROM `ozmodia`.`inventory` WHERE ch_ID="+Menu.charID);
			pst.executeUpdate();
		}

		pst = Game.con.prepareStatement("UPDATE `ozmodia`.`character` SET ch_health='"+ health +"', ch_gold='"+gold+"', ch_str='"+str+"', ch_dex='"+dex+"', ch_intl='"+intl+"', ch_lvl='"+level+"' WHERE ch_ID=" + Menu.charID);
		pst.executeUpdate();
	}
	
	public int defendDamage(int damage,int[] equippedItems){
        int totalDefense=10;
        totalDefense += Menu.itemList.get(equippedItems[1]).getValue();
        totalDefense += Menu.itemList.get(equippedItems[2]).getValue();
        totalDefense += Menu.itemList.get(equippedItems[3]).getValue();
        totalDefense += Menu.itemList.get(equippedItems[4]).getValue();
        float threshold=(float)damage/(float)totalDefense;
        if (threshold>1)
                threshold=1;
        float totalDamage = threshold*(float)damage;
        return (int)totalDamage;
	}
	
	public void fight(int damage){
		int damage2 = attack(monster.getStr(), monster.getDex(), 0);
		if(player.getDex() > monster.getDex()){ // player attacks first					
			monsterHP -= damage;
			monsterIsAlive = isAlive(monsterHP);
			text = monster.getName() + " received " + damage + " damage!";
			
			if(monsterIsAlive){
				playerHP -=  defendDamage(damage2,player.getEquippedItems());
				playerIsAlive = isAlive(playerHP);
				text2 = player.getName() + " received " + damage2 + " damage!";								
			}
		}
		else{ // monster attacks first
			playerHP -= defendDamage(damage2,player.getEquippedItems());
			playerIsAlive = isAlive(playerHP);
			text = player.getName() + " received " + damage2 + " damage!";
			
			if(playerIsAlive){
				monsterHP -= damage;
				monsterIsAlive = isAlive(monsterHP);
				text2 = monster.getName() + " received " + damage + " damage!";							
			}
		}
	}

}
