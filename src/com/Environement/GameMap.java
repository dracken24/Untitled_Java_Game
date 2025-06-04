package com.Environement;

import com.raylib.Texture;
import static com.raylib.Raylib.loadTexture;
import static com.raylib.Raylib.WHITE;
import static com.raylib.Raylib.drawTexturePro;
import com.raylib.Rectangle;
import com.raylib.Vector2;


public class GameMap
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/
    Texture layer1;		// Texture 1 of the sprite sheet
    Texture layer2;		// Texture 2 of the sprite sheet
    Texture layer3;		// Texture 3 of the sprite sheet

    // Player between layer 3 and 4

    Texture layer4;		// Texture 4 of the sprite sheet
    Texture layer5;		// Texture 5 of the sprite sheet
    Texture layer6;		// Texture 6 of the sprite sheet

    Rectangle recForMap;
    int scale;

/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

    public GameMap(String path_layer1, String path_layer2, String path_layer3,
        String path_layer4, String path_layer5, String path_layer6,
            Rectangle recForMap, int scale)
    {
        if (path_layer1 != null)
        {
            layer1 = loadTexture(path_layer1);
        }
        if (path_layer2 != null)
        {
            layer2 = loadTexture(path_layer2);
        }
        if (path_layer3 != null)
        {
            layer3 = loadTexture(path_layer3);
        }
        if (path_layer4 != null)
        {
            layer4 = loadTexture(path_layer4);
        }
        if (path_layer5 != null)
        {
            layer5 = loadTexture(path_layer5);
        }
        if (path_layer6 != null)
        {
            layer6 = loadTexture(path_layer6);
        }
        
        this.recForMap = recForMap;
        this.scale = scale;
    }

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

    public void drawLayer(int layer)
    {
        Vector2 origin = new Vector2(0, 0);
        Rectangle source = new Rectangle(0, 0, recForMap.getWidth(), recForMap.getHeight());
        Rectangle dest = new Rectangle(recForMap.getX(), recForMap.getY(), recForMap.getWidth() * scale, recForMap.getHeight() * scale);

        switch (layer)
        {
            case 1:
                if (this.layer1 != null)
                {
                    drawTexturePro(this.layer1, source, dest, origin, 0, WHITE);
                }
                break;
            case 2:
                if (this.layer2 != null)
                {
                    drawTexturePro(this.layer2, source, dest, origin, 0, WHITE);
                }
                break;
            case 3:
                if (this.layer3 != null)
                {
                    drawTexturePro(this.layer3, source, dest, origin, 0, WHITE);
                }
                break;
            case 4:
                if (this.layer4 != null)
                {
                    drawTexturePro(this.layer4, source, dest, origin, 0, WHITE);
                }
                break;
            case 5:
                if (this.layer5 != null)
                {
                    drawTexturePro(this.layer5, source, dest, origin, 0, WHITE);
                }
                break;
            case 6:
                if (this.layer6 != null)
                {
                    drawTexturePro(this.layer6, source, dest, origin, 0, WHITE);
                }
                break;
        }
    }

/***********************************************************************************/
/***                                 GETTERS                                       */
/***********************************************************************************/

    public Texture getLayer1()
    {
        return layer1;
    }

    public Texture getLayer2()
    {
        return layer2;
    }

    public Texture getLayer3()
    {
        return layer3;
    }

    public Texture getLayer4()
    {
        return layer4;
    }

    public Texture getLayer5()
    {
        return layer5;
    }

    public Texture getLayer6()
    {
        return layer6;
    }

/***********************************************************************************/
/***                                 SETTERS                                       */	
/***********************************************************************************/

    public void setLayer1(Texture layer1)
    {
        this.layer1 = layer1;
    }

    public void setLayer2(Texture layer2)
    {
        this.layer2 = layer2;
    }

    public void setLayer3(Texture layer3)
    {
        this.layer3 = layer3;
    }

    public void setLayer4(Texture layer4)
    {
        this.layer4 = layer4;
    }

    public void setLayer5(Texture layer5)
    {
        this.layer5 = layer5;
    }

    public void setLayer6(Texture layer6)
    {
        this.layer6 = layer6;
    }
}
