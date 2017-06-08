package org.frc4931.robot.shooter;

import org.strongback.command.Requirable;
import org.strongback.components.Motor;

/**
 * Created by jcrane on 5/26/17.
 */
public class Shooter implements Requirable {
    private static final double shooterSpeed = 1.0;
    private final Motor shootingMotor;
    private boolean isShootng = false;

    public Shooter(Motor shootingMotor) {
        this.shootingMotor = shootingMotor;
    }

    public void shoot () {
        shootingMotor.setSpeed(shooterSpeed);
    }

    public void stopShooting () {
        shootingMotor.setSpeed(0);
    }

    public void changeShootState() {
        if (isShootng)
            stopShooting();
        else
            shoot();
        isShootng = !isShootng;
    }

}
