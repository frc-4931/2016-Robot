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

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.vision.USBCamera;
import org.frc4931.robot.arm.Arm;
import org.frc4931.robot.arm.CalibrateArm;
import org.frc4931.robot.drive.DriveSystem;
import org.frc4931.robot.drive.TimedDrive;
import org.frc4931.robot.roller.Roller;
import org.strongback.Strongback;
import org.strongback.SwitchReactor;
import org.strongback.components.Motor;
import org.strongback.components.Switch;
import org.strongback.components.TalonSRX;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

public class Robot extends IterativeRobot {

    private static final String LOG_FILES_DIRECTORY_PATH = "/home/lvuser/";
    private static final int LEFT_FRONT_MOTOR_CHANNEL = 2;
    private static final int LEFT_REAR_MOTOR_CHANNEL = 3;
    private static final int RIGHT_FRONT_MOTOR_CHANNEL = 0;
    private static final int RIGHT_REAR_MOTOR_CHANNEL = 1;
    private static final int ROLLER_MOTOR_CAN_ID = 0;
    private static final int ARM_MOTOR_CAN_ID = 1;
    private static final double ARM_PULSES_PER_DEGREE = 7.0 * 71.0 / 360.0;

    private DriveSystem drive;
    private Arm arm;
    private Roller roller;
    private ContinuousRange driveSpeed;
    private ContinuousRange turnSpeed;
    private Switch armUp;
    private Switch armDown;
    private Switch rollerIn;
    private Switch rollerOut;

    public static final double AUTO_DRIVE_SPEED=1;
    public static final double AUTO_DRIVE_TIME=2;

    @Override
    public void robotInit() {
        Strongback.configure()
//                  .recordNoData().recordNoEvents().recordNoCommands();
                  .recordDataToFile(LOG_FILES_DIRECTORY_PATH)
                  .recordEventsToFile(LOG_FILES_DIRECTORY_PATH, 2097152);

        // Initialize the camera server
        USBCamera camera = new USBCamera("cam1");
        camera.setSize(240, 180);
        CameraServer.getInstance().setQuality(50);
        CameraServer.getInstance().startAutomaticCapture(camera);

        // Define the motors and the drive system ...
        Motor leftFrontMotor = Hardware.Motors.victorSP(LEFT_FRONT_MOTOR_CHANNEL);
        Motor leftRearMotor = Hardware.Motors.victorSP(LEFT_REAR_MOTOR_CHANNEL);
        Motor rightFrontMotor = Hardware.Motors.victorSP(RIGHT_FRONT_MOTOR_CHANNEL);
        Motor rightRearMotor = Hardware.Motors.victorSP(RIGHT_REAR_MOTOR_CHANNEL);
        Motor leftMotors = Motor.compose(leftFrontMotor, leftRearMotor);
        Motor rightMotors = Motor.compose(rightFrontMotor, rightRearMotor).invert();
        TankDrive tankDrive = new TankDrive(leftMotors, rightMotors);
        drive = new DriveSystem(tankDrive);

        // Initialize the subsystems ...
        TalonSRX armMotor = Hardware.Motors.talonSRX(ARM_MOTOR_CAN_ID, ARM_PULSES_PER_DEGREE);
        arm = new Arm(armMotor);

        Motor rollerMotor = Hardware.Motors.talonSRX(ROLLER_MOTOR_CAN_ID);
        roller = new Roller(rollerMotor);

        // Define the interface components ...
        FlightStick joystick = Hardware.HumanInterfaceDevices.logitechAttack3D(0);
        ContinuousRange throttle = joystick.getThrottle().map(t -> (1.0 - t) / 2);
        driveSpeed = joystick.getPitch().scale(throttle::read).invert();
        turnSpeed = joystick.getYaw().scale(throttle::read);
        armUp = joystick.getButton(6);
        armDown = joystick.getButton(4);
		rollerIn = joystick.getButton(3);
        rollerOut = joystick.getButton(5);

        // Register the functions that run when the switches change state ...
        SwitchReactor reactor = Strongback.switchReactor();

        reactor.onTriggered(joystick.getTrigger(), drive::toggleDirectionFlipped);

        // Set up the data recorder to capture the left & right motor speeds and the sensivity.
        // We have to do this before we start Strongback...
//        Strongback.dataRecorder()
//                  .register("Left motors", leftMotors)
//                  .register("Right motors", rightMotors)
//                  .register("Sensitivity", throttle.scaleAsInt(1000));
    }

    @Override
    public void autonomousInit() {
        // Start Strongback functions ...
        Strongback.restart();
        Strongback.submit(new TimedDrive(drive,AUTO_DRIVE_SPEED,0,AUTO_DRIVE_TIME));
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // Kill anything running, and start it ...
        Strongback.restart();
    }

    @Override
    public void teleopPeriodic() {
        drive.arcade(driveSpeed.read(), turnSpeed.read());

        if (armUp.isTriggered() == armDown.isTriggered()) {
            arm.stop();
        } else if (armUp.isTriggered()) {
            arm.raise();
        } else if (armDown.isTriggered()) {
            arm.lower();
        }

        if (rollerIn.isTriggered() == rollerOut.isTriggered()) {
            roller.stop();
        } else if (rollerIn.isTriggered()) {
            roller.suck();
        } else if (rollerOut.isTriggered()) {
            roller.spit();
        }
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

    @Override
    public void testInit() {
        Strongback.restart();
        Strongback.submit(new CalibrateArm(arm));
    }
}
