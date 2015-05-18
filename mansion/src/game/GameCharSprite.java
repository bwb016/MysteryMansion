package game;

import static helpers.Clock.Delta;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.opengl.Texture;

public class GameCharSprite {

	public enum Key_State {
		UP, DOWN, LEFT, RIGHT;
	}

	private static Key_State keyState;
	private static Key_State lastRender;

	private static float speed, x, y;
	private static CollisionGrid collide;
	private static int currentLevel,exp;
	private static String file;

	private static Animation ani;
	private static SpriteSheet sheet;
	private static int spriteWidth;
	private static int spriteHeight;
	private static TextManager textManager;
	public static int getCurrentLevel() {
		return currentLevel;
	}
	public static int getExp() {
		return exp;
	}
	public static Animation render() {
		try {
			sheet = new SpriteSheet(file, spriteWidth, spriteHeight);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		ani = new Animation(GameCharSprite.sheet, (int) GameCharSprite.speed);
		return ani;
	}
	public static void setCurrentLevel(int currentLevel) {
		GameCharSprite.currentLevel = currentLevel;
	}
	public static void setExp(int exp) {
		GameCharSprite.exp = exp;
	}
	private boolean first = true, alive = true, rendered = false;

	private TileGrid grid;

	private Tile startTile;

	private Texture texture;

	private int width, height;

	private long health;
	private boolean paused = false;
	private Inventory inventory;

	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	public GameCharSprite(String file, int spriteWidth, int spriteHeight,
			Tile startTile, TileGrid grid, int width, int height, int speed,
			int health, TextManager textManager, CollisionGrid collide, Inventory inventory) {
		GameCharSprite.file = file;
		this.startTile = startTile;
		GameCharSprite.x = startTile.getX();
		GameCharSprite.y = startTile.getY();
		this.width = width;
		this.height = height;
		this.grid = grid;
		GameCharSprite.collide = collide;
		GameCharSprite.speed = speed;
		GameCharSprite.spriteWidth = spriteWidth;
		GameCharSprite.spriteHeight = spriteHeight;
		GameCharSprite.currentLevel = 100;
		GameCharSprite.exp = 0;
		GameCharSprite.textManager = textManager;
		this.inventory = inventory;
		this.health = health;

	}

	public void Draw() {

	}

	public void Update() {
		//System.out.println(this.getXInt()+" "+this.getYInt());
		textManager.setText(textManager.getText(1), "Health: " + health, 650,
				100, Color.red);
		textManager.callText(1);
		textManager.setText(textManager.getText(2), "Backpack: "+" ["+(inventory.getCurrentIndex())+"] " + inventory.getBackpack()[inventory.getCurrentIndex()].getItemName(), 650,
				500, Color.red);
		textManager.callText(2);
		textManager.setText(textManager.getText(14), "Equipped", 690,
				200, Color.red);
		textManager.callText(14);
		if (rendered == false) {
			render();
			rendered = true;
		}
		ani.draw(x, y, width, height);	
		ani.stop();
		
		if(!paused ){
		keyboard(getKey());
		}
	}
	private void direction() {
		switch (keyState) {
		case UP:
			setFile("res/images/playerU2.png");
			//setFile("res/images/cecilU.png");
			render();
			lastRender = Key_State.UP;
			break;
		case DOWN:
			setFile("res/images/playerD2.png");
			//setFile("res/images/cecilD.png");
			render();
			lastRender = Key_State.DOWN;
			break;
		case LEFT:
			setFile("res/images/playerL2.png");
			
			//setFile("res/images/cecilL.png");
			render();
			lastRender = Key_State.LEFT;
			break;
		case RIGHT:
			setFile("res/images/playerR2.png");
			//setFile("res/images/cecilR.png");
			render();
			lastRender = Key_State.RIGHT;
			break;
	
		}
	
	}
	public boolean findNextB() {

		if (collide.getTile((int) Math.floor(getX() / 16),
				(int) Math.floor(getY() / 16 + 2)).canCollide()) {
			{
				// System.out.println("Bottom");
				return true;
			}
		}
		return false;

	}

	public boolean findNextL() {

		if (collide.getTile((int) Math.floor(getX() / 16 - 1),
				(int) Math.floor(getY() / 16) + 1).canCollide()) {
			{
				// System.out.println("Left"+getXInt2());
				return true;
			}
		}
		return false;

	}

	public boolean findNextR() {

		if (collide.getTile((int) Math.floor(getX() / 16 + 1),
				(int) Math.floor(getY() / 16) + 1).canCollide()) {
			{
				// System.out.println("Right");
				return true;
			}
		}
		return false;

	}

	public boolean findNextT() {

		if (collide.getTile((int) Math.floor(getX() / 16),
				(int) Math.ceil(getY() / 16 - 1)).canCollide()) {
			{
				// System.out.println("Top");
				return true;
			}
		}
		return false;

	}

	public long getHealth() {
		return health;
	}

	public int getHeight() {
		return height;
	}

	private int getKey() {
		if ((Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard
				.isKeyDown(Keyboard.KEY_UP)) && !findNextT()) {
			return 3;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard
				.isKeyDown(Keyboard.KEY_DOWN)) && !findNextB()) {
			return 4;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard
				.isKeyDown(Keyboard.KEY_LEFT)) && !findNextL()) {
			return 2;
		}
		if ((Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard
				.isKeyDown(Keyboard.KEY_RIGHT)) && !findNextR()) {
			return 1;
		}
		return 0;
	}

	public int getLevel() {
		return currentLevel;
	}

	public float getSpeed() {
		return speed;
	}

	public Tile getStartTile() {
		return startTile;
	}

	public Texture getTexture() {
		return texture;
	}

	public TileGrid getTileGrid() {
		return grid;
	}

	public int getWidth() {
		return width;
	}

	public float getX() {
		return x;
	}

	public int getXInt() {
		return (int) Math.floor(getX() * 3 / TileGrid.tileSize);
	}

	public int getXInt2() {
		return (int) Math.floor(getX() / TileGrid.tileSize);
	}

	public float getY() {
		return y;
	}

	public int getYInt() {
		return (int) Math.floor(getY() * 2 / (TileGrid.tileSize));
	}

	public int getYInt2() {
		return (int) Math.floor(getY() / TileGrid.tileSize);
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean isFirst() {
		return first;
	}

	private void keyboard(int key) {

		ani.draw(x, y, width, height);

		ani.stop();
		switch (key) {
		case 1:

			keyState = Key_State.RIGHT;
			if (lastRender != Key_State.RIGHT) {
				direction();
			}

			if (x < TileGrid.tileSize * TileGrid.COLUMN - 64) {
				ani.start();
				ani.draw(x, y, width, height);
				x += Delta() * speed;

			}
			break;

		case 2:
			keyState = Key_State.LEFT;
			if (lastRender != Key_State.LEFT) {
				direction();
			}
			if (x > 16) {
				ani.start();
				ani.draw(x, y, width, height);
				x -= Delta() * speed;

			}

			break;

		case 3:
			keyState = Key_State.UP;
			if (lastRender != Key_State.UP) {
				direction();
			}
			if (y > TileGrid.tileSize / 4) {
				ani.start();
				ani.draw(x, y, width, height);
				y -= Delta() * speed;

			}

			break;
		case 4:

			keyState = Key_State.DOWN;
			if (lastRender != Key_State.DOWN) {
				direction();
			}
			if (y < TileGrid.tileSize * TileGrid.ROW - 80) {
				ani.start();
				ani.draw(x, y, width, height);
				y += Delta() * speed;


			}

			break;

		}
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void setFile(String file) {
		GameCharSprite.file = file;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setHealth(long health) {
		this.health = health;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setLevel(int level) {
		System.out.println(level);
		currentLevel = level;
	}

	public void setSpeed(float speed) {
		GameCharSprite.speed = speed;
	}

	public void setStartTile(Tile startTile) {
		this.startTile = startTile;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(float x) {
		GameCharSprite.x = x;
	}

	public void setY(float y) {
		GameCharSprite.y = y;
	}
	public int getLevel2() {
		
		return 1;
	}
}