package com.holyshatots.dogedodge.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.holyshatots.dogedodge.controller.DogeDodge;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Doge Dodge";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new DogeDodge(), config);
	}
}
