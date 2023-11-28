// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.commands.MoveTowardTag;
import frc.robot.commands.SearchForTag;
import frc.robot.commands.ShootBalls;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

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

    // System.out.printf("z: %.3f\n", Limelight.targetPos().getZ());
  }

  @Override
  public void autonomousInit() {
    var search = Commands.sequence(
      // Commands.waitSeconds(100000000),
      new SearchForTag(driveTrain),
      new MoveTowardTag(driveTrain),
      new ShootBalls(intake)
    );

    // CommandScheduler.getInstance().schedule(search);

    // Arm down distance: 0.98m
    // Arm up distance: Like 0.71m
  }

  @Override
  public void autonomousPeriodic() {
    // double time = Timer.getFPGATimestamp();
    // double tx = Limelight.entry("tx").getDouble(Double.NaN);
    // double tv = Limelight.entry("tv").getDouble(Double.NaN);
    // Transform3d tpcs =
    // buildTransform(Limelight.entry("targetpose_cameraspace").getDoubleArray(new
    // double[0]), 3, 4, 5);

    // // System.out.printf("Area: %.2f", tempGet("ta").getDouble(-1.1));

// tv=>1.0
// tx=>20.336078643798828
// ty=>4.629744052886963
// tid=>1.0
// botpose_targetspace=>[D@1227d8d
// json=>{"Results":{"Bardcode":[],"Classifier":[],"Detector":[],"Fiducial":[{"fID":1,"fam":"16H5C","pts":[],"skew":[],"t6c_ts":[0.483653983615108,-0.4020882986298798,-3.636441675944039,-10.925767941398343,-29.57118812060138,0.8901967388468207],"t6r_fs":[3.6066576823463423,-3.4202391588029877,0.8648082986298798,0.7872941212983368,10.48545785837065,29.717304846901158],"t6r_ts":[0.483653983615108,-0.4020882986298798,-3.636441675944039,-10.925767941398343,-29.57118812060138,0.8901967388468207],"t6t_cs":[1.3794298173990611,-0.24198193400688628,3.4143699800679497,12.029996718448672,29.172806506933444,5.143028922616025],"t6t_rs":[1.3794298173990611,-0.24198193400688628,3.4143699800679497,12.029996718448672,29.172806506933444,5.143028922616025],"ta":0.0019492927240207791,"tx":20.336078820476573,"txp":1035.66015625,"ty":4.629744234985111,"typ":394.2979736328125}],"Retro":[],"botpose":[3.5616153144068567,-3.286451435514036,0.7033383845623493,-0.38767433066006174,8.22366010801189,27.399226001402962],"botpose_wpiblue":[11.832490314406858,0.7203985644859641,0.7033383845623493,-0.38767433066006174,8.22366010801189,27.399226001402962],"botpose_wpired":[4.70926840648688,7.293310886579812,0.7033383845623493,-0.38767433066006174,8.22366010801189,-152.60092603808843],"cl":37.619998931884766,"pID":0.0,"t6c_rs":[0.0,0.0,0.0,0.0,0.0,-0.0],"tl":62.874481201171875,"ts":1567091.568528,"v":1}}
// camerapose_robotspace=>[D@19ec67
// targetpose_cameraspace=>[D@1018215
// camerapose_targetspace=>[D@1d6b83b
// camMode=>0.0
// thor=>54.0
// tlong=>51.159095764160156
// botpose_wpired=>[D@1706f19
// cl=>37.619998931884766
// ta=>0.19462575018405914
// ledMode=>0.0
// tc=>[D@b7c004
// pipeline=>0.0
// botpose_wpiblue=>[D@1efed2d
// targetpose_robotspace=>[D@128854b
// tl=>62.874481201171875
// hb=>25268.0
// botpose=>[D@1ae2fd0
// tvert=>50.0
// tshort=>47.08235168457031
// ts=>2.290609836578369

    // for (var key : Limelight.TABLE.getKeys()) {
    //   System.out.println(key + "=>" + Limelight.entry(key).getValue().getValue());
    // }

    // System.out.println(Limelight.entry("tid").getInteger(-1));

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

    System.out.printf("Intake arm: %.3f\n", -xbox.getLeftY() * 0.2);
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
