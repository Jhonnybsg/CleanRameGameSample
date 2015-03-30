package br.com.gamezes.view;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface PlayerSelectScreenView {

	/**
	 * Carrega as imagens necessárias para a tela
	 */
	void loadImages();

	/**
	 * Carrega os arquivos de som para esta tela
	 */
	void loadSounds();

	/**
	 * Libera da memória todos os recursos carregados para esta tela
	 */
	void disposeAllResources();

	/**
	 * Desenha os elementos desta tela
	 */
	void drawElements();

	/**
	 * Carrega os arquivos de particulas
	 */
	void loadParticles();

}
