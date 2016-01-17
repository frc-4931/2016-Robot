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

package org.frc4931.robot.map;

import org.strongback.components.Switch;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.components.ui.InputDevice;
import org.strongback.hardware.Hardware;

/**
 * An input map that utilizes the button pad on our operator console.
 */
public class ConsoleInputMap implements InputMap {
    private InputDevice console;
    private FlightStick driveStick;

    public ConsoleInputMap() {
        console = Hardware.HumanInterfaceDevices.driverStationJoystick(0);
        driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(1);
    }

    @Override
    public ContinuousRange getDriveSpeed() {
        return driveStick.getPitch().invert();
    }

    @Override
    public ContinuousRange getTurnSpeed() {
        return driveStick.getYaw();
    }

    @Override
    public Switch getFlipSwitch() {
        return driveStick.getThumb();
    }
}
