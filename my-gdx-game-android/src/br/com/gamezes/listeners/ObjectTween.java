package br.com.gamezes.listeners;

import com.badlogic.gdx.graphics.Color;

public class ObjectTween {

	private float x;
	private float y;
	private Color color;
	private float scale;
	private float rotation;

	public ObjectTween(float x) {
		this.x = x;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getRotation() {
		return this.rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

}
