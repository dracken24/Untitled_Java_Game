/* =============================================================================== */
/* ---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~--- */
/*               -------------------------------------------------                 */
/*                PROJET: Java Dev          PAR: Dracken24                         */
/*               -------------------------------------------------                 */
/*                CREATED: 28-2nd-2025                                             */
/*                MODIFIED BY: Dracken24                                           */
/*                LAST MODIFIED: 28-2nd-2025                                       */
/*               -------------------------------------------------                 */
/*                FILE: InitPlayer.java                                            */
/*               -------------------------------------------------                 */
/* ---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~--- */
/* =============================================================================== */

package com.player;

import com.objects.SpriteSheet;
import com.raylib.Vector2;
import com.enums.SpriteMovement;
import com.enums.PlayerType;

public class InitPlayer
{
	public InitPlayer(PlayerType playerType, Player player,
		Vector2 playerPos, Vector2 playerSize)
    {
		switch (playerType)
		{
			case TEST:
                initTest(player, playerSize, playerPos);
				break;
			// case FIGHTER:
            //     initFighter();
			// 	break;
			// case SAMURAI:
            //     initSamurai(player, playerPos, playerSize);
			// 	break;
		}
    }

    private void initTest(Player player, Vector2 playerSize, Vector2 playerPos)
    {
		int scale = 2;

		// Idle
        player.movement.setIdle(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Idle.png",
			7,
			playerSize,
			11,
			scale,
			playerPos
		));

		// Walk Right
		player.movement.setRight(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Right.png",
			6,
			playerSize,
			8,
			scale,
			playerPos
		));

		// Walk Left
		player.movement.setLeft(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Left.png",
			6,
			playerSize,
			8,
			scale,
			playerPos
		));

		// Walkt Up
		player.movement.setUp(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Up.png",
			6,
			playerSize,
			8,
			scale,
			playerPos
		));

		// Walk Down
		player.movement.setDown(new SpriteSheet(
			"assets/Players/Player_Test_48x72/pTest_Down.png",
			6,
			playerSize,
			8,
			scale,
			playerPos
		));

		player.movement.setMovement(SpriteMovement.IDLE);
    }
}
