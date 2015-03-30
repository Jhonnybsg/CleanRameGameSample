package br.com.gamezes.screens;

import java.util.ArrayList;
import java.util.Random;

import android.util.Log;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import br.com.gamezes.abstracts.GameConfiguration;
import br.com.gamezes.listeners.ObjectTween;
import br.com.gamezes.listeners.SpriteTween;
import br.com.gamezes.model.ColideEffects;
import br.com.gamezes.model.SpriteModel;
import br.com.gamezes.presenter.GameStartScreenPresenter;
import br.com.gamezes.view.GameStartScreenView;
import br.com.gamezes.view.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameStartScreen implements Screen, GameStartScreenView,
		InputProcessor {

	private SpriteModel sTurbo;
	private float scaleXTurbo = 0.0f;
	private SpriteModel sFuel;
	private SpriteModel sTryAgain;
	private float distance = 0;

	private SpriteModel sObstacle;
	private Rectangle recObstacle;
	private boolean gameOver = false;

	private SpriteModel sDialogPause;
	private SpriteModel sContinue;
	private SpriteModel sBack;
	private ArrayList<SpriteModel> sStreet;

	private SpriteModel sCar;

	private ArrayList<SpriteModel> sButtons;
	private SpriteModel sBackPause;

	private GameStartScreenPresenter presenter;

	private boolean backPressed = false;
	private float scaleYFuel = 1.0f;

	private int xOffset = 0;

	private SpriteBatch batch;
	private ObjectTween object;

	private TweenManager tweenManager;

	private int numController = 1;

	private ParticleEffect particle;

	private Timeline timeline;
	private float timeTransition = 0.1f;

	private Array<Rectangle> objectsCombo3;
	private Array<Rectangle> objectsCombo5;
	private Array<Rectangle> objectsCombo10;

	private Rectangle recCar;

	private Array<SpriteModel> trashsGreen;
	private Array<SpriteModel> trashsRed;

	private ArrayList<Float> arrayPositons;

	private ArrayList<Boolean> reconfigureCombo3;
	private ArrayList<Boolean> reconfigureCombo5;

	private int drawComboTime = 1;

	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
			Gdx.files.internal("fonts/LondrinaSolid-Regular.otf"));
	private BitmapFont font12;
	private BitmapFont fontGameOver;

	private int combo = 0;

	private int selectedCar = 1;

	private ColideEffects colideEffects[];

	public GameStartScreen(SpriteBatch batch) {

		font12 = generator.generateFont(50);
		fontGameOver = generator.generateFont(100);
		generator.dispose();

		this.batch = batch;
		this.sButtons = new ArrayList<SpriteModel>();
		this.presenter = new GameStartScreenPresenter(this);

		this.tweenManager = new TweenManager();
		Tween.setCombinedAttributesLimit(4);
		Tween.registerAccessor(ObjectTween.class, new SpriteTween());

		this.objectsCombo3 = new Array<Rectangle>();
		this.objectsCombo5 = new Array<Rectangle>();
		this.objectsCombo10 = new Array<Rectangle>();

		this.arrayPositons = new ArrayList<Float>();

		this.trashsGreen = new Array<SpriteModel>();
		this.reconfigureCombo3 = new ArrayList<Boolean>();
		this.reconfigureCombo5 = new ArrayList<Boolean>();

		this.trashsRed = new Array<SpriteModel>();

		this.colideEffects = new ColideEffects[10];

		for (int i = 0; i < 10; i++) {
			this.colideEffects[i] = new ColideEffects();
		}

		GameConfiguration.processors.add(this);

	}

	public void doColideEffect(Rectangle rec) {
		for (int i = 0; i < colideEffects.length; i++) {
			if (!colideEffects[i].isDrawEffect()) {
				colideEffects[i].setDrawEffect(true);
				colideEffects[i].setPositionEffect(recCar.x, recCar.y,
						rec.getWidth(), recCar.getHeight() + 100);
				colideEffects[i].setTimeInitEffect(TimeUtils.millis());
				colideEffects[i].startEffect();
				break;
			}
		}
	}

	public void restartComboCount() {
		this.combo = 0;
	}

	// CONFIGURAÇÕES PARA O COMBO 3
	public void configureCombo3() {

		objectsCombo3.clear();

		float lastY = Gdx.graphics.getHeight();
		for (int i = 0; i < 3; i++) {
			Rectangle rec = new Rectangle(0, 0, 170, 150);
			int randomx = getRandomX();
			rec.x = arrayPositons.get(randomx);
			rec.y = lastY;
			objectsCombo3.add(rec);
			lastY += 400;
		}
	}

	public void drawCombo3() {

		boolean config = true;

		for (int i = 0; i < reconfigureCombo3.size(); i++) {
			config = config && reconfigureCombo3.get(i);
		}

		if (config) {
			configureCombo3();
			drawComboTime = 2;
		}

		for (int i = 0; i < objectsCombo3.size; i++) {

			objectsCombo3.get(i).y -= 15 * numController;
			if (selectedCar == 1) {
				trashsRed.get(0).setPosition(objectsCombo3.get(i).x,
						objectsCombo3.get(i).y);

				trashsRed.get(0).draw(batch);
			} else {
				trashsGreen.get(0).setPosition(objectsCombo3.get(i).x,
						objectsCombo3.get(i).y);

				trashsGreen.get(0).draw(batch);
			}

			if (objectsCombo3.get(i).y + objectsCombo3.get(i).getHeight() < 0) {
				reconfigureCombo3.set(i, true);

			} else {
				reconfigureCombo3.set(i, false);
			}

			if (recCar.overlaps(objectsCombo3.get(i))) {
				objectsCombo3.get(i).setX(-2000);
				reconfigureCombo3.set(i, true);
				this.combo++;
				this.scaleYFuel += 0.05f;
				this.doColideEffect(objectsCombo3.get(i));
				this.incrementXScaleTurbo();

			}

		}
	}

	// ======================

	// CONFIGURAÇÕES PARA O COMBO 5
	public void configureCombo5() {

		objectsCombo5.clear();

		float lastY = Gdx.graphics.getHeight();
		for (int i = 0; i < 5; i++) {
			Rectangle rec = new Rectangle(0, 0, 170, 150);
			int randomx = getRandomX();
			rec.x = arrayPositons.get(randomx);
			rec.y = lastY;
			objectsCombo5.add(rec);
			lastY += 400;
		}
	}

	public void drawCombo5() {

		boolean config = true;

		for (int i = 0; i < reconfigureCombo5.size(); i++) {
			config = config && reconfigureCombo5.get(i);
		}

		if (config) {
			configureCombo5();
			drawComboTime = 1;
		}

		for (int i = 0; i < objectsCombo5.size; i++) {

			objectsCombo5.get(i).y -= 15 * numController;

			if (selectedCar == 1) {
				trashsRed.get(0).setPosition(objectsCombo5.get(i).x,
						objectsCombo5.get(i).y);

				trashsRed.get(0).draw(batch);
			} else {
				trashsGreen.get(0).setPosition(objectsCombo5.get(i).x,
						objectsCombo5.get(i).y);

				trashsGreen.get(0).draw(batch);
			}

			if (objectsCombo5.get(i).y + objectsCombo5.get(i).getHeight() < 0) {
				reconfigureCombo5.set(i, true);
			} else {
				reconfigureCombo5.set(i, false);
			}

			if (recCar.overlaps(objectsCombo5.get(i))) {
				objectsCombo5.get(i).setX(-2000);
				reconfigureCombo5.set(i, true);
				this.combo++;
				this.scaleYFuel += 0.05f;
				this.doColideEffect(objectsCombo5.get(i));
				this.incrementXScaleTurbo();
			}
		}
	}

	public void calcVariation() {
		int width = Gdx.graphics.getWidth();
		GameStartScreen.this.xOffset = ((width - 40) / 8) * 2;
	}

	public void setCarPosition() {
		this.object.setX((20 + ((Gdx.graphics.getWidth() - 40) / 8))
				- sCar.getWidth() / 2);
	}

	public int getRandomX() {
		Random rand = new Random();
		int randomx = rand.nextInt(4);
		return randomx;
	}

	@Override
	public void loadImages() {

		for (int i = 0; i < 3; i++) {
			this.reconfigureCombo3.add(false);
		}

		for (int i = 0; i < 5; i++) {
			this.reconfigureCombo5.add(false);
		}

		sTurbo = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/turbo.png");
		sTurbo.setPosition(Gdx.graphics.getWidth() - 250,
				Gdx.graphics.getHeight() - 100);
		sTurbo.setScaleX(0.0f);

		sFuel = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/fuel.png");
		sFuel.setPosition(30, Gdx.graphics.getHeight() - 300);

		sTryAgain = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/try_again.png");
		sTryAgain.setPosition(
				(Gdx.graphics.getWidth() / 2) - sTryAgain.getWidth() / 2, 400);
		sTryAgain.setTag(2);

		SpriteModel cup = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/green_garrafa.png");
		trashsGreen.add(cup);
		SpriteModel bottle = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/green_garrafa.png");
		trashsGreen.add(bottle);

		SpriteModel redPet = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/red_pet.png");
		trashsRed.add(redPet);
		SpriteModel redTanque = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/red_tanque.png");
		trashsRed.add(redTanque);

		sCar = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/carro_verde.png");
		sCar.setPosition(
				(20 + ((Gdx.graphics.getWidth() - 40) / 8)) - sCar.getWidth()
						/ 2, 100);

		recCar = new Rectangle(0, 0, sCar.getWidth(), sCar.getHeight());

		sDialogPause = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/pause_txt.png");
		sDialogPause.setPosition(
				(Gdx.graphics.getWidth() / 2) - (sDialogPause.getWidth() / 2),
				(Gdx.graphics.getHeight() / 2) - (sDialogPause.getHeight() / 2)
						- 100);

		sContinue = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/continue.png");
		sContinue.setTag(0);
		sContinue.setPosition(
				(Gdx.graphics.getWidth() / 2) - (sContinue.getWidth() / 2),
				(Gdx.graphics.getHeight() / 2) - (sContinue.getHeight() / 2)
						- 200);

		sBack = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/sair.png");
		sBack.setTag(1);
		sBack.setPosition((Gdx.graphics.getWidth() / 2)
				- (sBack.getWidth() / 2), (Gdx.graphics.getHeight() / 2)
				- (sBack.getHeight() / 2) - 400);

		sButtons.add(sContinue);
		sButtons.add(sBack);
		sButtons.add(sTryAgain);

		sBackPause = new SpriteModel(GameConfiguration.imagePath
				+ "players_select_screen/base_select_player.png");
		sBackPause.setPosition(
				(Gdx.graphics.getWidth() / 2) - sBackPause.getWidth() / 2, 0);

		int posY = 0;

		this.sStreet = new ArrayList<SpriteModel>();

		for (int i = 0; i < 10; i++) {
			SpriteModel street = new SpriteModel(GameConfiguration.imagePath
					+ "game_start_screen/gamezes_rua.png");
			street.setPosition(0, posY);
			posY += street.getHeight();
			this.sStreet.add(street);

		}

		this.calcVariation();

		this.object = new ObjectTween(20 + ((Gdx.graphics.getWidth() - 40) / 8)
				- (sCar.getWidth() / 2));

		arrayPositons.add(20 + ((Gdx.graphics.getWidth() - 40) / 8)
				- sCar.getWidth() / 2);

		arrayPositons.add(arrayPositons.get(0) + xOffset);
		arrayPositons.add(arrayPositons.get(1) + xOffset);
		arrayPositons.add(arrayPositons.get(2) + xOffset);

		sObstacle = new SpriteModel(GameConfiguration.imagePath
				+ "game_start_screen/cavalete.png");

		sObstacle.setPosition(arrayPositons.get(getRandomX()),
				Gdx.graphics.getHeight());

		recObstacle = new Rectangle(0, 0, sObstacle.getWidth(),
				sObstacle.getHeight());

		// configureCombo3();
		configureCombo5();

		// tweenRight = Tween.to(object, SpriteTween.POS_XY, 0.1f)
		// .target(sCar.getX() + xOffset, sCar.getY())
		// .ease(TweenEquations.easeOutBack);
		//
		// tweenLeft = Tween.to(object, SpriteTween.POS_XY, 0.1f)
		// .target(sCar.getX() - xOffset, sCar.getY())
		// .ease(TweenEquations.easeOutBack);

		timeline = Timeline
				.createParallel()
				.beginParallel()
				.beginSequence()
				.push(Tween.to(object, SpriteTween.ROTATION, 0.1f).target(-10))
				.push(Tween.to(object, SpriteTween.POS_XY, 0.1f)
						.target(sCar.getX() + xOffset, sCar.getY())
						.ease(TweenEquations.easeOutBack))
				.push(Tween.to(object, SpriteTween.ROTATION, 0.1f).target(0.0f))
				.start();

	}

	public void initializeValues() {
		this.numController = 1;
		this.scaleYFuel = 1.0f;
		this.sFuel.setScaleY(scaleYFuel);
		this.sTurbo.setScaleX(0.0f);
		this.scaleXTurbo = 0.0f;
		this.sTurbo.setTexture(GameConfiguration.imagePath
				+ "game_start_screen/turbo.png");
	}

	@Override
	public void drawElements() {

		tweenManager.update(Gdx.graphics.getDeltaTime());

		if (timeline != null) {
			timeline.update(Gdx.graphics.getDeltaTime());
		}

		for (int i = 0; i < 10; i++) {
			SpriteModel street = GameStartScreen.this.sStreet.get(i);
			street.draw(batch);
			street.setPosition(street.getX(), street.getY() - 20
					* numController);
			if (street.getY() + street.getHeight() < 0) {
				street.setPosition(street.getX(), Gdx.graphics.getHeight());
			}
		}

		if (drawComboTime == 1) {
			this.drawCombo3();
		} else if (drawComboTime == 2) {
			this.drawCombo5();
		}

		this.updateYObstacle();
		GameStartScreen.this.sObstacle.draw(batch);
		this.verifyColisionObstacle();

		sCar.setPosition(object.getX(), sCar.getY());
		recCar.setX(object.getX());
		sCar.setRotation(object.getRotation());

		this.sFuel.setScaleY(scaleYFuel);
		this.sFuel.draw(batch);

		if (this.sTurbo.getScaleX() >= 1.0f) {
			this.sTurbo.setTexture(GameConfiguration.imagePath
					+ "game_start_screen/filledTurbo.png");

		}

		this.sTurbo.draw(batch);

		GameStartScreen.this.sCar.draw(batch);

		if (scaleYFuel > 0.0f) {
			scaleYFuel -= 0.001f;

			this.sBackPause.setVisibility(false);
			this.sTryAgain.setVisibility(false);
			this.sBack.setVisibility(false);

		} else {
			gameOver = true;
			numController = 0;
			this.sBackPause.setVisibility(true);
			this.sBackPause.draw(batch);
			this.fontGameOver
					.draw(batch, "Game Over", (Gdx.graphics.getWidth() / 4),
							Gdx.graphics.getHeight() / 2);
			this.sTryAgain.setVisibility(true);
			this.sTryAgain.draw(batch);
			this.sBack.setVisibility(true);
			this.sBack.draw(batch);
		}

		for (int i = 0; i < colideEffects.length; i++) {
			if (colideEffects[i].isDrawEffect()) {

				if (TimeUtils.millis() - colideEffects[i].getTimeInitEffect() < 2000)
					colideEffects[i].drawEffect(batch);
				else {
					colideEffects[i].setDrawEffect(false);
				}
			}
		}

		this.particle.start();
		this.particle.setPosition(sCar.getX() + sCar.getWidth() / 2,
				sCar.getY());
		this.particle.draw(batch, Gdx.graphics.getDeltaTime());

		font12.setColor(0.8f, 0.8f, 0.8f, 1);
		font12.draw(batch, "Collected:  " + combo, 40,
				Gdx.graphics.getHeight() - 50);

//		font12.draw(batch, "" + TimeUtils.nanoTime(), 100,
//				Gdx.graphics.getHeight() - 100);

		GameStartScreen.this.drawOptions();

	}

	public void incrementXScaleTurbo() {
		if (this.sTurbo.getScaleX() < 1.0f) {
			this.scaleXTurbo += 0.05f;
			this.sTurbo.setScaleX(scaleXTurbo);
		}
	}

	public void verifyColisionObstacle() {
		if (recObstacle.overlaps(recCar)) {
			this.scaleYFuel -= 0.05f;
			this.restartObstaclePosition();
		}
	}

	public void restartObstaclePosition() {
		sObstacle.setPosition(arrayPositons.get(getRandomX()), 2000);
	}

	public void updateYObstacle() {
		if (sObstacle.getY() + sObstacle.getHeight() > 0) {
			sObstacle.setPosition(sObstacle.getX(), sObstacle.getY() - 20
					* numController);

		} else {
			sObstacle.setPosition(arrayPositons.get(getRandomX()), 2000);
		}

		recObstacle.setX(sObstacle.getX());
		recObstacle.setY(sObstacle.getY());
	}

	public void drawOptions() {
		if (backPressed) {

			GameStartScreen.this.sBackPause.setVisibility(true);
			GameStartScreen.this.sDialogPause.setVisibility(true);
			GameStartScreen.this.sContinue.setVisibility(true);
			GameStartScreen.this.sBack.setVisibility(true);

			GameStartScreen.this.sBackPause.draw(batch);
			GameStartScreen.this.sDialogPause.draw(batch);
			GameStartScreen.this.sContinue.draw(batch);
			GameStartScreen.this.sBack.draw(batch);
		} else {
			GameStartScreen.this.sBackPause.setVisibility(false);
			GameStartScreen.this.sDialogPause.setVisibility(false);
			GameStartScreen.this.sContinue.setVisibility(false);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK) {

			if (backPressed == false) {
				numController = 0;
				backPressed = true;
			} else {
				numController = 1;
				backPressed = false;
			}
			// TODO: mostar menu de jogo pausado

			// GameConfiguration.doTransition = true;
			// GameConfiguration.nextScreen = 0;
			// Gdx.input.setInputProcessor(GameConfiguration.processors.get(0));
		}
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	public void setSelectedCar(int selectedCar) {
		this.selectedCar = selectedCar;
		switch (selectedCar) {
		case 1:
			this.sCar.setTexture(GameConfiguration.imagePath
					+ "game_start_screen/carro_vermelho.png");

			particle.load(
					Gdx.files.internal(GameConfiguration.particlesPath
							+ "red_particle"),
					Gdx.files.internal(GameConfiguration.particleImagesPath));
			break;
		case 2:
			this.sCar.setTexture(GameConfiguration.imagePath
					+ "game_start_screen/carro_verde.png");
			this.particle.load(
					Gdx.files.internal(GameConfiguration.particlesPath
							+ "green_particle"),
					Gdx.files.internal(GameConfiguration.particleImagesPath));
			break;

		default:
			break;
		}
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if (backPressed || gameOver) {
			SpriteModel sprite = GameStartScreen.this.presenter.verifyTouch(
					sButtons, x, y);
			if (sprite != null) {
			} else {
			}
			if (sprite != null) {
				switch (sprite.getTag()) {
				case 0:
					numController = 1;
					backPressed = false;
					break;
				case 1:

					GameConfiguration.nextScreen = 1;
					GameConfiguration.doTransition = true;
					Gdx.input.setInputProcessor(GameConfiguration.processors
							.get(1));
					backPressed = false;

					break;
				case 2:

					GameConfiguration.doTransition = true;
					GameConfiguration.nextScreen = 2;
					gameOver = false;
					break;
				default:
					break;
				}
			}
		}

		GameStartScreen.this.presenter.virifyMove(x);

		return false;
	}

	@Override
	public void moveToLeft() {

		if ((sCar.getX() - xOffset) > 20 && !backPressed) {

			// if ((!tweenLeft.isStarted() || tweenLeft.isFinished())
			// && (!tweenRight.isInitialized() || tweenRight.isFinished())) {
			// tweenLeft = Tween.to(object, SpriteTween.POS_XY, 0.1f)
			// .target(sCar.getX() - xOffset, sCar.getY())
			// .ease(TweenEquations.easeOutBack);
			// tweenLeft.start(tweenManager);
			// }

			if ((!timeline.isStarted() || timeline.isFinished())) {
				timeline = Timeline
						.createParallel()
						.beginParallel()
						.beginSequence()
						.push(Tween.to(object, SpriteTween.ROTATION, timeTransition)
								.target(10))
						.push(Tween
								.to(object, SpriteTween.POS_XY, timeTransition)
								.target(sCar.getX() - xOffset, sCar.getY())
								.ease(TweenEquations.easeInOutSine))
						.push(Tween.to(object, SpriteTween.ROTATION, timeTransition)
								.target(0.0f)).start();
			}

		}

	}

	@Override
	public void moveToRight() {

		if ((sCar.getX() + xOffset) < Gdx.graphics.getWidth() - 20
				&& !backPressed) {

			// if ((!tweenLeft.isStarted() || tweenLeft.isFinished())
			// && (!tweenRight.isInitialized() || tweenRight.isFinished())) {
			// tweenRight = Tween.to(object, SpriteTween.POS_XY, 0.1f)
			// .target(sCar.getX() + xOffset, sCar.getY())
			// .ease(TweenEquations.easeOutBack);
			// tweenRight.start(tweenManager);
			// }

			if ((!timeline.isStarted() || timeline.isFinished())) {
				timeline = Timeline
						.createParallel()
						.beginParallel()
						.beginSequence()
						.push(Tween.to(object, SpriteTween.ROTATION, timeTransition)
								.target(-10))
						.push(Tween
								.to(object, SpriteTween.POS_XY, timeTransition)
								.target(sCar.getX() + xOffset, sCar.getY())
								.ease(TweenEquations.easeInOutSine))
						.push(Tween.to(object, SpriteTween.ROTATION, timeTransition)
								.target(0.0f)).start();
			}

		}

	}

	// startTime = TimeUtils.millis();
	//
	// Tween.to(object, SpriteTween.POSITION_X, 1000f)
	// .target(sCar.getX() + xOffset)
	// .ease(TweenEquations.easeInOutElastic).start(tweenManager);
	//
	// sCar.setPosition(object.x, sCar.getY());

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
		particle = new ParticleEffect();

		// particle.load(
		// Gdx.files.internal(GameConfiguration.particlesPath + "turbina"),
		// Gdx.files.internal(GameConfiguration.particleImagesPath));

		this.particle.start();
	}

	@Override
	public void drawParticles() {
		// TODO Auto-generated method stub

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
