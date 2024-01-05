package net.estemon.studio.brickbreaker;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import net.estemon.studio.brickbreaker.BrickBreakerGame;
import net.estemon.studio.brickbreaker.config.GameConfig;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("BrickBreaker");
		config.setWindowedMode(GameConfig.WIDTH, GameConfig.HEIGHT);
		new Lwjgl3Application(new BrickBreakerGame(), config);
	}
}
