package net.estemon.studio.brickbreaker.entity;

import com.badlogic.gdx.math.Vector2;

import net.estemon.studio.util.entity.EntityBase;

public class Paddle extends EntityBase {

    // attributes
    private Vector2 velocity = new Vector2();

    // constructors

    public Paddle() {

    }

    // public methods
    public void update(float delta) {
        setX(x + velocity.x * delta);
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public void setVelocityX(float velocityX) {
        velocity.x = velocityX;
    }
}
