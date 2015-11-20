package br.ufv.willian.auxiliares;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 

public class InsereDadosBanco {
	
	
	 public void inserirTela(Connection conexao, int id, String nome, int quant_moedas, int quant_inimigos) { 
		 
	        try{  
	            String guardar = "INSERT INTO tb_tela (id, nome, quant_moedas, quant_inimigos) VALUES  (?, ?, ?, ?)";  
	            PreparedStatement persistir = conexao.prepareStatement(guardar);  
	              
	            persistir.setInt(1, id);
	            persistir.setString(2, nome);  
	            persistir.setInt(3, quant_moedas);  
	            persistir.setInt(4, quant_inimigos);  
	              
	            persistir.execute();
	              
	        }catch(SQLException e){  
	            System.out.println("Não foi possível gravar os DADOS !");  
	            e.printStackTrace();  
	        }
	        
	        
	 }
	 
	 public void inserirJogador(Connection conexao, String nome, String tela, int quant_mortes, int quant_moedas, int quant_inimigos) {  
         
		 try{  
	            String guardar = "INSERT INTO tb_jogador (nome, tela_jogada, quant_mortes, quant_moedas, quant_inimigos) VALUES  (?, ?, ?, ?, ?)";  
	            PreparedStatement persistir = conexao.prepareStatement(guardar);  
	              
	            persistir.setString(1, nome); 
	            persistir.setString(2, tela); 
	            persistir.setInt(3, quant_mortes); 
	            persistir.setInt(4, quant_moedas);  
	            persistir.setInt(5, quant_inimigos);  
	              
	            persistir.execute();
	              
	        }catch(SQLException e){  
	            System.out.println("Não foi possível gravar os DADOS !");  
	            e.printStackTrace();  
	        }
	        
	        try {
				conexao.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  
	 }
	 
	
	 /*
	 public static void main (String [] args){
		 ConexaoMySQL conexao = ConexaoMySQL.getInstance();
		 
		 InsereDadosBanco insere = new InsereDadosBanco();
		 insere.inserirTela(conexao.getConexaoMySQL(), 1, "tela1", 1, 1);
		 insere.inserirJogador(conexao.getConexaoMySQL(), "Willian testando tela 1", "tela1", 1, 1, 1);
	 }
	 */

}
