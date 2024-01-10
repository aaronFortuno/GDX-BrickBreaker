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

import net.estemon.studio.brickbreaker.config.GameConfig;
import net.estemon.studio.brickbreaker.entity.Ball;
import net.estemon.studio.brickbreaker.entity.Brick;
import net.estemon.studio.brickbreaker.entity.Paddle;
import net.estemon.studio.brickbreaker.entity.Pickup;
import net.estemon.studio.brickbreaker.input.PaddleInputController;
import net.estemon.studio.brickbreaker.script.BallSlowDownScript;
import net.estemon.studio.brickbreaker.script.BallSpeedUpScript;
import net.estemon.studio.brickbreaker.script.PaddleExpandScript;
import net.estemon.studio.brickbreaker.script.PaddleShrinkScript;
import net.estemon.studio.util.shape.RectangleUtils;

public class GameController {

    // attributes
    private final GameWorld gameWorld;
    private final GameRenderer renderer;

    // constructors
    public GameController(GameWorld gameWorld, GameRenderer renderer) {
        this.gameWorld = gameWorld;
        this.renderer = renderer;
    }

    // public methods
    public void update(float delta) {
        // handle debug input
        handleDebugInput();

        if (gameWorld.isGameOver()) {
            return;
        }

        Ball ball = gameWorld.getBall();
        if (ball.isNotActive() && Gdx.input.justTouched()) {
            gameWorld.activateBall();
        }

        gameWorld.update(delta);
    }

    public Vector2 screenToWorld(Vector2 screenCoordinates) {
        return renderer.screenToWorld(screenCoordinates);
    }

    // private methods
    private void handleDebugInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) {
            gameWorld.toggleDrawGrid();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {
            gameWorld.toggleDrawDebug();
        }
    }
}
