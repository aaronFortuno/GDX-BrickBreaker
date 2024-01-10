package net.estemon.studio.brickbreaker.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.estemon.studio.brickbreaker.BrickBreakerGame;
import net.estemon.studio.brickbreaker.common.ScoreController;
import net.estemon.studio.brickbreaker.common.SoundController;
import net.estemon.studio.brickbreaker.entity.EntityFactory;
import net.estemon.studio.brickbreaker.input.PaddleInputController;
import net.estemon.studio.util.game.GameBase;

public class GameScreen extends ScreenAdapter {

    // attributes
    private final GameBase game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final ScoreController scoreController;

    private GameWorld gameWorld;        // model
    private GameController controller;  // controller
    private GameRenderer renderer;      // view

    private EntityFactory factory;
    private PaddleInputController paddleInputController;
    private SoundController soundController;

    // constructors
    public GameScreen(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
        scoreController = ((BrickBreakerGame)game).getScoreController();

    }

    // public methods

    @Override
    public void show() {
        factory = new EntityFactory(assetManager);
        soundController = new SoundController(assetManager);

        gameWorld = new GameWorld(soundController, scoreController, factory);
        renderer = new GameRenderer(gameWorld, batch, assetManager);
        controller = new GameController(gameWorld, renderer);

        paddleInputController = new PaddleInputController(gameWorld.getPaddle(), controller);
    }

    @Override
    public void render(float delta) {
        if (!gameWorld.isGameOver()) {
            paddleInputController.update(delta);
        }
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}