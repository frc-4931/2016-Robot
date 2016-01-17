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

import edu.wpi.first.wpilibj.IterativeRobot;
import org.frc4931.robot.drive.DriveSystem;
import org.frc4931.robot.map.HardwareMap;
import org.frc4931.robot.map.InputMap;
import org.frc4931.robot.map.PortableInputMap;
import org.frc4931.robot.map.ScorpioHardwareMap;
import org.strongback.Strongback;
import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;

public class Robot extends IterativeRobot {
    private DriveSystem driveSystem;
    private ContinuousRange driveSpeed;
    private ContinuousRange turnSpeed;
    private Switch flipDirection;
    private boolean lastFlipState;

    @Override
    public void robotInit() {
        HardwareMap hardwareMap = new ScorpioHardwareMap();
        InputMap inputMap = new PortableInputMap();

        driveSystem = hardwareMap.getDriveSystem();
        driveSpeed = inputMap.getDriveSpeed();
        turnSpeed = inputMap.getTurnSpeed();
        flipDirection = inputMap.getFlipSwitch();
    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
        driveSystem.arcade(driveSpeed.read(), turnSpeed.read());

        boolean flip = flipDirection.isTriggered();
        if (flip && !lastFlipState) {
            driveSystem.toggleDirectionFlipped();
        }
        lastFlipState = flip;
    }

    @Override
    public void disabledInit() {
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();

        lastFlipState = false;
    }
}
