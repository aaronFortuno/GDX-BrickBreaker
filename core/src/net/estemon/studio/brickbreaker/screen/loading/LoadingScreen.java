package net.estemon.studio.brickbreaker.screen.loading;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.utils.Array;

import net.estemon.studio.brickbreaker.assets.AssetDescriptors;
import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.screen.game.GameScreen;
import net.estemon.studio.util.game.GameBase;
import net.estemon.studio.util.screen.loading.LoadingScreenBase;

public class LoadingScreen extends LoadingScreenBase {

    // constructors
    public LoadingScreen(GameBase game) {
        super(game);
    }

    // protected methods
    @Override
    protected Array<AssetDescriptor> getAssetDescriptors() {
        return AssetDescriptors.ALL;
    }

    @Override
    protected void onLoadDone() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    protected float getHudWidth() {
        return GameConfig.HUD_WIDTH;
    }

    @Override
    protected float getHudHeigth() {
        return GameConfig.HUD_HEIGHT;
    }
}