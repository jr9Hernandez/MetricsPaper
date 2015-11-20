package br.ufv.willian.auxiliares;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RankingJogador {
	
	private HttpURLConnection httpConn;
	public int num_jogadores = -1 , partidas_pos_jog = -1, mortes_pos_jog = -1, moedas_pos_jogador = -1, inimigos_pos_jogador = -1, partidas_total_jog = -1, 
			partidas_1_total = -1, mortes_total_jog = -1, mortes_1_mortes = -1, moedas_total_jog = -1, moedas_1_moedas = -1, inimigos_total_jog = -1, 
			inimigos_1_inimigos = -1, mortes_1_fases = -1, moedas_1_fases = -1, inimigos_1_fases = -1;
	
	public ArrayList<String> copiaRanking(String user) throws IOException{
		//String requestURL ="http://200.235.131.136/mario/index.php?user=" + user;
		String requestURL ="http://localhost/mario/index.php?user=" + user;
		URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        ArrayList<String> response = new ArrayList<String>();
		int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.add(line);
            }
            reader.close();
            httpConn.disconnect();
        }
		return response; 
	}
	
	public void separaRanking(String texto){
		
		char[] array = texto.toCharArray();
		ArrayList<String> ranking = new ArrayList<String>();
		String aux = "";
		for(int i = 0; i < array.length -1 ; i++){
			if(array[i] == '-'){
				ranking.add(aux);
				aux = "";				
			}
			else
				aux += array[i];			
		}
		ranking.add(aux);
		if(ranking.size() != 16)
			return;
		num_jogadores        = Integer.parseInt(ranking.get(0));
		partidas_pos_jog     = Integer.parseInt(ranking.get(1));
		partidas_total_jog   = Integer.parseInt(ranking.get(2));
		partidas_1_total     = Integer.parseInt(ranking.get(3));
		mortes_pos_jog       = Integer.parseInt(ranking.get(4));		
		mortes_total_jog     = Integer.parseInt(ranking.get(5));
		mortes_1_mortes      = Integer.parseInt(ranking.get(6));
		mortes_1_fases       = Integer.parseInt(ranking.get(7));
		moedas_pos_jogador   = Integer.parseInt(ranking.get(8));
		moedas_total_jog     = Integer.parseInt(ranking.get(9));
		moedas_1_moedas      = Integer.parseInt(ranking.get(10));
		moedas_1_fases       = Integer.parseInt(ranking.get(11));
		inimigos_pos_jogador = Integer.parseInt(ranking.get(12));
		inimigos_total_jog   = Integer.parseInt(ranking.get(13));
		inimigos_1_inimigos  = Integer.parseInt(ranking.get(14));
		inimigos_1_fases     = Integer.parseInt(ranking.get(15));
		
	}
	
	public static void main(String [] args) throws IOException{
		
		RankingJogador ranking = new RankingJogador();
		ArrayList<String> resultado = ranking.copiaRanking("user1354482102-26-10-2014-13h46m13s114ms"); 
		for(int i=0; i<resultado.size(); i++){
			System.out.println(resultado.get(i));
		}
		
		ranking.separaRanking(resultado.get(0));
		System.out.println("num_jogadores: " + ranking.num_jogadores + 
				"\npos_partidas: " + ranking.partidas_pos_jog  + " partidas_total_jog: " + ranking.partidas_total_jog +  " partidas_1_total: " + ranking.partidas_1_total +
				"\nmortes_pos_jog: " +  ranking.mortes_pos_jog + " mortes_total_jog: " +  ranking.mortes_total_jog + " mortes_1_mortes: " +  ranking.mortes_1_mortes + " mortes_1_fases: " +ranking.mortes_1_fases +
				"\nmoedas_pos_jogador: " + ranking.moedas_pos_jogador + " moedas_total_jog: " + ranking.moedas_total_jog + " moedas_1_moedas: " + ranking.moedas_1_moedas + " moedas_1_fases: " + ranking.moedas_1_fases +
				"\ninimigos_pos_jogador: " + ranking.inimigos_pos_jogador + " inimigos_total_jog:" + ranking.inimigos_total_jog + " inimigos_1_inimigos:" + ranking.inimigos_1_inimigos + " inimigos_1_fases: " +ranking.inimigos_1_fases
				);
	}

}
