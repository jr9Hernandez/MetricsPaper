package pet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ElementosTela {
	
	/*
	 * Vetores de itens do mapa 
	*/
	private GapsMap gaps[]; //vetor de buracos
	private PlatformsMap platforma[]; // vetor de plataformas
	private BlocksMap blocks_coin[]; // vetor de blocos com moedas
	private BlocksMap blocks_pwup[]; // vetor de blocos com power-up
	private CoinsMap coins[]; // moedas
	private EnemyGomba gomba_sem_asa[]; //goomba sem asas
	private EnemyGomba gomba_com_asa[]; // goomba com asas
	private EnemyKoopa gKoopa_sem_asa[]; // Koopa verde sem asa
	private EnemyKoopa gKoopa_com_asa[]; //Koopa verde com asa
	private EnemyKoopa rKoopa_sem_asa[]; //Koopa vermelha sem asa
	private EnemyKoopa rKoopa_com_asa[]; //Koopa vermelha com asa
	private PlantsMap plants[]; // Plantas
	private SpikesMap spike[]; //Spike
	private TubeMap tube[]; // Tubos
	
	
	/*
	 * Quantidade de itens 
	*/
	private int quant_gap; //buracos
	private int quant_platforma; // plataformas
	private int quant_blocks_coin; // blocos com moedas
	private int quant_blocks_pwup; // blocos com power-up
	private int quant_coins; // moedas
	private int quant_gomba_sem_asa; //goomba sem asas
	private int quant_gomba_com_asa; // goomba com asas
	private int quant_gKoopa_sem_asa; // Koopa verde sem asa
	private int quant_gKoopa_com_asa; //Koopa verde com asa
	private int quant_rKoopa_sem_asa; //Koopa vermelha sem asa
	private int quant_rKoopa_com_asa; //Koopa vermelha com asa
	private int quant_plants; // Plantas
	private int quant_spike; //Spike
	private int quant_tube; // Tubos
	
		
	public ElementosTela(int gap, int tube, int coins, int quests, int platforms, 
			int mountains, int goombas[], int green_koopas[], int red_koopas[], int plants, int spikes){
		
		//System.out.println("Buracos: " + gap + "\nTubos: "+ tube);
		
		this.gaps = new GapsMap[gap];
		this.platforma = new PlatformsMap[tube];
		this.blocks_coin = new BlocksMap[quests]; 
		this.blocks_pwup = new BlocksMap[quests];
		this.coins = new CoinsMap[coins];
		this.gomba_sem_asa = new EnemyGomba[goombas[0]];
		this.gomba_com_asa = new EnemyGomba[goombas[1]];
		this.gKoopa_sem_asa = new EnemyKoopa[green_koopas[0]]; 
		this.gKoopa_com_asa = new EnemyKoopa[green_koopas[1]];
		this.rKoopa_sem_asa = new EnemyKoopa[red_koopas[0]];
		this.rKoopa_com_asa= new EnemyKoopa[red_koopas[1]];
		this.plants = new PlantsMap[plants];
		this.spike = new SpikesMap[spikes];
		this.tube = new TubeMap[tube];
		
		this.quant_gap = 0;
		this.quant_platforma = 0;
		this.quant_blocks_coin = 0; 
		this.quant_blocks_pwup = 0; 
		this.quant_coins = 0; 
		this.quant_gomba_sem_asa = 0; 
		this.quant_gomba_com_asa = 0; 
		this.quant_gKoopa_sem_asa = 0; 
		this.quant_gKoopa_com_asa = 0; 
		this.quant_rKoopa_sem_asa = 0; 
		this.quant_plants = 0; 
		this.quant_spike = 0; 
		this.quant_tube = 0; 
	}
	
	
	public void setGap(int x, int y, int largura){
		this.gaps[quant_gap] = new GapsMap();
		this.gaps[quant_gap].setCoordenadas(x, y);
		this.gaps[quant_gap].setLargura(largura);
		this.quant_gap++;	
	}
	
	public void setTube(int x, int y, int altura){
		this.tube[quant_tube] = new TubeMap();
		this.tube[quant_tube].setCoordenadas(x, y);
		this.tube[quant_tube].setAltura(altura);
		this.quant_tube++;	
	}
	
	public void setPlatform(int x, int y, int largura, int altura){
		//System.out.println("Quantidade de plataforma: "+ quant_platforma + "\nCoordenadas\n\tx:"+x+"\ty: "+y+"\tlargura: "+largura+"\tautura: "+altura);
		this.platforma[quant_platforma] = new PlatformsMap();
		this.platforma[quant_platforma].setCoordenadas(x, y);
		this.platforma[quant_platforma].setLargura(largura);
		this.platforma[quant_platforma].setAltura(altura);
		this.quant_platforma++;
	}
	
	public void setCoin(int x, int y){
		this.coins[quant_coins] = new CoinsMap();
		this.coins[quant_coins].setCoordenadas(x, y);	
		this.quant_coins++;
	}
	
	public void setBlocoCoin(int x, int y){
		this.blocks_coin[quant_blocks_coin] = new BlocksMap();
		this.blocks_coin[quant_blocks_coin].setCoordenadas(x, y);
		this.quant_blocks_coin++;
	}
	
	public void setBlocoPowerUp(int x, int y){
		this.blocks_pwup[quant_blocks_pwup] = new BlocksMap();
		this.blocks_pwup[quant_blocks_pwup].setCoordenadas(x, y);
		this.quant_blocks_pwup++;		
	}
	
	public void setGombaSemAsa(int x, int y){
		this.gomba_sem_asa[quant_gomba_sem_asa] = new EnemyGomba();
		this.gomba_sem_asa[quant_gomba_sem_asa].setCoordenadas(x, y);
		this.quant_gomba_sem_asa++;
	}
	
	public void setGombaComAsa(int x, int y){
		this.gomba_com_asa[quant_gomba_com_asa] = new EnemyGomba();
		this.gomba_com_asa[quant_gomba_com_asa].setCoordenadas(x, y);
		this.quant_gomba_com_asa++;		
	}
	
	public void setKoopaVerdeSemAsa(int x, int y){
		this.gKoopa_sem_asa[quant_gKoopa_sem_asa] = new EnemyKoopa();
		this.gKoopa_sem_asa[quant_gKoopa_sem_asa].setCoordenadas(x, y);
		this.quant_gKoopa_sem_asa++;
	}
	
	public void setKoopaVerdeComAsa(int x, int y){
		this.gKoopa_com_asa[quant_gKoopa_com_asa] = new EnemyKoopa();
		this.gKoopa_com_asa[quant_gKoopa_com_asa].setCoordenadas(x, y);
		this.quant_gKoopa_com_asa++;
	}
	
	public void setKoopaVermelhaSemAsa(int x, int y){
		this.rKoopa_sem_asa[quant_rKoopa_sem_asa] = new EnemyKoopa();
		this.rKoopa_sem_asa[quant_rKoopa_sem_asa].setCoordenadas(x, y);
		this.quant_rKoopa_sem_asa++;
	}
	
	public void setKoopaVermelhaComAsa(int x, int y){
		this.rKoopa_com_asa[quant_rKoopa_com_asa] = new EnemyKoopa();
		this.rKoopa_com_asa[quant_rKoopa_com_asa].setCoordenadas(x, y);
		this.quant_rKoopa_com_asa++;
	}
	
	public void setPlant(int x, int y){
		this.plants[quant_plants] = new PlantsMap();
		this.plants[quant_plants].setCoordenadas(x, y);
		this.quant_plants++;		
	}
	
	public void setSpike(int x, int y){
		this.spike[quant_spike]= new SpikesMap();
		this.spike[quant_spike].setCoordenadas(x, y);
		this.quant_spike++;
	}
	
	public Coordenadas getTube(int i){
		return tube[i].getCoordenadas();
	}
	
	
	public void salvaEmTxt(){
		
		//File arquivo = new File( "C:/Users/WILLIAN/mario/fase.txt" ); //Para salvar em um diretório expecífico
		File arquivo = new File( "fase.txt" ); //Para salvar na pasta do projeto
		//Verifivar se o arquivo ou diretório existe
		boolean existe = arquivo.exists();
		
		if(!existe){
			//cria um arquivo (vazio)
			try {
				arquivo.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			//cria um diretório
			//arquivo.mkdir();
		}
		
		//Caso o arquivo já exista, este trecho irá reseta-lo, ou seja torna-lo vazio
		try {
			FileWriter str = new FileWriter(arquivo);
			str.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * Salvando quantidades e coordenadas no arquivo
		*/
		
		//Gaps
		salvaQuantidade(arquivo, quant_gap);
		for(int i=0; i<quant_gap ; i++)
			gaps[i].SalvaArquivo(arquivo);
		
		//Tubes
		salvaQuantidade(arquivo, quant_tube);
		for(int i = 0; i < quant_tube; i++)
			tube[i].SalvaArquivo(arquivo);
		
		//Platforms
		salvaQuantidade(arquivo, quant_platforma);
		for(int i=0; i < quant_platforma; i++)
			platforma[i].SalvaArquivo(arquivo);
		
		//Moedas
		salvaQuantidade(arquivo, quant_coins);
		for(int i=0; i< quant_coins; i++)
			coins[i].SalvaArquivo(arquivo);
		
		//Blocos com moedas
		salvaQuantidade(arquivo, quant_blocks_coin);
		for(int i = 0; i< quant_blocks_coin; i++)
			blocks_coin[i].SalvaArquivo(arquivo);
		
		//Blocos com power-up
		salvaQuantidade(arquivo, quant_blocks_pwup);
		for(int i=0; i< quant_blocks_pwup; i++)
			blocks_pwup[i].SalvaArquivo(arquivo);
		
		//Gomba sem asas
		salvaQuantidade(arquivo, quant_gomba_sem_asa);
		for(int i=0; i<quant_gomba_sem_asa; i++)
			gomba_sem_asa[i].SalvaArquivo(arquivo);
		
		//Gomba com asas
		salvaQuantidade(arquivo, quant_gomba_com_asa);
		for(int i = 0; i < quant_gomba_com_asa; i++)
			gomba_com_asa[i].SalvaArquivo(arquivo);
		
		//Koopas verdes sem asas
		salvaQuantidade(arquivo, quant_gKoopa_sem_asa);
		for(int i = 0; i < quant_gKoopa_sem_asa; i++)
			gKoopa_sem_asa[i].SalvaArquivo(arquivo);
		
		//Koopas verdes com asas
		salvaQuantidade(arquivo, quant_gKoopa_com_asa);
		for(int i = 0; i< quant_gKoopa_com_asa; i++)
			gKoopa_com_asa[i].SalvaArquivo(arquivo);
		
		//Koopas vermelhas sem asas
		salvaQuantidade(arquivo, quant_gKoopa_sem_asa);
		for(int i = 0; i < quant_rKoopa_sem_asa; i++)
			rKoopa_sem_asa[i].SalvaArquivo(arquivo);
				
		//Koopas vermelhas com asas
		salvaQuantidade(arquivo, quant_rKoopa_com_asa);
		for(int i = 0; i< quant_gKoopa_com_asa; i++)
			rKoopa_com_asa[i].SalvaArquivo(arquivo);
		
		//Plantas
		salvaQuantidade(arquivo, quant_plants);
		for(int i = 0; i < quant_plants; i++)
			plants[i].SalvaArquivo(arquivo);
		
		//Spikes
		salvaQuantidade(arquivo, quant_spike);
		for(int i = 0; i < quant_spike; i++)
			spike[i].SalvaArquivo(arquivo);
			
		
	}
	
	/*
	 * Método para salvar em um arquivo Txt a quantidade de um certo item
	*/
	public void salvaQuantidade(File arquivo, int quant){
		
		FileWriter fw;
		try {
			fw = new FileWriter(arquivo, true);
			fw.write( quant +  "\n");		
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
