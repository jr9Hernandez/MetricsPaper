package br.ufv.julian;

import dk.itu.mario.level.Level;

public class Metric2 { 

	private int width;
	private int height;
	Level level;
	
	private int N;
	private double alpha, beta;
	private double R2;
	private double svar, svar0, svar1, countGaps;
	
	private double[] levelElementsx;
	private double[] levelElementsy;
	private double[][] levelElements;
	private double[] predictValues;
	private double[] differences;
	private static final int levelElementsSup = 0;
	private static final int levelElementsLef = 1;
	private static final int levelElementsRig = 2;
	double sumaDifferences;
	double AvgDifferences;

	public Metric2(int width, int height, Level level) {
		
		this.width = width;
		this.height = height;
		this.level = level;
		levelElementsx = new double[this.width];
		levelElementsy = new double[this.width];
		predictValues= new double[this.width];
		differences= new double[this.width];
		sumaDifferences=0;

	}
	
	public double Metric2M() {

		
		countOtherElements(level.getMap());
		linearRegresion(levelElementsx,levelElementsy);
		for(int i=0;i<width;i++)
		{
			predictValues[i]=predict(levelElementsx[i]);
		}
		for(int i=0;i<width;i++)
		{
			if(levelElementsy[i]==0)
			{
				countGaps=countGaps+1;
			}
			else
			{
			differences[i]=Math.abs(predictValues[i]-levelElementsy[i]);
			sumaDifferences=sumaDifferences+differences[i];
			}
		}
		AvgDifferences=sumaDifferences/(width-countGaps);
		System.out.println("AvgDifferences "+AvgDifferences);
		return AvgDifferences;
	}
	
	public void countOtherElements(byte[][] array) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < array[i].length; j++) {

				
				if (array[i][j] == (byte) (2+8*16)) {// RIGHT_UP_GRASS_EDGE
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
					
				}
				else if (array[i][j] == (byte) (0+8*16)) {// LEFT_UP_GRASS_EDGE
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
					
				}
				else if (array[i][j] == (byte) (5 + 8 * 16)) {// HILL_TOP
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
					
				}
				else if (array[i][j] == (byte) (4 + 8 * 16)) {//HILL_TOP_LEFT
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
					
				}
			    else if (array[i][j] == (byte) (6 + 8 * 16)) {// HILL_TOP_RIGHT
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
					
				}
				else if (array[i][j] == (byte) (4 + 11 * 16)) {// HILL_TOP_LEFT_IN
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
					
				}
				else if (array[i][j] == (byte) (6 + 11 * 16)) {// HILL_TOP_RIGHT_IN
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
				}
				else if (array[i][j] == (byte) (1 + 8 * 16)) {// HILL_TOP_W
					levelElementsx[i]=i;
					levelElementsy[i]=j;
					break;
				}
				else
				{
					levelElementsx[i]=i;
					levelElementsy[i]=0;
				}
				

				}

		}
			
			
//		test del vector
		for(int i=0; i< width;i++)
		{
			
				System.out.print(levelElementsx[i]+" ");
				System.out.print(levelElementsy[i]);
			    System.out.println("");
		}
	}	

	public void linearRegresion(double[] x, double[] y)
	{


		/**
		 * Performs a linear regression on the data points <tt>(y[i], x[i])</tt>.
		 * @param x the values of the predictor variable
		 * @param y the corresponding values of the response variable
		 * @throws java.lang.IllegalArgumentException if the lengths of the two arrays are not equal
		 */

		if (x.length != y.length) {
			throw new IllegalArgumentException("array lengths are not equal");
		}
		N = x.length;

		// first pass
		double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
		for (int i = 0; i < N; i++) sumx  += x[i];
		for (int i = 0; i < N; i++) sumx2 += x[i]*x[i];
		for (int i = 0; i < N; i++) sumy  += y[i];
		double xbar = sumx / N;
		double ybar = sumy / N;

		// second pass: compute summary statistics
		double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
		for (int i = 0; i < N; i++) {
			xxbar += (x[i] - xbar) * (x[i] - xbar);
			yybar += (y[i] - ybar) * (y[i] - ybar);
			xybar += (x[i] - xbar) * (y[i] - ybar);
		}
		beta  = xybar / xxbar;
		alpha = ybar - beta * xbar;

		// more statistical analysis
		double rss = 0.0;      // residual sum of squares
		double ssr = 0.0;      // regression sum of squares
		for (int i = 0; i < N; i++) {
			double fit = beta*x[i] + alpha;
			rss += (fit - y[i]) * (fit - y[i]);
			ssr += (fit - ybar) * (fit - ybar);
		}

		int degreesOfFreedom = N-2;
		R2    = ssr / yybar;
		svar  = rss / degreesOfFreedom;
		svar1 = svar / xxbar;
		svar0 = svar/N + xbar*xbar*svar1;

	}
	/**
	 * Returns the <em>y</em>-intercept &alpha; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
	 * @return the <em>y</em>-intercept &alpha; of the best-fit line <em>y = &alpha; + &beta; x</em>
	 */
	public double intercept() {
		return alpha;
	}

	/**
	 * Returns the slope &beta; of the best of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>.
	 * @return the slope &beta; of the best-fit line <em>y</em> = &alpha; + &beta; <em>x</em>
	 */
	public double slope() {
		return beta;
	}

	/**
	 * Returns the coefficient of determination <em>R</em><sup>2</sup>.
	 * @return the coefficient of determination <em>R</em><sup>2</sup>, which is a real number between 0 and 1
	 */
	public double R2() {
		return R2;
	}

	/**
	 * Returns the standard error of the estimate for the intercept.
	 * @return the standard error of the estimate for the intercept
	 */
	public double interceptStdErr() {
		return Math.sqrt(svar0);
	}

	/**
	 * Returns the standard error of the estimate for the slope.
	 * @return the standard error of the estimate for the slope
	 */
	public double slopeStdErr() {
		return Math.sqrt(svar1);
	}

	/**
	 * Returns the expected response <tt>y</tt> given the value of the predictor
	 *    variable <tt>x</tt>.
	 * @param x the value of the predictor variable
	 * @return the expected response <tt>y</tt> given the value of the predictor
	 *    variable <tt>x</tt>
	 */
	public double predict(double x) {
		return beta*x + alpha;
	}

	/**
	 * Returns a string representation of the simple linear regression model.
	 * @return a string representation of the simple linear regression model,
	 *   including the best-fit line and the coefficient of determination <em>R</em><sup>2</sup>
	 */
	public String toStr() {
		String s = "";
		s += String.format("%.2f N + %.2f", slope(), intercept());
		return (s + "  (R^2 = " + String.format("%.3f", R2()) + ")") ;
	}
	
}
