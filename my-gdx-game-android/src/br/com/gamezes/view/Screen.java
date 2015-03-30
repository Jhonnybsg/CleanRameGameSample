package br.com.gamezes.view;

public interface Screen {

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

	/**
	 * Desenha as partículas na tela
	 */
	void drawParticles();
}
