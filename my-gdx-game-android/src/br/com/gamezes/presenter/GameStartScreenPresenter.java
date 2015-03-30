package br.com.gamezes.presenter;

import java.util.ArrayList;

import br.com.gamezes.abstracts.GameConfiguration;
import br.com.gamezes.model.SpriteModel;
import br.com.gamezes.view.GameStartScreenView;

import com.badlogic.gdx.Gdx;

public class GameStartScreenPresenter {

	private GameStartScreenView view;

	public GameStartScreenPresenter(GameStartScreenView view) {
		this.view = view;
	}

	public SpriteModel verifyTouch(ArrayList<SpriteModel> sprites, int x, int y) {

		for (SpriteModel sprite : sprites) {

			if (sprite.getVisibility()) {

				if ((sprite.getX() < x)
						&& ((sprite.getX() + sprite.getWidth()) > x)
						&& (sprite.getY() < Gdx.graphics.getHeight() - y)
						&& ((sprite.getY() + sprite.getHeight()) > ((Gdx.graphics
								.getHeight() - y)))) {
					return sprite;
				}
			} else {
				continue;
			}
		}
		return null;
	}

	public void clearSelection(ArrayList<SpriteModel> sprites, String image) {
		for (SpriteModel sprite : sprites) {
			sprite.setTexture(GameConfiguration.imagePath
					+ "players_select_screen/" + image);
		}
	}

	public void virifyMove(int x) {

		if (x < Gdx.graphics.getWidth() / 2) {
			this.view.moveToLeft();
		} else if (x > Gdx.graphics.getWidth() / 2) {
			this.view.moveToRight();
		}
	}

}
