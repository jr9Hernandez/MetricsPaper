package br.ufv.dpi;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.Random;

//import br.ufv.willian.auxiliares.ConexaoMySQL;
//import br.ufv.willian.auxiliares.InsereDadosBanco;


public class Servidor {

    /**
     * The port that the server listens on.
     */
    private static final int PORT = 15123;//9001; 

    private static int quant_copied_files = 0;

    /**
     * The appplication main method, which just listens on a port and
     * spawns handler threads.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("SERVIDOR INICIADO");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                new Handler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    /**
     * A handler thread class.  Handlers are spawned from the listening
     * loop and are responsible for a dealing with a single client
     * and broadcasting its messages.
     */
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Constructs a handler thread, squirreling away the socket.
         * All the interesting work is done in the run method.
         */
        public Handler(Socket socket) {
            this.socket = socket;
        }

        /**
         * Services this thread's client by repeatedly requesting a
         * screen name until a unique one has been submitted, then
         * acknowledges the name and registers the output stream for
         * the client in a global set, then repeatedly gets inputs and
         * broadcasts them.
         */
        public void run() {
            try {
            	
            	//ServerSocket serverSocket = new ServerSocket(PORT); 
        		//System.out.println("Cliente conectado");
        		//Socket socket = serverSocket.accept(); 
            	/*
        		System.out.println("Accepted connection : " + socket); 
        		File transferFile = new File ("Willian"); 
        		byte [] bytearray = new byte [(int)transferFile.length()]; 
        		FileInputStream fin = new FileInputStream(transferFile); 
        		BufferedInputStream bin = new BufferedInputStream(fin); 
        		bin.read(bytearray,0,bytearray.length); 
        		OutputStream os = socket.getOutputStream(); 
        		System.out.println("Sending Files..."); 
        		os.write(bytearray,0,bytearray.length); 
        		os.flush(); 
        		socket.close(); 
        		System.out.println("File transfer complete");
        		*/
        		
        		
        		//Codigo que antes era do clienteint filesize = 1022386; 
            	int filesize = 1022386; 
        		int bytesRead; 
        		int currentTot = 0; 
            	byte [] bytearray = new byte [filesize];
        		InputStream is = socket.getInputStream(); 
        		FileOutputStream fos = new FileOutputStream("arq_recebido.obj"); 
        		//System.out.println("O arquivo 'socket_transferido' foi carregado!!");
        		BufferedOutputStream bos = new BufferedOutputStream(fos); 
        		bytesRead = is.read(bytearray,0,bytearray.length);
        		currentTot = bytesRead; 
        		System.out.println("\nIniciando Transferencia...");
        		System.out.print("\tCopiando");
        		do { 
        			//System.out.print( "\033[H\033[2J" );
        			System.out.print( ".");
        			
        			bytesRead = is.read(bytearray, currentTot, (bytearray.length-currentTot)); 
        			if(bytesRead >= 0)
        				currentTot += bytesRead; 
        		}while(bytesRead > -1); 
        		bos.write(bytearray, 0 , currentTot); 
        		bos.flush(); 
        		bos.close(); 
        		socket.close(); 
        		System.out.println("\nTransferencia realizada com sucesso...\n");
        		
        		try {
					fazCopia();
					quant_copied_files++;
		            System.out.println("\n\t"+ quant_copied_files  + " arquivos recebidos...");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}        		
        		
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {
                	//System.out.println("Fechou o socket");
                    socket.close();
                    //System.out.println("\n");
                } catch (IOException e) {
                }
            } 
            
            
        }
        
        /*
        public void salvaDadosRecebidosBD(DadosAvaliacaoTelas dados){
        	ConexaoMySQL conexao = ConexaoMySQL.getInstance();
        	conexao.ReiniciarConexao();
        	InsereDadosBanco insere = new InsereDadosBanco();
        	insere.inserirJogador(conexao.getConexaoMySQL(), dados.getUsuario(), dados.getTela(), dados.getNumMortes(), 
        			dados.getNumMoedasColetadasGamePlay(), dados.getNumInimigosMortosGamePlay());
        	conexao.FecharConexao();
        	
        }
        */
        
        public void fazCopia() throws IOException, ClassNotFoundException{ 
        	//Se der erro nao copia o arquivo para a pasta
        	DadosFormulario d = new DadosFormulario();
        	//DadosAvaliacaoTelas d = new DadosAvaliacaoTelas();
            d = d.carregarArquivo("arq_recebido.obj");            
        	//***********************************************
        	Calendar calendar = Calendar.getInstance();
        	int ano = calendar.get(Calendar.YEAR);
        	int mes = calendar.get(Calendar.MONTH);
        	int dia  = calendar.get(Calendar.DAY_OF_MONTH);
    		int hour = calendar.get(Calendar.HOUR_OF_DAY);     
    		int minute = calendar.get(Calendar.MINUTE);     
    		int second = calendar.get(Calendar.SECOND);
    		int mili = calendar.get(Calendar.MILLISECOND);
        	
        	Random randon = new Random();
        	File arquivo_recebido = new File("arq_recebido.obj");  
            
            File copia_arquivo_recebido = new File("ArquivosRecebidos/" + d.getUser() + "-recebidoem-" + dia + "-" + mes + "-" + 
            		ano + "-" + hour + "h" + minute + "m" + second + "s" + mili + "ms"); 
            
            System.out.println("Salvando aquivos no diretorio");
            //Salva a Tela
            FileInputStream fisOrigemArquivo = new FileInputStream(arquivo_recebido);  
            FileOutputStream fisDestinoArquivo = new FileOutputStream(copia_arquivo_recebido);  
            FileChannel fcOrigem = fisOrigemArquivo.getChannel();    
            FileChannel fcDestino = fisDestinoArquivo.getChannel();    
            fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);    
            fisOrigemArquivo.close();    
            fisDestinoArquivo.close();  
            
                  
            System.out.println("Finalizado!!!");          
            
            //salvaDadosRecebidosBD(d);
        }
        
    }    
    
    public void fazCopia(Socket socket) throws IOException{  
    	String nome = "" + socket +""; 
    	File arquivo_recebido = new File("socket_recebido.obj");  
        
        File copia_arquivo_recebido = new File("ArquivosRecebidos/" + nome); 
        
        System.out.println("Salvando aquivos no diretorio");
        //Salva a Tela
        FileInputStream fisOrigemArquivo = new FileInputStream(arquivo_recebido);  
        FileOutputStream fisDestinoArquivo = new FileOutputStream(copia_arquivo_recebido);  
        FileChannel fcOrigem = fisOrigemArquivo.getChannel();    
        FileChannel fcDestino = fisDestinoArquivo.getChannel();    
        fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);    
        fisOrigemArquivo.close();    
        fisDestinoArquivo.close();  
        
              
        System.out.println("Finalizado!!!");
    }
    
    public static boolean testaArquivo(){
    	//DadosFormulario carrega = new DadosFormulario();
    	DadosAvaliacaoTelas carrega = new DadosAvaliacaoTelas();
		try {
			//DadosFormulario carregado = carrega.carregarArquivo();
			DadosAvaliacaoTelas carregado = carrega.carregarArquivo();
			if(carregado != null)
				return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("\nClasse Incompatível\n");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.err.println("\nErro com o arquivo\n");
			return false;
		}
    	return false;
    }
}
