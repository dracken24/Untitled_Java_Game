package com.player;

import static com.raylib.Raylib.KeyboardKey.KEY_A;
import static com.raylib.Raylib.KeyboardKey.KEY_D;
import static com.raylib.Raylib.KeyboardKey.KEY_LEFT_SHIFT;
import static com.raylib.Raylib.KeyboardKey.KEY_W;
import static com.raylib.Raylib.KeyboardKey.KEY_S;
import static com.raylib.Raylib.isKeyDown;

import com.raylib.Vector2;
import com.raylib.Rectangle;

import com.objects.SpriteSheet;
import com.enums.SpriteMovement;

public class PlayerMovement
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/

    SpriteSheet	idle;
    SpriteSheet	up;
    SpriteSheet	down;
    SpriteSheet	left;
    SpriteSheet	right;

    SpriteSheet	attack01;
    SpriteSheet	attack02;
    SpriteSheet	attack03;

    SpriteSheet	death;
    SpriteSheet	hurt;

	SpriteSheet	currentAction;
	SpriteSheet	lastAction;

    SpriteMovement actionInProgress;

    int actionCounter;

    Vector2 velocity;
    Vector2 velocityMinMax_Y;
    Vector2 velocityMinMax_X;
    Vector2 lastPlayerPosition;

    Vector2 offset;

    boolean isRunning;

/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

    public PlayerMovement()
    {
        this.actionInProgress = SpriteMovement.IDLE;
        this.currentAction = idle;
        this.lastAction = null;

        this.actionCounter = 0;

        this.velocity = new Vector2(0, 0);
        this.velocityMinMax_Y = new Vector2(0, 4);
        this.velocityMinMax_X = new Vector2(0, 4);

        this.lastPlayerPosition = new Vector2(0, 0);

        this.isRunning = false;
    }

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

	public void update()
	{
        catchInput();
        setMovement(this.actionInProgress);

		if (this.lastAction != this.currentAction)
		{
			this.currentAction.resetCounter();
		}

		// this.currentAction.updateSprite(false, playerPosition, offset);
        
        // this.lastPlayerPosition = playerPosition;
	}

    public void printPlayer(Vector2 playerPosition, Vector2 offset)
    {
        this.currentAction.updateSprite(false, playerPosition, offset);
    }

    public Vector2 applyMovement(Vector2 position, Rectangle colisionBox, Vector2 velocity)
    {
        position.setX(position.getX() + velocity.getX());
        colisionBox.setX(colisionBox.getX() + velocity.getX());

        position.setY(position.getY() + velocity.getY());
        colisionBox.setY(colisionBox.getY() + velocity.getY());

        return position;
    }

    void catchInput()
	{
		boolean walkOnX = false;
        boolean walkOnY = false;

        // System.out.println("actionCounter: " + actionCounter);
		
		if (isKeyDown(KEY_D))
		{
			// rightSide = false;
            walkOnX = true;
            velocity.setX(velocityMinMax_X.getY());

            this.actionInProgress = SpriteMovement.RIGHT;
		}
		if (isKeyDown(KEY_A))
		{
			// rightSide = true;
            walkOnX = true;
            velocity.setX(-velocityMinMax_X.getY());

            this.actionInProgress = SpriteMovement.LEFT;
		}

        if (isKeyDown(KEY_W))
        {
            walkOnY = true;
            velocity.setY(-velocityMinMax_Y.getY());

            this.actionInProgress = SpriteMovement.UP;
        }
        if (isKeyDown(KEY_S))
        {
            walkOnY = true;
            velocity.setY(velocityMinMax_Y.getY());

            this.actionInProgress = SpriteMovement.DOWN;
        }

        if (isKeyDown(KEY_LEFT_SHIFT))
        {
            this.isRunning = true;
            int isNegatif = 1;
            if (velocity.getX() < 0)
            {
                isNegatif = -1;
            }
            velocity.setX(velocityMinMax_X.getY() * 5 * isNegatif);
            isNegatif = 1;
            if (velocity.getY() < 0)
            {
                isNegatif = -1;
            }
            velocity.setY(velocityMinMax_Y.getY() * 5 * isNegatif);
        }
        else
        {
            this.isRunning = false;

        }

        // System.out.println("actionCounter: " + actionCounter);
        // System.out.println("actionInProgress: " + actionInProgress);
		if (walkOnX == false)
		{
            velocity.setX(0);
		}
        if (walkOnY == false)
        {
            velocity.setY(0);
        }
        if (velocity.getX() == 0 && velocity.getY() == 0)
        {
            this.actionInProgress = SpriteMovement.IDLE;
        }
	}

/***********************************************************************************/
/***                                 GETTERS                                       */
/***********************************************************************************/

    public SpriteSheet getUp()
    {
        return up;
    }

    public SpriteSheet getDown()
    {
        return down;
    }

    public SpriteSheet getLeft()
    {
        return left;
    }

    public SpriteSheet getRight()
    {
        return right;
    }

    public SpriteSheet getAttack01()
    {
        return attack01;
    }

    public SpriteSheet getAttack02()
    {
        return attack02;
    }

    public SpriteSheet getAttack03()
    {
        return attack03;
    }   

    public SpriteSheet getIdle()
    {
        return idle;
    }    

    public SpriteSheet getDeath()
    {
        return death;
    }       

    public SpriteSheet getHurt()
    {
        return hurt;
    }   

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public int getActionCounter()
    {
        return actionCounter;
    }

    public SpriteMovement getActionInProgress()
    {
        return actionInProgress;
    }

    public Vector2 getLastPosition()
    {
        return lastPlayerPosition;
    }

/***********************************************************************************/
/***                                 SETTERS                                       */
/***********************************************************************************/

    public void setUp(SpriteSheet up)
    {
        this.up = up;
    }

    public void setDown(SpriteSheet down)
    {
        this.down = down;
    }

    public void setLeft(SpriteSheet left)
    {
        this.left = left;
    }

    public void setRight(SpriteSheet right)
    {
        this.right = right;
    }

    public void setAttack01(SpriteSheet attack01)
    {
        this.attack01 = attack01;
    }

    public void setAttack02(SpriteSheet attack02)
    {
        this.attack02 = attack02;
    }   

    public void setAttack03(SpriteSheet attack03)
    {
        this.attack03 = attack03;
    }   

    public void setIdle(SpriteSheet idle)
    {
        this.idle = idle;
    }

    public void setDeath(SpriteSheet death)
    {
        this.death = death;
    }

    public void setHurt(SpriteSheet hurt)
    {
        this.hurt = hurt;
    }

    public void setCurrentAction(SpriteSheet currentAction)
    {
        this.lastAction = this.currentAction;
        this.currentAction = currentAction;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;
    }

    public void setActionCounter(int actionCounter)
    {
        this.actionCounter = actionCounter;
    }

    public void setMovement(SpriteMovement movement)
    {
        switch (movement)
        {
            case IDLE:
                if (actionCounter == 0)
                {
                    setCurrentAction(idle);
                }
                break;
            case RIGHT:
                if (actionCounter == 0)
                {
                    setCurrentAction(right);
                }
                break;
            case LEFT:
                if (actionCounter == 0)
                {
                    setCurrentAction(left);
                }
                break;
            case UP:
                if (actionCounter == 0)
                {
                    setCurrentAction(up);
                }
                break;
            case DOWN:
                if (actionCounter == 0)
                {
                    setCurrentAction(down);
                }
                break;
        }
    }

    public void setLastPosition(Vector2 lastPosition)
    {
        this.lastPlayerPosition = lastPosition;
    }
}

