package com.player;

import com.objects.SpriteSheet;
import com.raylib.Vector2;
import com.enums.SpriteMovement;
import com.enums.PlayerType;
import com.raylib.Rectangle;

public class InitPlayer
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/
	Player	player;
	Vector2	playerPos;
	Vector2	playerSize;
	int		scale;

/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

	public InitPlayer(PlayerType playerType, Player player, Vector2 WindowSize)
    {
		InitBasePlayer(WindowSize);

		switch (playerType)
		{
			case WARRIOR:
                initWarrior();
				break;
			// case FIGHTER:
            //     initFighter(player, playerSize, playerPos, scale);
			// 	break;
			// case SAMURAI:
            //     initSamurai(player, playerSize, playerPos, scale);
			// 	break;
		}
    }

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

	// Initialize the player scale, size, position, colision box size and offset
	public void InitBasePlayer(Vector2 WindowSize)
	{
		// Initialize the player position and size
		this.playerPos = new Vector2(0, 0);
		this.playerSize = new Vector2(64, 64);
		Vector2 collBoxSize = new Vector2(28, 28);
		this.scale = 2;

		// Initialize the player colision box
		Rectangle playerColisionSize = new Rectangle(
			-(this.playerSize.getX() / 2 * this.scale),
			-(this.playerSize.getY() / 2 * this.scale) + ((this.playerSize.getY() - collBoxSize.getY()) * this.scale) - this.scale,
			collBoxSize.getX(),
			collBoxSize.getY()
		);

		// Initialize the player offset to center the player on the screen
		Vector2 playerOffset = new Vector2(WindowSize.getX() / 2, WindowSize.getY() / 2);

		// Create the player
		this.player = new Player(
			this.playerPos,
			this.playerSize,
			playerColisionSize,
			this.scale,
			playerOffset
		);

		// Initialize the player
		// new InitPlayer(PlayerType.WARRIOR, player, playerPos, playerSize, playerScale);
	}

    private void initWarrior()
    {
		// Idle
        player.movement.setIdle(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Idle.png",
			7,
			this.playerSize,
			11,
			this.scale,
			this.playerPos
		));

		// Walk Right
		player.movement.setRight(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Right.png",
			6,
			playerSize,
			7,
			scale,
			playerPos
		));

		// Walk Left
		player.movement.setLeft(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Left.png",
			6,
			playerSize,
			7,
			scale,
			playerPos
		));

		// Walkt Up
		player.movement.setUp(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Up.png",
			6,
			playerSize,
			7,
			scale,
			playerPos
		));

		// Walk Down
		player.movement.setDown(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Down.png",
			6,
			playerSize,
			7,
			scale,
			playerPos
		));

		player.movement.setMovement(SpriteMovement.IDLE);
    }

/***********************************************************************************/
/***                                 GETTERS                                       */
/***********************************************************************************/

	public Player getPlayer()
	{
		return this.player;
	}

/***********************************************************************************/
/***                                 SETTERS                                       */	
/***********************************************************************************/

}
