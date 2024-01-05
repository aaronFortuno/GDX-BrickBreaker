package net.estemon.studio.brickbreaker.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Brick;
import net.estemon.studio.util.GdxUtils;
import net.estemon.studio.util.ViewportUtils;
import net.estemon.studio.util.debug.DebugCameraConfig;
import net.estemon.studio.util.debug.DebugCameraController;
import net.estemon.studio.util.debug.ShapeRendererUtils;

public class GameRenderer implements Disposable {

    // attributes
    private final GameController controller;
    private final SpriteBatch batch;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private DebugCameraController debugCameraController;

    // constructors
    public GameRenderer(GameController controller, SpriteBatch batch, AssetManager assetManager) {
        this.controller = controller;
        this.batch = batch;
        this.assetManager = assetManager;
        init();
    }

    // init
    private void init() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    // public methods
    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // clear screen
        GdxUtils.clearScreen();

        // drawing
        renderDebug();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {

    }

    // private methods
    private void renderDebug() {
        viewport.apply();
        ViewportUtils.drawGrid(viewport, renderer);

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
    }

    private void drawDebug() {
        Color oldColor = renderer.getColor().cpy();

        // drawing paddle
        renderer.setColor(Color.CORAL);
        Rectangle paddleBounds = controller.getPaddle().getBounds();
        ShapeRendererUtils.rect(renderer, paddleBounds);

        // drawing bricks
        renderer.setColor(Color.ROYAL);
        for (Brick brick : controller.getBricks()) {
            Rectangle brickBounds = brick.getBounds();
            ShapeRendererUtils.rect(renderer, brickBounds);
        }

        // drawing ball
        renderer.setColor(Color.VIOLET);
        Circle ballBounds = controller.getBall().getBounds();
        ShapeRendererUtils.circle(renderer, ballBounds);


        renderer.setColor(oldColor);
    }
}
