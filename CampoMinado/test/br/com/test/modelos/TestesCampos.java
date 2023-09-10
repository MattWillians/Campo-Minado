package br.com.test.modelos;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.projeto.campominado.exessao.ExplosaoException;
import br.com.projeto.campominado.modelo.Campo;

class TestesCampos {
	
	Campo campo; 
	Campo campo2;
	
	@BeforeEach
	void criarObjetos() {
		campo = new Campo(12,12);
	}
	
	@Test
	void testVizinhoCima() {
		campo2 = new Campo(12, 11);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoBaixo() {
		campo2 = new Campo(13, 12);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoEsquerda() {
		campo2 = new Campo(12, 11);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoDireita() {
		campo2 = new Campo(12, 13);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoDiagonalSuperiorDireita() {
		campo2 = new Campo(11, 13);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoDiagonalSuperiorEsquerda() {
		campo2 = new Campo(11, 11);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoDiagonalInferiorDireita() {
		campo2 = new Campo(13, 13);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoDiagonalInferiorEsquerda() {
		campo2 = new Campo(13, 11);
		boolean result = campo.addVizinho(campo2);
		
		assertTrue(result);
	}
	
	@Test
	void testVizinhoForaDeArea() {
		campo2 = new Campo(9, 11);
		boolean result = campo.addVizinho(campo2);
		
		assertFalse(result);
	}
	
	@Test
	void testMarcacaoPadrao() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testTrocaMarcacao() { 
		campo.alterarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testTrocaMarcacao2Vezes() { 
		campo.alterarMarcacao();
		campo.alterarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void abrirCampoNaoMinadoENaoMarcado(){
		assertTrue(campo.abrirCampo());
	}
	
	@Test
	void abrirCampoMinadoEMarcado() {
		campo.alterarMarcacao();
		campo.minar();
		assertFalse(campo.abrirCampo());
	}
	
	@Test
	void abrirCampoMinadoENaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			campo.abrirCampo();
		});
	}
	
	@Test
	void abrirCampoMarcado() {
		campo.alterarMarcacao();
		assertFalse(campo.abrirCampo());
	}
	
	@Test
	void abrirOsVizinhos() {
		
		Campo c33 = new Campo(3, 3);
		Campo c22 = new Campo(2, 2);
		Campo c11 = new Campo(1, 1);
		
		c22.addVizinho(c11);
		
		c33.addVizinho(c22);
		c33.abrirCampo();
		
		assertTrue(c22.isAberto() && c11.isAberto());
	}
	
	@Test
	void abrirOsVizinhosComMina() {
		
		Campo c33 = new Campo(3, 3);
		
		Campo c11 = new Campo(1, 1);
		Campo c12 = new Campo(1, 1);
		c12.minar();
		
		Campo c22 = new Campo(2, 2);
		
		c22.addVizinho(c11);
		c22.addVizinho(c12);
		
		c33.addVizinho(c22);
		c33.abrirCampo();
		
		assertTrue(c22.isAberto() && !c11.isAberto());
	}
}
