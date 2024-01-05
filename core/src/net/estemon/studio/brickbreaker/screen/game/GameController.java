package net.estemon.studio.brickbreaker.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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

    private boolean drawGrid = true;

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
        // handle debug input
        handleDebugInput();

        // paddle logic
        paddleInputController.update(delta);
        paddle.update(delta);
        blockPaddleFromLeavingWorld();

        // ball logic
        ball.update(delta);
        blockBallFromLeavingWorld();

        checkCollisions();
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

    public boolean isDrawGrid() {
        return drawGrid;
    }

    // private methods
    private void handleDebugInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            drawGrid = !drawGrid;
        }
    }

    private void checkCollisions() {
        checkBallWithPaddleCollision();
        checkBallWithBrickCollision();
    }

    private void checkBallWithPaddleCollision() {
        Circle ballBounds = ball.getBounds();
        Rectangle paddleBounds = paddle.getBounds();

        if (Intersector.overlaps(ballBounds, paddleBounds)) {
            float ballCenterX = ball.getX() + GameConfig.BALL_RADIUS;
            float percent = (ballCenterX - paddle.getX()) / paddle.getWidth(); // 0-1

            // interpolate angle between 150 and 30
            float boundsAngle = 150 - percent * 120;
            ball.setVelocity(boundsAngle, ball.getSpeed());
        }
    }

    private void checkBallWithBrickCollision() {
        Circle ballBounds = ball.getBounds();
        for (int i = 0; i < bricks.size; i++) {
            Brick brick = bricks.get(i);
            Rectangle brickBounds = brick.getBounds();

            if (!Intersector.overlaps(ballBounds, brickBounds)) {
                continue;
            }


        }
    }
}
