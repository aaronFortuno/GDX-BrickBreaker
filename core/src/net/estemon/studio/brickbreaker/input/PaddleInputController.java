package net.estemon.studio.brickbreaker.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Paddle;

public class PaddleInputController {

    // attributes
    private final Paddle paddle;

    // constructors
    public PaddleInputController(Paddle paddle) {
        this.paddle = paddle;
    }

    // public methods
    public void update(float delta) {
        float velocityX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocityX = -GameConfig.PADDLE_VELOCITY_X;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocityX = GameConfig.PADDLE_VELOCITY_X;
        }

        paddle.setVelocityX(velocityX);
    }
}
