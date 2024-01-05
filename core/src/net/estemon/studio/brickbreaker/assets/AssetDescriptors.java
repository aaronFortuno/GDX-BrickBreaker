package net.estemon.studio.brickbreaker.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public final class AssetDescriptors {

    // constants
    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<>(AssetPaths.SCORE_FONT, BitmapFont.class);

    // all descriptors
    public static final Array<AssetDescriptor> ALL = new Array<>();

    // static init
    static {
        ALL.addAll(
                FONT
        );
    }

    // constructors
    private AssetDescriptors() {} // not instantiable
}
