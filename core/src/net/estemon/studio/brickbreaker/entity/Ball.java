package net.estemon.studio.brickbreaker.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import net.estemon.studio.util.entity.EntityBase;
import net.estemon.studio.util.shape.ShapeUtils;

public class Ball extends EntityBase {

    // constructor
    public Ball() {
    }

    // protected methods
    @Override
    protected float[] createVertices() {
        return ShapeUtils.createOctagon(
                width / 2f,
                height / 2f,
                width / 2f
        );
    }
}