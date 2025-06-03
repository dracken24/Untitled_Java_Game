/* =============================================================================== */
/* ---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~--- */
/*               -------------------------------------------------                 */
/*                PROJET: Java Dev          PAR: Dracken24                         */
/*               -------------------------------------------------                 */
/*                CREATED: 28-2nd-2025                                             */
/*                MODIFIED BY: Dracken24                                           */
/*                LAST MODIFIED: 28-2nd-2025                                       */
/*               -------------------------------------------------                 */
/*                FILE: Player.java                                                */
/*               -------------------------------------------------                 */
/* ---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~--- */
/* =============================================================================== */

package com.player;

import com.enums.SpriteMovement;
import com.raylib.Vector2;
import static com.raylib.Raylib.drawRectangleRec;
import static com.raylib.Raylib.WHITE;
import static com.raylib.Raylib.KeyboardKey.KEY_B;
import static com.raylib.Raylib.KeyboardKey.KEY_R;
import static com.raylib.Raylib.isKeyPressed;

import com.raylib.Color;

import com.raylib.Rectangle;

public class Player
{
/***********************************************************************************/
/***                                 VARIABLES                                   ***/
/***********************************************************************************/

	public PlayerMovement	movement;
	Vector2		position;
	Vector2		size;
	Rectangle	colisionBox;
	Rectangle	weaponColisionBox;
	int			scale;
	Vector2		offset;
	Vector2		collisionBoxOffset;

	Vector2		initialPosition;
	Rectangle	initialColisionBox;

	private float bounceForce;

	public static Color RED_SHADOW = new Color((byte)230, (byte)41, (byte)55, (byte)105);
	public static Color DARKPURPLE_SHADOW = new Color((byte)112, (byte)31, (byte)126, (byte)105);

/***********************************************************************************/
/***                                 CONSTRUCTOR                                 ***/
/***********************************************************************************/

	public Player(Vector2 position, Vector2 size, Rectangle colisionBox,
		Rectangle weaponColisionBox, int scale, Vector2 offset)
	{
		movement = new PlayerMovement();
		this.position = position;
		this.size = size;
		this.scale = scale;
		this.colisionBox = colisionBox;
		this.weaponColisionBox = weaponColisionBox;
		this.offset = offset;
		initialPosition = new Vector2(position.getX(), position.getY());
		initialColisionBox = new Rectangle(colisionBox.getX(), colisionBox.getY(), colisionBox.getWidth(), colisionBox.getHeight());
		bounceForce = 0.0f;
		collisionBoxOffset = new Vector2(colisionBox.getX(), colisionBox.getY());
	}

/***********************************************************************************/
/***                                 FUNCTIONS                                   ***/
/***********************************************************************************/

	boolean isDebug = true;
	public void update()
	{
		// drawSize();
		if (isKeyPressed(KEY_B))
		{
			isDebug = !isDebug;
		}
		if (isDebug)
		{
			// drawColisionBox();
			// System.out.println("Weapon coll box x: " + this.weaponColisionBox.getX() + "  y: " + this.weaponColisionBox.getY() );
		}
		

		movement.applyMovement(position, colisionBox, movement.getVelocity());
		movement.update(position, offset);
	}

	void drawColisionBox()
	{
		Rectangle colBox = new Rectangle(
			this.colisionBox.getX() + offset.getX(), 
			this.colisionBox.getY() + offset.getY(), 
			this.colisionBox.getWidth() * scale,
			this.colisionBox.getHeight() * scale
		);
		
		drawRectangleRec(colBox, DARKPURPLE_SHADOW);
	}

	void drawSize()
	{
		drawRectangleRec(
			new Rectangle(position.getX() - (size.getX() / 2 * scale) + offset.getX(),
			position.getY() - (size.getY() / 2 * scale) + offset.getY(),
			size.getX() * scale,
			size.getY() * scale), WHITE
		);
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

	public boolean getIsJumping()
	{
		return movement.getIsJumping();
	}

	public boolean getIsAtRest()
	{
		return movement.getIsAtRest();
	}

	public SpriteMovement getActionInProgress()
	{
		return movement.getActionInProgress();
	}

	public float getBounceForce()
	{
		return bounceForce;
	}

	public Vector2 getCollisionBoxOffset()
	{
		return collisionBoxOffset;
	}

	public Rectangle getWeaponCollisonBox()
	{
		return weaponColisionBox;
	}

	public Rectangle getWeaponCollisonBoxPlusOffset()
	{
		return new Rectangle(weaponColisionBox.getX() + offset.getX(), weaponColisionBox.getY() + offset.getY(), weaponColisionBox.getWidth() * scale, weaponColisionBox.getHeight() * scale);
	}

/***********************************************************************************/
/***                                 SETTERS                                       */

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

	public void setIsJumping(boolean isJumping)
	{
		movement.setIsJumping(isJumping);
	}

	public void setIsAtRest(boolean isAtRest)
	{
		movement.setIsJumping(isAtRest);
	}

	public void setActionCounter(int actionCounter)
	{
		movement.setActionCounter(actionCounter);
	}

	public void setIsWallCollide(boolean isWallCollide)
	{
		movement.setIsWallCollide(isWallCollide);
	}

	public void setBounceForce(float bounceForce)
	{
		this.bounceForce = bounceForce;
	}

	public void setWeaponCollisonBox(Rectangle collBox)
	{
		this.weaponColisionBox = collBox;
	}
}