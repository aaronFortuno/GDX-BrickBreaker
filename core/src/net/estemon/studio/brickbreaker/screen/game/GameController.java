package net.estemon.studio.brickbreaker.screen.game;

import com.badlogic.gdx.utils.Array;

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Ball;
import net.estemon.studio.brickbreaker.entity.Brick;
import net.estemon.studio.brickbreaker.entity.EntityFactory;
import net.estemon.studio.brickbreaker.entity.Paddle;
import net.estemon.studio.brickbreaker.input.PaddleInputController;

public class GameController {

    // attributes
    private EntityFactory factory;
    private Paddle paddle;
    private PaddleInputController paddleInputController;
    private Array<Brick> bricks = new Array<>();
    private Ball ball;

    // constructors
    public GameController() {
        init();
    }

    private void init() {
        factory = new EntityFactory();
        paddle = factory.createPaddle();
        paddleInputController = new PaddleInputController(paddle);

        bricks.addAll(factory.createBricks());

        ball = factory.createBall();
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

        ball.update(delta);
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Array<Brick> getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }
}
