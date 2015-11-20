package br.ufv.willian.auxiliares;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class MedidorDeDificuldade implements Serializable{
	
	//balde<dificuldade<nome_da_tela>>>
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int QUANTIDADE_BALDE = 5; /*Balde � uma variavel de varia��o de dificuldades por tela 
	 								Neste caso, cada nova tela a ser escolhida ter� 5 n�veis de dificuldade
	 							 */
	private final int QUANTIDADE_TELAS_POR_BALDE = 10; // Quantidade m�xima de telas de uma fase
	//private ArrayList<String> vetor_telas[] = new ArrayList<String>[quantidade_telas];
	public ArrayList<ArrayList<ArrayList<String>>> balde = new ArrayList<ArrayList<ArrayList<String>>>(); //Telas mistas
	//private ArrayList<ArrayList<ArrayList<String>>> somente_buracos = new ArrayList<ArrayList<ArrayList<String>>>(); //Telas que s� possuem buracos
	//private ArrayList<ArrayList<ArrayList<String>>> somente_canhao = new ArrayList<ArrayList<ArrayList<String>>>(); //Telas que possuem somente canhoes
	
	private boolean tabelaOcupacao[] = new boolean [QUANTIDADE_TELAS_POR_BALDE]; /* Vetor para auxiliar na aloca��o de telas para a fase 
																		Evita tentar retornar uma tela de um n�vel que nao haja
																		telas classificadas.
																	*/
	private final double PESO_BURACO = 1; // 0.5
	private final double PESO_INIMIGO = 2; //1.0
	private final double PESO_CANHAO = 4; // 3.0
	private final double PESO_ALADO = 3; //1.5
	private final double PESO_ARMORED_TURTLE = 4; //3.0
	private final double PESO_ARMORED_TURTLE_ALADO = 5; // 4
	
	
	//InformacoesTelas info = new InformacoesTelas();
	
	public MedidorDeDificuldade(){
		
		
		//O construtor controi a estrutura para que as telas sejam inseridas
		for(int i=0; i< QUANTIDADE_TELAS_POR_BALDE; i++){			
			
			tabelaOcupacao[i] = false;
			ArrayList<ArrayList<String>> dificuldade = new ArrayList<ArrayList<String>>();
			
			for(int y=0; y < QUANTIDADE_BALDE; y++){
				dificuldade.add(new ArrayList<String>());
			}
			
			balde.add(dificuldade);
		}	
		
		//System.out.println("Contrutor MedidorDeDificuldade executado com sucesso!");
		
	}
	
	public void montaTabelaDificuldade () throws IOException, ClassNotFoundException{
		//M�todo para montar a lista de telas de acordo com a dificuldade 
		System.out.println("Iniciando montagem da tabela!");
		
		ArrayList<String> lista_info = new ArrayList<String>();
		String diretorio = "TelasSelecionadas/InfoTelas/";
		lerArquivosInfo(lista_info, diretorio);
		String nome;
		InformacoesTelas info_aux;
		int dificuldade;

		for (int y=0; y < lista_info.size(); y++){	
			info_aux = new InformacoesTelas();
			nome = getNomeTela(lista_info.get(y));
			//System.out.println("InfoTela: " + nome);
			//info_aux = info_aux.carregaInfoTela("telasEinfo/InfoTelas/" + lista_info.get(y)); //Original 
			//info_aux = info_aux.carregaInfoTela("telasEinfoComPlataforma/InfoTelas/" + lista_info.get(y));
			info_aux = info_aux.carregaInfoTela(diretorio + lista_info.get(y));
			info_aux.setNomeTela(nome);
			info_aux.contaTiposInimigos();
			dificuldade = calculaDificuldade(info_aux);
			info_aux.setDificuldadeCalculada(dificuldade);
			salvaTelaArrayList(nome, dificuldade); //Salva o nome da tela em seu ArrayList correspondente			
			//info_aux.salvaInfoTela("telasEinfo/InfoTelas/" + lista_info.get(y));	
			//imprimeTestes(info_aux); //Imprimi informa��es sobre a Tela
			
		}
		System.out.println("Tabela de Dificuldade montada com sucesso!");
		//salvaTabelaTelas("telasEinfoComPlataforma/"); //Salva o arquivo contendo a tabela de telas
		//System.out.println("Tabela de telas por dificuldade salva com sucesso!!!");
		
	}	
	
		
	public void montaTabelaDificuldade (int quantidade) throws IOException, ClassNotFoundException{
		
		carregaTabelaTelas("");
		ArrayList<String> lista_info = new ArrayList<String>();
		lista_info.add("infoTela2");
		String nome;
		InformacoesTelas info_aux;
		int dificuldade;
		
		if(quantidade > lista_info.size())quantidade = lista_info.size();
		
		for (int y=0; y < quantidade; y++){	
			info_aux = new InformacoesTelas();
			nome = getNomeTela(lista_info.get(y));
			info_aux = info_aux.carregaInfoTela(lista_info.get(y));
			info_aux.setNomeTela(nome);
			info_aux.contaTiposInimigos();
			dificuldade = calculaDificuldade(info_aux);
			info_aux.setDificuldadeCalculada(dificuldade);
			salvaTelaArrayList(nome, dificuldade); //Salva o nome da tela em seu ArrayList correspondente			
			//info_aux.salvaInfoTela("telasEinfo/InfoTelas/" + lista_info.get(y));	
			//info_aux.salvaInfoTela("infoTelaNew");
			imprimeTestes(info_aux);
		}
		
		
	}
	
	public int calculaDificuldade(InformacoesTelas info) throws IOException, ClassNotFoundException{
		int buracos = info.getQuantBuracos();
		int inimigo = info.getQuantInimigo();
		int canhao = info.getQuantCanhoes();
		int alado = info.getQuantAlado();
		int armored = info.getQuantArmoredTurtle();
		int armored_alado = info.getQuantArmoredTurtleAlado();
		
		int dificuldade_calculada =  (int) (PESO_BURACO * buracos + PESO_INIMIGO * inimigo + 
				PESO_CANHAO * canhao + PESO_ALADO * alado + armored * PESO_ARMORED_TURTLE + armored_alado * PESO_ARMORED_TURTLE_ALADO);	
				
		return dificuldade_calculada;		
	}
	
	/**
	 * Retorna o ArrayList de dificuldade d
	 * @param d : Dificuldade
	 * @return : ArrayList de dificuldade d
	 */
	public ArrayList<String> retornaArray(int d){
		
		if(d >= QUANTIDADE_BALDE * QUANTIDADE_TELAS_POR_BALDE) {
			d = QUANTIDADE_BALDE * QUANTIDADE_TELAS_POR_BALDE - 1;
		}
		
		int aux = d / QUANTIDADE_BALDE;
		int indice = d % QUANTIDADE_BALDE;
		
		ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>(); 
		array2.addAll(balde.get(aux));//Retorna o ArrayList de dificuldade
		
		return array2.get(indice);
	}
	public void salvaTelaArrayList(String nomeTela, int dificuldade){
		
		if(dificuldade >= QUANTIDADE_BALDE * QUANTIDADE_TELAS_POR_BALDE) {
			dificuldade = QUANTIDADE_BALDE * QUANTIDADE_TELAS_POR_BALDE - 1;
		}
		
		int aux = dificuldade / QUANTIDADE_BALDE;
		int indice = dificuldade % QUANTIDADE_BALDE; 
		
		/*
		System.out.println("\n\tDificuldade Real: " + dificuldade +
				"\n\tBalde: " + aux + 
				"\n\tArray Dificuldade: " + indice);
		*/
		
		ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>(); 
		array2.addAll(balde.get(aux));//Retorna o ArrayList de dificuldade
		ArrayList<String> array = new ArrayList<String>();
		array.addAll(array2.get(indice));
		array.add(nomeTela);		
		
		array2.set(indice, array);
		balde.set(aux, array2);	
		tabelaOcupacao[aux] = true;
	}
	
	
	public void lerArquivosInfo(ArrayList<String> lista, String diretorio){
		
		//File dir = new File("telasEinfo/InfoTelas/");  //Pasta os arquivos de informacao das telas
		File dir = new File(diretorio);  //Pasta os arquivos de informacao das telas
		  
		String[] arquivos = dir.list();  //Array para guardar o nome de todas as fases
		if (arquivos == null) {   
			System.out.println("Diretorio vazio");
		} else { 
		    for (int i=0; i<arquivos.length; i++) { 
		        lista.add(arquivos[i]);
		    }  
		}
	}
	
	/*
	 * Recebe como entrada o nome do arquivo de Informacao e retorna o nome da tela
	 * */
	public String getNomeTela(String nomeInfo){		
		String nomeTela = "tela";
		char[] nomeArray = nomeInfo.toCharArray(); 
		for(int i = 4; i < nomeArray.length; i++)
			nomeTela += nomeArray[i];
		
		return nomeTela;
	}
	
	public void imprimeTodos(){		
		
		for(int i = 0; i < balde.size(); i++){
			ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>();
			if(tabelaOcupacao[i]){
				System.out.println("\nBALDE: " + i);			
				array2.addAll(balde.get(i));
				for(int j = 0; j < array2.size(); j++){
					System.out.print("\n\tDificuldade Calculada: " + j);
					ArrayList<String> array = new ArrayList<String>();
					array.addAll(array2.get(j));
					System.out.println(" Quantidade: " + array.size());
					for(int y=0; y < array.size(); y++){
						System.out.println("\t\t"+array.get(y));
					}
					array = null;					
				}
				array2 = null;	
			}
			else{
				
				System.out.println("\nBALDE: " + i + " nulo. Nao ha fases para este balde!! ");
				
			}
		}
	}
	
	public void imprimeQuantidadeDeTelas(){
		
		for(int i = 0; i < balde.size(); i++){
			ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>();
			if(tabelaOcupacao[i]){
				System.out.println("\nBALDE: " + i);			
				array2.addAll(balde.get(i));
				for(int j = 0; j < array2.size(); j++){
					System.out.println("\n\tDificuldade Calculada: " + j);
					ArrayList<String> array = new ArrayList<String>();
					array.addAll(array2.get(j));
					System.out.println("\t\tQuantidade de Telas: "+array.size());					
					array = null;					
				}
				array2 = null;	
			}
			else{
				
				System.out.println("\nBALDE: " + i + " nulo. Nao ha fases para este balde!! ");
				
			}
		}
		
	}
	
	public ArrayList<String> convertBaldeEmArray(){
		ArrayList<String> solucao = new ArrayList<String>();
		for(int i = 0; i < balde.size(); i++){
			ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>();
			if(tabelaOcupacao[i]){
				System.out.println("\nBALDE: " + i);			
				array2.addAll(balde.get(i));
				for(int j = 0; j < array2.size(); j++){
					System.out.println("\n\tDificuldade Calculada: " + j);
					ArrayList<String> array = new ArrayList<String>();
					array.addAll(array2.get(j));
					//System.out.println("\t\tQuantidade de Telas: "+array.size());
					solucao.addAll(array);
					array = null;					
				}
				array2 = null;	
			}
			else{
				
				System.out.println("\nBALDE: " + i + " nulo. Nao ha fases para este balde!! ");
				
			}
		}
		
		return solucao;
	}
	
	public void imprimeTestes(InformacoesTelas info) throws IOException, ClassNotFoundException{
		info.contaTiposInimigos();
		System.out.println("\n\nTela "+ info.getNomeTela() +
			"\nQuantidade:\n\t\tBuracos: " + info.getQuantBuracos() + "\n\t\tCanhoes: " + info.getQuantCanhoes() +
			"\n\t\tInimigos nao alados: " + info.getQuantInimigo() + "\n\t\tInimigos alados: " + info.getQuantAlado());
				
		System.out.println("Dificuldade calculada: " + info.getDificuldadeCalculada());
		//info.listaInimigos();
				
				//info.imprimeGlobais();
	}
	
	public String retornaTelaDoBalde(int balde){
		Random random = new Random();
		
		ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>();
		if(!tabelaOcupacao[balde]){
			System.out.println("Nao ha telas disponiveis para o balde " + balde + 
					" Retornando tela do proximo balde!\n ");	
			balde++;
		}		
		
		array2.addAll(this.balde.get(balde));
		ArrayList<String> array = new ArrayList<String>();
		
		int dificuldade = random.nextInt(QUANTIDADE_BALDE);	
		array.addAll(array2.get(dificuldade));
		while(array.size()<1){
			dificuldade = (dificuldade + 1) % QUANTIDADE_BALDE;
			array = new ArrayList<String>();
			array.addAll(array2.get(dificuldade));
		}
		int fase = random.nextInt(array.size());	
		
		return array.get(fase);
	}
	
	public int getQuantidadeTelas(){
		return QUANTIDADE_TELAS_POR_BALDE;
	}
	
	/**
	 * Recebe como entrada o n�vel de dificuldade desejada, e retorna o nome da tela que possua aquela dificuldade
	 * @param dificuldade
	 * @return nome_de_uma_tela 
	 */
	public String retornaTelaEspecifica(int dificuldade, ArrayList<String> telasJaSelecionadas){
		int [] vet = converteDificuldadeTela(dificuldade);
		
		//System.out.println("Dificuldade passada: " + dificuldade + " BALDE: " + vet[0] + " Indice no Balde: " + vet[1]);
		return retornaTelaEspecifica(vet[0], vet[1], telasJaSelecionadas);
	}
	
	/**
	 * Procura na lista de telas candidatas se existe alguma com nome diferente das que já estao presente
	 * na lista de telas escolhidas
	 * 
	 * @param telasEscolhidas : ArrayList com o nome das telas já escolhidas
 	   @param telasCandidatas : ArrayList com o nome das telas candidatas
 	   @param indice : indice da tela candidata
	 * @return nome_tela válido
	 */
	public String testaArrayDeTelas(ArrayList<String> telasEscolhidas, ArrayList<String> telasCandidatas, int indice){
		/*
		System.out.println("Tela candidata: " + telasCandidatas.get(indice) + " de " + telasCandidatas.size() + " telas.");
		System.out.print("Telas ja escolhidas: ");
		for(int i = 0; i < telasEscolhidas.size(); i++)
			System.out.print(telasEscolhidas.get(i) + " ");
		System.out.println();
		*/
		
		int aux = 0, indice_aux = indice; 
		for(int i = 0; i <= telasCandidatas.size(); i++){
			aux = 0;
			for(int j = 0; j < telasEscolhidas.size(); j++){
				//System.out.println(telasEscolhidas.get(j) + " vs " + telasCandidatas.get(indice_aux));
				if(telasEscolhidas.get(j) == telasCandidatas.get(indice_aux)){
					aux++;
					//System.out.println("Entrou");
					break;
				}
			}			
			
			if(aux == 0){
				//System.out.println("Tela selecionada: " + telasCandidatas.get(indice_aux));
				//System.out.println("\n");
				return telasCandidatas.get(indice_aux);
			}//else
				//System.out.println("Saiu do for 1 \n");
			
			//System.out.print("indice_aux anterior: " + indice_aux);
			indice_aux++;
			indice_aux %= telasCandidatas.size();
			//System.out.println(" - indice_aux novo: " + indice_aux);
		}
		//System.out.println("Tela selecionada: " + telasCandidatas.get(indice_aux));	
		//System.out.println("\n");
		return telasCandidatas.get(indice);
	}
	
	/**
	 * 
	 * @param balde
	 * 	Indice do Balde
	 * @param dificuldade
	 * 	Indice de dificuldade dentro do balde
	 * @return Nome_da_tela
	 */
	public String retornaTelaEspecifica(int balde, int dificuldade, ArrayList<String> telasJaSelecionadas){
		
		Random random = new Random();
		
		ArrayList<ArrayList<String>> array2 = new ArrayList<ArrayList<String>>();
		if(!tabelaOcupacao[balde]){
			System.out.println("Nao ha telas disponiveis para o balde " + balde + 
					"\nRetornando tela do proximo balde");	
			if(balde == 0)balde++;
			else balde--;
			dificuldade = 0;
		}		
		
		array2.addAll(this.balde.get(balde));
		ArrayList<String> array = new ArrayList<String>();
		
		array.addAll(array2.get(dificuldade));
		while(array.size()<1){
			dificuldade = (dificuldade + 1) % QUANTIDADE_BALDE;
			array = new ArrayList<String>();
			array.addAll(array2.get(dificuldade));
		}
		int fase = random.nextInt(array.size());	
		
		return testaArrayDeTelas(telasJaSelecionadas, array, fase);
		//return array.get(fase);
	}
	
	/**
	 * Recebe a dificuldade Real de uma tela e retorna seu endere�o de Balde e dificuldade dentro do balde
	 * @param dificuldade
	 * 	Dificuldade
	 * @return array com os indices do balde e da dificuldade no balde
	 * 
	 */
	public int[] converteDificuldadeTela(int dificuldade){
		//Funcao que recebe uma dificuldade real e retorna um possivel balde(0 - QUANT_TELAS) e dificuldade convertida(0 - TAM_BALDE)
		int[] vet = new int[2];
		
		if(dificuldade >= QUANTIDADE_BALDE * QUANTIDADE_TELAS_POR_BALDE) {
			dificuldade = QUANTIDADE_BALDE * QUANTIDADE_TELAS_POR_BALDE - 1;
		}
		
		vet[0] = dificuldade / QUANTIDADE_BALDE;
		vet[1] = dificuldade % QUANTIDADE_BALDE;
		
		
		return vet;
	}
	
	public void salvaTabelaTelas(String diretorio) throws IOException{
		
		FileOutputStream fos = new FileOutputStream(diretorio + "tabelaTelas");
		//FileOutputStream fos = new FileOutputStream("telasExtras/SemFixWall/tabelaTelas");
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(this);
		os.close();
		
	}
	
	/**
	 * 
	 * @param diretorio Ex: Telas/
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public MedidorDeDificuldade carregaTabelaTelas(String diretorio) throws IOException, ClassNotFoundException {
		
		FileInputStream fis = new FileInputStream(diretorio + "tabelaTelas");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object obj = ois.readObject();
		MedidorDeDificuldade tabela   = (MedidorDeDificuldade) obj;
		ois.close();

		//System.out.println("Retornando o arquivo da tabela de telas!");
		
		return tabela;
	}
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException{
		
		System.out.println("INICIADO...\n");
		MedidorDeDificuldade montador = new MedidorDeDificuldade();
		
		/*
		try {
			montador.montaTabelaDificuldade();
			montador.salvaTabelaTelas("TelasSelecionadas/"); //Salva o arquivo tabelaTelas
			montador.imprimeQuantidadeDeTelas();
			//montador.montaTabelaDificuldade(1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		montador = montador.carregaTabelaTelas("");
		montador.imprimeTodos();
		//montador.imprimeQuantidadeDeTelas();
		
		System.out.println("\nFINALIZADO");
		
	}
}
