/* =============================================================================== */
/* ---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~--- */
/*               -------------------------------------------------                 */
/*                PROJET: Java Dev          PAR: Dracken24                         */
/*               -------------------------------------------------                 */
/*                CREATED: 28-2nd-2025                                             */
/*                MODIFIED BY: Dracken24                                           */
/*                LAST MODIFIED: 28-2nd-2025                                       */
/*               -------------------------------------------------                 */
/*                FILE: init.java                                                  */
/*               -------------------------------------------------                 */
/* ---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~---~--- */
/* =============================================================================== */

package com.game;

import static com.raylib.Raylib.initWindow;
import static com.raylib.Raylib.setTargetFPS;

import static com.raylib.Raylib.beginDrawing;
import static com.raylib.Raylib.beginTextureMode;
import static com.raylib.Raylib.clearBackground;
import static com.raylib.Raylib.endDrawing;
import static com.raylib.Raylib.drawText;
import static com.raylib.Raylib.drawTextureRec;
import static com.raylib.Raylib.VIOLET;
import static com.raylib.Raylib.LIGHTGRAY;

import com.raylib.Vector2;
import com.raylib.Camera2D;
import com.raylib.Rectangle;
import static com.raylib.Raylib.beginMode2D;
import static com.raylib.Raylib.endMode2D;
import static com.raylib.Raylib.endTextureMode;
import static com.raylib.Raylib.WHITE;

import com.enums.PlayerType;
import com.player.Player;
import com.player.InitPlayer;

// import java.util.List;

public class Core
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/

	Vector2		WindowSize;
	String		title;

	Player		player;

	Cameras	cameras;

	// TODO: 01: 1/3 Map Gestion 
	// List<Maps> maps;
	// Map		currentMap;

/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

	public Core(Vector2 windowSize, String title)
	{
		// Initialize the window
		WindowSize = windowSize;
		this.title = title;
		initWindow((int)WindowSize.getX(), (int)WindowSize.getY(), title);
        setTargetFPS(60);

		// Initialize the player
		initPlayer();

		// Initialize the cameras
		cameras = new Cameras();

		// Initialize the main camera
		Rectangle recForCam = new Rectangle(0, 0, windowSize.getX(), windowSize.getY());
		cameras.initOneCamera(cameras.getMainCamera(), recForCam, windowSize);
		cameras.getMainCamera().setOffset(new Vector2(0, 0));

		// Set the target to follow the player
		cameras.setTargetToFollow(player.getPosition());

		// TODO: 01: 2/3 Map Gestion 
		// Initialize the maps
		// initMaps();
	}

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

	public void update()
	{
		// For adjust the camera to follow the player
		followCamera(player.getPosition());

		beginDrawing();
			beginTextureMode(cameras.getMainTexture());
				clearBackground(LIGHTGRAY);
				beginMode2D(cameras.getMainCamera());
				
					onDrawning();

				endMode2D();
			endTextureMode();

			renderOnScreen();

		endDrawing();
	}

	void followCamera(Vector2 targetPosition)
	{
		Camera2D mainCam = cameras.getMainCamera();
		mainCam.setTarget(targetPosition);
	}

	void onDrawning()
	{
		drawText("Untitled Action RPG Game", 470, 150, 20, VIOLET);

		// Update the player 
		player.update();

		// TODO: 01: 3/3 Map Gestion
		// currentMap.update();
	}

	void renderOnScreen()
	{
		// draw the final texture
		drawTextureRec(
			cameras.getMainTexture().getTexture(),
			new Rectangle(
				0,                                                    // x
				0,                                                    // y
				cameras.getMainTexture().getTexture().getWidth(),     // width
				-cameras.getMainTexture().getTexture().getHeight()    // height (negative to invert)
			),
			new Vector2(0, 0),
			WHITE
		);
	}

	// Initialize the player scale, size, position, colision box size and offset
	void initPlayer()
	{
		// Initialize the player position and size
		Vector2 playerPos = new Vector2(0, 0);
		Vector2 playerSize = new Vector2(64, 64);
		Vector2 collBoxSize = new Vector2(38, 64);
		int playerScale = 2;

		// Initialize the player colision box
		Rectangle playerColisionSize = new Rectangle(
			-(playerSize.getX() / 2 * playerScale),
			-(playerSize.getY() / 2 * playerScale) + ((playerSize.getY() - collBoxSize.getY()) * playerScale) - playerScale,
			collBoxSize.getX(),
			collBoxSize.getY()
		);

		// Initialize the player offset to center the player on the screen
		Vector2 playerOffset = new Vector2(WindowSize.getX() / 2, WindowSize.getY() / 2);

		// Create the player
		player = new Player(
			playerPos,
			playerSize,
			playerColisionSize,
			playerScale,
			playerOffset
		);

		// Initialize the player
		new InitPlayer(PlayerType.WARRIOR, player, playerPos, playerSize, playerScale);
	}
}
