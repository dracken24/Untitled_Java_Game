package com.Physic;

import static com.raylib.Raylib.RED;
import static com.raylib.Raylib.checkCollisionRecs;
import static com.raylib.Raylib.drawRectangle;
import static com.raylib.Raylib.drawRectangleRec;

import com.raylib.Color;
import com.raylib.Rectangle;
import com.raylib.Vector2;

import com.Environement.GameMap;
import com.player.Player;
import com.game.Core;

public class Collisions
{
	public static Color PINK_SHADOW = new Color((byte)255, (byte)100, (byte)255, (byte)100);
	public static Color BLUE_SHADOW = new Color((byte)100, (byte)100, (byte)255, (byte)100);

	public static void checkCollision(Player player, GameMap currentMap)
	{
		Rectangle playerColisionBo = new Rectangle(
			player.getPosition().getX() + player.getOffset().getX() - player.getColisionBox().getWidth(),
			player.getPosition().getY() + player.getOffset().getY() - player.getColisionBox().getHeight(),
			player.getColisionBox().getWidth(),
			player.getColisionBox().getHeight()
		);
			
			
			Rectangle playerBoxScaled = new Rectangle(
				player.getPosition().getX() + player.getOffset().getX() - player.getColisionBox().getWidth(),
				player.getPosition().getY() + player.getOffset().getY(),
				playerColisionBo.getWidth() * player.getScale(),
				playerColisionBo.getHeight() * player.getScale()
		);
		
		if (Core.debugMode)
		{
			// NOTE: draw the player box
			drawRectangleRec(playerBoxScaled, PINK_SHADOW);
		}

		for (int i = 0; i < currentMap.collisionMap.getCollisionMap().length; i++)
		{
			for (int j = 0; j < currentMap.collisionMap.getCollisionMap()[i].length; j++)
			{
				if (currentMap.collisionMap.getCollisionMap()[i][j] == 1)
				{
					Rectangle obstacleBox = new Rectangle(i * 64, j * 64, 64, 64);

					if (Core.debugMode)
					{
						// NOTE: draw the obstacle box
						drawRectangle(i * 64, j * 64, 64, 64, BLUE_SHADOW);
					}
					
					String collisionSide = detectCollisionSide(player, playerBoxScaled, obstacleBox);
					
					if (!collisionSide.equals("NONE"))
					{
						if (Core.debugMode)
						{
							// NOTE: draw the obstacle box hit
							drawRectangle(i * 64, j * 64, 64, 64, RED);
						}
						
						float adjustment = 0;
						switch(collisionSide)
						{
							case "BOTTOM":
								adjustment = Math.abs((playerBoxScaled.getY() + playerBoxScaled.getHeight()) - obstacleBox.getY());
								
								adjustment = Math.min(adjustment, 5.0f);
								player.setPosition(new Vector2(
									player.getPosition().getX(),
									player.getPosition().getY() - adjustment
								));
								player.movement.setVelocity(new Vector2(player.movement.getVelocity().getX(), 0));
								break;
								
							case "TOP":
								adjustment = Math.abs(obstacleBox.getY() + obstacleBox.getHeight() - playerBoxScaled.getY());
								player.setPosition(new Vector2(
									player.getPosition().getX(),
									player.getPosition().getY() + adjustment
								));
								player.movement.setVelocity(new Vector2(player.movement.getVelocity().getX(), 0));
								break;
								
							case "LEFT":
								adjustment = Math.abs(obstacleBox.getX() + obstacleBox.getWidth() - playerBoxScaled.getX());
								player.setPosition(new Vector2(
									player.getPosition().getX() + adjustment,
									player.getPosition().getY()
								));
								player.movement.setVelocity(new Vector2(0, player.movement.getVelocity().getY()));
								break;

							case "RIGHT":
								adjustment = Math.abs((playerBoxScaled.getX() + playerBoxScaled.getWidth()) - obstacleBox.getX());
								
								adjustment = Math.min(adjustment, 5.0f);
								player.setPosition(new Vector2(
									player.getPosition().getX() - adjustment,
									player.getPosition().getY()
								));
								player.movement.setVelocity(new Vector2(0, player.movement.getVelocity().getY()));
								break;
						}
						
						// Update the collision box after the movement
						Rectangle colBox = player.getColisionBox();
						colBox.setX(player.getPosition().getX() - player.getSize().getX());
						colBox.setY(player.getPosition().getY() - player.getSize().getY());
						player.setColisionBox(colBox);
					}
				}
			}
		}

		// Stop player on map frontier in X
		if (player.getPosition().getX() > currentMap.getMapSize().getX() * currentMap.getTileSize().getX() - player.getOffset().getX() - (playerColisionBo.getWidth() / 2) * player.getScale())
		{
			player.setPosition(new Vector2(
				currentMap.getMapSize().getX() * currentMap.getTileSize().getX() - player.getOffset().getX() - (playerColisionBo.getWidth() / 2) * player.getScale(),
				player.getPosition().getY()
			));
		}
		if (player.getPosition().getX() < 0 - player.getOffset().getX() + (playerColisionBo.getHeight()))
		{
			player.setPosition(new Vector2(
				0 - player.getOffset().getX() + (playerColisionBo.getHeight()),
				player.getPosition().getY()
			));
		}

		// Stop player on map frontier in Y
		if (player.getPosition().getY() > currentMap.getMapSize().getY() * currentMap.getTileSize().getY() - player.getOffset().getY() - (playerColisionBo.getHeight()) * player.getScale())
		{
			player.setPosition(
				new Vector2(player.getPosition().getX(),
				currentMap.getMapSize().getY() * currentMap.getTileSize().getY() - player.getOffset().getY() - (playerColisionBo.getHeight()) * player.getScale()
			));
		}
		if (player.getPosition().getY() < 0 - player.getOffset().getY())
		{
			player.setPosition(
				new Vector2(player.getPosition().getX(),
				0 - player.getOffset().getY()
			));
		}
	}

	static String detectCollisionSide(Player player, Rectangle playerBoxScaled, Rectangle obstacleBox)
	{
		Rectangle obstacleBoxScaled = new Rectangle(
			obstacleBox.getX(),
			obstacleBox.getY(),
			obstacleBox.getWidth(),
			obstacleBox.getHeight()
		);

		// Check if there is a collision
		if (checkCollisionRecs(playerBoxScaled, obstacleBoxScaled))
		{
			// Calculate the penetration distances
			float overlapLeft = Math.abs((playerBoxScaled.getX() + playerBoxScaled.getWidth()) - obstacleBoxScaled.getX());
			float overlapRight = Math.abs(obstacleBoxScaled.getX() + obstacleBoxScaled.getWidth() - playerBoxScaled.getX());
			float overlapTop = Math.abs((playerBoxScaled.getY() + playerBoxScaled.getHeight()) - obstacleBoxScaled.getY());
			float overlapBottom = Math.abs(obstacleBoxScaled.getY() + obstacleBoxScaled.getHeight() - playerBoxScaled.getY());

			// Find the smallest penetration
			float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

			// Return the side with the smallest penetration
			if (minOverlap == overlapTop)
			{
				// System.out.println("BOTTOM");
				return "BOTTOM";
			}
			if (minOverlap == overlapBottom)
			{
				// System.out.println("TOP");
				return "TOP";
			}
			if (minOverlap == overlapLeft)
			{
				// System.out.println("RIGHT");
				return "RIGHT";
			}
			if (minOverlap == overlapRight)
			{
				// System.out.println("LEFT");
				return "LEFT";
			}
		}
		
		return "NONE";
	}
}
