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

package org.frc4931.robot.arm;

import org.strongback.command.Command;
import org.strongback.components.Switch;
import org.strongback.control.TalonController;

public class LowerArmWhile extends Command {
    private final Arm arm;
    private final Switch shouldContinue;

    public LowerArmWhile(Arm arm, Switch shouldContinue) {
        super(arm);
        this.arm = arm;
        this.shouldContinue = shouldContinue;
    }

    @Override
    public void initialize() {
        arm.setControlMode(TalonController.ControlMode.PERCENT_VBUS);
        arm.lower();
    }

    @Override
    public boolean execute() {
        return !shouldContinue.isTriggered();
    }

    @Override
    public void end() {
        arm.stop();
    }
}
