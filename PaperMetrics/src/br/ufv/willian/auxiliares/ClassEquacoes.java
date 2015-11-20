package br.ufv.willian.auxiliares;

public class ClassEquacoes {

	public int equacao(int a, int b, int x){
		
		return a*x + b;
	}
	
	public int equacao(int a, int b, int c, int x){
		
		return a*x*x + b*x + c;
	}
	
	public int equacao(int c, int x){
		int a = -2;
		int b = 5;
		return equacao(a, b, c, x);
	}
	
	public int equacao(int x){
		/*
		 * -x^2 + 12x + 3 
		 * Início em y=3. 
		 * Fim em y=23. 
		 * Auge em y=39.
		 */
		return equacao(-1, 12, 3, x);
	}
}
