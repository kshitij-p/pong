package pong.Powerup;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import pong.GlobalConstants;
import pong.Ball.Ball;
import pong.Player.Player;
import pong.util.PLAYER_ENUM;
import pong.util.util;

public class PowerupManager {

    public ArrayList<Powerup> pickablePowerups;
    private Player player1;
    private Player player2;

    private ScheduledExecutorService executorService;
    private ScheduledFuture<?> player1PowerupFuture;
    private ScheduledFuture<?> player2PowerupFuture;
    private ScheduledFuture<?> spawnPowerupFuture;

    public PowerupManager(Player player1, Player player2) {
        this.pickablePowerups = new ArrayList<>(2);
        this.player1 = player1;
        this.player2 = player2;

        this.executorService = Executors.newScheduledThreadPool(1);

    }

    public void reset() {
        dequeuePowerupCreation();
        removePowerupForAll();
        while (this.pickablePowerups.size() > 0) {
            this.pickablePowerups.remove(pickablePowerups.size() - 1);
        }
    }

    public boolean canSpawn() {
        return (player1.powerup == null || player2.powerup == null)
                && pickablePowerups.size() < GlobalConstants.MAX_PICKABLE_POWER_UPS;
    }

    public void drawPowerups(Graphics2D g2) {
        for (Powerup powerup : pickablePowerups) {
            powerup.draw(g2);
        }
    }

    public PowerupManager queuePowerupCreation() {
        if (!canSpawn() || spawnPowerupFuture != null)
            return this;
        spawnPowerupFuture = executorService.schedule(() -> spawnPowerup(),
                GlobalConstants.POWER_UP_SPAWN_INTERVAL_SECS, TimeUnit.SECONDS);
        return this;
    }

    public PowerupManager dequeuePowerupCreation() {
        if (spawnPowerupFuture != null) {
            spawnPowerupFuture.cancel(true);
            spawnPowerupFuture = null;
        }
        return this;
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
        if (spawnPowerupFuture != null) {
            spawnPowerupFuture.cancel(true);
            spawnPowerupFuture = null;
        }
        if (!canSpawn())
            return this;

        double powerupY = ThreadLocalRandom.current().nextDouble(GlobalConstants.POWER_UP_HEIGHT + util.getScreenTop(),
                util.getScreenBottom() - GlobalConstants.POWER_UP_HEIGHT);
        double powerupX = 0;

        double playerOffsetPadding = 10;

        double powerupXSpawnStart = GlobalConstants.POWER_UP_WIDTH + util.getScreenLeft()
                + GlobalConstants.PLAYER_POS_OFFSET + playerOffsetPadding;
        double powerupXSpawnEnd = (util.getScreenRight() - GlobalConstants.POWER_UP_WIDTH)
                - (GlobalConstants.PLAYER_POS_OFFSET + playerOffsetPadding);

        // If both dont have power up, spawn randomly anywhere
        if (player1.powerup == null && player2.powerup == null) {

            powerupX = ThreadLocalRandom.current().nextDouble(
                    powerupXSpawnStart,
                    powerupXSpawnEnd);

        } else if (player1.powerup == null) {
            // If player 1 doesnt have power up, spawn biased towards player1

            powerupX = ThreadLocalRandom.current().nextDouble(powerupXSpawnStart, GlobalConstants.WINDOW_WIDTH / 2);

        } else {
            // If player 2 doesnt have power up, spawn biased towards player1
            powerupX = ThreadLocalRandom.current().nextDouble(powerupXSpawnStart,
                    powerupXSpawnEnd);
        }

        pickablePowerups
                .add(new Speedup(powerupX, powerupY, this));

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
            }, GlobalConstants.POWER_UP_DURATION_SECS, TimeUnit.SECONDS);

        } else if (playerToAssign == PLAYER_ENUM.TWO && player2.powerup == null) {
            player2.powerup = powerup;
            powerup.applyPowerup(player2.getController());

            player2PowerupFuture = executorService.schedule(() -> {

                removePowerUpFor(playerToAssign);
            }, GlobalConstants.POWER_UP_DURATION_SECS, TimeUnit.SECONDS);
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
