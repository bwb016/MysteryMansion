package game;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class GameObj {

	private TileGrid grid;
	private float  x, y;

	private Texture texture;
	private int width, height, health;
	private GameCharSprite character;

	public GameObj(Texture texture, int width,
			int height,  int x, int y, GameCharSprite character) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.character = character;

	}
	public GameObj(Texture texture, int width,
			int height,  int x, int y) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.character = character;

	}

	

	public void Draw() {

		DrawQuadTex(texture, x, y, width, height);
	}

	public int getHealth() {
		return health;
	}

	public int getHeight() {
		return height;
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

	public float getY() {
		return y;
	}

	

	

	
	

	public void setHeight(int height) {
		this.height = height;
	}

	

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void Update() {
		

	}
}