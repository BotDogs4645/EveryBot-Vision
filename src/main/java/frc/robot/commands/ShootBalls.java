// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.Intake;

public class ShootBalls extends Command {

    private Intake intake;

    public ShootBalls(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        Commands.sequence(
            // Move arm up
            Commands.deadline(
                Commands.waitSeconds(0.5),
                Commands.run(() -> intake.setArm(0.5), intake)
            ),
            // Stop arm
            Commands.runOnce(() -> intake.setArm(0), intake),

            // Wait a bit
            Commands.waitSeconds(0.1),

            // Shoot balls
            Commands.deadline(
                Commands.waitSeconds(1),
                Commands.run(() -> intake.setIntake(0.5), intake)
            ),

            // Stop intake
            Commands.runOnce(() -> intake.setIntake(0), intake)
        ).handleInterrupt(() -> {
            intake.setArm(0);
            intake.setIntake(0);
        }).schedule();
    }
}