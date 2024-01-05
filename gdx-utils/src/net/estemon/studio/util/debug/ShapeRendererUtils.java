package net.estemon.studio.util.debug;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import net.estemon.studio.util.Validate;

public class ShapeRendererUtils {

    // constructors
    private ShapeRendererUtils() {} // not instantiable

    // public methods
    public static void rect(ShapeRenderer renderer, Rectangle rectangle) {
        // validate parameters
        Validate.notNull(renderer);
        Validate.notNull(rectangle);

        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
