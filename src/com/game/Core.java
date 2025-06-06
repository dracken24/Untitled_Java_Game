package com.game;

import static com.raylib.Raylib.LIGHTGRAY;
import static com.raylib.Raylib.GREEN;
import static com.raylib.Raylib.WHITE;
import static com.raylib.Raylib.beginDrawing;
import static com.raylib.Raylib.beginMode2D;
import static com.raylib.Raylib.beginTextureMode;
import static com.raylib.Raylib.clearBackground;
import static com.raylib.Raylib.drawText;
import static com.raylib.Raylib.drawTextureRec;
import static com.raylib.Raylib.endDrawing;
import static com.raylib.Raylib.endMode2D;
import static com.raylib.Raylib.endTextureMode;
import static com.raylib.Raylib.initWindow;
import static com.raylib.Raylib.setTargetFPS;

import static com.raylib.Raylib.KeyboardKey.KEY_B;
import static com.raylib.Raylib.isKeyPressed;	

import com.raylib.Camera2D;
import com.raylib.Rectangle;
import com.raylib.Vector2;

import java.util.ArrayList;

import com.Environement.CollisionMap;
import com.Environement.GameMap;
import com.MapsBuild.Cyty_01;
import com.enums.PlayerType;
import com.player.InitPlayer;
import com.player.Player;
import com.Physic.Collisions;

// Class for global game gestion
public class Core
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/

	Vector2		WindowSize;			// Window size
	String		title;				// Window title

	Player		player;				// Player

	Cameras	cameras;				// Cameras

	ArrayList<GameMap> allMaps;
	GameMap		currentMap;
	// Collisions collision;
	public static boolean debugMode = true;

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

		// Initialize the maps
		allMaps = new ArrayList<>();

		CollisionMap collisionMap = new CollisionMap(new Vector2(48, 48), new Vector2(64, 64), 2);
		// mountCollMap(collisionMap);

		GameMap map1 = new Cyty_01(
			"Cyty_01",
			"assets/Environement/City_01/town_01.png",
			null,
			null,
			"assets/Environement/City_01/town_01_top_player.png",
			null,
			null,
			new Rectangle(0, 0, 1536, 1536),
			new Vector2(64, 64),
			2,
			collisionMap
		);
		map1.collisionMap.printMap();

		allMaps.add(map1);

		currentMap = map1;
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
		


		if (isKeyPressed(KEY_B))
		{
			debugMode = !debugMode;
		}
	}

	void followCamera(Vector2 targetPosition)
	{
		Camera2D mainCam = cameras.getMainCamera();
		mainCam.setTarget(targetPosition);
	}

	void onDrawning()
	{
		// drawText("Untitled Action RPG Game", 470, 150, 20, VIOLET);

		// Draw Map layer under the player
		currentMap.drawLayer(1);
		currentMap.drawLayer(2);
		currentMap.drawLayer(3);
		
			player.update();
			player.movement.printPlayer(player.getPosition(), player.getOffset());

		// Draw Map layer over the player
		currentMap.drawLayer(4);
		currentMap.drawLayer(5);
		currentMap.drawLayer(6);

		// Check collision before applying movement
		Collisions.checkCollision(player, currentMap);
		
		// Update the player
		player.movement.applyMovement(player.getPosition(), player.getColisionBox(), player.movement.getVelocity());

		if (debugMode)
		{
			drawText(
				"X: " + (player.getPosition().getX() + player.getOffset().getX() - player.getColisionBox().getWidth()) +
					" Y: " + (player.getPosition().getY() + player.getOffset().getY() - player.getColisionBox().getHeight()),
				(int)player.getPosition().getX() + (int)WindowSize.getX() / 2 - 100,
				(int)player.getPosition().getY() + (int)WindowSize.getY() / 2 - 100,
				20,
				GREEN
			);
		}

		// currentMap.collisionMap.printMap();
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
		Vector2 playerPos = new Vector2(0, 1400);
		Vector2 playerSize = new Vector2(64, 64);
		Vector2 collBoxSize = new Vector2(28, 28);
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

	// void mountCollMap(CollisionMap collisionMap)
	// {
	// 	collisionMap.setCollisionAt(1, 1, 1);
	// 	collisionMap.setCollisionAt(2, 2, 1);
	// 	collisionMap.setCollisionAt(3, 3, 1);
	// 	collisionMap.setCollisionAt(5, 5, 1);
	// }
}
