package net.estemon.studio.brickbreaker.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import net.estemon.studio.brickbreaker.common.ScoreController;
import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Ball;
import net.estemon.studio.brickbreaker.entity.Brick;
import net.estemon.studio.brickbreaker.entity.EntityFactory;
import net.estemon.studio.brickbreaker.entity.Paddle;
import net.estemon.studio.brickbreaker.entity.Pickup;
import net.estemon.studio.brickbreaker.input.PaddleInputController;
import net.estemon.studio.brickbreaker.script.BallSlowDownScript;
import net.estemon.studio.brickbreaker.script.BallSpeedUpScript;
import net.estemon.studio.brickbreaker.script.PaddleExpandScript;
import net.estemon.studio.brickbreaker.script.PaddleShrinkScript;
import net.estemon.studio.util.shape.RectangleUtils;

public class GameWorld {

    // attributes
    private final ScoreController scoreController;

    private final EntityFactory factory;
    private Paddle paddle;
    private Array<Brick> bricks = new Array<>();
    private Ball ball;

    private boolean drawGrid = true;
    private boolean drawDebug = true;

    private Array<ParticleEffectPool.PooledEffect> effects = new Array<>();
    private Array<Pickup> pickups = new Array<>();


    // constructors
    public GameWorld(ScoreController scoreController, EntityFactory factory) {
        this.scoreController = scoreController;
        this.factory = factory;
        init();
    }

    private void init() {
        paddle = factory.createPaddle();

        ball = factory.createBall();

        startLevel();
    }

    // public methods
    public void update(float delta) {
        if (ball.isNotActive()) {
            return;
        }

        // paddle logic
        paddle.update(delta);
        blockPaddleFromLeavingWorld();

        // ball logic
        ball.update(delta);
        blockBallFromLeavingWorld();

        updatePickups(delta);
        updateEffects(delta);
        checkCollisions();

        // check endgame
        if (bricks.isEmpty()) {
            startLevel();
        }
    }

    public void activateBall() {
        ball.setVelocity(GameConfig.BALL_START_ANGLE, GameConfig.BALL_START_SPEED);
    }

    public void toggleDrawGrid() {
        drawGrid = !drawGrid;
    }

    public void toggleDrawDebug() {
        drawDebug = !drawDebug;
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

    public Array<Pickup> getPickups() {
        return pickups;
    }

    public String getScoreString() {
        return scoreController.getScoreString();
    }

    public Array<ParticleEffectPool.PooledEffect> getEffects() {
        return effects;
    }

    public boolean isDrawGrid() {
        return drawGrid;
    }

    public boolean isDrawDebug() {
        return drawDebug;
    }

    // private methods
    private void blockPaddleFromLeavingWorld() {
        // left
        if (paddle.getX() <= GameConfig.PADDLE_MIN_X) {
            paddle.setX(GameConfig.PADDLE_MIN_X);
        }

        // right
        if (paddle.getX() >= GameConfig.PADDLE_MAX_X - paddle.getWidth()) {
            paddle.setX(GameConfig.PADDLE_MAX_X - paddle.getWidth());
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

    private void checkCollisions() {
        checkBallWithPaddleCollision();
        checkBallWithBrickCollision();
        checkPaddleWithPickupCollision();
    }

    private void checkBallWithPaddleCollision() {
        Polygon ballBounds = ball.getBounds();
        Polygon paddleBounds = paddle.getBounds();

        if (Intersector.overlapConvexPolygons(ballBounds, paddleBounds)) {
            float ballCenterX = ball.getX() + GameConfig.BALL_RADIUS;
            float percent = (ballCenterX - paddle.getX()) / paddle.getWidth(); // 0-1

            // interpolate angle between 150 and 30
            float boundsAngle = 150 - percent * 120;
            ball.setVelocity(boundsAngle, ball.getSpeed());
        }
    }

    private void checkBallWithBrickCollision() {
        Polygon ballPolygon = ball.getBounds();

        float ballRadius = ball.getWidth() / 2f;
        Circle ballBounds = new Circle(
                ball.getX() + ballRadius,
                ball.getY() + ballRadius,
                ballRadius
        );

        for (int i = 0; i < bricks.size; i++) {
            Brick brick = bricks.get(i);
            Polygon brickPolygon = brick.getBounds();

            Rectangle brickBounds = brickPolygon.getBoundingRectangle();

            if (!Intersector.overlapConvexPolygons(ballPolygon, brickPolygon)) {
                continue;
            }

            Vector2 topLeft = RectangleUtils.getTopLeft(brickBounds);
            Vector2 topRight = RectangleUtils.getTopRight(brickBounds);
            Vector2 bottomLeft = RectangleUtils.getBottomLeft(brickBounds);
            Vector2 bottomRight = RectangleUtils.getBottomRight(brickBounds);

            Vector2 center = new Vector2(ballBounds.x, ballBounds.y);
            float squareRadius = ballBounds.radius * ballBounds.radius;

            boolean topHit = Intersector.intersectSegmentCircle(topLeft, topRight, center, squareRadius);
            boolean bottomHit = Intersector.intersectSegmentCircle(bottomLeft, bottomRight, center, squareRadius);
            boolean leftHit = Intersector.intersectSegmentCircle(bottomLeft, topLeft, center, squareRadius);
            boolean rightHit = Intersector.intersectSegmentCircle(bottomRight, topRight, center, squareRadius);

            if ((ball.getVelocity().y < 0 && topHit) || (ball.getVelocity().y > 0 && bottomHit)) {
                ball.multiplyVelocityY(-1f);
            } else if ((ball.getVelocity().x > 0 && leftHit) || (ball.getVelocity().x < 0 && rightHit)) {
                ball.multiplyVelocityX(-1f);
            }

            // create fire effect
            float effectX = brick.getX() + brick.getWidth() / 2f;
            float y = brick.getY() + brick.getHeight() / 2f;
            spawnFireEffect(effectX, y);

            // spawn pickups
            if (MathUtils.random() < GameConfig.PICKUP_PROBABILITY) {
                float pickupX = brick.getX() + (brick.getWidth() - GameConfig.PICKUP_SIZE) / 2f;
                spawnPickup(pickupX, y);
            }

            bricks.removeIndex(i);

            // score control
            scoreController.addScore(GameConfig.BRICK_SCORE);
            System.out.println("[SCORE] " + scoreController.getScoreString());
        }
    }

    private void checkPaddleWithPickupCollision() {
        Polygon paddleBounds = paddle.getBounds();

        for (int i = 0; i < pickups.size; i++) {
            Pickup pickup = pickups.get(i);
            Polygon pickupBounds = pickup.getBounds();
            if (Intersector.overlapConvexPolygons(paddleBounds, pickupBounds)) {
                float x = pickup.getX() + pickup.getWidth() / 2f;
                float y = pickup.getY();

                addScript(pickup);
                spawnStarEffect(x, y);
                pickups.removeIndex(i);
                factory.freePickup(pickup);
            }
        }
    }

    private void updateEffects(float delta) {
        for (int i = 0; i < effects.size; i++) {
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.update(delta);

            if (effect.isComplete()) {
                effects.removeIndex(i);
                effect.free();
            }
        }
    }

    private void updatePickups(float delta) {
        for (int i = 0; i < pickups.size; i++) {
            Pickup pickup = pickups.get(i);
            pickup.update(delta);

            if (pickup.getY() < -pickup.getHeight()) {
                factory.freePickup(pickup);
                pickups.removeIndex(i);
            }
        }
    }

    private void spawnFireEffect(float effectX, float effectY) {
        ParticleEffectPool.PooledEffect effect = factory.createFire(effectX, effectY);
        effects.add(effect);
    }

    private void spawnStarEffect(float x, float y) {
        ParticleEffectPool.PooledEffect effect = factory.createStar(x, y);
        effects.add(effect);
    }

    private void spawnPickup(float x, float y) {
        Pickup pickup = factory.createPickup(x, y);
        pickups.add(pickup);
    }

    private void startLevel() {
        bricks.addAll(factory.createBricks());
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y);
        ball.setPosition(GameConfig.BALL_START_X, GameConfig.BALL_START_Y);
        ball.stop();
    }

    private void addScript(Pickup pickup) {
        if (pickup.isExpand()) {
            paddle.addScript(new PaddleExpandScript());
        } else if (pickup.isShrink()) {
            paddle.addScript(new PaddleShrinkScript());
        } else if (pickup.isSlowDown()) {
            ball.addScript(new BallSlowDownScript());
        } else if (pickup.isSpeedUp()) {
            ball.addScript(new BallSpeedUpScript());
        }
    }
}
