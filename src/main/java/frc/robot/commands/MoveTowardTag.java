// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Limelight;
import frc.robot.subsystems.DriveTrain;

import static frc.robot.Constants.MoveTowardTag.*;

public class MoveTowardTag extends Command {

    private DriveTrain driveTrain;

    public MoveTowardTag(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        double xAngle = Limelight.entry("tx").getDouble(0);
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

        if (Limelight.targetPos() == null) {
            CommandScheduler.getInstance().schedule(new SearchForTag(driveTrain));
        }
    }

    @Override
    public boolean isFinished() {
        var targetPos = Limelight.targetPos();
        return targetPos != null && targetPos.getZ() < SHOOT_DISTANCE;
    }
}