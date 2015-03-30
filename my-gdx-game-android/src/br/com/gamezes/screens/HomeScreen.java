package br.com.gamezes.screens;

import br.com.gamezes.abstracts.GameConfiguration;
import br.com.gamezes.model.SpriteModel;
import br.com.gamezes.view.HomeScreenView;
import br.com.gamezes.view.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * 
 * @author Jhonny Silva:
 * 
 *         Possui os elementos da tela home do game Documentação para Poolling
 *         events: https://github.com/libgdx/libgdx/wiki/Polling Documentação
 *         para InputProcessor:
 *         https://github.com/libgdx/libgdx/wiki/Event-handling
 */
public class HomeScreen extends GameConfiguration implements Screen,
		HomeScreenView, InputProcessor {

	private SpriteBatch batch;

	private SpriteModel sBackground;
	private SpriteModel sBackElement;
	private SpriteModel sBackLixo;
	private SpriteModel sSubtitle;
	private int signal = -1;

	private SpriteModel sTitle;
	private float translationY = 0.0f;

	private ParticleEffect particle;

	public HomeScreen(SpriteBatch batch, String path) {
		this.batch = batch;
		GameConfiguration.processors.add(this);

	}

	@Override
	public void loadParticles() {
		particle = new ParticleEffect();

		particle.load(Gdx.files.internal(particlesPath + "turbina"),
				Gdx.files.internal(particleImagesPath));

		this.particle.setPosition(500, 1000);
		this.particle.start();
		this.particle.setDuration(3000);

	}

	@Override
	public void loadImages() {

		sBackground = new SpriteModel(GameConfiguration.imagePath
				+ "home_screen/bg_space.png");
		sBackground.setPosition(
				(Gdx.graphics.getWidth() / 2) - sBackground.getWidth() / 2,
				Gdx.graphics.getHeight() - sBackground.getHeight() + 250);

		sBackElement = new SpriteModel(GameConfiguration.imagePath
				+ "home_screen/bg_nave.png");
		sBackElement.setPosition(
				(Gdx.graphics.getWidth() / 2) - (sBackElement.getWidth() / 2),
				0);

		sTitle = new SpriteModel(GameConfiguration.imagePath
				+ "home_screen/marca.png");
		HomeScreen.this.translationY = -sTitle.getHeight();
		sTitle.setColor(1, 1, 1, 0);
		sTitle.setPosition((Gdx.graphics.getWidth() / 2)
				- (sTitle.getWidth() / 2), translationY);

		sBackLixo = new SpriteModel(GameConfiguration.imagePath
				+ "home_screen/lixo1.png");
		sBackLixo.setPosition(
				(Gdx.graphics.getWidth() / 2) - (sBackLixo.getWidth() / 2),
				0.0f);

		sSubtitle = new SpriteModel(GameConfiguration.imagePath
				+ "home_screen/subtitle.png");
		sSubtitle
				.setPosition(
						(Gdx.graphics.getWidth() / 2)
								- (sSubtitle.getWidth() / 2), 200);
		sSubtitle.setColor(1, 1, 1, 1);

	}

	@Override
	public void drawElements() {

		HomeScreen.this.sBackground.draw(this.batch);
		HomeScreen.this.sBackElement.draw(this.batch);
		HomeScreen.this.sBackLixo.draw(this.batch);
		HomeScreen.this.subtitleAnimation();
		HomeScreen.this.titleAnimation();

		// this.particle.start();
		// this.particle.draw(batch, Gdx.graphics.getDeltaTime());

	}

	public void subtitleAnimation() {
		float a = HomeScreen.this.sSubtitle.getSpriteAlpha();

		if (a >= 0.9f) {
			signal = -1;
		}
		if (a <= 0.5f) {
			signal = 1;
		}

		a += 0.02f * signal;

		HomeScreen.this.sSubtitle.setColor(1, 1, 1, a);
		HomeScreen.this.sSubtitle.draw(this.batch);

	}

	public void titleAnimation() {

		if (translationY < 150.0f) {

			translationY += 20.0f;

			HomeScreen.this.sTitle
					.setPosition(this.sTitle.getX(), translationY);
		}

		float a = HomeScreen.this.sTitle.getSpriteAlpha();

		if (a < 0.9f) {
			a += 0.02f;
			HomeScreen.this.sTitle.setColor(1, 1, 1, a);
		}
		HomeScreen.this.sTitle.draw(this.batch);

	}

	@Override
	public void loadSounds() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disposeAllResources() {
		this.batch.dispose();
	}

	@Override
	public void drawParticles() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {

		GameConfiguration.nextScreen = 1;
		GameConfiguration.doTransition = true;
		Gdx.input.setInputProcessor(GameConfiguration.processors.get(1));

		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// this.particle.setPosition(x, Gdx.graphics.getHeight() - y);
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {
			GameConfiguration.doTransition = true;
			GameConfiguration.nextScreen = -1;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
