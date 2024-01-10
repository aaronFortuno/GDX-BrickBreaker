package net.estemon.studio.brickbreaker.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import net.estemon.studio.brickbreaker.assets.AssetDescriptors;
import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.util.Direction;
import net.estemon.studio.util.parallax.ParallaxLayer;

public class EntityFactory {

    // attributes
    private final AssetManager assetManager;
    private ParticleEffectPool fireEffectPool;
    private ParticleEffectPool starEffectPool;
    private Pool<Pickup> pickupPool;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
        init();
    }

    private void init() {
        ParticleEffect fireEffect = assetManager.get(AssetDescriptors.FIRE);
        ParticleEffect starEffect = assetManager.get(AssetDescriptors.STAR);
        fireEffectPool = new ParticleEffectPool(fireEffect, 5, 15);
        starEffectPool = new ParticleEffectPool(starEffect, 5, 10);
        pickupPool = Pools.get(Pickup.class, 10);
    }

    // public methods
    public ParallaxLayer createBackground() {
        ParallaxLayer background = new ParallaxLayer();
        background.setSize(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
        background.setDirection(Direction.DOWN);
        return background;
    }

    public Paddle createPaddle() {
        Paddle paddle = new Paddle();
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y);
        paddle.setSize(GameConfig.PADDLE_START_WIDTH, GameConfig.PADDLE_HEIGHT);
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
        ball.setVelocity(GameConfig.BALL_START_ANGLE, GameConfig.BALL_START_SPEED);

        return ball;
    }

    public Brick createBrick(float x, float y) {
        Brick brick = new Brick();
        brick.setPosition(x, y);
        brick.setSize(GameConfig.BRICK_WIDTH, GameConfig.BRICK_HEIGHT);

        return brick;
    }

    public ParticleEffectPool.PooledEffect createFire(float x, float y) {
        ParticleEffectPool.PooledEffect effect = fireEffectPool.obtain();
        effect.setPosition(x, y);
        effect.start();
        return effect;
    }

    public ParticleEffectPool.PooledEffect createStar(float x, float y) {
        ParticleEffectPool.PooledEffect effect = starEffectPool.obtain();
        effect.setPosition(x, y);
        effect.start();
        return effect;
    }

    public Pickup createPickup(float x, float y) {
        Pickup pickup = pickupPool.obtain();
        pickup.setType(PickupType.random());
        pickup.setSize(GameConfig.PICKUP_SIZE, GameConfig.PICKUP_SIZE);
        pickup.setPosition(x, y);
        pickup.setVelocityY(GameConfig.PICKUP_VELOCITY_Y);
        return pickup;
    }

    public void freePickup(Pickup pickup) {
        pickupPool.free(pickup);
    }

    public void freePickups(Array<Pickup> pickups) {
        pickupPool.freeAll(pickups);
    }

    // private methods

}
