package br.com.gamezes.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpriteModel {

	private Sprite sprite;
	private int tag;
	private boolean visible = true;
	public float x;

	public SpriteModel(String path) {
		this.sprite = new Sprite(new Texture(Gdx.files.internal(path)));
	}

	public void setVisibility(boolean visible) {
		this.visible = visible;
	}

	public boolean getVisibility() {
		return this.visible;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public int getTag() {
		return this.tag;
	}

	public void setPosition(float x, float y) {
		this.sprite.setPosition(x, y);
	}

	public Vector2 getPosition() {
		return new Vector2(this.sprite.getX(), this.sprite.getY());
	}

	public float getX() {
		return this.sprite.getX();
	}

	public float getY() {
		return this.sprite.getY();
	}

	public void setTexture(String path) {
		this.sprite.setTexture(new Texture(Gdx.files.internal(path)));
	}

	public float getWidth() {
		return this.sprite.getWidth();
	}

	public float getHeight() {
		return this.sprite.getHeight();
	}

	public void setScaleY(float scaleY) {
		this.sprite.setScale(this.sprite.getScaleX(), scaleY);
	}

	public float getScaleY() {
		return this.sprite.getScaleY();
	}

	public void setScaleX(float scaleX) {
		this.sprite.setScale(scaleX, sprite.getScaleY());
	}

	public float getScaleX() {
		return this.sprite.getScaleX();
	}

	public void setColor(float r, float g, float b, float a) {
		this.sprite.setColor(r, g, b, a);
	}

	public float getSpriteAlpha() {
		return this.sprite.getColor().a;
	}

	public void draw(SpriteBatch batch) {
		this.sprite.draw(batch);
	}

	public void setRotation(float degrees) {
		this.sprite.setRotation(degrees);
	}
}
