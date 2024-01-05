package net.estemon.studio.brickbreaker.config;

public final class GameConfig {

    // desktop only
    public static final int WIDTH = 960; // px
    public static final int HEIGHT = 768; // px

    public static final float WORLD_WIDTH = 30f;
    public static final float WORLD_HEIGHT = 24f;

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f;

    public static final float PADDLE_WIDTH = 3f;
    public static final float PADDLE_HEIGHT = 1f;

    public static final float PADDLE_START_X = (WORLD_WIDTH - PADDLE_WIDTH) / 2f;
    public static final float PADDLE_START_Y = 1f;

    public static final float PADDLE_VELOCITY_X = 15f;

    public static final float PADDLE_PADDING = 0.3f;
    public static final float PADDLE_MIN_X = PADDLE_PADDING;
    public static final float PADDLE_MAX_X = WORLD_WIDTH - PADDLE_WIDTH - PADDLE_PADDING;

    public static final float BRICK_WIDTH = 2.125f;
    public static final float BRICK_HEIGHT = 1f;

    public static final float BRICK_LEFT_PAD = 0.5f;
    public static final float BRICK_TOP_PAD = 2.5f;
    public static final float BRICK_COLUMN_SPACING = 0.5f;
    public static final float BRICK_ROW_SPACING = 0.5f;
    public static final int COLUMN_COUNT = 12;
    public static final int ROW_COUNT = 6;


    private GameConfig() {} // not instantiable
}
