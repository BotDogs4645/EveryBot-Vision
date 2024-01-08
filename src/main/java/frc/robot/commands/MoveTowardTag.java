// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;

import static frc.robot.Constants.MoveTowardTag.*;

public class MoveTowardTag extends CommandBase {

    private DriveTrain driveTrain;
    private Limelight limelight;

    public MoveTowardTag(DriveTrain driveTrain, Limelight limelight) {
        this.driveTrain = driveTrain;
        this.limelight = limelight;
        addRequirements(driveTrain, limelight);
    }

    @Override
    public void execute() {
        double xAngle = limelight.entry("tx").getDouble(0);
        if (xAngle == 0) return;

        if (xAngle < MIN_ANGLE) {
            driveTrain.arcadeDrive(MOVE_SPEED, -TURN_SPEED);
        } else if (xAngle > MAX_ANGLE) {
            driveTrain.arcadeDrive(MOVE_SPEED, TURN_SPEED);
        } else {
            driveTrain.arcadeDrive(MOVE_SPEED, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.stop();

        if (limelight.targetPos() == null) {
            CommandScheduler.getInstance().schedule(new SearchForTag(driveTrain, limelight));
        }
    }

    @Override
    public boolean isFinished() {
        var targetPos = limelight.targetPos();
        return targetPos != null && targetPos.getZ() < SHOOT_DISTANCE;
    }
}