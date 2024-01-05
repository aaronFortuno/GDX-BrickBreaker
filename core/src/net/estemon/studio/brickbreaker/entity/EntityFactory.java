package net.estemon.studio.brickbreaker.entity;

import com.badlogic.gdx.utils.Array;
import net.estemon.studio.brickbreaker.config.GameConfig;

public class EntityFactory {

    // public methods
    public Paddle createPaddle() {
        Paddle paddle = new Paddle();
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y);
        paddle.setSize(GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_HEIGHT);
        return paddle;
    }

    public Array<Brick> createBricks() {
        Array<Brick> bricks = new Array<>();

        float startX = GameConfig.BRICK_LEFT_PAD;
        float startY = GameConfig.WORLD_HEIGHT - GameConfig.BRICK_TOP_PAD - GameConfig.BRICK_HEIGHT;

        for (int row = 0; row < GameConfig.ROW_COUNT; row++) {
            float brickY = startY - row * (GameConfig.BRICK_ROW_SPACING + GameConfig.BRICK_HEIGHT);

            for (int col = 0; col < GameConfig.COLUMN_COUNT; col++) {
                float brickX = startX + col * (GameConfig.BRICK_COLUMN_SPACING + GameConfig.BRICK_WIDTH);

                bricks.add(createBrick(brickX, brickY));
            }
        }

        return bricks;
    }

    public Ball createBall() {
        Ball ball = new Ball();
        ball.setPosition(GameConfig.BALL_START_X, GameConfig.BALL_START_Y);
        ball.setSize(GameConfig.BALL_SIZE);
        ball.setVelocity(GameConfig.BALL_START_ANGLE, GameConfig.BALL_VELOCITY);

        return ball;
    }

    // private methods
    private Brick createBrick(float x, float y) {
        Brick brick = new Brick();
        brick.setPosition(x, y);
        brick.setSize(GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);

        return brick;
    }
}
