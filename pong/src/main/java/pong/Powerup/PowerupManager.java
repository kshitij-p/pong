package pong.Powerup;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import pong.GlobalConstants;
import pong.Ball.Ball;
import pong.Player.Player;
import pong.util.PLAYER_ENUM;

public class PowerupManager {

    public ArrayList<Powerup> pickablePowerups;
    private Player player1;
    private Player player2;

    private ScheduledExecutorService executorService;
    private ScheduledFuture<?> player1PowerupFuture;
    private ScheduledFuture<?> player2PowerupFuture;

    public PowerupManager(Player player1, Player player2) {
        this.pickablePowerups = new ArrayList<>(2);
        this.player1 = player1;
        this.player2 = player2;

        this.executorService = Executors.newScheduledThreadPool(1);
    }

    public void drawPowerups(Graphics2D g2) {
        for (Powerup powerup : pickablePowerups) {
            powerup.draw(g2);
        }
    }

    public void getCollision(Ball ball) {

        double ballLeft = ball.getX();
        double ballTop = ball.getY();

        double ballRight = ballLeft + ball.getWidth();
        double ballBot = ballTop + ball.getHeight();

        int toRemove = -1;

        for (int i = 0; i < pickablePowerups.size(); i++) {
            Powerup powerup = pickablePowerups.get(i);
            double powerupLeft = powerup.getX();
            double powerupTop = powerup.getY();

            double powerupRight = powerupLeft + powerup.getWidth();
            double powerupBot = powerupTop + powerup.getHeight();

            if (ballLeft >= powerupLeft && ballRight <= powerupRight && ballTop >= powerupTop
                    && ballBot <= powerupBot) {

                toRemove = i;
                break;
            }
        }

        if (toRemove == -1)
            return;

        PLAYER_ENUM toGiveTo = ball.hitBy();

        if (!canGivePowerup(toGiveTo))
            return;

        Powerup powerup = pickablePowerups.remove(toRemove);
        giveAndApplyPowerUp(toGiveTo, powerup);

    }

    public PowerupManager spawnPowerup() {
        // To do make it spawn power ups only if 1 player can receive them and spawn it
        // towards the player who doesnt have it
        if (pickablePowerups.size() >= GlobalConstants.MAX_PICKABLE_POWER_UPS) {
            return this;
        }

        pickablePowerups.add(new Speedup(300, 180, this));
        pickablePowerups.add(new Speedup(400, 120, this));

        return this;
    }

    public boolean canGivePowerup(PLAYER_ENUM toGiveTo) {
        if (toGiveTo == PLAYER_ENUM.ONE)
            return player1.powerup == null;
        return player2.powerup == null;
    }

    public PowerupManager giveAndApplyPowerUp(PLAYER_ENUM playerToAssign, Powerup powerup) {

        if (playerToAssign == PLAYER_ENUM.ONE && player1.powerup == null) {
            player1.powerup = powerup;
            powerup.applyPowerup(player1.getController());

            player1PowerupFuture = executorService.schedule(() -> {

                removePowerUpFor(playerToAssign);
            }, 3, TimeUnit.SECONDS);

        } else if (playerToAssign == PLAYER_ENUM.TWO && player2.powerup == null) {
            player2.powerup = powerup;
            powerup.applyPowerup(player2.getController());

            player2PowerupFuture = executorService.schedule(() -> {

                removePowerUpFor(playerToAssign);
            }, 10, TimeUnit.SECONDS);
        }

        return this;
    }

    public PowerupManager removePowerUpFor(PLAYER_ENUM playerToReset) {
        if (playerToReset == PLAYER_ENUM.ONE) {
            if (player1PowerupFuture != null) {
                player1PowerupFuture.cancel(true);
                player1PowerupFuture = null;
            }
            if (player1.powerup != null) {
                player1.powerup.removePowerup(player1.getController());
                player1.powerup = null;
            }
        } else {
            if (player2PowerupFuture != null) {
                player2PowerupFuture.cancel(true);
                player2PowerupFuture = null;
            }
            if (player2.powerup != null) {
                player2.powerup.removePowerup(player2.getController());
                player2.powerup = null;
            }

        }
        return this;
    }

    public PowerupManager removePowerupForAll() {

        removePowerUpFor(PLAYER_ENUM.ONE);
        removePowerUpFor(PLAYER_ENUM.TWO);
        return this;
    }

}
