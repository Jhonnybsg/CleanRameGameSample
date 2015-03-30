package br.com.gamezes.activity;

import java.util.ArrayList;

import br.com.gamezes.abstracts.GameConfiguration;
import br.com.gamezes.screens.GameStartScreen;
import br.com.gamezes.screens.HomeScreen;
import br.com.gamezes.screens.PlayerSelectScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameCicle implements ApplicationListener {

	private SpriteBatch batch;

	private HomeScreen homeScreen;
	private PlayerSelectScreen playerSelectScreen;
	private GameStartScreen gameStartScreen;

	private OrthographicCamera camera;

	private ShapeRenderer renderer;
	private MainActivity activity;

	private InputProcessor ipAux;

	public GameCicle(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	public void create() {

		Gdx.input.setCatchBackKey(true);

		GameConfiguration.alpha = 0.01f;
		GameConfiguration.signal = 1;
		GameConfiguration.currentScreen = 0;
		GameConfiguration.nextScreen = 0;
		GameConfiguration.doTransition = false;
		GameConfiguration.processors = new ArrayList<InputProcessor>();

		Texture.setEnforcePotImages(false);

		batch = new SpriteBatch();

		homeScreen = new HomeScreen(batch, GameConfiguration.imagePath);
		homeScreen.loadImages();

		playerSelectScreen = new PlayerSelectScreen(batch);
		playerSelectScreen.loadImages();

		gameStartScreen = new GameStartScreen(batch);
		gameStartScreen.loadImages();
		gameStartScreen.loadParticles();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());

		renderer = new ShapeRenderer();

		Gdx.input.setInputProcessor(GameConfiguration.processors.get(0));

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 1, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		switch (GameConfiguration.currentScreen) {
		case -1:
			break;
		case 0:
			homeScreen.drawElements();
			break;
		case 1:
			playerSelectScreen.drawElements();
			break;
		case 2:
			gameStartScreen.drawElements();
			break;
		default:
			break;
		}

		batch.end();

		GameCicle.this.drawTransition();

	}

	public void drawTransition() {
		if (GameConfiguration.doTransition && GameConfiguration.alpha > 0.0f) {

			if (GameConfiguration.nextScreen != -1) {

				if (ipAux == null) {
					this.ipAux = Gdx.input.getInputProcessor();
				}

				Gdx.input.setInputProcessor(null);

				Gdx.gl.glEnable(GL10.GL_BLEND);
				Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA,
						GL10.GL_ONE_MINUS_SRC_ALPHA);

				renderer.begin(ShapeType.FilledRectangle);
				renderer.setColor(0.0f, 0.0f, 0.0f, GameConfiguration.alpha);
				renderer.filledRect(0, 0, Gdx.graphics.getWidth(),
						Gdx.graphics.getHeight());
				renderer.end();
				Gdx.gl.glDisable(GL10.GL_BLEND);
				GameConfiguration.alpha += 0.04f * GameConfiguration.signal;

				if (GameConfiguration.alpha >= 1.0f) {
					GameConfiguration.signal = -1;
					GameConfiguration.currentScreen = GameConfiguration.nextScreen;
				}
				if (GameConfiguration.alpha <= 0.0f) {
					GameConfiguration.doTransition = false;
					GameConfiguration.alpha = 0.01f;
					GameConfiguration.signal = 1;
					Gdx.input.setInputProcessor(ipAux);
					ipAux = null;
				}
			} else {
				if (this.activity.onConfirm == false) {
					this.activity.confirmExit();
					this.activity.onConfirm = true;
				}
			}
			if (GameConfiguration.nextScreen == 2) {
				gameStartScreen.setSelectedCar(playerSelectScreen
						.getSelectedCar());
				gameStartScreen.initializeValues();
				gameStartScreen.configureCombo5();
				gameStartScreen.configureCombo3();
				gameStartScreen.restartObstaclePosition();
				gameStartScreen.restartComboCount();
				// gameStartScreen.setCarPosition();

			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
