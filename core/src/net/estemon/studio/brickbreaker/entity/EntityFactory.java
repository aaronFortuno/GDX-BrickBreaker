package net.estemon.studio.brickbreaker.entity;

import net.estemon.studio.brickbreaker.config.GameConfig;

public class EntityFactory {

    public Paddle createPaddle() {
        Paddle paddle = new Paddle();
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y);
        paddle.setSize(GameConfig.PADDLE_WIDTH, GameConfig.PADDLE_HEIGHT);
        return paddle;
    }
}
