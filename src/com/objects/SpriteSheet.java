package com.objects;

import com.raylib.Texture;
import com.raylib.Color;
import com.raylib.Vector2;

import static com.raylib.Raylib.loadTexture;
import static com.raylib.Raylib.drawTexturePro;
import static com.raylib.Raylib.drawRectangle;
import static com.raylib.Raylib.WHITE;
import com.raylib.Rectangle;

public class SpriteSheet
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/

	Texture spriteSheet;		// Texture of the sprite sheet
	Vector2 position;			// Position for the square of the sprite sheet
    float scale;				// Scale of the sprite sheet
    float rotation;				// Rotation of the sprite sheet
    Color color = new Color();	// Color of the sprite sheet

	int frameCount;				// Number of frames in the sprite sheet
	
	int currentFrame;			// Current frame of the sprite sheet
	Vector2 tileSize;			// Width and height of the frame in the sprite sheet

	int skipFPS;				// Number of frames to skip in the sprite sheet
	int skipFPSCount;			// Count frame to skip
	int animationTotalFrame;	// Total frame of the animation

/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

	public SpriteSheet(String path, int frameCount, Vector2 tileSize, int skipFPS)
	{
		initSpriteSheet(
			path,
			frameCount,
			tileSize,
			skipFPS,
			1,
			new Vector2(tileSize.getX() / 2, tileSize.getY() / 2)
		);
	}

	public SpriteSheet(String path, int frameCount, Vector2 tileSize,
		int skipFPS, float scale, Vector2 position)
	{
		initSpriteSheet(
			path,
			frameCount,
			tileSize,
			skipFPS,
			scale,
			position
		);
	}

	void initSpriteSheet(String path, int frameCount, Vector2 tileSize,
		int skipFPS, float scale, Vector2 position)
	{
		try
		{
			this.spriteSheet = loadTexture(path);
		}
		catch (Exception e)
		{
			System.out.println("Error loading texture: " + e.getMessage());
		}

		this.position = new Vector2(position.getX(), position.getY());
		this.scale = scale;
		this.rotation = 0;
		this.color = WHITE;
		this.frameCount = frameCount;
		this.currentFrame = 0;
		this.skipFPS = skipFPS;
		this.skipFPSCount = 0;
		this.tileSize = tileSize;
		this.animationTotalFrame = frameCount * skipFPS;
	}

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

	public void updateSprite(boolean resetFrame, Vector2 playerPosition, Vector2 offset)
	{
		Rectangle source = new Rectangle(
			currentFrame * tileSize.getX(),
			0,
			tileSize.getX(),
			tileSize.getY()
		);
		Rectangle dest = new Rectangle(
			playerPosition.getX() + offset.getX(), 
			playerPosition.getY() + offset.getY(), 
			tileSize.getX() * scale,
			tileSize.getY() * scale
		);
		Vector2 origin = new Vector2(tileSize.getX() * scale / 2, tileSize.getY() * scale / 2);

		// NOTE: param source is for the zone of the sprite sheet to draw
		// NOTE: param dest is for the position and size of the sprite sheet to draw
		// NOTE: param origin is for the center of the sprite sheet to draw

		
		drawTexturePro(spriteSheet, source, dest, origin, rotation, color);

		if (skipFPSCount < skipFPS)
		{
			skipFPSCount++;
		}
		else
		{
			currentFrame++;
			skipFPSCount = 0;
		}

		if (currentFrame >= frameCount || resetFrame)
		{
			currentFrame = 0;
		}
	}

	public void drawColisionBox(Vector2 colisionBoxPosition)
	{
		drawRectangle(
			(int)(colisionBoxPosition.getX() - (tileSize.getX() * scale / 2)), 
			(int)(colisionBoxPosition.getY() - (tileSize.getY() * scale / 2)), 
			(int)(tileSize.getX() * scale), 
			(int)(tileSize.getY() * scale), 
			color
		);
	}


	public void resetCounter()
	{
		currentFrame = 0;
		skipFPSCount = 0;
	}

/***********************************************************************************/
/***                                 GETTERS                                       */
/***********************************************************************************/

    public Texture getTexture()
	{
		return spriteSheet;
	}

    public Vector2 getPosition()
	{
		return position;
	}

    public float getScale()
	{
		return scale;
	}
    
    public float getRotation()
	{
		return rotation;
	}

    public Color getColor()
	{
		return color;
	}

	public int getFrameCount()
	{
		return frameCount;
	}

	public int getSkipFPS()
	{
		return skipFPS;
	}

	public int getSkipFPSCount()
	{
		return skipFPSCount;
	}

	public Vector2 gettileSize()
	{
		return tileSize;
	}

	public int getAnimationTotalFrame()
	{
		return animationTotalFrame;
	}

/***********************************************************************************/
/***                                 SETTERS                                       */	
/***********************************************************************************/

    public void setPosition(int x, int y)
	{
		position.setX(x);
		position.setY(y);
	}
    
    public void setScale(float scale)
	{
		this.scale = scale;
	}

    public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

    public void setColor(Color color)
	{
		this.color = color;
	}

	public void setFrameCount(int frameCount)
	{
		this.frameCount = frameCount;
	}

	public void setSkipFPS(int skipFPS)
	{
		this.skipFPS = skipFPS;
	}

	public void setSkipFPSCount(int skipFPSCount)
	{
		this.skipFPSCount = skipFPSCount;
	}

	public void settileSize(Vector2 tileSize)
	{
		this.tileSize = tileSize;
	}
}
