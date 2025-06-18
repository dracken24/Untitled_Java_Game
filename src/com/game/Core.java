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
import static com.raylib.Raylib.drawRectangle;

import com.raylib.Camera2D;
import com.raylib.Rectangle;
import com.raylib.Vector2;
import com.raylib.Color;

import java.util.ArrayList;

// import com.Environement.CollisionMap;
import com.Environement.InitAllMaps;
import com.Environement.GameMap;
// import com.MapsBuild.Cyty_01;
import com.Utility.ForBuildGame;
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
	Vector2		playerPosForSeeCollisions;				// Player

	Cameras	cameras;				// Cameras

	ArrayList<GameMap> allMaps;
	GameMap		currentMap;
	// Collisions collision;
	public static boolean debugMode = true;
	
	int	loadingCounter;
	
	public static Color BLACK_SHADOW = new Color((byte)0, (byte)0, (byte)0, (byte)255);

/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

	public Core(Vector2 windowSize, String title)
	{

		// Initialize the window
		WindowSize = windowSize;
		this.title = title;
		this.loadingCounter = 0;
		initWindow((int)WindowSize.getX(), (int)WindowSize.getY(), title);
        setTargetFPS(60);

		// Initialize the cameras
		cameras = new Cameras();
		
		// Initialize the main camera
		Rectangle recForCam = new Rectangle(0, 0, windowSize.getX(), windowSize.getY());
		cameras.initOneCamera(cameras.getMainCamera(), recForCam, windowSize);
		cameras.getMainCamera().setOffset(new Vector2(0, 0));
		
		// Initialize the player
		InitPlayer initPlayer = new InitPlayer(PlayerType.WARRIOR, player, windowSize);
		this.player = initPlayer.getPlayer();
		this.playerPosForSeeCollisions = this.player.getPosition();

		// Set the target to follow the player
		cameras.setTargetToFollow(player.getPosition());

		// Init All Maps
		initMaps();
	}

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

	public void update()
	{
		// For adjust the camera to follow the player
		followCamera(player.getPosition());

		stopPlayerOnLoading();

		// Update the player
		player.movement.applyMovement(player.getPosition(), player.movement.getVelocity());
		this.playerPosForSeeCollisions = this.player.getPosition();

		// Check for map changing
		String mapName = Collisions.checkCollision(player, currentMap);
		if (mapName != null)
		{
			for (GameMap map : allMaps)
			{
				if (map.getName().equals(mapName))
				{
					this.currentMap = map;
					setPlayerToStartPosition();
				}
			}

			this.loadingCounter = 80;
		}

		// Render on screen
		beginDrawing();
			beginTextureMode(cameras.getMainTexture());
				clearBackground(LIGHTGRAY);
				beginMode2D(cameras.getMainCamera());
				
				onDrawning();

				endMode2D();
			endTextureMode();

			// followCamera(player.getPosition());
			renderOnScreen();

		endDrawing();

		// // Update the player
		// player.movement.applyMovement(player.getPosition(), player.movement.getVelocity());
		// this.playerPosForSeeCollisions = this.player.getPosition();

		// TODO: for see collision and other stuff in debug mode. Remove in real game
		if (isKeyPressed(KEY_B))
		{
			debugMode = !debugMode;
		}
	}

	void onDrawning()
	{
		// Draw Map layer under the player
		currentMap.drawLayer(1);
		currentMap.drawLayer(2);
		currentMap.drawLayer(3);

			player.movement.printPlayer(player.getPosition(), player.getOffset());

		// Draw Map layer over the player
		currentMap.drawLayer(4);
		currentMap.drawLayer(5);
		currentMap.drawLayer(6);

		ForBuildGame.printCollision(this.player, this.playerPosForSeeCollisions, this.currentMap, this.WindowSize);

		if (this.loadingCounter > 0)
		{
			drawRectangle((int)player.getPosition().getX(), (int)player.getPosition().getY(), (int)WindowSize.getX(), (int)WindowSize.getY(), BLACK_SHADOW);

			drawText(
				"Loading ...",
				(int)player.getPosition().getX() + (int)WindowSize.getX() / 2 - 180,
				(int)player.getPosition().getY() + (int)WindowSize.getY() / 2 - 80,
				80,
				GREEN
			);

			this.loadingCounter--;
		}
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

	void initMaps()
	{
		// Initialize the maps
		this.allMaps = new ArrayList<>();

		this.currentMap = InitAllMaps.initAll(allMaps);

		setPlayerToStartPosition();
	}

		void followCamera(Vector2 targetPosition)
	{
		Camera2D mainCam = cameras.getMainCamera();
		mainCam.setTarget(targetPosition);
	}

	void setPlayerToStartPosition()
	{
		Vector2 playerPosStart = new Vector2(this.currentMap.getPlayerPositionStart().getX(), this.currentMap.getPlayerPositionStart().getY());
		playerPosStart.setX(playerPosStart.getX() - player.getOffset().getX() + player.getColisionBox().getWidth() / 2 * player.getScale());
		playerPosStart.setY(playerPosStart.getY() - player.getOffset().getY());

		player.setPosition(playerPosStart);
	}

	void stopPlayerOnLoading()
	{
		if (this.loadingCounter == 0)
		{
			player.update();
		}
		else
		{
			player.movement.setVelocity(new Vector2( 0, 0));
			player.movement.setCurrentAction(player.movement.getIdle());
		}
	}
}
