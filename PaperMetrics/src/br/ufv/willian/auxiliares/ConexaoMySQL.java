package br.ufv.willian.auxiliares;

//Classes necessárias para uso de Banco de dados // 
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException; 


//Início da classe de conexão// 
public class ConexaoMySQL { 
	public static String status = "Não conectado...";
	public static ConexaoMySQL con;	
	public Connection connection;
	
	public static ConexaoMySQL getInstance(){
		if(con == null)
			con = new ConexaoMySQL();
		return con;
	}
	
	//Método Construtor da Classe// 
	public ConexaoMySQL() {
		
	} 
	
	
	//Método de Conexão// 
	public java.sql.Connection getConexaoMySQL() { 
		 
		//Connection connection = null; //atributo do tipo Connection
		if(connection == null){
		
			try { 
				// Carregando o JDBC Driver padrão 
				String driverName = "com.mysql.jdbc.Driver"; 
				Class.forName(driverName); 
				
				// Configurando a nossa conexão com um banco de dados// 
				String serverName = "localhost"; //caminho do servidor do BD 
				String mydatabase = "projeto_mario_bd"; //nome do seu banco de dados 
				String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 
				String username = "root"; //nome de um usuário de seu BD 
				String password = "mysqllevi"; //sua senha de acesso 
				//String password = ""; //sua senha de acesso
				connection = DriverManager.getConnection(url, username, password); 
				
				//Testa sua conexão// 
				if (connection != null) { 
					status = ("STATUS--->Conectado com sucesso!"); 
				} else { 
					status = ("STATUS--->Não foi possivel realizar conexão"); 
				} 
				
				return connection; 
				
			} catch (ClassNotFoundException e) { 
				//Driver não encontrado 
				System.out.println("O driver expecificado nao foi encontrado."); 
				return null; 
			} catch (SQLException e) { 
				//Não conseguindo se conectar ao banco 
				System.out.println("Nao foi possivel conectar ao Banco de Dados."); 
				return null; 
			}
		}else
			return connection;
	} 
	
	//Método que retorna o status da sua conexão// 
	public String statusConection() { 
		return status; 
	} 
	
	//Método que fecha sua conexão// 
	public boolean FecharConexao() { 
		try { 
			//ConexaoMySQL.getConexaoMySQL().close(); 
			getConexaoMySQL().close();
			status = "Não conectado...";
			return true; 
		} catch (SQLException e) { 
			return false; 
		} 
	} 
	
	//Método que reinicia sua conexão// 
	public java.sql.Connection ReiniciarConexao() { 
		FecharConexao();
		connection = null;
		return getConexaoMySQL(); 
	}
	
	/*
	public static void main(String[] args){
		
		ConexaoMySQL con = ConexaoMySQL.getInstance();
		
		System.out.println("Sua conexao antes de conectar: " + con.statusConection());
		
		//ConexaoMySQL.getConexaoMySQL();
		con.getConexaoMySQL();
		
		System.out.println("Após criar instancia da conexao: " + con.statusConection());
		
		con.FecharConexao();
		
		System.out.println("Sua conexao antes de conectar: " + con.statusConection());
			
		
	}*/
}
		
		
	
	

		
			
			
		
	


