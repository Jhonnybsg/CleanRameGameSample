package br.com.gamezes.screens;

import java.util.ArrayList;

import br.com.gamezes.abstracts.GameConfiguration;
import br.com.gamezes.model.SpriteModel;
import br.com.gamezes.presenter.PlayerSelectScreenPresenter;
import br.com.gamezes.view.PlayerSelectScreenView;
import br.com.gamezes.view.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerSelectScreen extends GameConfiguration implements Screen,
		PlayerSelectScreenView, InputProcessor {

	private PlayerSelectScreenPresenter presenter;

	private SpriteModel sBackground;
	private SpriteModel sBackElement;
	private SpriteModel sBaseBottom;

	private SpriteModel sPlayer1;
	private SpriteModel sPlayer2;

	private SpriteModel sStart;
	private SpriteModel sExit;

	private SpriteModel sBaseSelect;

	private SpriteBatch batch;

	private ArrayList<SpriteModel> sPlayers;
	private ArrayList<SpriteModel> sButtons;

	public PlayerSelectScreen(SpriteBatch batch) {
		this.batch = batch;
		this.presenter = new PlayerSelectScreenPresenter(this);
		this.sPlayers = new ArrayList<SpriteModel>();
		this.sButtons = new ArrayList<SpriteModel>();
		GameConfiguration.processors.add(this);
	}

	@Override
	public void loadImages() {

		// Elementos no background
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

		// Elementos para seleção do jogador
		sPlayer1 = new SpriteModel(imagePath
				+ "players_select_screen/red_person.png");
		sPlayer1.setPosition(Gdx.graphics.getWidth() / 2 - sPlayer1.getWidth()
				- 40, Gdx.graphics.getHeight() / 2 - sPlayer1.getHeight() / 2);
		sPlayer1.setTag(1);

		sPlayer2 = new SpriteModel(imagePath
				+ "players_select_screen/green.png");
		sPlayer2.setPosition(sPlayer1.getX() + sPlayer1.getWidth() + 40,
				sPlayer1.getY());
		sPlayer2.setTag(2);

		sBaseSelect = new SpriteModel(GameConfiguration.imagePath
				+ "players_select_screen/base_select_player_button.png");
		sBaseSelect.setPosition(sPlayer1.getX() + (sPlayer1.getWidth() / 2)
				- sBaseSelect.getWidth() / 2, Gdx.graphics.getHeight() / 2
				- sBaseSelect.getHeight() / 2);
		sBaseSelect.setTag(1);

		sStart = new SpriteModel(imagePath
				+ "players_select_screen/start_unpressed.png");
		sStart.setPosition(
				(Gdx.graphics.getWidth() / 2 - sStart.getWidth() / 2), 100);

		sExit = new SpriteModel(imagePath + "players_select_screen/start.png");
		sExit.setPosition((Gdx.graphics.getWidth() / 2) + 20, 100);

		sBaseBottom = new SpriteModel(GameConfiguration.imagePath
				+ "players_select_screen/base_select_player.png");
		sBaseBottom.setPosition(
				(Gdx.graphics.getWidth() / 2) - (sBaseBottom.getWidth() / 2),
				300);

		sButtons.add(sStart);
		// sButtons.add(sExit);

		sPlayers.add(sPlayer1);
		sPlayers.add(sPlayer2);
	}

	@Override
	public void drawElements() {

		PlayerSelectScreen.this.sBackground.draw(this.batch);
		PlayerSelectScreen.this.sBackElement.draw(this.batch);

		PlayerSelectScreen.this.sBaseBottom.draw(batch);
		PlayerSelectScreen.this.sBaseSelect.draw(batch);
		PlayerSelectScreen.this.sPlayer1.draw(batch);
		PlayerSelectScreen.this.sPlayer2.draw(batch);

		PlayerSelectScreen.this.sStart.draw(batch);
	}

	@Override
	public void loadSounds() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disposeAllResources() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadParticles() {
		// TODO Auto-generated method stub

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
		SpriteModel spriteTouched = PlayerSelectScreen.this.presenter
				.verifyTouch(sPlayers, x, y);
		if (spriteTouched != null) {

			PlayerSelectScreen.this.sBaseSelect.setPosition(
					spriteTouched.getX() + (spriteTouched.getWidth() / 2)
							- sBaseSelect.getWidth() / 2, sBaseSelect.getY());
			PlayerSelectScreen.this.sBaseSelect.setTag(spriteTouched.getTag());

			// PlayerSelectScreen.this.presenter.clearSelection(sPlayers,
			// "player.png");

			// spriteTouched.setTexture(imagePath
			// + "players_select_screen/player_sel.png");

		}

		SpriteModel spriteButton = PlayerSelectScreen.this.presenter
				.verifyTouch(sButtons, x, y);

		if (spriteButton != null) {

			// PlayerSelectScreen.this.presenter.clearSelection(sButtons,
			// "start.png");
			//
			// spriteButton.setTexture(imagePath
			// + "players_select_screen/start_sel.png");

			GameConfiguration.nextScreen = 2;
			GameConfiguration.doTransition = true;
			Gdx.input.setInputProcessor(GameConfiguration.processors.get(2));

		}

		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		// PlayerSelectScreen.this.touchXoffset = (int) (x -
		// PlayerSelectScreen.this.touchStartX);
		// sPlayer1.setPosition(sPlayer1.getX() + this.touchXoffset,
		// sPlayer1.getY());
		// sPlayer2.setPosition(sPlayer2.getX() + this.touchXoffset,
		// sPlayer2.getY());
		// PlayerSelectScreen.this.touchStartX = x;
		return false;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {
			GameConfiguration.doTransition = true;
			GameConfiguration.nextScreen = 0;
			Gdx.input.setInputProcessor(GameConfiguration.processors.get(0));
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

	public int getSelectedCar() {
		return this.sBaseSelect.getTag();
	}

}
