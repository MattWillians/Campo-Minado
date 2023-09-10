package br.com.projeto.campominado;

import br.com.projeto.campominado.modelo.Tabuleiro;
import br.com.projeto.campominado.visao.Terminal;

public class MainApp {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Terminal terminalGame = new Terminal(new Tabuleiro(10, 10, 6));
	}
}
