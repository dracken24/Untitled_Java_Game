import com.raylib.Vector2;
import static com.raylib.Raylib.windowShouldClose;
import static com.raylib.Raylib.closeWindow;

import com.game.Core;

public class App
{
/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/

    Core core;
    
/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

    public App()
    {
        core = new Core(new Vector2(1200, 750), "Untitled Action RPG Game");
    }

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

    public static void main(String[] args)
    {
        App app = new App();

        while (!windowShouldClose())
        {
            app.core.update();
        }

        closeWindow();
    }
} 