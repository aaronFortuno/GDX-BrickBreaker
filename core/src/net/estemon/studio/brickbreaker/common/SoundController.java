package net.estemon.studio.brickbreaker.common;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import net.estemon.studio.brickbreaker.assets.AssetDescriptors;

public class SoundController {

    // attributes
    private final AssetManager assetManager;

    private Sound hit;
    private Sound lost;
    private Sound pickup;

    // constructors
    public SoundController(AssetManager assetManager) {
        this.assetManager = assetManager;
        init();
    }

    private void init() {
        hit = assetManager.get(AssetDescriptors.HIT);
        lost = assetManager.get(AssetDescriptors.LOST);
        pickup = assetManager.get(AssetDescriptors.PICKUP);
    }

    // public methods
    public void hit() {
        hit.play();
    }

    public void lost() {
        lost.play();
    }

    public void pickup() {
        pickup.play();
    }
}
