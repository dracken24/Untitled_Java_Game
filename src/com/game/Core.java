package com.game;

import static com.raylib.Raylib.LIGHTGRAY;
import static com.raylib.Raylib.BLUE;
import static com.raylib.Raylib.GREEN;
import static com.raylib.Raylib.RED;
import static com.raylib.Raylib.VIOLET;
import static com.raylib.Raylib.WHITE;
import static com.raylib.Raylib.beginDrawing;
import static com.raylib.Raylib.beginMode2D;
import static com.raylib.Raylib.beginTextureMode;
import static com.raylib.Raylib.checkCollisionRecs;
import static com.raylib.Raylib.clearBackground;
import static com.raylib.Raylib.drawRectangleRec;
import static com.raylib.Raylib.drawText;
import static com.raylib.Raylib.drawRectangle;
import static com.raylib.Raylib.drawTextureRec;
import static com.raylib.Raylib.endDrawing;
import static com.raylib.Raylib.endMode2D;
import static com.raylib.Raylib.endTextureMode;
import static com.raylib.Raylib.initWindow;
import static com.raylib.Raylib.setTargetFPS;

import java.util.ArrayList;

import com.Environement.CollisionMap;
import com.Environement.GameMap;
import com.enums.PlayerType;
import com.player.InitPlayer;
import com.player.Player;
import com.raylib.Camera2D;
import com.raylib.Rectangle;
import com.raylib.Vector2;

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
		mountCollMap(collisionMap);

		GameMap map1 = new GameMap(
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
		
		player.movement.setLastPosition(player.getPosition());
		player.setLastColisionBox(player.getColisionBox());
	}

	String detectCollisionSide(Rectangle playerBox, Rectangle obstacleBox)
	{
		// Get the edges of the player with the scale
		float playerLeft = playerBox.getX() * player.getScale();
		float playerRight = playerBox.getX() + playerBox.getWidth() * player.getScale();
		float playerTop = playerBox.getY() * player.getScale();
		float playerBottom = playerBox.getY() + playerBox.getHeight() * player.getScale();
		Rectangle playerBoxScaled = new Rectangle(player.getPosition().getX() + player.getOffset().getX() - player.getColisionBox().getWidth(), player.getPosition().getY() + player.getOffset().getY() - player.getColisionBox().getHeight(), playerBox.getWidth() * player.getScale(), playerBox.getHeight() * player.getScale());

		// draw the player box
		drawRectangleRec(
			playerBoxScaled,
			BLUE
		);
		
		float obstacleLeft = obstacleBox.getX();
		float obstacleRight = obstacleBox.getX() + obstacleBox.getWidth();
		float obstacleTop = obstacleBox.getY();
		float obstacleBottom = obstacleBox.getY() + obstacleBox.getHeight();
		Rectangle obstacleBoxScaled = new Rectangle(obstacleBox.getX(), obstacleBox.getY(), obstacleBox.getWidth(), obstacleBox.getHeight());

		// draw the obstacle box
		drawRectangleRec(
			obstacleBoxScaled,
			GREEN
		);

		// Check if there is a collision
		// if (playerRight >= obstacleLeft && playerLeft <= obstacleRight &&
		// 	playerBottom >= obstacleTop && playerTop <= obstacleBottom)
		if (checkCollisionRecs(playerBoxScaled, obstacleBoxScaled))
		{
			System.out.println("Collision");
			// Get the penetration distances
			float overlapLeft = playerRight - obstacleLeft;
			float overlapRight = obstacleRight - playerLeft;
			float overlapTop = playerBottom - obstacleTop;
			float overlapBottom = obstacleBottom - playerTop;

			// Get the smallest penetration
			float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), 
				Math.min(overlapTop, overlapBottom)); 

			// Return the side with the smallest penetration
			if (minOverlap == overlapTop)
			{
				System.out.println("BOTTOM");
				return "BOTTOM"; // The bottom of the player touches the top of the obstacle
			}
			if (minOverlap == overlapBottom)
			{
				System.out.println("TOP");
				return "TOP"; // The top of the player touches the bottom of the obstacle
			}
			if (minOverlap == overlapLeft)
			{
				System.out.println("RIGHT");
				return "RIGHT"; // The right side of the player touches the left side of the obstacle
			}
			if (minOverlap == overlapRight)
			{
				System.out.println("LEFT");
				return "LEFT"; // The left side of the player touches the right side of the obstacle
			}
		}
		
		return "NONE"; // No collision
	}

	void checkCollision()
	{
		Rectangle playerColisionBox = new Rectangle(
			player.getPosition().getX() + player.getOffset().getX() - player.getColisionBox().getWidth(),
			player.getPosition().getY() + player.getOffset().getY() - player.getColisionBox().getHeight(),
			player.getColisionBox().getWidth(),
			player.getColisionBox().getHeight()
		);

		for (int i = 0; i < currentMap.collisionMap.getCollisionMap().length; i++)
		{
			for (int j = 0; j < currentMap.collisionMap.getCollisionMap()[i].length; j++)
			{
				if (currentMap.collisionMap.getCollisionMap()[i][j] == 1)
				{
					Rectangle obstacleBox = new Rectangle(i * 64, j * 64, 64, 64);
					drawRectangle(i * 64, j * 64, 64, 64, BLUE);
					
					String collisionSide = detectCollisionSide(playerColisionBox, obstacleBox);
					
					if (!collisionSide.equals("NONE"))
					{
						drawRectangle(i * 64, j * 64, 64, 64, RED);
						
						float adjustment = 0;
						switch(collisionSide)
						{
							case "BOTTOM":
								adjustment = playerColisionBox.getY() + playerColisionBox.getHeight() - obstacleBox.getY();
								player.setPosition(new Vector2(
									player.getPosition().getX(),
									player.getPosition().getY() - adjustment
								));
								player.movement.setVelocity(new Vector2(player.movement.getVelocity().getX(), 0));
								break;
								
							case "TOP":
								adjustment = obstacleBox.getY() + obstacleBox.getHeight() - playerColisionBox.getY();
								player.setPosition(new Vector2(
									player.getPosition().getX(),
									player.getPosition().getY() + adjustment
								));
								player.movement.setVelocity(new Vector2(player.movement.getVelocity().getX(), 0));
								break;
								
							case "LEFT":
							case "RIGHT":
								adjustment = collisionSide.equals("LEFT") ? 
									obstacleBox.getX() + obstacleBox.getWidth() - playerColisionBox.getX() :
									playerColisionBox.getX() + playerColisionBox.getWidth() - obstacleBox.getX();
								
								player.setPosition(new Vector2(
									player.getPosition().getX() + (collisionSide.equals("LEFT") ? adjustment : -adjustment),
									player.getPosition().getY()
								));
								player.movement.setVelocity(new Vector2(0, player.movement.getVelocity().getY()));
								break;
						}
						
						// Mettre à jour la boîte de collision après le déplacement
						Rectangle colBox = player.getColisionBox();
						colBox.setX(player.getPosition().getX() - player.getSize().getX());
						colBox.setY(player.getPosition().getY() - player.getSize().getY());
						player.setColisionBox(colBox);
					}
				}
			}
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

		// Update the player
		player.update();
		
		// Check collision before applying movement
		checkCollision();
		

		player.movement.applyMovement(player.getPosition(), player.getColisionBox(), player.movement.getVelocity());
		player.movement.printPlayer(player.getPosition(), player.getOffset());

		// Draw Map layer over the player
		// currentMap.drawLayer(4);
		currentMap.drawLayer(5);
		currentMap.drawLayer(6);

		drawText(
			"X: " + (player.getPosition().getX() + player.getOffset().getX() - player.getColisionBox().getWidth()) +
				" Y: " + (player.getPosition().getY() + player.getOffset().getY() - player.getColisionBox().getHeight()),
			(int)player.getPosition().getX() + (int)WindowSize.getX() / 2 - 100,
			(int)player.getPosition().getY() + (int)WindowSize.getY() / 2 - 100,
			20,
			VIOLET
		);

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

	void mountCollMap(CollisionMap collisionMap)
	{
		collisionMap.setCollisionAt(1, 1, 1);
		collisionMap.setCollisionAt(2, 2, 1);
		collisionMap.setCollisionAt(3, 3, 1);
	}
}
