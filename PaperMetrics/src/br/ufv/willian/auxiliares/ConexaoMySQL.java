package br.ufv.willian.auxiliares;

//Classes necess�rias para uso de Banco de dados // 
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException; 


//In�cio da classe de conex�o// 
public class ConexaoMySQL { 
	public static String status = "N�o conectado...";
	public static ConexaoMySQL con;	
	public Connection connection;
	
	public static ConexaoMySQL getInstance(){
		if(con == null)
			con = new ConexaoMySQL();
		return con;
	}
	
	//M�todo Construtor da Classe// 
	public ConexaoMySQL() {
		
	} 
	
	
	//M�todo de Conex�o// 
	public java.sql.Connection getConexaoMySQL() { 
		 
		//Connection connection = null; //atributo do tipo Connection
		if(connection == null){
		
			try { 
				// Carregando o JDBC Driver padr�o 
				String driverName = "com.mysql.jdbc.Driver"; 
				Class.forName(driverName); 
				
				// Configurando a nossa conex�o com um banco de dados// 
				String serverName = "localhost"; //caminho do servidor do BD 
				String mydatabase = "projeto_mario_bd"; //nome do seu banco de dados 
				String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 
				String username = "root"; //nome de um usu�rio de seu BD 
				String password = "mysqllevi"; //sua senha de acesso 
				//String password = ""; //sua senha de acesso
				connection = DriverManager.getConnection(url, username, password); 
				
				//Testa sua conex�o// 
				if (connection != null) { 
					status = ("STATUS--->Conectado com sucesso!"); 
				} else { 
					status = ("STATUS--->N�o foi possivel realizar conex�o"); 
				} 
				
				return connection; 
				
			} catch (ClassNotFoundException e) { 
				//Driver n�o encontrado 
				System.out.println("O driver expecificado nao foi encontrado."); 
				return null; 
			} catch (SQLException e) { 
				//N�o conseguindo se conectar ao banco 
				System.out.println("Nao foi possivel conectar ao Banco de Dados."); 
				return null; 
			}
		}else
			return connection;
	} 
	
	//M�todo que retorna o status da sua conex�o// 
	public String statusConection() { 
		return status; 
	} 
	
	//M�todo que fecha sua conex�o// 
	public boolean FecharConexao() { 
		try { 
			//ConexaoMySQL.getConexaoMySQL().close(); 
			getConexaoMySQL().close();
			status = "N�o conectado...";
			return true; 
		} catch (SQLException e) { 
			return false; 
		} 
	} 
	
	//M�todo que reinicia sua conex�o// 
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
		
		System.out.println("Ap�s criar instancia da conexao: " + con.statusConection());
		
		con.FecharConexao();
		
		System.out.println("Sua conexao antes de conectar: " + con.statusConection());
			
		
	}*/
}
		
		
	
	

		
			
			
		
	


