package net.estemon.studio.brickbreaker.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import net.estemon.studio.brickbreaker.BrickBreakerGame;

public class GameScreen extends ScreenAdapter {

    // attributes
    private final BrickBreakerGame game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;

    private GameController controller;
    private GameRenderer renderer;

    // constructors
    public GameScreen(BrickBreakerGame game) {
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    // public methods

    @Override
    public void show() {
        controller = new GameController();
        renderer = new GameRenderer(controller, batch, assetManager);
    }

    @Override
    public void render(float delta) {
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