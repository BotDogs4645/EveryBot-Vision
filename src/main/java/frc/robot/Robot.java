// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.MoveTowardTag;
import frc.robot.commands.SearchForTag;
import frc.robot.commands.ShootBalls;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Limelight;

import static frc.robot.Constants.Ports.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private XboxController xbox = new XboxController(CONTROLLER);

  private DriveTrain driveTrain;
  private Intake intake;
  private Limelight limelight;

  @Override
  public void robotInit() {
    driveTrain = new DriveTrain();
    intake = new Intake();
    limelight = new Limelight();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

    var t = limelight.targetPos();
    if (t == null) {
      System.out.println("No Limelight target!");
    } else {
      System.out.printf("Target position: {x: %.3f, y: %.3f, z: %.3f}\n", t.getX(), t.getY(), t.getZ());
    }
  }

  @Override
  public void autonomousInit() {
    var search = Commands.sequence(
      new SearchForTag(driveTrain, limelight),
      new MoveTowardTag(driveTrain, limelight),
      new ShootBalls(intake)
    );

    CommandScheduler.getInstance().schedule(search);
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    // Right joystick controls movement (arcade drive)
    // Right bumper makes it go faster.
    double xScalar = 0.5;
    double zScalar = 0.4;

    if (xbox.getRightBumper()) {
      xScalar = 1;
      zScalar = 0.7;
    }

    double y = xbox.getRightY(), x = xbox.getRightX();

    driveTrain.arcadeDrive(
      -y * xScalar,
      x * zScalar
    );
    // Left joystick controls the arm.
    // Left bumper picks up with the intake and
    //  left trigger shoots with the intake.

    intake.setArm(-xbox.getLeftY() * 0.2);

    if (xbox.getLeftBumper()) {
      intake.setIntake(-1.0);
    } else if (xbox.getLeftTriggerAxis() > 0.2) {
      intake.setIntake(1.0);
    } else {
      intake.setIntake(0.0);
    }

  }

  @Override
  public void disabledInit() {
    driveTrain.stop();
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

  @Override
  public void simulationInit() {
  }

  @Override
  public void simulationPeriodic() {
  }
}
