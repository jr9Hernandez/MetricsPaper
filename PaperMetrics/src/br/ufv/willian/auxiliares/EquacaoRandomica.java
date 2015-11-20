package br.ufv.willian.auxiliares;

import java.util.Random;

public class EquacaoRandomica implements EquacaoFases{

	/*
	private double a;
	private double b;
	private double c;
	
	public EquacaoRandomica(double a, double b, double c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	*/
	
	//@Override
	public double resultadoFuncao(int x) {
		// TODO Auto-generated method stub
		Random r = new Random();
		double resultado = r.nextInt(5) + 1;
				
		return resultado;
	}

	//@Override
	public double getA() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public double getB() {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Override
	public double getC() {
		// TODO Auto-generated method stub
		return 0;
	}

}