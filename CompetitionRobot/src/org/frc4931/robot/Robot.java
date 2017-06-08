/*
 * Copyright (c) 2016 FRC Team 4931
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/* Created Sun Jan 10 12:59:55 CST 2016 */
package org.frc4931.robot;

import org.frc4931.robot.drive.DriveSystem;
import org.frc4931.robot.shooter.Shooter;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.DistanceSensor;
import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    private static final String LOG_FILES_DIRECTORY_PATH = "/home/lvuser/frc4931";

    private static final int LEFT_FRONT_MOTOR_PWM_CHANNEL = 2;
    private static final int LEFT_REAR_MOTOR_PWM_CHANNEL = 3;
    private static final int RIGHT_FRONT_MOTOR_PWM_CHANNEL = 0;
    private static final int RIGHT_REAR_MOTOR_PWM_CHANNEL = 1;
    private static final int LEFT_SHOOTER_PWM_CHANNEL = 4;
    private static final int RIGHT_SHOOTER_PWM_CHANNEL = 5;
    private static final int FORWARD_AIN_CHANNEL = 0;
    private static final double FORWARD_INCHES_PER_VOLT = 102.4;

    private DriveSystem drive;
    private ContinuousRange driveSpeed;
    private ContinuousRange turnSpeed;
    private double driveScale = 1.0;

    @Override
    public void robotInit() {
        Strongback.configure();
                // .recordEventsToFile(LOG_FILES_DIRECTORY_PATH, 2097152)
                // .recordDataToFile(LOG_FILES_DIRECTORY_PATH)
                //.initialize();

        // Define the motors and the drive system ...
        Motor leftFrontMotor = Hardware.Motors.victorSP(LEFT_FRONT_MOTOR_PWM_CHANNEL);
        Motor leftRearMotor = Hardware.Motors.victorSP(LEFT_REAR_MOTOR_PWM_CHANNEL);
        Motor rightFrontMotor = Hardware.Motors.victorSP(RIGHT_FRONT_MOTOR_PWM_CHANNEL);
        Motor rightRearMotor = Hardware.Motors.victorSP(RIGHT_REAR_MOTOR_PWM_CHANNEL);
        Motor leftMotors = Motor.compose(leftFrontMotor, leftRearMotor);
        Motor rightMotors = Motor.compose(rightFrontMotor, rightRearMotor).invert();
        Motor leftShooter = Hardware.Motors.talonSRX(LEFT_SHOOTER_PWM_CHANNEL);
        Motor rightShooter = Hardware.Motors.talonSRX(RIGHT_SHOOTER_PWM_CHANNEL).invert();
        Motor shooterMotors = Motor.compose(leftShooter, rightShooter);
        TankDrive tankDrive = new TankDrive(leftMotors, rightMotors);
        DistanceSensor forward = Hardware.DistanceSensors.analogUltrasonic(FORWARD_AIN_CHANNEL, FORWARD_INCHES_PER_VOLT);
        Shooter shooter = new Shooter(shooterMotors);
        drive = new DriveSystem(tankDrive, forward);

        // Define the interface components ...
        FlightStick driverJoystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
        ContinuousRange throttle = driverJoystick.getThrottle().map(t -> (1.0 - t) / 2);
        driveSpeed = driverJoystick.getPitch().scale(throttle::read).scale(() -> driveScale).invert();
        turnSpeed = driverJoystick.getYaw().scale(throttle::read);
        Switch flipDirection = driverJoystick.getTrigger();
        Switch shoot = driverJoystick.getThumb();

        // Register the functions that run when the switches change state ...
        SwitchReactor reactor = Strongback.switchReactor();
        reactor.onTriggered(shoot,  () -> shooter.changeShootState());
        reactor.onTriggered(flipDirection, () -> shooter.shoot());
        reactor.onUntriggered(flipDirection, () -> shooter.stopShooting());

        //reactor.onTriggered(flipDirection, () -> driveScale *= -1.0);

    }

    @Override
    public void autonomousInit() {
        // Start Strongback functions ...
        Strongback.restart();
    }

    @Override
    public void teleopInit() {
        // Kill anything running, and start it ...
        Strongback.restart();
    }

    @Override
    public void teleopPeriodic() {
        drive.arcade(driveSpeed.read(), turnSpeed.read());

        SmartDashboard.putNumber("Throttle", driveSpeed.read());

        double forwardDistance = drive.getForwardProximity().getDistanceInInches();
        SmartDashboard.putNumber("Forward Distance", forwardDistance);
        SmartDashboard.putBoolean("Distance < 12in", forwardDistance < 12.0);
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

    @Override
    public void testInit() {
        Strongback.restart();
    }
}
