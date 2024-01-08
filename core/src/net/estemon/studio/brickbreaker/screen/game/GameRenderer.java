package net.estemon.studio.brickbreaker.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.brickbreaker.assets.AssetDescriptors;
import net.estemon.studio.brickbreaker.assets.RegionNames;
import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Ball;
import net.estemon.studio.brickbreaker.entity.Brick;
import net.estemon.studio.brickbreaker.entity.Paddle;
import net.estemon.studio.util.GdxUtils;
import net.estemon.studio.util.Validate;
import net.estemon.studio.util.ViewportUtils;
import net.estemon.studio.util.debug.DebugCameraController;
import net.estemon.studio.util.debug.ShapeRendererUtils;
import net.estemon.studio.util.entity.EntityBase;

public class GameRenderer implements Disposable {

    // attributes
    private final GameController controller;
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final GlyphLayout layout = new GlyphLayout();

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private DebugCameraController debugCameraController;

    private BitmapFont scoreFont;

    private TextureRegion backgroundRegion;
    private TextureRegion paddleRegion;
    private TextureRegion ballRegion;
    private TextureRegion brickRegion;


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
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        renderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        scoreFont = assetManager.get(AssetDescriptors.FONT_32);

        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        paddleRegion = gameplayAtlas.findRegion(RegionNames.PADDLE);
        ballRegion = gameplayAtlas.findRegion(RegionNames.BALL);
        brickRegion = gameplayAtlas.findRegion(RegionNames.BRICK);
    }

    // public methods
    public void render(float delta) {
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // clear screen
        GdxUtils.clearScreen();

        // drawing
        renderGamePlay();
        renderDebug();
        renderHud();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelPerUnit(viewport);
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // private methods
    private void renderGamePlay() {
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay();

        batch.end();
    }

    private void drawGamePlay() {
        // background
        batch.draw(backgroundRegion, 0, 0, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        // paddle
        Paddle paddle = controller.getPaddle();
        drawEntity(batch, paddleRegion, paddle);

        // ball
        Ball ball = controller.getBall();
        batch.draw(ballRegion, ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        // bricks
        Array<Brick> bricks = controller.getBricks();
        for (int i = 0; i < bricks.size; i++) {
            Brick brick = bricks.get(i);
            drawEntity(batch, brickRegion, brick);
        }

        // effects
        Array<ParticleEffectPool.PooledEffect> effects = controller.getEffects();
        for (int i = 0; i < effects.size; i++) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(batch);
        }
    }

    private void renderDebug() {
        viewport.apply();
        if (controller.isDrawGrid()) {
            ViewportUtils.drawGrid(viewport, renderer);
        }

        if (controller.isDrawDebug()) {
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);

            drawDebug();

            renderer.end();
        }
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

    private void renderHud() {
        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        drawHud();

        batch.end();
    }

    private void drawHud() {
        String scoreString = "SCORE: " + controller.getScoreString();
        layout.setText(scoreFont, scoreString);

        scoreFont.draw(batch, layout, 20f, GameConfig.HUD_HEIGHT - layout.height);

    }

    // static methods
    private static void drawEntity(SpriteBatch batch, TextureRegion region, EntityBase entity) {
        Validate.notNull(batch);
        Validate.notNull(region);
        Validate.notNull(entity);

        batch.draw(region, entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    }
}
