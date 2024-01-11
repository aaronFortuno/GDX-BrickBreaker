package net.estemon.studio.util.screen.transition.implementation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import net.estemon.studio.util.GdxUtils;
import net.estemon.studio.util.screen.transition.ScreenTransitionBase;

public class FadeScreenTransition extends ScreenTransitionBase {

    // constructors
    public FadeScreenTransition(float duration) {
        super(duration);
    }

    // public methods
    @Override
    public void render(SpriteBatch batch, Texture currentScreen, Texture nextScreen, float percentage) {
        int currentScreenWidth = currentScreen.getWidth();
        int currentScreenHeight = currentScreen.getHeight();
        int nextScreenWidth = nextScreen.getWidth();
        int nextScreenHeight = nextScreen.getHeight();

        // interpolate percentage
        percentage = Interpolation.fade.apply(percentage);

        // clear screen and render
        GdxUtils.clearScreen();

        Color oldColor = batch.getColor().cpy();
        batch.begin();

        // draw current screen
        batch.setColor(1, 1, 1, 1f - percentage);   // transparency fading out
        batch.draw(currentScreen,                               // texture
                0, 0,                                        // x, y
                0, 0,                                    // origin x, y
                currentScreenWidth, currentScreenHeight,        // width, height
                1, 1,                                   // scale x, y
                0,                                              // rotation
                0, 0,                                     // src x, y
                currentScreenWidth, currentScreenHeight,        // src width, height
                false, true                               // flip x, y
        );
        batch.setColor(1, 1, 1, percentage);          // transparency fading in
        batch.draw(nextScreen,
                0, 0,
                0, 0,
                nextScreenWidth, nextScreenHeight,
                1, 1,
                0,
                0, 0,
                nextScreenWidth, nextScreenHeight,
                false, true
        );

        batch.setColor(oldColor);
        batch.end();
    }
}
