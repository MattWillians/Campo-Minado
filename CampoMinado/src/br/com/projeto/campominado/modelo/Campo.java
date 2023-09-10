package br.com.projeto.campominado.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.projeto.campominado.exessao.ExplosaoException;

public class Campo {
	
	private boolean isAberto = false;
	private boolean isMinado = false;
	private boolean isMarcado = false;
	
	private final int linha;
	private final int coluna;
	
	private List<Campo> camposVizinhos = new ArrayList<>();
	
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	public boolean addVizinho(Campo campoVizinho) {
		
		//verificamos se o Vizinho esta na diagonal, verificando se o mesmo
		//esta em linhas diferentes do que estamos verificando
		
		boolean isMesmaLinha = this.getLinha() != campoVizinho.getLinha();
		boolean isMesmaColuna = this.getColuna() != campoVizinho.getColuna();
		boolean isEmDiagonal = isMesmaColuna && isMesmaLinha;
		
		int deltaLinha = Math.abs(this.getLinha() - campoVizinho.getLinha()); // retorna 1 se for mesma linha, 0 se não.
		int deltaColuna = Math.abs(this.getColuna()  - campoVizinho.getColuna()); // retorna 1 se for mesma linha, 0 se não.
		int deltaFinal = deltaColuna + deltaLinha; // Retorna 1 se for mesma linha, 2 se for diagonal
		
		if ((isEmDiagonal && deltaFinal == 2) || (!isEmDiagonal && deltaFinal == 1)) {
			this.camposVizinhos.add(campoVizinho);
			return true;
		}
		
		return false;
	}
	
	public void alterarMarcacao() {
		if (!this.isAberto ) {
			this.isMarcado = !this.isMarcado;
		} 
	}
	
	public boolean abrirCampo() {
		
		if (!this.isAberto && !this.isMarcado) {
			
			this.isAberto = true;
			
			if (isMinado) {
				throw new ExplosaoException();
			} else if (vizinhosSeguros()) {
				camposVizinhos.forEach(v -> v.abrirCampo());
			}
			
			return true;
		
		} else {
			return false;
		}
		
	}
	
	public boolean vizinhosSeguros() {
		return camposVizinhos.stream().noneMatch(e -> e.isMinado);
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !isMinado && isAberto;
		boolean protegido = isMinado && isMarcado;
		
		return desvendado || protegido;
	}
	
	long minasNasVizinhancas() {
		return camposVizinhos.stream().filter(vz -> vz.isMinado).count();
	}
	
	void reiniciarAtributos() {
		this.isAberto = false;
		this.isMarcado = false;
		this.isMinado = false;
	}
	
	public void minar() {
		this.isMinado = true;
	}
	
	public boolean isMinado() {
		return isMinado;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	public boolean isMarcado() {
		return isMarcado;
	}
	
	public boolean isAberto() {
		return isAberto;
	}
	
	void setAberto(boolean setAberto) {
		this.isAberto = setAberto;
	}
	

	public String toString() {
		if (isMarcado) {
			return "x";
		} else if (isAberto && isMinado) {
			return "*";
		} else if (isAberto && minasNasVizinhancas() > 0) {
			return Long.toString(minasNasVizinhancas());
		} else if (isAberto){
			return " ";
		} else {
			return "?";
		}
	}
}
