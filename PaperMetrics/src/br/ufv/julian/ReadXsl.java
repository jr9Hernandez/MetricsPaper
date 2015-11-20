package br.ufv.julian;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

import br.ufv.willian.LevelGenerator;
import dk.itu.mario.level.Level;

public class ReadXsl {
	//perfectirijillo

	double metric1;
	double metric2;
	double metric3;
	double metric4;
	double avgMetric4;
	int gerador;
	
	String will1Array[];
	String will2Array[];
	String randArray[];
	String peterArray[];
	
	int countwill1Array;
	int countwill2Array;
	int countrandArray;
	int countpeterArray;

	public ReadXsl() {
	}

	public void reading(int width, int height, long seed, int difficulty,
			int type) {
		String s="";
		String delimitadores = "\\s+";
		String[] cadenasSeparadas;
		int counterTiles = 0;
		String tipo;
		int counterPeter =0;
		try {
			FileOutputStream fileOut = new FileOutputStream("D:\\MESTRADO2014\\pesquisa2\\metrics\\resultados\\PETER\\poi-test.xls");
			InputStream input = new BufferedInputStream(new FileInputStream(
					"D:\\MESTRADO2014\\pesquisa2\\metrics\\resultados\\PETER\\arquivo.xls"));
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			
			while (rows.hasNext()) {
				
				HSSFRow row = (HSSFRow) rows.next();
				row.createCell(18);
				row.createCell(19);
				row.createCell(20);
				System.out.println("\n");
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();

					if (cell.getColumnIndex() == 10) {
						if (gerador == 1) {

							s = cell.getStringCellValue();
							// System.out.print( s);
							cadenasSeparadas = s.split(delimitadores);
							System.out.println("el tam de las separadas"
									+ cadenasSeparadas.length);
							Level currentLevel = new Level();
							currentLevel = LevelGenerator.createLevelMetrics(
									width, height, seed, difficulty, type, 0,
									cadenasSeparadas);
							Metrics objMetrics = new Metrics(
									currentLevel.getWidth(),
									currentLevel.getHeight(), currentLevel);
							metric1 = objMetrics.MetricsCalc(1,currentLevel);
							metric2=objMetrics.MetricsCalc(2,currentLevel);
							metric3=objMetrics.MetricsCalc(3,currentLevel);
							//metric4=objMetrics.MetricsCalc(4);
						} else if (gerador == 2) {

							cadenasSeparadas = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
							Level currentLevel = new Level();
							currentLevel = LevelGenerator.createLevelMetrics(
									width, height,  Long.parseLong(cadenasSeparadas[1]), Integer.parseInt(cadenasSeparadas[3]), Integer.parseInt(cadenasSeparadas[5]), 2,
									cadenasSeparadas);
							Metrics objMetrics = new Metrics(
									currentLevel.getWidth(),
									currentLevel.getHeight(), currentLevel);
							metric1 = objMetrics.MetricsCalc(1,currentLevel);
							metric2=objMetrics.MetricsCalc(2,currentLevel);
							metric3=objMetrics.MetricsCalc(3,currentLevel);
							//metric4=objMetrics.MetricsCalc(4);
							
							
						} else if (gerador == 3) {

							counterPeter=counterPeter+1;
							cadenasSeparadas = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
							if(counterPeter<28)
							{
								cadenasSeparadas[0]="player_mal.txt";
							}
							else
							{
								cadenasSeparadas[0]="player_bien.txt";
							}
							Level currentLevel = new Level();
							currentLevel = LevelGenerator.createLevelMetrics(
									width, height,  0, 0, 0, 4,
									cadenasSeparadas);
							System.out.println("aca entramos3");
							Metrics objMetrics = new Metrics(
									currentLevel.getWidth(),
									currentLevel.getHeight(), currentLevel);
							metric1 = objMetrics.MetricsCalc(1,currentLevel);
							metric2=objMetrics.MetricsCalc(2,currentLevel);
							metric3=objMetrics.MetricsCalc(3,currentLevel);
							
							//metric4=objMetrics.MetricsCalc(4);
						}

					}
					

					else if (cell.getColumnIndex() == 9) {
						counterTiles = counterTiles + 1;
						if (counterTiles > 1) {
							tipo = cell.getStringCellValue();
							if (tipo.startsWith("EquacaoRandomica")) {
								gerador = 1;
								// System.out.println(tipo);
							} else if (tipo.startsWith("PeterLevelGenerator")) {
								s = cell.getStringCellValue();
								gerador = 3;
								// System.out.println(tipo);
							} else if (tipo.startsWith("RandomLevel")) {
								s = cell.getStringCellValue();
								gerador = 2;
								// System.out.println(tipo);
							} else if (tipo.startsWith("EquacaoEspecial")) {
								gerador = 1;
							}
						}
					} else if (  cell.getColumnIndex() == 18) {
						
						if (gerador == 1) {
							cell.setCellValue(metric1);

						} else if (gerador == 2) {
							cell.setCellValue(metric1);
							// System.out.println(tipo);
						} else if (gerador == 3) {
							//gerador = 2;
							cell.setCellValue(metric1);
						}

					} else if (  cell.getColumnIndex() == 19) {
						
						if (gerador == 1) {
							cell.setCellValue(metric2);

						} else if (gerador == 2) {
							cell.setCellValue(metric2);
							// System.out.println(tipo);
						} else if (gerador == 3) {
							//gerador = 2;
							cell.setCellValue(metric2);
						}

					} else if (  cell.getColumnIndex() == 20) {
						
						if (gerador == 1) {
							cell.setCellValue(metric3);

						} else if (gerador == 2) {
							cell.setCellValue(metric3);
							// System.out.println(tipo);
						} else if (gerador == 3) {
							//gerador = 2;
							cell.setCellValue(metric3);
						}

					}
					

				}
			}
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void compresionDistance(int width, int height, long seed, int difficulty,
			int type)
	{
		
		will1Array=new String[500];
		will2Array=new String[500];
		randArray=new String[500];
		peterArray=new String[500];
		
		countwill1Array=0;
		countwill2Array=0;
		countrandArray=0;
		countpeterArray=0;
		
		int banderaWill=0;
		String s="";
		String delimitadores = "\\s+";
		String[] cadenasSeparadas;
		int counterTiles = 0;
		String tipo;
		int counterPeter =0;
		try {
			
			 
			InputStream input = new BufferedInputStream(new FileInputStream(
					"D:\\MESTRADO2014\\pesquisa2\\metrics\\resultados\\PETER\\arquivo.xls"));
			POIFSFileSystem fs = new POIFSFileSystem(input);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			
			while (rows.hasNext()) {
				
				HSSFRow row = (HSSFRow) rows.next();
				System.out.println("\n");
				Iterator cells = row.cellIterator();
				while (cells.hasNext()) {
					HSSFCell cell = (HSSFCell) cells.next();

					if (cell.getColumnIndex() == 10) {
						if (gerador == 1) {

							if(banderaWill==2)
							{
								s = cell.getStringCellValue();
								will1Array[countwill1Array]=s;
								countwill1Array++;
								
							}
							else if(banderaWill==1)
							{
								s = cell.getStringCellValue();
								will2Array[countwill2Array]=s;
								countwill2Array++;
								
							}
						}

					}
					

					else if (cell.getColumnIndex() == 9) {
						counterTiles = counterTiles + 1;
						if (counterTiles > 1) {
							tipo = cell.getStringCellValue();
							if (tipo.startsWith("EquacaoRandomica")) {
								banderaWill=1;
								gerador = 1;
								// System.out.println(tipo);
							} else if (tipo.startsWith("PeterLevelGenerator")) {
								s = cell.getStringCellValue();
								peterArray[countpeterArray]=s;
								countpeterArray++;
								gerador = 3;
								// System.out.println(tipo);
							} else if (tipo.startsWith("RandomLevel")) {								
								s = cell.getStringCellValue();
								randArray[countrandArray]=s;
								countrandArray++;
								gerador = 2;
								// System.out.println(tipo);
							} else if (tipo.startsWith("EquacaoEspecial")) {
								banderaWill=2;
								gerador = 1;
							}
						}
					} 
					

				}
			}

		} catch (IOException ex) { 
			ex.printStackTrace();
		}
		
		
		//Letura de Arrays
		
		
		
		try {			
			BufferedWriter outw1 = new BufferedWriter(new FileWriter("D:\\MESTRADO2014\\pesquisa2\\metrics\\resultados\\PETER\\CompresionW1.txt"));
			BufferedWriter outw2 = new BufferedWriter(new FileWriter("D:\\MESTRADO2014\\pesquisa2\\metrics\\resultados\\PETER\\CompresionW2.txt"));
			BufferedWriter outp = new BufferedWriter(new FileWriter("D:\\MESTRADO2014\\pesquisa2\\metrics\\resultados\\PETER\\CompresionP.txt"));
			BufferedWriter outr = new BufferedWriter(new FileWriter("D:\\MESTRADO2014\\pesquisa2\\metrics\\resultados\\PETER\\CompresionR.txt"));
			
			for(int i=0;i<countwill1Array;i++)
			{
				avgMetric4=0;
				s = will1Array[i];
				// System.out.print( s);
				cadenasSeparadas = s.split(delimitadores);
				System.out.println("el tam de las separadas"
						+ cadenasSeparadas.length);
				Level currentLevel1 = new Level();
				currentLevel1 = LevelGenerator.createLevelMetrics(
						width, height, seed, difficulty, type, 0,
						cadenasSeparadas);
				
				for(int j=0;j<countwill1Array;j++)
				{
					
					if(i!=j)
					{
					s = will1Array[j];
					// System.out.print( s);
					cadenasSeparadas = s.split(delimitadores);
					System.out.println("el tam de las separadas"
							+ cadenasSeparadas.length);
					Level currentLevel2 = new Level();
					currentLevel2 = LevelGenerator.createLevelMetrics(
							width, height, seed, difficulty, type, 0,
							cadenasSeparadas);
					
					
					Metrics objMetrics = new Metrics(
							currentLevel1.getWidth(),
							currentLevel1.getHeight(), currentLevel1);
					metric4 = objMetrics.MetricsCalc(4,currentLevel2);
					avgMetric4=avgMetric4+metric4;					
					}					
				}
				avgMetric4=avgMetric4/(countwill1Array-1);
				outw1.write(String.valueOf(avgMetric4+" \n"));
				
			}
			
			for(int i=0;i<countwill2Array;i++)
			{
				avgMetric4=0;
				s = will2Array[i];
				// System.out.print( s);
				cadenasSeparadas = s.split(delimitadores);
				System.out.println("el tam de las separadas"
						+ cadenasSeparadas.length);
				Level currentLevel1 = new Level();
				currentLevel1 = LevelGenerator.createLevelMetrics(
						width, height, seed, difficulty, type, 0,
						cadenasSeparadas);
				
				for(int j=0;j<countwill2Array;j++)
				{
					if(i!=j)
					{
					s = will2Array[j];
					// System.out.print( s);
					cadenasSeparadas = s.split(delimitadores);
					System.out.println("el tam de las separadas"
							+ cadenasSeparadas.length);
					Level currentLevel2 = new Level();
					currentLevel2 = LevelGenerator.createLevelMetrics(
							width, height, seed, difficulty, type, 0,
							cadenasSeparadas);
					
					
					Metrics objMetrics = new Metrics(
							currentLevel1.getWidth(),
							currentLevel1.getHeight(), currentLevel1);
					metric4 = objMetrics.MetricsCalc(4,currentLevel2);
					avgMetric4=avgMetric4+metric4;	
					
					}
					
				}
				avgMetric4=avgMetric4/(countwill2Array-1);
				outw2.write(String.valueOf(avgMetric4+" \n"));
				
			}
			
			for(int i=0;i<countrandArray;i++)
			{
				avgMetric4=0;
				s=randArray[i];
				cadenasSeparadas = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
				Level currentLevel1 = new Level();
				currentLevel1 = LevelGenerator.createLevelMetrics(
						width, height,  Long.parseLong(cadenasSeparadas[1]), Integer.parseInt(cadenasSeparadas[3]), Integer.parseInt(cadenasSeparadas[5]), 2,
						cadenasSeparadas);
				
				for(int j=0;j<countrandArray;j++)
				{
					if(i!=j)
					{
					s=randArray[j];
					cadenasSeparadas = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
					Level currentLevel2 = new Level();
					currentLevel2 = LevelGenerator.createLevelMetrics(
							width, height,  Long.parseLong(cadenasSeparadas[1]), Integer.parseInt(cadenasSeparadas[3]), Integer.parseInt(cadenasSeparadas[5]), 2,
							cadenasSeparadas);
					Metrics objMetrics = new Metrics(
							currentLevel1.getWidth(),
							currentLevel1.getHeight(), currentLevel1);					
					
					metric4 = objMetrics.MetricsCalc(4,currentLevel2);
					avgMetric4=avgMetric4+metric4;	
					
					}
					
				}
				avgMetric4=avgMetric4/(countrandArray-1);
				outr.write(String.valueOf(avgMetric4+" \n"));
				
			}
			
			for(int i=0;i<countpeterArray;i++)
			{
				avgMetric4=0;
				s=peterArray[i];
				counterPeter=counterPeter+1;
				cadenasSeparadas = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
				
				if(counterPeter<countpeterArray/2)
				{
					cadenasSeparadas[0]="player_mal.txt";
				}
				else
				{
					cadenasSeparadas[0]="player_bien.txt";
				}
				
				Level currentLevel1 = new Level();
				currentLevel1 = LevelGenerator.createLevelMetrics(
						width, height,  0, 0, 0, 4,
						cadenasSeparadas);
				System.out.println("aca entramos3");
				
				for(int j=0;j<countpeterArray;j++)
				{
					if(i!=j)
					{
					s=peterArray[j];
					cadenasSeparadas = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
					
					cadenasSeparadas[0]="player_bien.txt";
					
					Level currentLevel2 = new Level();
					currentLevel2 = LevelGenerator.createLevelMetrics(
							width, height,  0, 0, 0, 4,
							cadenasSeparadas);
					System.out.println("aca entramos3");
					Metrics objMetrics = new Metrics(
							currentLevel1.getWidth(),
							currentLevel1.getHeight(), currentLevel1);
					metric4 = objMetrics.MetricsCalc(4,currentLevel2);
					avgMetric4=avgMetric4+metric4;	
					
					}
					
				}
				avgMetric4=avgMetric4/(countpeterArray-1);
				outp.write(String.valueOf(avgMetric4+" \n"));
				
			}			
			
			System.out.println("contadores"+countwill1Array+" "+countwill2Array+" "+countrandArray+" "+countpeterArray);
			
			outw1.close();
			outw2.close();
			outr.close();
			outp.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("entramos al catch ");
			e.printStackTrace();
		}
	}

}
