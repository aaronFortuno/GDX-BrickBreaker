package net.estemon.studio.brickbreaker.script;

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Paddle;
import net.estemon.studio.util.entity.script.EntityScriptBase;

public class PaddleExpandScript extends EntityScriptBase<Paddle> {

    // attributes
    private float finalWidth;
    private boolean shouldFinish;

    // public methods
    @Override
    public void added(Paddle entity) {
        super.added(entity);

        float currentWidth = entity.getWidth();
        finalWidth = currentWidth + GameConfig.PADDLE_START_WIDTH * GameConfig.PADDLE_RESIZE_FACTOR;

        if (finalWidth >= GameConfig.PADDLE_MAX_WIDTH) {
            finalWidth = GameConfig.PADDLE_MAX_WIDTH;
        }
    }

    @Override
    public void update(float delta) {
        if (isFinished()) {
            return;
        }

        float currentWidth = entity.getWidth();
        float newWidth = currentWidth + currentWidth * delta * 2f;

        if (newWidth >= finalWidth) {
            newWidth = finalWidth;
            shouldFinish = true;
        }

        entity.setWidth(newWidth);

        if (shouldFinish) {
            finish();
        }
    }
}
