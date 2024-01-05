package net.estemon.studio.util.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import net.estemon.studio.util.Validate;

public class RectangleUtils {

    // constructor
    private RectangleUtils() {} // not instantiable

    // public methods
    public static Vector2 getTopLeft(Rectangle rect) {
        Validate.notNull(rect);
        return new Vector2(rect.x, rect.y + rect.height);
    }

    public static Vector2 getTopRight(Rectangle rect) {
        Validate.notNull(rect);
        return new Vector2(rect.x + rect.width, rect.y + rect.height);
    }

    public static Vector2 getBottomLeft(Rectangle rect) {
        Validate.notNull(rect);
        return new Vector2(rect.x, rect.y);
    }

    public static Vector2 getBottomRight(Rectangle rect) {
        Validate.notNull(rect);
        return new Vector2(rect.x + rect.width, rect.y);
    }
}
