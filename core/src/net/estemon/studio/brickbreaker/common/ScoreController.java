package net.estemon.studio.brickbreaker.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import net.estemon.studio.brickbreaker.BrickBreakerGame;

public class ScoreController {

    // constants
    private static final String HIGH_SCORE_KEY = "highScore";

    // attributes
    private final Preferences PREFS;
    private int score;
    private int highScore;

    // constructors

    public ScoreController() {
        PREFS = Gdx.app.getPreferences(BrickBreakerGame.class.getSimpleName());
        highScore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
    }

    // public methods
    public void reset() {
        score = 0;
    }

    public void addScore(int toAdd) {
        score += toAdd;
    }

    public void updateHighScore() {
        if (score < highScore) {
            return;
        }

        highScore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highScore);
        PREFS.flush();
    }

    public String getScoreString() {
        return Integer.toString(score);
    }

    public String getHighScoreString() {
        return Integer.toString(highScore);
    }

    // private methods
}
