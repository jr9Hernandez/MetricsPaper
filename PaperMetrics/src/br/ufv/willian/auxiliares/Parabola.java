package br.ufv.willian.auxiliares;

public class Parabola implements EquacaoFases{
	
	private double a;
	private double b;
	private double c;
	
	public Parabola(double a, double b, double c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	//@Override
	public double resultadoFuncao(int x) {
		// TODO Auto-generated method stub
		double resultado = (a*x*x) + (b*x) + c;
		return resultado;
	}
	
	public double getA(){
		return a;
	}
	
	public double getB(){
		return b;
	}
	
	public double getC(){
		return c;
	}

}
