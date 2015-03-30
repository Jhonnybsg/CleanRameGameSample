package br.com.gamezes.abstracts;

import java.util.ArrayList;

import com.badlogic.gdx.InputProcessor;

public abstract class GameConfiguration {

	public static String imagePath = "sprites/";
	public static String particlesPath = "particles/";
	public static String particleImagesPath = particlesPath
			+ "/sprite_particles/";
	public static int currentScreen = 0;
	public static int nextScreen = 0;
	public static boolean doTransition = false;
	public static float alpha = 0.01f;
	public static ArrayList<InputProcessor> processors;
	public static float signal = 1;
	

}
