package net.estemon.studio.brickbreaker.script;

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Ball;
import net.estemon.studio.util.entity.script.EntityScriptBase;

public class BallSpeedUpScript extends EntityScriptBase<Ball> {

    // attributes
    private float finalSpeed;

    // public methods
    @Override
    public void added(Ball entity) {
        super.added(entity);

        float currentSpeed = entity.getSpeed();
        finalSpeed = currentSpeed + GameConfig.BALL_START_SPEED * GameConfig.BALL_SPEED_FACTOR;

        if (finalSpeed >= GameConfig.BALL_MAX_SPEED) {
            finalSpeed = GameConfig.BALL_MAX_SPEED;
        }
    }

    @Override
    public void update(float delta) {
        float angleDeg = entity.getAngleDeg();
        entity.setVelocity(angleDeg, finalSpeed);
        finish();
    }
}
