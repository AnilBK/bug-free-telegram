package com.anilbk.vectorlaw.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.anilbk.vectorlaw.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=640;
		config.height=480;
		config.resizable=false;
		config.title="Parm Law of Forces by G3";
		new LwjglApplication(new Main(), config);
	}
}
