package com.Environement;

import com.raylib.Vector2;
import static com.raylib.Raylib.drawRectangle;
import static com.raylib.Raylib.RED;

public class CollisionMap
{
/***********************************************************************************/
/***                                 VARIABLES                                   ***/
/***********************************************************************************/

    private String[][] collisionMap;
    private Vector2 tileSize;

/***********************************************************************************/
/***                                 CONSTRUCTOR                                 ***/
/***********************************************************************************/

    public CollisionMap(Vector2 size, Vector2 tileSize, int scale)
    {
        this.collisionMap = new String[(int)size.getX()][(int)size.getY()];
        this.tileSize = tileSize;
    }

/***********************************************************************************/
/***                                 FUNCTIONS                                   ***/
/***********************************************************************************/

    public void printMap()
    {
        for (int i = 0; i < collisionMap.length; i++)
        {
            for (int j = 0; j < collisionMap[i].length; j++)
            {
                
                if (collisionMap[i][j] == "1")
                {
                    // System.out.print(collisionMap[i][j]);
                    // Add rectangle on screen
                    drawRectangle(
                        (int)(i * tileSize.getX()),
                        (int)(j * tileSize.getY()),
                        (int)(tileSize.getX()),
                        (int)(tileSize.getY()),
                        RED
                    );
                }
            }
            // System.out.println();
        }
    }

/***********************************************************************************/
/***                                 GETTERS                                     ***/
/***********************************************************************************/

    public String[][] getCollisionMap()
    {
        return collisionMap;
    }

/***********************************************************************************/
/***                                 SETTERS                                       */
/***********************************************************************************/

    public void setCollisionAt(int x, int y, String value)
    {
        if (x < 0 || x >= collisionMap.length || y < 0 || y >= collisionMap[0].length)
        {
            throw new IndexOutOfBoundsException("CollisionMap index out of bounds");
        }
        // if (value != "0" && value != "1")
        // {
        //     throw new IllegalArgumentException("CollisionMap value must be 0 or 1");
        // }

        collisionMap[x][y] = value;
    }
}
