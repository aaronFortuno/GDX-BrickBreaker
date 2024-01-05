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
        // paddle logic
        paddleInputController.update(delta);
        paddle.update(delta);
        blockPaddleFromLeavingWorld();

        // ball logic
        ball.update(delta);
        blockBallFromLeavingWorld();
    }

    private void blockPaddleFromLeavingWorld() {
        // left
        if (paddle.getX() <= GameConfig.PADDLE_MIN_X) {
            paddle.setX(GameConfig.PADDLE_MIN_X);
        }

        // right
        if (paddle.getX() >= GameConfig.PADDLE_MAX_X) {
            paddle.setX(GameConfig.PADDLE_MAX_X);
        }
    }

    private void blockBallFromLeavingWorld() {
        // left <-> right
        if (ball.getX() <= 0) {
            ball.setX(0);
            ball.multiplyVelocityX(-1f);
        } else if (ball.getX() >= GameConfig.WORLD_WIDTH - GameConfig.BALL_SIZE) {
            ball.setX(GameConfig.WORLD_WIDTH - GameConfig.BALL_SIZE);
            ball.multiplyVelocityX(-1f);
        }

        // top <-> bottom
        if (ball.getY() <= 0) {
            ball.setY(0);
            ball.multiplyVelocityY(-1f);
        } else if (ball.getY() >= GameConfig.WORLD_HEIGHT - GameConfig.BALL_SIZE) {
            ball.setY(GameConfig.WORLD_HEIGHT - GameConfig.BALL_SIZE);
            ball.multiplyVelocityY(-1f);
        }
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
