package com.player;

import com.raylib.Vector2;

import com.raylib.Rectangle;

public class Player
{
/***********************************************************************************/
/***                                 VARIABLES                                   ***/
/***********************************************************************************/

	public PlayerMovement	movement;

	Vector2		position;		// Player position on World
	Vector2		size;			// Player sizeda
	Vector2		offset;			// Player offset
	float		scale;			// Player scale

	Rectangle	colisionBox;	// Player colision box

/***********************************************************************************/
/***                                 CONSTRUCTOR                                 ***/
/***********************************************************************************/

	public Player(Vector2 position, Vector2 size, Rectangle colisionBox,
		float scale, Vector2 offset)
	{
		this.movement = new PlayerMovement();
		this.position = position;
		this.size = size;
		this.scale = scale;
		this.colisionBox = colisionBox;
		this.offset = offset;
	}

/***********************************************************************************/
/***                                 FUNCTIONS                                   ***/
/***********************************************************************************/

	public void update()
	{
		this.movement.update();
	}

/***********************************************************************************/
/***                                 GETTERS                                     ***/
/***********************************************************************************/

	public Vector2 getPosition()
	{
		return position;
	}

	public Rectangle getColisionBox()
	{
		return colisionBox;
	}

	public Vector2 getOffset()
	{
		return offset;
	}

	public Vector2 getSize()
	{
		return this.size;
	}

	public float getScale()
	{
		return scale;
	}

/***********************************************************************************/
/***                                 SETTERS                                       */
/***********************************************************************************/

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
}
