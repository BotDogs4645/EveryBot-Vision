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

  XboxController xbox = new XboxController(0);

  private DriveTrain driveTrain;
  private Intake intake;

  @Override
  public void robotInit() {
    driveTrain = new DriveTrain();
    intake = new Intake();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    var search = Commands.sequence(
      new SearchForTag(driveTrain),
      new MoveTowardTag(driveTrain),
      new ShootBalls(intake)
    );

    CommandScheduler.getInstance().schedule(search);
  }

  @Override
  public void autonomousPeriodic() {

    // Code to print every limelight key
    // for (var key : Limelight.TABLE.getKeys()) {
    //   System.out.println(key + "=>" + Limelight.entry(key).getValue().getValue());
    // }

    // Print target position
    var t = Limelight.targetPos();
    System.out.printf("x: %.3f, y: %.3f, z: %.3f\n", t.getX(), t.getY(), t.getZ());

  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    /*
     * Make leftr jpystick control arm, leave triggers for intake and percision
     * drive and maybe a turbo mode hehe
     * camden would love a lack of speed limitations
     */
    if (xbox.getRightBumper()) {
      driveTrain.arcadeDrive(-xbox.getRightY(), xbox.getRightX() * 0.6);
    } else if (xbox.getRightTriggerAxis() > 0.2) {
      driveTrain.arcadeDrive(-xbox.getRightY() * 0.4, xbox.getRightX() * 0.3);
    } else {
      driveTrain.arcadeDrive(-xbox.getRightY() * 0.6, xbox.getRightX() * 0.5);
    }

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
