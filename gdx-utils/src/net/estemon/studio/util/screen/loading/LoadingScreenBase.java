package net.estemon.studio.util.screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.estemon.studio.util.GdxUtils;
import net.estemon.studio.util.Validate;
import net.estemon.studio.util.game.GameBase;
import net.estemon.studio.util.screen.ScreenBaseAdapter;

public abstract class LoadingScreenBase extends ScreenBaseAdapter {

    // constants
    private static final float DEFAULT_HUD_WIDTH = 640f;
    private static final float DEFAULT_HUD_HEIGHT = 480f;
    private static final float DEFAULT_PROGRESS_BAR_HEIGHT = 60f;
    // attributes
    protected final GameBase game;
    private AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;

    private float progress;
    private float waitTime = 0.5f;

    private float progressBarWidth;
    private float progressBarHeight;

    private float hudWidth;
    private float hudHeight;

    private boolean changeScreen;

    // constructors
    protected LoadingScreenBase(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // abstract methods
    protected abstract Array<AssetDescriptor> getAssetDescriptors();
    protected abstract void onLoadDone();

    // public methods
    @Override
    public void show() {
        hudWidth = getHudWidth();
        hudHeight = getHudHeigth();

        progressBarWidth = getProgressBarWidth();
        progressBarHeight = getProgressBarHeight();

        camera = new OrthographicCamera();
        viewport = new FitViewport(hudWidth, hudHeight, camera);
        renderer = new ShapeRenderer();

        Validate.notNull(getAssetDescriptors());

        for (AssetDescriptor descriptor : getAssetDescriptors()) {
            assetManager.load(descriptor);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        GdxUtils.clearScreen();
        viewport.apply();
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        renderer.end();

        if (changeScreen) {
            onLoadDone();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }

    // protected methods
    protected float getHudWidth() {
        return DEFAULT_HUD_WIDTH;
    }
    protected float getHudHeigth() {
        return DEFAULT_HUD_HEIGHT;
    }
    protected float getProgressBarWidth() {
        return getHudWidth() / 2f;
    }
    protected float getProgressBarHeight() {
        return DEFAULT_PROGRESS_BAR_HEIGHT;
    }

    // private methods
    private void update(float delta) {
        progress = assetManager.getProgress();

        if (assetManager.update()) {
            waitTime -= delta;

            if (waitTime <= 0f) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        float progressBarX = (hudWidth - progressBarWidth) / 2f;
        float progressBarY = (hudHeight - progressBarHeight) / 2f;

        renderer.rect(progressBarX, progressBarY, progress * progressBarWidth, progressBarHeight);
    }
}
