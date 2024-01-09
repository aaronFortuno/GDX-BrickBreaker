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
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
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
import net.estemon.studio.brickbreaker.entity.Pickup;
import net.estemon.studio.util.GdxUtils;
import net.estemon.studio.util.Validate;
import net.estemon.studio.util.ViewportUtils;
import net.estemon.studio.util.debug.DebugCameraController;
import net.estemon.studio.util.debug.ShapeRendererUtils;
import net.estemon.studio.util.entity.EntityBase;

public class GameRenderer implements Disposable {

    // attributes
    private final GameWorld gameWorld;
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

    private TextureRegion expandRegion;
    private TextureRegion shrinkRegion;
    private TextureRegion slowDownRegion;
    private TextureRegion speedUpRegion;


    // constructors
    public GameRenderer(GameWorld gameWorld, SpriteBatch batch, AssetManager assetManager) {
        this.gameWorld = gameWorld;
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

        expandRegion = gameplayAtlas.findRegion(RegionNames.EXPAND);
        shrinkRegion = gameplayAtlas.findRegion(RegionNames.SHRINK);
        slowDownRegion = gameplayAtlas.findRegion(RegionNames.SLOW_DOWN);
        speedUpRegion = gameplayAtlas.findRegion(RegionNames.SPEED_UP);
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

    public Vector2 screenToWorld(Vector2 screenCoordinates) {
        return viewport.unproject(screenCoordinates);
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
        Paddle paddle = gameWorld.getPaddle();
        drawEntity(batch, paddleRegion, paddle);

        // ball
        Ball ball = gameWorld.getBall();
        batch.draw(ballRegion, ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        // bricks
        Array<Brick> bricks = gameWorld.getBricks();
        for (int i = 0; i < bricks.size; i++) {
            Brick brick = bricks.get(i);
            drawEntity(batch, brickRegion, brick);
        }

        // pickups
        Array<Pickup> pickups = gameWorld.getPickups();
        for (int i = 0; i < pickups.size; i++) {
            Pickup pickup = pickups.get(i);
            TextureRegion pickupRegion = findPickupRegion(pickup);
            drawEntity(batch, pickupRegion, pickup);
        }

        // effects
        Array<ParticleEffectPool.PooledEffect> effects = gameWorld.getEffects();
        for (int i = 0; i < effects.size; i++) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(batch);
        }
    }

    private TextureRegion findPickupRegion(Pickup pickup) {
        TextureRegion region = null;
        if (pickup.isExpand()) {
            region = expandRegion;
        } else if (pickup.isShrink()) {
            region = shrinkRegion;
        } else if (pickup.isSlowDown()) {
            region = slowDownRegion;
        } else if (pickup.isSpeedUp()) {
            region = speedUpRegion;
        }

        if (region == null) {
            throw new IllegalArgumentException("Can't find region for pickupType: " + pickup.getType());
        }

        return region;
    }

    private void renderDebug() {
        viewport.apply();
        if (gameWorld.isDrawGrid()) {
            ViewportUtils.drawGrid(viewport, renderer);
        }

        if (gameWorld.isDrawDebug()) {
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
        Polygon paddleBounds = gameWorld.getPaddle().getBounds();
        ShapeRendererUtils.polygon(renderer, paddleBounds);

        // drawing bricks
        renderer.setColor(Color.ROYAL);
        for (Brick brick : gameWorld.getBricks()) {
            Polygon brickBounds = brick.getBounds();
            ShapeRendererUtils.polygon(renderer, brickBounds);
        }

        // drawing ball
        renderer.setColor(Color.VIOLET);
        Polygon ballBounds = gameWorld.getBall().getBounds();
        ShapeRendererUtils.polygon(renderer, ballBounds);

        // pickups
        Array<Pickup> pickups = gameWorld.getPickups();
        for (int i = 0; i < pickups.size; i++) {
            Pickup pickup = pickups.get(i);
            Polygon pickupBounds = pickup.getBounds();
            ShapeRendererUtils.polygon(renderer, pickupBounds);
        }

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
        String scoreString = "SCORE: " + gameWorld.getScoreString();
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
