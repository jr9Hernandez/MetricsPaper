package br.ufv.willian.auxiliares;

public class EquacaoEspecial implements EquacaoFases{

	private double a;
	private double b;
	private double c;
	
	public EquacaoEspecial(double a, double b, double c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	//@Override
	public double resultadoFuncao(int x) {
		// TODO Auto-generated method stub
		//if(x == 8)
			//x = 5;
		
		double resultado = (a*x*x) + (b*x) + c;
		
		if(resultado == 8)
			resultado = 3;
		if(resultado == 7)
			resultado = 4;
		if(resultado == 6)
			resultado = 5;
		
		/*if(x > 6)
			resultado += (x - 8);*/
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
