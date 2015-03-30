package br.com.gamezes.presenter;

import java.util.ArrayList;

import br.com.gamezes.abstracts.GameConfiguration;
import br.com.gamezes.model.SpriteModel;
import br.com.gamezes.view.PlayerSelectScreenView;

import com.badlogic.gdx.Gdx;

public class PlayerSelectScreenPresenter {

	private PlayerSelectScreenView view;

	public PlayerSelectScreenPresenter(PlayerSelectScreenView view) {
		this.view = view;

	}

	// public void sum(int currentScreen) {
	// GameConfiguration.nextScreen = 1;
	// GameConfiguration.doTransition = false;
	// }

	public SpriteModel verifyTouch(ArrayList<SpriteModel> sprites, int x,
			int y) {

		for (SpriteModel sprite : sprites) {
			if ((sprite.getX() < x)
					&& ((sprite.getX() + sprite.getWidth()) > x)
					&& (sprite.getY() < Gdx.graphics.getHeight() - y)
					&& ((sprite.getY() + sprite.getHeight()) > ((Gdx.graphics
							.getHeight() - y)))) {
				return sprite;
			}
		}
		return null;
	}
	
	public void clearSelection(ArrayList<SpriteModel> sprites, String image){
		for (SpriteModel sprite : sprites) {
			sprite.setTexture(GameConfiguration.imagePath
					+ "players_select_screen/" + image);
		}
	}

}
