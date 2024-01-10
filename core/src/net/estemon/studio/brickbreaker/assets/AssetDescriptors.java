package net.estemon.studio.brickbreaker.assets;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public final class AssetDescriptors {

    // constants
    public static final AssetDescriptor<BitmapFont> FONT_16 =
            new AssetDescriptor<>(AssetPaths.FONT_16, BitmapFont.class);
    public static final AssetDescriptor<BitmapFont> FONT_32 =
            new AssetDescriptor<>(AssetPaths.FONT_32, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<ParticleEffect> FIRE =
            new AssetDescriptor<>(AssetPaths.FIRE, ParticleEffect.class);
    public static final AssetDescriptor<ParticleEffect> STAR =
            new AssetDescriptor<>(AssetPaths.STAR, ParticleEffect.class);

    public static final AssetDescriptor<Sound> HIT =
            new AssetDescriptor<>(AssetPaths.HIT, Sound.class);
    public static final AssetDescriptor<Sound> LOST =
            new AssetDescriptor<>(AssetPaths.LOST, Sound.class);
    public static final AssetDescriptor<Sound> PICKUP =
            new AssetDescriptor<>(AssetPaths.PICKUP, Sound.class);

    public static final AssetDescriptor<Skin> SKIN =
            new AssetDescriptor<>(AssetPaths.SKIN, Skin.class);

    // all descriptors
    public static final Array<AssetDescriptor> ALL = new Array<>();

    // static init
    static {
        ALL.addAll(
                FONT_16,
                FONT_32,
                GAME_PLAY,
                FIRE,
                STAR,
                HIT,
                LOST,
                PICKUP,
                SKIN
        );
    }

    // constructors
    private AssetDescriptors() {} // not instantiable
}
