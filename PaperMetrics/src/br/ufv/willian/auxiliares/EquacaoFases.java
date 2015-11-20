package br.ufv.willian.auxiliares;

public interface EquacaoFases {
	/**
	 * 
	 * @param Valor de X para na equação
	 * @return Valor de Y calculado a partir do valor de X
	 */
	public double resultadoFuncao(int x);
	
	public double getA();
	
	public double getB();
	
	public double getC();
}
