package br.com.gamezes.model;

import br.com.gamezes.abstracts.GameConfiguration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ColideEffects {

	boolean effectInitialized = false;
	long timeInitEffect = 0;
	boolean drawEffect = false;

	ParticleEffect effect;

	public ColideEffects() {
		effect = new ParticleEffect();
		effect.load(
				Gdx.files.internal(GameConfiguration.particlesPath
						+ "StarEmittersNotContinuous"),
				Gdx.files.internal(GameConfiguration.particleImagesPath));

	}

	public void drawEffect(SpriteBatch batch) {

		effect.draw(batch, Gdx.graphics.getDeltaTime());
	}

	public void startEffect() {
		effect.start();
		// effect.allowCompletion();
		effect.update(3000);
	}

	public void setPositionEffect(float x, float y, float width, float height) {
		effect.setPosition(x + width / 2, y + height);
	}

	// GETTERS AND SETTERS

	public boolean isEffectInitialized() {
		return effectInitialized;
	}

	public void setEffectInitialized(boolean effectInitialized) {
		this.effectInitialized = effectInitialized;
	}

	public long getTimeInitEffect() {
		return timeInitEffect;
	}

	public void setTimeInitEffect(long timeInitEffect) {
		this.timeInitEffect = timeInitEffect;
	}

	public boolean isDrawEffect() {
		return drawEffect;
	}

	public void setDrawEffect(boolean drawEffect) {
		this.drawEffect = drawEffect;
		effect.update(0);
	}

}
