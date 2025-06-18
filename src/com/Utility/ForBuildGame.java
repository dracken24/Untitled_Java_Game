package com.Utility;

import static com.raylib.Raylib.RED;
import static com.raylib.Raylib.GREEN;
import static com.raylib.Raylib.drawRectangle;
import static com.raylib.Raylib.drawRectangleRec;
import static com.raylib.Raylib.drawText;

import com.raylib.Rectangle;
import com.raylib.Vector2;

import com.Environement.GameMap;
import com.player.Player;
import com.game.Core;
import com.Physic.Collisions;

public class ForBuildGame
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/


/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

	ForBuildGame()
	{}

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

	public static void printCollision(Player player, Vector2 playerPosForSeeCollisions,
		GameMap currentMap, Vector2 WindowSize)
	{
		// NOTE: Work only in debug Mode
		if (Core.debugMode)
		{
			Rectangle playerColisionBo = new Rectangle(
				playerPosForSeeCollisions.getX() + player.getOffset().getX() - player.getColisionBox().getWidth(),
				playerPosForSeeCollisions.getY() + player.getOffset().getY() - player.getColisionBox().getHeight(),
				player.getColisionBox().getWidth(),
				player.getColisionBox().getHeight()
			);
				
				
				Rectangle playerBoxScaled = new Rectangle(
					playerPosForSeeCollisions.getX() + player.getOffset().getX() - player.getColisionBox().getWidth(),
					playerPosForSeeCollisions.getY() + player.getOffset().getY(),
					playerColisionBo.getWidth() * player.getScale(),
					playerColisionBo.getHeight() * player.getScale()
			);

			// NOTE: draw the player box
			drawRectangleRec(playerBoxScaled, Collisions.PINK_SHADOW);

			for (int i = 0; i < currentMap.collisionMap.getCollisionMap().length; i++)
			{
				for (int j = 0; j < currentMap.collisionMap.getCollisionMap()[i].length; j++)
				{
					Rectangle obstacleBox = new Rectangle(i * 64, j * 64, 64, 64);

					// check if plager hit change map tyle
					if (currentMap.collisionMap.getCollisionMap()[i][j].length() > 1)
					{
						// NOTE: draw the obstacle box
						drawRectangle(i * 64, j * 64, 64, 64, Collisions.YELLOW_SHADOW);
					}

					// check if plager hit obstacle tyle
					if (currentMap.collisionMap.getCollisionMap()[i][j] == "1")
					{
						// NOTE: draw the obstacle box
						drawRectangle(i * 64, j * 64, 64, 64, Collisions.BLUE_SHADOW);
						
						String collisionSide = Collisions.detectCollisionSide(player, playerBoxScaled, obstacleBox);
						// System.out.println("Coll side: " + collisionSide);
						if (!collisionSide.equals("NONE"))
						{
							// NOTE: draw the obstacle box hit
							drawRectangle(i * 64, j * 64, 64, 64, RED);
						}
					}
				}
			}

			// NOTE: draw position up to player
			drawText(
				"X: " + (player.getPosition().getX() + player.getOffset().getX() - player.getColisionBox().getWidth()) +
					" Y: " + (player.getPosition().getY() + player.getOffset().getY()),
				(int)player.getPosition().getX() + (int)WindowSize.getX() / 2 - 100,
				(int)player.getPosition().getY() + (int)WindowSize.getY() / 2 - 100,
				20,
				GREEN
			);
		}
	}

/***********************************************************************************/
/***                                 GETTERS                                       */
/***********************************************************************************/

/***********************************************************************************/
/***                                 SETTERS                                       */	
/***********************************************************************************/
}
