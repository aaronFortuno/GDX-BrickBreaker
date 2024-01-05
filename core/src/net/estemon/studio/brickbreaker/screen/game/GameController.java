package net.estemon.studio.brickbreaker.screen.game;

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.EntityFactory;
import net.estemon.studio.brickbreaker.entity.Paddle;
import net.estemon.studio.brickbreaker.input.PaddleInputController;

public class GameController {

    // attributes
    private EntityFactory factory;
    private Paddle paddle;
    private PaddleInputController paddleInputController;

    // constructors
    public GameController() {
        init();
    }

    private void init() {
        factory = new EntityFactory();
        paddle = factory.createPaddle();
        paddleInputController = new PaddleInputController(paddle);
    }

    // public methods
    public void update(float delta) {
        paddleInputController.update(delta);
        paddle.update(delta);

        // block paddle from leaving the world
        if (paddle.getX() <= GameConfig.PADDLE_MIN_X) {
            paddle.setX(GameConfig.PADDLE_MIN_X);
        }

        if (paddle.getX() >= GameConfig.PADDLE_MAX_X) {
            paddle.setX(GameConfig.PADDLE_MAX_X);
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }
}
