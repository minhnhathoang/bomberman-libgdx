package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import main.Bomberman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = 1000;
		config.height = 900;
		config.foregroundFPS = 60;

		config.addIcon("logo.png", Files.FileType.Internal);

		new LwjglApplication(new Bomberman(), config);
	}
}
