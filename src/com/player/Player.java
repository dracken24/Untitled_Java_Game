package com.player;

import com.enums.SpriteMovement;
import com.raylib.Vector2;
import static com.raylib.Raylib.drawRectangleRec;
// import static com.raylib.Raylib.WHITE;
import static com.raylib.Raylib.KeyboardKey.KEY_B;
import static com.raylib.Raylib.isKeyPressed;

import com.raylib.Color;

import com.raylib.Rectangle;

public class Player
{
/***********************************************************************************/
/***                                 VARIABLES                                   ***/
/***********************************************************************************/

	public PlayerMovement	movement;
	Vector2		position;		// Player position on World
	Vector2		size;			// Player size
	Rectangle	colisionBox;	// Player colision box
	float		scale;			// Player scale
	Vector2		offset;			// Player offset

	Vector2		initialPosition;	// Player initial position
	Rectangle	initialColisionBox;	// Player initial colision box

	public static Color RED_SHADOW = new Color((byte)230, (byte)41, (byte)55, (byte)105);
	public static Color DARKPURPLE_SHADOW = new Color((byte)112, (byte)31, (byte)126, (byte)105);

/***********************************************************************************/
/***                                 CONSTRUCTOR                                 ***/
/***********************************************************************************/

	public Player(Vector2 position, Vector2 size, Rectangle colisionBox,
		float scale, Vector2 offset)
	{
		movement = new PlayerMovement();
		this.position = position;
		this.size = size;
		this.scale = scale;
		this.colisionBox = colisionBox;
		this.offset = offset;
		initialPosition = new Vector2(position.getX(), position.getY());
		initialColisionBox = new Rectangle(colisionBox.getX(), colisionBox.getY(), colisionBox.getWidth(), colisionBox.getHeight());
	}

/***********************************************************************************/
/***                                 FUNCTIONS                                   ***/
/***********************************************************************************/

	boolean isDebug = false;

	public void update()
	{
		// drawSize();
		if (isKeyPressed(KEY_B))
		{
			isDebug = !isDebug;
		}
		if (isDebug)
		{
			drawColisionBox();
			// System.out.println("Weapon coll box x: " + this.weaponColisionBox.getX() + "  y: " + this.weaponColisionBox.getY() );
		}

		movement.applyMovement(position, colisionBox, movement.getVelocity());
		movement.update(position, offset);
	}

	void drawColisionBox()
	{
		Rectangle colBox = new Rectangle(
			(this.colisionBox.getX() + offset.getX() + this.colisionBox.getHeight() - this.colisionBox.getWidth()), 
			this.colisionBox.getY() + offset.getY(), 
			this.colisionBox.getWidth() * scale,
			this.colisionBox.getHeight() * scale
		);
		
		drawRectangleRec(colBox, DARKPURPLE_SHADOW);
	}

/***********************************************************************************/
/***                                 GETTERS                                     ***/
/***********************************************************************************/

	public Vector2 getPosition()
	{
		return position;
	}

	public Vector2 getVelocity()
	{
		return movement.getVelocity();
	}

	public Rectangle getColisionBox()
	{
		return colisionBox;
	}

	public Rectangle getColisionBoxPlusOffset()
	{
		return new Rectangle(
			colisionBox.getX() + offset.getX(), 
			colisionBox.getY() + offset.getY(), 
			colisionBox.getWidth() * scale,
			colisionBox.getHeight() * scale
		);
	}

	public Vector2 getOffset()
	{
		return offset;
	}

	public SpriteMovement getActionInProgress()
	{
		return movement.getActionInProgress();
	}

/***********************************************************************************/
/***                                 SETTERS                                       */
/***********************************************************************************/

	public void setMovement(SpriteMovement movement)
	{
		this.movement.setMovement(movement);
	}

	public void setPosition(Vector2 position)
	{
		this.position = position;
	}

	public void setColisionBox(Rectangle colisionBox)
	{
		this.colisionBox = colisionBox;
	}

	public void setOffset(Vector2 offset)
	{
		this.offset = offset;
	}

	public void setActionCounter(int actionCounter)
	{
		movement.setActionCounter(actionCounter);
	}

	public void setIsWallCollide(boolean isWallCollide)
	{
		movement.setIsWallCollide(isWallCollide);
	}
}
