package br.ufv.willian.auxiliares;

import java.io.IOException;

import br.ufv.dpi.DadosAvaliacaoTelas;
import dk.itu.mario.MarioInterface.GamePlay;

public class GamePlayerTela {
	
	
	
	public static void main(String [] args){
		DadosAvaliacaoTelas dados = new DadosAvaliacaoTelas();
		try {
			dados = dados.carregarArquivo("tela_data");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GamePlay gp = dados.getGamePlay();
		//GamePlay gp = new GamePlay();
		//gp = gp.read("player.txt");
		
		
		System.out.println("" +
				"completionTime:" + gp.completionTime + "\n" +
				"totalTime:" + gp.totalTime + "\n" + //sums all the time, including from previous games if player died
				"jumpsNumber:" + gp.jumpsNumber + "\n" +  // total number of jumps
				"duckNumber:" + gp.duckNumber + "\n" +  //total number of ducks
				"timeSpentDucking:" + gp.timeSpentDucking + "\n" +  // time spent in ducking mode
				"emptyBlocksDestroyed:" + gp.emptyBlocksDestroyed + "\n" +  //number of empty blocks destroyed
				"coinsCollected:" + gp.coinsCollected + "\n" +  //number of coins collected
				"coinBlocksDestroyed:" + gp.coinBlocksDestroyed + "\n" +  //number of coin block destroyed
				"powerBlocksDestroyed:" + gp.powerBlocksDestroyed + "\n" +  //number of power block destroyed
				"kickedShells:" + gp.kickedShells + "\n" +  //number of shells Mario kicked
				"enemyKillByFire:" + gp.enemyKillByFire + "\n" +  //number of enemies killed by shooting them
				"enemyKillByKickingShell:" + gp.enemyKillByKickingShell + "\n" +  //number of enemies killed by kicking a shell on them
				"totalTimeLittleMode:" + gp.totalTimeLittleMode + "\n" +  //total time spent in little mode
				"totalTimeLargeMode:" + gp.totalTimeLargeMode + "\n" +  //total time spent in large mode
				"totalTimeFireMode:" + gp.totalTimeFireMode + "\n" +  //total time spent in fire mode
				"timesSwichingPower:" + gp.timesSwichingPower + "\n" + //number of Times Switched Between Little, Large or Fire Mario
				"aimlessJumps:" + gp.aimlessJumps + "\n" + //number of jumps without a reason
				"percentageBlocksDestroyed:" + gp.percentageBlocksDestroyed + "\n" +  //percentage of all blocks destroyed
				"percentageCoinBlocksDestroyed:" + gp.percentageCoinBlocksDestroyed + "\n" +  //percentage of coin blocks destroyed
				"percentageEmptyBlockesDestroyed:" + gp.percentageEmptyBlockesDestroyed + "\n" +  //percentage of empty blocks destroyed
				"percentagePowerBlockDestroyed:" + gp.percentagePowerBlockDestroyed + "\n" + //percentage of power blocks destroyed
				"timesOfDeathByFallingIntoGap:" + gp.timesOfDeathByFallingIntoGap + "\n" +  //number of death by falling into a gap
				"totalEnemies:" + gp.totalEnemies + "\n" +  //total number of enemies
				"totalEmptyBlocks:" + gp.totalEmptyBlocks + "\n" +  //total number of empty blocks
				"totalCoinBlocks:" + gp.totalCoinBlocks + "\n" + //total number of coin blocks
				"totalpowerBlocks:" + gp.totalpowerBlocks + "\n" +  //total number of power blocks
				"totalCoins:" + gp.totalCoins + "\n" +  //total number of coins
				"timesOfDeathByRedTurtle:" + gp.timesOfDeathByRedTurtle + "\n" +  //number of times Mario died by red turtle
				"timesOfDeathByGoomba:" + gp.timesOfDeathByGoomba + "\n" +  //number of times Mario died by Goomba
				"timesOfDeathByGreenTurtle:" + gp.timesOfDeathByGreenTurtle + "\n" +  //number of times Mario died by green turtle
				"timesOfDeathByArmoredTurtle:" + gp.timesOfDeathByArmoredTurtle + "\n" +  //number of times Mario died by Armored turtle
				"timesOfDeathByJumpFlower:" + gp.timesOfDeathByJumpFlower + "\n" +  //number of times Mario died by Jump Flower
				"timesOfDeathByCannonBall:" + gp.timesOfDeathByCannonBall + "\n" +  //number of time Mario died by Cannon Ball
				"timesOfDeathByChompFlower:" + gp.timesOfDeathByChompFlower + "\n" +  //number of times Mario died by Chomp Flower
				"RedTurtlesKilled:" + gp.RedTurtlesKilled + "\n" +  //number of Red Turtle Mario killed
				"GreenTurtlesKilled:" + gp.GreenTurtlesKilled + "\n" + //number of Green Turtle Mario killed
				"ArmoredTurtlesKilled:" + gp.ArmoredTurtlesKilled + "\n" +  //number of Armored Turtle Mario killed
				"GoombasKilled:" + gp.GoombasKilled + "\n" +  //number of Goombas Mario killed
				"CannonBallKilled:" + gp.CannonBallKilled + "\n" +  //number of Cannon Ball Mario killed
				"JumpFlowersKilled:" + gp.JumpFlowersKilled + "\n" +  //number of Jump Flower Mario killed
				"ChompFlowersKilled:" + gp.ChompFlowersKilled + "\n"  +  //number of Chomp Flower Mario killed
				"");
		
		
	}

}
