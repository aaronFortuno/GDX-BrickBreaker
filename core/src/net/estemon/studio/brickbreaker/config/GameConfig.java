package net.estemon.studio.brickbreaker.config;

public final class GameConfig {

    // desktop only
    public static final int WIDTH = 1024; // px
    public static final int HEIGHT = 768; // px

    public static final float HUD_WIDTH = 1024f;
    public static final float HUD_HEIGHT = 768f;

    public static final float WORLD_WIDTH = 32f;
    public static final float WORLD_HEIGHT = 24f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f;

    public static final float PADDLE_START_WIDTH = 3f;
    public static final float PADDLE_MIN_WIDTH = 1.2f;
    public static final float PADDLE_MAX_WIDTH = 4.8f;
    public static final float PADDLE_RESIZE_FACTOR = 0.15f;
    public static final float PADDLE_HEIGHT = 1f;

    public static final float PADDLE_START_X = (WORLD_WIDTH - PADDLE_START_WIDTH) / 2f;
    public static final float PADDLE_START_Y = 1f;

    public static final float PADDLE_VELOCITY_X = 15f;

    public static final float PADDLE_PADDING = 0.3f;
    public static final float PADDLE_MIN_X = PADDLE_PADDING;
    public static final float PADDLE_MAX_X = WORLD_WIDTH - PADDLE_PADDING;
    public static final float PADDLE_EXPAND_SHRINK_SPEED = 10f;

    public static final float BRICK_WIDTH = 2.125f;
    public static final float BRICK_HEIGHT = 1f;

    public static final float BRICK_LEFT_PAD = 0.5f;
    public static final float BRICK_TOP_PAD = 2.5f;
    public static final float BRICK_COLUMN_SPACING = 0.5f;
    public static final float BRICK_ROW_SPACING = 0.5f;
    public static final int COLUMN_COUNT = 12;
    public static final int ROW_COUNT = 8;

    public static final float BALL_SIZE = 0.8f;
    public static final float BALL_RADIUS = BALL_SIZE / 2f;
    public static final float BALL_START_X = PADDLE_START_X + (PADDLE_START_WIDTH - BALL_SIZE) / 2f;
    public static final float BALL_START_Y = PADDLE_START_Y + PADDLE_HEIGHT;

    public static final float BALL_START_SPEED = 16f;
    public static final float BALL_MIN_SPEED = 10f;
    public static final float BALL_MAX_SPEED = 22f;
    public static final float BALL_SPEED_FACTOR = 0.3f;

    public static final float BALL_START_ANGLE = 60f;


    public static final int BRICK_SCORE = 10;

    public static final float PICKUP_SPAWN_TIME = 2f;
    public static final float PICKUP_VELOCITY_Y = -6f;
    public static final float PICKUP_SIZE = 1f;
    public static final float PICKUP_PROBABILITY = 0.3f;

    public static final int LIVES_START = 3;
    public static final float LIFE_HUD_WIDTH = 40f;
    public static final float LIFE_HUD_HEIGHT = 12f;
    public static final float LIFE_HUD_SPACING = 10f;

    public static final float HUD_PADDING = 20f;

    public static final float BACKGROUND_SPEED = 0.2f;

    private GameConfig() {} // not instantiable
}
