package net.estemon.studio.brickbreaker.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Paddle;
import net.estemon.studio.brickbreaker.screen.game.GameController;

public class PaddleInputController {

    // attributes
    private final Paddle paddle;
    private final GameController controller;

    // constructors
    public PaddleInputController(Paddle paddle, GameController controller) {
        this.paddle = paddle;
        this.controller = controller;
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
