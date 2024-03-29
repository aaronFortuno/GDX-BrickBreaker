package net.estemon.studio.util.shape;

import com.badlogic.gdx.math.MathUtils;

public final class ShapeUtils {

    // constructors
    private ShapeUtils() {} // not instantiable

    // public methods
    public static float[] createRectangle(float width, float height) {
        return createRectangle(0, 0, width, height);
    }

    public static float[] createRectangle(float x, float y, float width, float height) {
        return new float[] {
                x, y,                   // bottom left
                x + width, y,           // bottom right
                x + width, y + height,  // top right
                x, y + height           // top left
        };
    }

    public static float[] createOctagon(float radius) {
        return createOctagon(0, 0, radius);
    }

    public static float[] createOctagon(float originX, float originY, float radius) {
        return createPolygon(originX, originY, radius, 8);
    }

    public static float[] createPolygon(float originX, float originY, float radius, int vertexCount) {
        float[] polygonVertices = new float[vertexCount * 2];

        for (int i = 0, j = 0; i < vertexCount; i++) {
            polygonVertices[j++] = originX + radius * MathUtils.cosDeg(360 * i / vertexCount); // x position
            polygonVertices[j++] = originY + radius * MathUtils.sinDeg(360 * i / vertexCount); // y position
        }

        return polygonVertices;
    }
}
