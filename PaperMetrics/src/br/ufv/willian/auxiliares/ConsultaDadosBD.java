package br.ufv.willian.auxiliares;

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.SQLException; 
import java.util.ArrayList;

public class ConsultaDadosBD {
	
	class Tela{
		int id;
    	String nome_tela;
        int quant_moedas;
        int quant_inimigos;
	}
	
	class Jogador{
		String nome;
		String tela_jogada;
		int quant_mortes;
		int quant_moedas;
		int quant_inimigos;		
	}
	 
	public ArrayList<Tela> consultaInfoTela(Connection conexao, String condicao) {  
         
		ArrayList<Tela> array = new ArrayList<Tela>();
		
	        try{  
	            String consulta = "SELECT * FROM tb_tela " +  condicao;  
	            	              
	            java.sql.Statement stmt = conexao.createStatement();
	            java.sql.ResultSet rs = stmt.executeQuery(consulta);
	            
	            while(rs.next()){
	            	int id = rs.getInt("id");
	            	String nome_tela = rs.getString("nome");
	                int quant_moedas = rs.getInt("quant_moedas");
	                int quant_inimigos = rs.getInt("quant_inimigos");


	                Tela registroLido= new Tela();
	                registroLido.id = id;
	                registroLido.nome_tela = nome_tela;
	                registroLido.quant_moedas = quant_moedas;
	                registroLido.quant_inimigos = quant_inimigos;
	                
	                array.add(registroLido);
	             }
	              
	        }catch(SQLException e){  
	            System.out.println("Não foi possível ler os DADOS !");  
	            e.printStackTrace();  
	        }
	        
	        return array;
	  
	 }
	 
	 public ArrayList<Jogador> consultaInfoJogador(Connection conexao, String condicao) { 
		 
		 ArrayList<Jogador> array = new ArrayList<Jogador>();
        		 
	     try{  
	         String consulta = "SELECT * FROM tb_jogador " + condicao; 
	            
	         java.sql.Statement stmt = conexao.createStatement();
	         java.sql.ResultSet rs = stmt.executeQuery(consulta);
	            
	         while(rs.next()){
	        	String nome = rs.getString("nome");
	        	String tela_jogada = rs.getString("tela_jogada");
	        	int quant_mortes = rs.getInt("quant_mortes");
	            int quant_moedas = rs.getInt("quant_moedas");
	            int quant_inimigos = rs.getInt("quant_inimigos");

	            Jogador registroLido= new Jogador();
                registroLido.nome = nome;
                registroLido.tela_jogada = tela_jogada;
                registroLido.quant_mortes = quant_mortes;
                registroLido.quant_moedas = quant_moedas;
                registroLido.quant_inimigos = quant_inimigos;
	            
	            array.add(registroLido);
	         }
	              
	        }catch(SQLException e){  
	            System.out.println("Não foi possível ler os DADOS !");  
	            e.printStackTrace();  
	        }
	        
	        return array;
	        	  
	 }
	 
	
	 public static void main (String [] args){
		 
		 ConexaoMySQL conexao = ConexaoMySQL.getInstance();		 
		 ConsultaDadosBD consulta = new ConsultaDadosBD();
		 
		 System.out.println("TABELA TELA");
		 ArrayList<Tela> resultela = consulta.consultaInfoTela(conexao.getConexaoMySQL(),"WHERE nome = 'tela1'");
		 for(int i= 0; i< resultela.size(); i++){
			 Tela tela = resultela.get(i);
			 System.out.println("Id:" + tela.id + 
				 "\nNome: " + tela.nome_tela + 
				 "\nQuant_moedas: " + tela.quant_moedas +
				 "\nQuant_inimigos: " + tela.quant_inimigos  + "\n");
		 }
		 
		 System.out.println("\nTABELA JOGADOR");
		 ArrayList<Jogador> result_jogador = consulta.consultaInfoJogador(conexao.getConexaoMySQL(),"");
		 for(int i = 0; i < result_jogador.size(); i++){
			 Jogador player = result_jogador.get(i);
			 System.out.println( 
				 "Nome: " + player.nome + 
				 "\nTela: " + player.tela_jogada +
				 "\nQuant_mortes: " + player.quant_mortes +
				 "\nQuant_moedas: " + player.quant_moedas +
				 "\nQuant_inimigos: " + player.quant_inimigos + "\n");
		}
	 }

}