package net.estemon.studio.brickbreaker;


import net.estemon.studio.brickbreaker.common.ScoreController;
import net.estemon.studio.brickbreaker.screen.loading.LoadingScreen;
import net.estemon.studio.util.game.GameBase;


public class BrickBreakerGame extends GameBase {

	// attributes
	private ScoreController scoreController;

	public BrickBreakerGame() {
		super();
	}

	// public methods
	@Override
	public void postCreate() {
		scoreController = new ScoreController();
		setScreen(new LoadingScreen(this));
	}

	public ScoreController getScoreController() {
		return scoreController;
	}
}