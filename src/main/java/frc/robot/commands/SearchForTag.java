// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;

import static frc.robot.Constants.SearchForTag.*;

public class SearchForTag extends CommandBase {

    private DriveTrain driveTrain;
    private Limelight limelight;

    public SearchForTag(DriveTrain driveTrain, Limelight limelight) {
        this.driveTrain = driveTrain;
        this.limelight = limelight;
        addRequirements(driveTrain, limelight);
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
        return limelight.hasTarget();
    }
}