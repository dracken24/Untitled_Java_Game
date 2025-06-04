package com.game;

import com.raylib.RenderTexture;
import com.raylib.Vector2;
import com.raylib.Rectangle;
import com.raylib.Camera2D;
import static com.raylib.Raylib.loadRenderTexture;

public class Cameras
{
/***********************************************************************************/
/***                                 CLASSES                                       */
/***********************************************************************************/

    class oneCamera
    {
        public RenderTexture  texture = new RenderTexture();
        public Rectangle recForCam = new Rectangle();
        public Camera2D camera = new Camera2D();

        public Vector2 offset = new Vector2();
        public Vector2 targetToFollow = new Vector2();

        public oneCamera()
        {
        }
    }

/***********************************************************************************/
/***                                 VARIABLES                                     */
/***********************************************************************************/

    oneCamera mainCamera;

/***********************************************************************************/
/***                                 CONSTRUCTOR                                   */
/***********************************************************************************/

    public Cameras()
    {
        mainCamera = new oneCamera();
    }

/***********************************************************************************/
/***                                 FUNCTIONS                                     */
/***********************************************************************************/

    public void update()
    {
    }

    public void initOneCamera(Camera2D camera, Rectangle recForCam, Vector2 windowSize)
    {
        // Créer une nouvelle RenderTexture avec les dimensions de la fenêtre
        RenderTexture renderTexture = loadRenderTexture(
            (int)windowSize.getX(),
            (int)windowSize.getY()
        );

        // Initialiser la caméra
        camera = new Camera2D();
        camera.setZoom(1.0f);
        camera.setRotation(0.0f);

        mainCamera.offset = new Vector2(0, 0);
        mainCamera.camera = camera;
        mainCamera.texture = renderTexture;
        mainCamera.recForCam = recForCam;
    }

/***********************************************************************************/
/***                                 GETTERS                                       */
/***********************************************************************************/

    public Camera2D getMainCamera()
    {
        return mainCamera.camera;
    }

    public RenderTexture getMainTexture()
    {
        return mainCamera.texture;
    }

    public Vector2 getTargetToFollow()
    {
        return mainCamera.targetToFollow;
    }

    public Vector2 getOffset()
    {
        return mainCamera.offset;
    }

/***********************************************************************************/
/***                                 SETTERS                                       */
/***********************************************************************************/

    public void setMainCamera(Camera2D camera)
    {
        mainCamera.camera = camera;
    }

    public void setTargetToFollow(Vector2 targetPosition)
    {
        mainCamera.targetToFollow = targetPosition;
    }

    public void setOffset(Vector2 offset)
    {
        mainCamera.offset = offset;
    }
}