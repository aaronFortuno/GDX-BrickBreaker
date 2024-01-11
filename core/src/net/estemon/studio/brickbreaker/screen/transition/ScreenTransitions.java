package net.estemon.studio.brickbreaker.screen.transition;

import com.badlogic.gdx.math.Interpolation;

import net.estemon.studio.util.Direction;
import net.estemon.studio.util.screen.transition.ScreenTransition;
import net.estemon.studio.util.screen.transition.implementation.FadeScreenTransition;
import net.estemon.studio.util.screen.transition.implementation.ScaleScreenTransition;
import net.estemon.studio.util.screen.transition.implementation.SlideScreenTransition;

public final class ScreenTransitions {

    // constants
    public static final ScreenTransition FADE = new FadeScreenTransition(2f);
    public static final ScreenTransition SCALE = new ScaleScreenTransition(2f, true, Interpolation.exp5);
    public static final ScreenTransition SLIDE = new SlideScreenTransition(2f, true, Direction.DOWN, Interpolation.swingIn);

    // constructors
    private ScreenTransitions() {} // not instantiable
}
