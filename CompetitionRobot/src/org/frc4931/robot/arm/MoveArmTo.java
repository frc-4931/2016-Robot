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

/**
 * This is the command to move the arm to a specified angle
 * 
 * @author Julian Nieto
 *
 */
public class MoveArmTo extends org.strongback.command.Command {
    private static final double DEGREE_TOLERANCE = 5.0;
    private static final double SHOVEL_OF_FRIES_DOWN = 190.0;
    private static final double SHOVEL_OF_FRIES_UP  = 130.0;
    private static final double PORTICULUS_DOWN = 190.0;
    private static final double PORTICULUS_UP = 130.0;

    private final Arm arm;
    private final double targetAngle;

    /**
     * Create a command with the desired target angle.
     *
     * @param arm arm inputed
     * @param targetAngle degrees desired
     */
    public MoveArmTo(Arm arm, double targetAngle) {
        super(arm);
        this.arm = arm;
        this.targetAngle = targetAngle;
    }

    @Override
    public boolean execute() {
        if (arm.getAngle() > targetAngle) {
            arm.lower();
        } else {
            arm.raise();
        }
        return Math.abs(arm.getAngle() - targetAngle) <= DEGREE_TOLERANCE;
    }

    @Override
    public void end() {
        arm.stop();
    }
}
