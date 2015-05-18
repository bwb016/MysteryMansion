package game;

import static helpers.Artist.QuickLoad;
import helpers.Artist;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.opengl.Texture;

public class FightManager {

	public enum State {
		TALK, FIGHT;
	}

	public static State state = State.TALK;

	private static String[] battleStrings;
	private static String[] attackList;
	private GameCharSprite character;
	private Inventory inventory;
	private Texture dialogue;
	private Texture enemyTex;
	private Texture playerTex;
	private TextManager text;
	private String dialogueText;
	private boolean first = true;
	private Texture enemyStatus;
	private Texture playerStatus;
	private int enemyHealth = 20, enemyLevel = 1, enemyExp = 10;
	private int battleScene1 = 1,battlescene2 = 2;
	private boolean wasPressed = true;
	private int battleStrings_index = 0;
	private Sound slash;
	private Sound stab;

	private String enemyName = "Possessed Skeleton";

	private boolean chooseAttack;

	private boolean playerInput;

	private boolean isFirstAttack = true;

	private boolean getResults;

	private String lastAttack;
	private boolean gameover = false;

	private Sound punch;

	private Sound evil;

	private int battleScene;

	public FightManager(GameCharSprite character, Inventory inventory,
			TextManager textManager) {
		this.text = textManager;
		this.character = character;
		this.inventory = inventory;
		load();
		battleStrings = new String[10];
		attackList = new String[10];
		try {
			stab = new Sound("res/sound/sword.wav");
			slash = new Sound("res/sound/swing.wav");
			punch = new Sound("res/sound/punch.wav");
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private void attack() {
		if (isFirstAttack) {
			chooseAttack = true;
			isFirstAttack = false;
		}
		if (state == State.FIGHT) {
			if(character.getHealth() < 1 || enemyHealth < 1){
				enemyHealth = 0;
				
				gameover();
				
			}
			
			
			if (chooseAttack) {
				dialogueText = "Choose Your attack:  1)" + attackList[0]
						+ "  2)" + attackList[1] + "  3)" + attackList[2]
						+ "  4)" + attackList[3];
				playerInput = true;
				chooseAttack = false;
			}
			if (playerInput && !getResults) {
				if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
					slash.play();
					enemyHealth -= getDamage(attackList[0]);
					character.setHealth(character.getHealth() - ((int)(Math.random()*10)+1));
					lastAttack = attackList[0];
					playerInput = false;
					chooseAttack = false;
					getResults = true;
					wasPressed = true;
				}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
				stab.play();
				enemyHealth -= getDamage(attackList[1]);
				character.setHealth(character.getHealth() - ((int)(Math.random()*10)+1));
				lastAttack = attackList[1];
				playerInput = false;
				chooseAttack = false;
				getResults = true;
				wasPressed = true;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
				punch.play();
				enemyHealth -= getDamage(attackList[2]);
				character.setHealth(character.getHealth() - ((int)(Math.random()*10)+1));
				lastAttack = attackList[2];
				playerInput = false;
				chooseAttack = false;
				getResults = true;
				wasPressed = true;

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
				enemyHealth -= getDamage(attackList[3]);
				character.setHealth(character.getHealth() - ((int)(Math.random()*10)+1));
				lastAttack = attackList[3];
				playerInput = false;
				chooseAttack = false;
				getResults = true;
				wasPressed = true;
			}
			}
			if (getResults) {

				setDialogue(lastAttack + " .... < press enter > ");

				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					System.out.println(wasPressed);
					getResults = false;
					chooseAttack = true;

					if (wasPressed) {

						wasPressed = false;
					}

				}

			}

		}
	}

	private int attackHit() {
		int attackDamage = 0;
		return attackDamage;
	}

	private void Dialogue() {

		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {

			if (wasPressed && state == State.TALK) {
				System.out.println(battleStrings_index);
				setDialogue("" + battleStrings[battleStrings_index]);
				if (battleStrings_index + 1 < 40) {
					battleStrings_index++;
				}
			}
			wasPressed = false;
		} else {
			if (state == State.TALK)
				wasPressed = true;
		}
		if (first) {
			dialogueText = "Prepare for battle!!..............."
					+ "< press enter >";
			first = false;
			loadBattle(battleScene);
		}
		
			if (battleStrings[battleStrings_index].equals("")
					&& state != State.FIGHT) {
				System.out.println("Fight");
				state = State.FIGHT;

			}
			

		
	}

	private void Draw() {
		Artist.DrawQuadTex(playerTex, 100, 400 - 10, 128*2, 128*2);
		Artist.DrawQuadTex(dialogue, 0, TileGrid.COLUMN * TileGrid.tileSize
				- 128, 448 * 2 + 64, 128);
		Artist.DrawQuadTex(enemyTex, 600, 50, 256/2, (320 + 50)/2);
		Artist.DrawQuadTex(enemyStatus, 10, 50, 256, 128);
		Artist.DrawQuadTex(playerStatus, 700 - 80, 400 - 10, 256, 128);

	}

	private void gameover() {
		state = State.TALK;
		chooseAttack = false;
		getResults = false;
		wasPressed = false;
		if(!gameover){
		GameCharSprite.setExp(GameCharSprite.getExp() + enemyExp);
		gameover = true;
		}
		battleStrings[battleStrings_index]= battleStrings[battleStrings_index+1];
		
		
	}

	public int getBattleScene() {
		return battleScene;
	}

	private int getDamage(String string) {


		
		switch(string){
		case "[Slash]":
			return (int) (Math.random()*10+1);
		case "[Stab]":
			return (int) (Math.random()*5+1);
		case "[Punch]":
			return (int) (Math.random()*8+1);
		case "[Run]":
			return -10;
		}
		return 0;
		
	}

	public String getDialogue() {
		return dialogueText;
	}

	public int getEnemyExp() {
		return enemyExp;
	}

	public int getEnemyHealth() {
		return enemyHealth;
	}

	public int getEnemyLevel() {
		return enemyLevel;
	}

	public String getEnemyName() {
		return enemyName;
	}

	public Texture getEnemyStatus() {
		return enemyStatus;
	}

	public Texture getEnemyTex() {
		return enemyTex;
	}

	private void load() {
		dialogue = QuickLoad("dialogue");
		enemyTex = QuickLoad("pveChar/skelton");
		playerTex = QuickLoad("images/playerU2fight");
		enemyStatus = QuickLoad("status");
		playerStatus = QuickLoad("status");

	}

	private void loadBattle(int battleScene2) {
		switch (battleScene) {
		case 1:
			battleStrings[0] = "Skeleton: <makes skelton noises..";
			battleStrings[1] = "Skeleton: Crack!!..";
			battleStrings[2] = "Skeleton: CrAck Crack CRACK1";
			battleStrings[3] = " ";
			battleStrings[4] = "";
			battleStrings[5] = "CRACKKKKkkkkk.....";
			battleStrings[6] = "endgame";
			battleStrings[7] = "";
			battleStrings[8] = "";
			battleStrings[9] = "";
			

			attackList[0] = "[Slash]";
			attackList[1] = "[Stab]";
			attackList[2] = "[Punch]";
			attackList[3] = "[Run]";
			break;
		case 2:
			
			
			battleStrings[0] = "Cecil: Fool you can't kill me!";
			battleStrings[1] = "Cecil: I don't care who needs your body! ";
			battleStrings[2] = "Cecil: DIE DIE DIE";
			battleStrings[3] = " ";
			battleStrings[4] = "";
			battleStrings[5] = "Cecil: Cecil hurt!!";
			battleStrings[6] = "endgame";
			battleStrings[7] = "";
			battleStrings[8] = "";
			battleStrings[9] = "";
			
			enemyTex = QuickLoad("images/cecilDfight");
			enemyName = "Cecil - Boss";
			enemyLevel = 5;
			enemyExp = 100;
			

			attackList[0] = "[Slash]";
			attackList[1] = "[Stab]";
			attackList[2] = "[Punch]";
			attackList[3] = "[Run]";
			break;
		}

	}

	public void setBattleScene(int battleScene) {
		this.battleScene = battleScene;
	}

	public void setDialogue(String dialogue) {
		this.dialogueText = dialogue;
	}

	public void setEnemyExp(int enemyExp) {
		this.enemyExp = enemyExp;
	}

	public void setEnemyHealth(int enemyHealth) {
		this.enemyHealth = enemyHealth;
	}

	public void setEnemyLevel(int enemyLevel) {
		this.enemyLevel = enemyLevel;
	}

	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
	}

	public void setEnemyStatus(Texture enemyStatus) {
		this.enemyStatus = enemyStatus;
	}

	public void setEnemyTex(Texture enemyTex) {
		this.enemyTex = enemyTex;
	}

	private void statuses() {
		text.setText(text.getText(4), dialogueText, 50, TileGrid.COLUMN
				* TileGrid.tileSize - 100, Color.black);
		text.callText(4);
		// ------------------------------------------
		// player status
		// ------------------------------------------
		text.setText(text.getText(2), "" + character.getHealth(), 700 + 30, // health
				390, Color.black);
		text.callText(2);
		// ------------------------------------------
		text.setText(text.getText(5), "" + character.getLevel2(), 700 + 30, // level
				390 + 45, Color.black);
		text.callText(5);
		// ------------------------------------------
		text.setText(text.getText(7), "" + GameCharSprite.getExp(), 700 + 30,// exp
				390 + 90, Color.black);
		text.callText(7);
		// ------------------------------------------
		text.setText(text.getText(6), "Player:  Callon", 630,// name
				350, Color.red);
		text.callText(6);
		// ------------------------------------------
		// enemy status
		// ------------------------------------------

		text.setText(text.getText(8), "" + enemyHealth, 100 + 30, // health
				50, Color.black);
		text.callText(8);
		// ------------------------------------------
		text.setText(text.getText(9), "" + enemyLevel, 100 + 30, // level
				50 + 45, Color.black);
		text.callText(9);
		// ------------------------------------------
		text.setText(text.getText(10), "" + enemyExp, 100 + 30,// exp
				50 + 90, Color.black);
		text.callText(10);
		// ------------------------------------------
		text.setText(text.getText(11), "Enemy:  " + enemyName, 20,// name
				20, Color.red);
		text.callText(11);
	}

	public void Update(int battleScene3) {
		battleScene = battleScene3;

		Draw();
		Dialogue();
		statuses();
		if (state == State.FIGHT) {
			attack();

		}
		if (battleStrings[battleStrings_index].equals("endgame")){
			GL11.glClearColor(0.1f, 0.1f, 0.0f, 0.0f);
			enemyHealth = 50;
			battleStrings_index = 0;
			battleScene = 2;
			Game.state = game.Game.State.GAME;
			
		}
	}
	
	public void reset(){
		first = true;
		isFirstAttack = true;
		enemyHealth = 50;
	}
}
