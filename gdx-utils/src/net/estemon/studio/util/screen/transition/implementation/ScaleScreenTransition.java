package net.estemon.studio.util.screen.transition.implementation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import net.estemon.studio.util.GdxUtils;
import net.estemon.studio.util.Validate;
import net.estemon.studio.util.screen.transition.ScreenTransitionBase;

public class ScaleScreenTransition extends ScreenTransitionBase {

    // attributes
    private boolean scaleOut;
    private Interpolation interpolation;

    // constructors
    public ScaleScreenTransition(float duration) {
        this(duration, false);
    }
    public ScaleScreenTransition(float duration, boolean scaleOut) {
        this(duration, scaleOut, Interpolation.linear);
    }
    public ScaleScreenTransition(float duration, boolean scaleOut, Interpolation interpolation) {
        super(duration);

        Validate.notNull(interpolation, "INTERPOLATION is required");

        this.scaleOut = scaleOut;
        this.interpolation = interpolation;
    }

    // public methods
    @Override
    public void render(SpriteBatch batch, Texture currentScreen, Texture nextScreen, float percentage) {
        // interpolate percentage
        percentage = interpolation.apply(percentage);

        // assume scale out is false (e. g. scaling in)
        float scale = 1 - percentage;

        if (scaleOut) {
            scale = percentage;
        }

        // drawing order depends onf scale type (in or out)
        Texture topTexture = scaleOut ? nextScreen : currentScreen;
        Texture bottomTexture = scaleOut ? currentScreen : nextScreen;

        int topTextureWidth = topTexture.getWidth();
        int topTextureHeight = topTexture.getHeight();
        int bottomTextureWidth = bottomTexture.getWidth();
        int bottomTextureHeight = bottomTexture.getHeight();

        // drawing
        GdxUtils.clearScreen();
        batch.begin();

        // bottom texture (static)
        batch.draw(bottomTexture,
                0, 0,
                0, 0,
                bottomTextureWidth, bottomTextureHeight,
                1, 1,
                0,
                0, 0,
                bottomTextureWidth, bottomTextureHeight,
                false, true
        );

        // top texture (dynamic)
        batch.draw(topTexture,
                0, 0,
                topTextureWidth / 2f, topTextureHeight / 2f,
                topTextureWidth, topTextureHeight,
                scale, scale,
                0,
                0, 0,
                topTextureWidth, topTextureHeight,
                false, true
        );
        batch.end();
    }
}
