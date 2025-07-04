package com.Physic;

import static com.raylib.Raylib.checkCollisionRecs;

import com.raylib.Color;
import com.raylib.Rectangle;
import com.raylib.Vector2;

import com.Environement.GameMap;
import com.player.Player;

public class Collisions
{
	public static Color PINK_SHADOW = new Color((byte)255, (byte)100, (byte)255, (byte)100);
	public static Color BLUE_SHADOW = new Color((byte)100, (byte)100, (byte)255, (byte)100);
	public static Color YELLOW_SHADOW = new Color((byte)255, (byte)255, (byte)100, (byte)100);

	public static String checkCollision(Player player, GameMap currentMap)
	{
		boolean hitRight = false;
		boolean hitLeft = false;
		boolean hitUp = false;
		boolean hitDown = false;

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

		for (int i = 0; i < currentMap.collisionMap.getCollisionMap().length; i++)
		{
			for (int j = 0; j < currentMap.collisionMap.getCollisionMap()[i].length; j++)
			{
				Rectangle obstacleBox = new Rectangle(i * 64, j * 64, 64, 64);

				// check if plager hit change map tyle
				if (currentMap.collisionMap.getCollisionMap()[i][j].length() > 1)
				{
					if (checkCollisionRecs(playerBoxScaled, obstacleBox))
					{
						System.out.println("Returned map name: " + currentMap.collisionMap.getCollisionMap()[i][j]);
						return currentMap.collisionMap.getCollisionMap()[i][j];
					}
				}

				// check if plager hit obstacle tyle
				if (currentMap.collisionMap.getCollisionMap()[i][j] == "1")
				{
					String collisionSide = detectCollisionSide(player, playerBoxScaled, obstacleBox);
					
					if (!collisionSide.equals("NONE"))
					{
						float adjustment = 0;
						switch(collisionSide)
						{
							case "BOTTOM":
								if (hitDown == false)
								{
									// break;
									adjustment = Math.abs((playerBoxScaled.getY() + playerBoxScaled.getHeight()) - obstacleBox.getY());
									
									adjustment = Math.min(adjustment, 5.0f);
								}
								player.setPosition(new Vector2(
									player.getPosition().getX(),
									player.getPosition().getY() - adjustment
								));
								player.movement.setVelocity(new Vector2(player.movement.getVelocity().getX(), 0));

								hitDown = true;
								break;
								
							case "TOP":
								if (hitUp == false)
								{
									adjustment = Math.abs(obstacleBox.getY() + obstacleBox.getHeight() - playerBoxScaled.getY());
									// break;
								}

								player.setPosition(new Vector2(
									player.getPosition().getX(),
									player.getPosition().getY() + adjustment
								));
								player.movement.setVelocity(new Vector2(player.movement.getVelocity().getX(), 0));

								hitUp = true;
								break;
								
							case "LEFT":
								if (hitLeft == false)
								{
									// break;
									adjustment = Math.abs(obstacleBox.getX() + obstacleBox.getWidth() - playerBoxScaled.getX());
								}

								player.setPosition(new Vector2(
									player.getPosition().getX() + adjustment,
									player.getPosition().getY()
								));
								player.movement.setVelocity(new Vector2(0, player.movement.getVelocity().getY()));

								hitLeft = true;
								break;

							case "RIGHT":
								if (hitRight == false)
								{
									// break;
									adjustment = Math.abs((playerBoxScaled.getX() + playerBoxScaled.getWidth()) - obstacleBox.getX());
									
									adjustment = Math.min(adjustment, 5.0f);
								}

								player.setPosition(new Vector2(
									player.getPosition().getX() - adjustment,
									player.getPosition().getY()
								));
								player.movement.setVelocity(new Vector2(0, player.movement.getVelocity().getY()));

								hitRight = true;
								break;
						}
					}
				}
			}
		}

		// Stop player on map frontier in X
		if (player.getPosition().getX() > currentMap.getMapSize().getX() * currentMap.getTileSize().getX()
			- player.getOffset().getX() - (playerColisionBo.getWidth() / 2) * player.getScale())
		{
			player.setPosition(new Vector2(
				currentMap.getMapSize().getX() * currentMap.getTileSize().getX() - player.getOffset().getX()
					-(playerColisionBo.getWidth() / 2) * player.getScale(),
				player.getPosition().getY()
			));
		}
		if (player.getPosition().getX() < -player.getOffset().getX() + (playerColisionBo.getHeight()))
		{
			player.setPosition(new Vector2(
				-player.getOffset().getX() + (playerColisionBo.getHeight()),
				player.getPosition().getY()
			));
		}

		// Stop player on map frontier in Y
		if (player.getPosition().getY() > currentMap.getMapSize().getY() * currentMap.getTileSize().getY()
			- player.getOffset().getY() - (playerColisionBo.getHeight()) * player.getScale())
		{
			player.setPosition(
				new Vector2(
					player.getPosition().getX(),
					currentMap.getMapSize().getY() * currentMap.getTileSize().getY() - player.getOffset().getY()
						- (playerColisionBo.getHeight()) * player.getScale()
			));
		}
		if (player.getPosition().getY() < -player.getOffset().getY())
		{
			player.setPosition(
				new Vector2(player.getPosition().getX(),
				-player.getOffset().getY()
			));
		}

		return null;
	}

	public static String detectCollisionSide(Player player, Rectangle playerBoxScaled, Rectangle obstacleBox)
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

	void changeMap(String mapName)
	{
		System.out.println(mapName);
	}
}

