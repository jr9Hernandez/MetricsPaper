package petermawhorter;

import dk.itu.mario.MarioInterface.GamePlay;

import petermawhorter.components.Hierarchy;

public class SkillJudge {

  public static int SLOW_SPEED_THRESHOLD = 120;
  public static int MEDIUM_SPEED_THRESHOLD = 50;
  public static double WALK_THRESHOLD = 0.4;
  public static double KICKER_THRESHOLD = 0.4;
  public static double ACCURATE_THRESHOLD = 1.2;
  public static int POWERUPS_THRESHOLD = 6;
  public static double LOW_SWITCH_THRESHOLD = 1.1;
  public static double HIGH_SWITCH_THRESHOLD = 0.7;

  public static int CANNON = Hierarchy.FLOWER + 1;
  public static int PIT = Hierarchy.FLOWER + 2;

  public boolean completed = false;
  public boolean nodeaths = false;
  public int speed = 0; // 0 - slow; 1 - medium; 2 - fast
  public boolean walker = false;
  public double completionist = 0;
  public double deconstructive = 0;
  public boolean kicker = false;
  public boolean accurate = false;
  public double pyro = 0;
  public int killer1 = -1;
  public int killer2 = -1;
  public boolean diver = false;
  public boolean shapechanger = false;

  public static SkillJudge judge(GamePlay trace) {
    int deaths = (
      trace.timesOfDeathByRedTurtle +
      trace.timesOfDeathByGoomba +
      trace.timesOfDeathByGreenTurtle +
      trace.timesOfDeathByArmoredTurtle +
      trace.timesOfDeathByJumpFlower +
      trace.timesOfDeathByCannonBall +
      (int) trace.timesOfDeathByFallingIntoGap +
      trace.timesOfDeathByChompFlower
    );
    int kills = (
      trace.GoombasKilled +
      trace.RedTurtlesKilled +
      trace.GreenTurtlesKilled +
      trace.ArmoredTurtlesKilled +
      trace.JumpFlowersKilled +
      trace.ChompFlowersKilled +
      trace.CannonBallKilled
    );

    SkillJudge result = new SkillJudge();

    // Completed?
    if (deaths < 3 && trace.completionTime < 200) {
      result.completed = true;
    } else {
      result.completed = false;
    }
    // Deaths:
    if (trace.totalTime > trace.completionTime) {
      result.nodeaths = false;
    } else {
      result.nodeaths = true;
    }
    // Speed:
    if (trace.completionTime > SkillJudge.SLOW_SPEED_THRESHOLD) {
      result.speed = 0;
    } else if (trace.completionTime > SkillJudge.MEDIUM_SPEED_THRESHOLD) {
      result.speed = 1;
    } else {
      result.speed = 2;
    }
    // Walker?
    if (
      trace.completionTime > 0 &&
      trace.timeSpentRunning / (double) trace.completionTime < WALK_THRESHOLD
    ) {
      result.walker = true;
    } else {
      result.walker = false;
    }
    // Completionist:
    if (trace.totalCoins > 0) {
      result.completionist = trace.coinsCollected / (double) trace.totalCoins;
    } else {
      result.completionist = 0;
    }

    // Deconstructive:
    if (trace.totalEmptyBlocks > 0) {
      result.deconstructive =
        trace.emptyBlocksDestroyed / (double) trace.totalEmptyBlocks;
    } else {
      result.deconstructive = 0;
    }

    // Kicker?
    result.kicker = false;
    if (
      (
        trace.RedTurtlesKilled + trace.GreenTurtlesKilled > 0
      ) && (
        (
          trace.kickedShells /
          (double) (trace.RedTurtlesKilled + trace.GreenTurtlesKilled)
        ) > KICKER_THRESHOLD
      )
    ) {
        result.kicker = true;
    }
    // Accurate?
    if (
      trace.kickedShells > 0 &&
      trace.enemyKillByKickingShell / (double) trace.kickedShells >
        ACCURATE_THRESHOLD
    ) {
      result.accurate = true;
    } else {
      result.accurate = false;
    }
    // Pyromaniac:
    if (kills > 0) {
      result.pyro = trace.enemyKillByFire / (double) kills;
    } else {
      result.pyro = 0;
    }

    // Killers:
    if (trace.timesOfDeathByRedTurtle > 0) {
      result.killer1 = Hierarchy.RED_KOOPA;
    } else if (trace.timesOfDeathByGoomba > 0) {
      result.killer1 = Hierarchy.GOOMBA;
    } else if (trace.timesOfDeathByGreenTurtle > 0) {
      result.killer1 = Hierarchy.GREEN_KOOPA;
    } else if (trace.timesOfDeathByArmoredTurtle > 0) {
      result.killer1 = Hierarchy.SPIKY;
    } else if (trace.timesOfDeathByJumpFlower > 0) {
      result.killer1 = Hierarchy.FLOWER;
    } else if (trace.timesOfDeathByCannonBall > 0) {
      result.killer1 = CANNON;
    } else if (trace.timesOfDeathByFallingIntoGap > 0) {
      result.killer1 = PIT;
    }

    if (
      (
        trace.timesOfDeathByRedTurtle > 0 &&
        result.killer1 != Hierarchy.RED_KOOPA
      ) || trace.timesOfDeathByRedTurtle > 1
    ) {
      result.killer2 = Hierarchy.RED_KOOPA;
    } else if (
      (
        trace.timesOfDeathByGoomba > 0 &&
        result.killer1 != Hierarchy.GOOMBA
      ) || trace.timesOfDeathByGoomba > 1
    ) {
      result.killer2 = Hierarchy.GOOMBA;
    } else if (
      (
        trace.timesOfDeathByGreenTurtle > 0 &&
        result.killer1 != Hierarchy.GREEN_KOOPA
      ) || trace.timesOfDeathByGreenTurtle > 1
    ) {
      result.killer2 = Hierarchy.GREEN_KOOPA;
    } else if (
      (
        trace.timesOfDeathByArmoredTurtle > 0 &&
        result.killer1 != Hierarchy.SPIKY
      ) || trace.timesOfDeathByArmoredTurtle > 1
    ) {
      result.killer2 = Hierarchy.SPIKY;
    } else if (
      (
        trace.timesOfDeathByJumpFlower > 0 &&
        result.killer1 != Hierarchy.FLOWER
      ) || trace.timesOfDeathByJumpFlower > 1
    ) {
      result.killer2 = Hierarchy.FLOWER;
    } else if (
      (
        trace.timesOfDeathByCannonBall > 0 &&
        result.killer1 != CANNON
      ) || trace.timesOfDeathByCannonBall > 1
    ) {
      result.killer2 = CANNON;
    } else if (
      (
        trace.timesOfDeathByFallingIntoGap > 0 &&
        result.killer1 != PIT
      ) || trace.timesOfDeathByFallingIntoGap > 1
    ) {
      result.killer2 = PIT;
    }

    // Diver?
    if (result.killer1 == PIT && result.killer2 == PIT) {
      result.diver = true;
    } else {
      result.diver = false;
    }

    if (
      trace.totalpowerBlocks > 0 &&
      (
        trace.totalpowerBlocks < POWERUPS_THRESHOLD &&
        trace.timesSwichingPower / (double) trace.totalpowerBlocks >
          LOW_SWITCH_THRESHOLD
      ) ||
      (
        trace.totalpowerBlocks >= POWERUPS_THRESHOLD &&
        trace.timesSwichingPower / (double) trace.totalpowerBlocks >
          HIGH_SWITCH_THRESHOLD
      )
    ) {
      result.shapechanger = true;
    } else {
      result.shapechanger = false;
    }

    return result;
  }
}
