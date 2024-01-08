package net.estemon.studio.brickbreaker.entity;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

import net.estemon.studio.brickbreaker.assets.AssetDescriptors;
import net.estemon.studio.brickbreaker.config.GameConfig;

public class EntityFactory {

    // attributes
    private final AssetManager assetManager;
    private ParticleEffectPool fireEffectPool;
    private Pool<Pickup> pickupPool;

    public EntityFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
        init();
    }

    private void init() {
        ParticleEffect effect = assetManager.get(AssetDescriptors.FIRE);
        fireEffectPool = new ParticleEffectPool(effect, 5, 20);

        pickupPool = Pools.get(Pickup.class, 10);
    }

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
