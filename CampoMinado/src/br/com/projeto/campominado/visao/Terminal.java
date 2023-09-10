package br.com.projeto.campominado.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.projeto.campominado.exessao.ExplosaoException;
import br.com.projeto.campominado.exessao.SairException;
import br.com.projeto.campominado.modelo.Tabuleiro;

public class Terminal {
	
	private Tabuleiro tabuleiro;
	private Scanner inputScanner = new Scanner(System.in);

	public Terminal(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	
		iniciarJogo();
	}

	private void iniciarJogo() {
		try {
			boolean continuar = true;
		
			while (continuar) {
				
				cicloDoJogo();
				
				System.out.println("Deseja iniciar o jogo? (S / N) \n>>> ");
				String resp = inputScanner.next(); 	
				
				if (resp.toLowerCase().equals("s")) {
					tabuleiro.reiniciarJogo();
				} else {
					continuar = false;
				}
				
			}
		} catch (SairException e){
			System.out.println("Saindo do Jogo...");
		} finally {
			inputScanner.close();
		}
		
	}

	private void cicloDoJogo() {
		try {
			while (!tabuleiro.zerouJogo()) {
				System.out.println(tabuleiro.toString());
				
				String valorDigitado = capturarValorDigitado("Digite (x, y) <- USE VIRGULA: ");
				
				
				Iterator<Integer> valXY = Arrays
						.stream(valorDigitado.split(","))
						.map(e -> Integer.parseInt(e.trim()))
						.iterator();
				
				valorDigitado = capturarValorDigitado("1 - Para abrir uma nova casa \n2 - Para marcar uma casa\n>>> ");
				
				if (valorDigitado.equals("1")) {
					tabuleiro.abrirCampo(valXY.next(), valXY.next());
					
				} else if (valorDigitado.equals("2")){
					tabuleiro.marcarCampo(valXY.next(), valXY.next());
				
				}
			}
			System.out.println(tabuleiro.toString());
			System.out.println("Você ganhou!");
		
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro.toString());
			System.out.println("Você perdeu!");
		}
		
	}

	private String capturarValorDigitado(String string) {
		System.out.print(string);
		String result = inputScanner.next();
		
		if ("sair".equalsIgnoreCase(result)) {
			throw new SairException();
		}
		return result;
	}
}
