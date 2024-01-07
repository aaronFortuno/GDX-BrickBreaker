package net.estemon.studio.brickbreaker.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public final class AssetDescriptors {

    // constants
    public static final AssetDescriptor<BitmapFont> FONT_16 =
            new AssetDescriptor<>(AssetPaths.FONT_16, BitmapFont.class);
    public static final AssetDescriptor<BitmapFont> FONT_32 =
            new AssetDescriptor<>(AssetPaths.FONT_32, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    // all descriptors
    public static final Array<AssetDescriptor> ALL = new Array<>();

    // static init
    static {
        ALL.addAll(
                FONT_16,
                FONT_32,
                GAME_PLAY
        );
    }

    // constructors
    private AssetDescriptors() {} // not instantiable
}
