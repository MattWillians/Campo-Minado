package br.com.projeto.campominado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import br.com.projeto.campominado.exessao.ExplosaoException;

public class Tabuleiro {
	
	private int qtdLinhas;
	private int qtdColunas;
	private int totMinas;
	
	private final List<Campo> camposDoTabuleiro = new ArrayList<>();

	public Tabuleiro(int qtdLinhas, int qtdColunas, int totMinas) {
		
		this.qtdLinhas = qtdLinhas;
		this.qtdColunas = qtdColunas;
		this.totMinas = totMinas;
	
		gerarTabuleiro();
		associarVizinhos();
		sortearCamposMinados();
		
	}
	
	public void abrirCampo(int linha, int coluna) {
		try {
			camposDoTabuleiro.stream()
			.filter(cm -> cm.getLinha() == linha && cm.getColuna() == coluna)
			.findFirst()
			.ifPresent(c -> c.abrirCampo());

		} catch (ExplosaoException e) {
			camposDoTabuleiro.forEach(c -> c.setAberto(true));
			throw e;
		}
	}
	
	public void marcarCampo(int linha, int coluna) {
		camposDoTabuleiro.stream()
		.filter(cm -> cm.getLinha() == linha && cm.getColuna() == coluna)
		.findFirst()
		.ifPresent(c -> c.alterarMarcacao());
	}

	private void sortearCamposMinados() {
		
		long totalMinasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();
		
		while (totalMinasArmadas < totMinas) {
			int posAleatoria = (int) (Math.random() * camposDoTabuleiro.size());
			camposDoTabuleiro.get(posAleatoria).minar();
			totalMinasArmadas = camposDoTabuleiro.stream().filter(minado).count();
		}
	}

	private void associarVizinhos() {
		for (Campo campo : camposDoTabuleiro) {
			for (Campo campoVizinho : camposDoTabuleiro) {
				campo.addVizinho(campoVizinho);
			}
		}
		
	}

	private void gerarTabuleiro() {
		for (int linha = 0; linha < qtdLinhas; linha++) {
			for (int coluna = 0; coluna < qtdColunas; coluna++) {
				camposDoTabuleiro.add(new Campo(linha, coluna));
			}
		}
		
	}
	
	public boolean zerouJogo() {
		return camposDoTabuleiro.stream().allMatch(c -> c.objetivoAlcancado());
	}
	
	public void reiniciarJogo() {
		camposDoTabuleiro.stream().forEach(e -> e.reiniciarAtributos());
		sortearCamposMinados();
	}
	
	public String toString() {
		
		StringBuilder sB = new StringBuilder();
		int idx = 0;
		sB.append("__|");
		for (int linha = 0; linha < qtdLinhas; linha++) {
			sB.append(" "+linha+" ");
		}
		sB.append("\n");
		
		for (int linha = 0; linha < qtdLinhas; linha++) {
			sB.append(linha + " |");
			
			for (int coluna = 0; coluna < qtdColunas; coluna++) {
				
				sB.append(" ");
				sB.append(camposDoTabuleiro.get(idx));
				sB.append(" ");
				idx++;
			}
			sB.append("\n");
		}
		
		return sB.toString();
	}
}