// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Limelight;
import frc.robot.subsystems.DriveTrain;

import static frc.robot.Constants.SearchForTag.*;

public class SearchForTag extends Command {

    private DriveTrain driveTrain;

    public SearchForTag(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        driveTrain.setMotors(TURN_SPEED, -TURN_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.stop();
    }

    @Override
    public boolean isFinished() {
        return Limelight.hasTarget();
    }
}