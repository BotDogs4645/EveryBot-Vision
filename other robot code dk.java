// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import java.util.Arrays;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  private CANSparkMax m_leftMotor1;
  private CANSparkMax m_rightMotor1;
  private CANSparkMax m_leftMotor2;
  private CANSparkMax m_rightMotor2;
  private CANSparkMax m_arm;


  private VictorSPX m_intake;

  XboxController xbox = new XboxController(0);
  private double startTime;

  private MotorControllerGroup m_left, m_right;

  @Override
  public void robotInit() {
    m_leftMotor1= new CANSparkMax(7, MotorType.kBrushed);
    m_leftMotor2= new CANSparkMax(2, MotorType.kBrushed);
    m_rightMotor1= new CANSparkMax(3, MotorType.kBrushed);
    m_rightMotor2= new CANSparkMax(4, MotorType.kBrushed);
    m_arm = new CANSparkMax(5, MotorType.kBrushless);//arm motor NOT THE INTAKE MOTOR
    m_intake = new VictorSPX(6); // Intake motor

    m_left = new MotorControllerGroup(m_leftMotor1, m_leftMotor2);
    m_right = new MotorControllerGroup(m_rightMotor2, m_rightMotor1);

    m_right.setInverted(true);

    m_myRobot = new DifferentialDrive(m_left,m_right);
  
    

  }
  @Override
  public void robotPeriodic() {}


  @Override
  public void autonomousInit() {
    startTime = Timer.getFPGATimestamp();
  }

  boolean hasStop = false;

  NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry entry(String key) {
    return limelight.getEntry(key);
  }

  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();

    double tx = entry("tx").getDouble(Double.NaN);
    double ty = entry("ty").getDouble(Double.NaN);
    double ta = entry("ta").getDouble(Double.NaN);
    double ts = entry("ts").getDouble(Double.NaN);

    double thor = entry("thor").getDouble(Double.NaN);
    double tid = entry("tid").getInteger(-1);

    Transform3d tpcs = buildTransform(entry("targetpose_cameraspace").getDoubleArray(new double[0]), 3, 4, 5);

    System.out.println("====================");
    System.out.printf("x: %.2f, y: %.2f, area: %.2f, rotation: %.2f\n", tx, ty, ta, ts);
    System.out.printf("Target ID: %s\n", tid);
    System.out.printf("Target position in camera space: %s\n", tpcs);
    System.out.printf("BY THE POWER OF THOR: %.2f\n", thor);

    System.out.printf("Z value is: %.2f\n", tpcs.getZ());

    // Limelight is 35cm to the right and 44cm up

    // var led = entry("ledMode");

    // System.out.println(led.getNumber(0).equals(1.0));

    // if (led.getNumber(0).equals(1.0)) {
    //   led.setNumber(3);
    // } else if (led.getNumber(0).equals(3.0)) {
    //   led.setNumber(1);
    // }

    // for (var key : limelight.getKeys()) {
    //   var value = limelight.getEntry(key).getValue();
    //   var valRaw = value.getValue().toString();
    //   if (value.isDoubleArray()) {
    //     valRaw = Arrays.toString(value.getDoubleArray());
    //   }

    //   System.out.printf("%s => %s\n", key, valRaw);
    // }

// ﻿﻿﻿﻿﻿﻿ tv => 1.0 ﻿
// ﻿﻿﻿﻿﻿﻿ tx => 5.240962982177734 ﻿
// ﻿﻿﻿﻿﻿﻿ ty => -6.835796356201172 ﻿
// ﻿﻿﻿﻿﻿﻿ tid => 1.0 ﻿
// ﻿﻿﻿﻿﻿﻿ botpose_targetspace => [0.04699710486608206, -0.03547511913367262, -0.9183090966967813, 5.1623633410676115, -9.76361723866231, 0.4683196531482357] ﻿
// ﻿﻿﻿﻿﻿﻿ tclass =>  ﻿
// ﻿﻿﻿﻿﻿﻿ stream => 0.0 ﻿
// ﻿﻿﻿﻿﻿﻿ getpipe => 0.0 ﻿
// ﻿﻿﻿﻿﻿﻿ json => {"Results":{"Bardcode":[],"Classifier":[],"Detector":[],"Fiducial":[{"fID":1,"fam":"16H5C","pts":[],"skew":[],"t6c_ts":[0.04699710486608206,-0.03547511913367262,-0.9183090966967813,5.1623633410676115,-9.76361723866231,0.4683196531482357],"t6r_fs":[6.324790840947702,-2.9835858864582936,0.49819511913367265,0.4634053642845933,-5.241613298115701,9.721442743389517],"t6r_ts":[0.04699710486608206,-0.03547511913367262,-0.9183090966967813,5.1623633410676115,-9.76361723866231,0.4683196531482357],"t6t_cs":[0.10970120284828812,0.11785645879471456,0.9059990674572511,-5.317546025319511,9.680550247964433,-1.3601480536860207],"t6t_rs":[0.10970120284828812,0.11785645879471456,0.9059990674572511,-5.317546025319511,9.680550247964433,-1.3601480536860207],"ta":0.02584465593099594,"tx":5.240963019291982,"txp":735.7191772460938,"ty":-6.83579632061722,"typ":604.7164916992188}],"Retro":[],"botpose":[6.324791071691821,-2.98358740563441,0.49819470454722353,0.4634022282514483,-5.241638267226351,9.721540334105942],"botpose_wpiblue":[14.59566607169182,1.02326259436559,0.49819470454722353,0.4634022282514483,-5.241638267226351,9.721540334105942],"botpose_wpired":[1.946091845525041,6.990454189034965,0.49819470454722353,0.4634022282514483,-5.241638267226351,-170.2786117053855],"cl":37.619998931884766,"pID":0.0,"t6c_rs":[0.0,0.0,0.0,0.0,0.0,-0.0],"tl":63.74700164794922,"ts":1563972.553696,"v":1}} ﻿
// ﻿﻿﻿﻿﻿﻿ camerapose_robotspace => [0.0, 0.0, 0.0, 0.0, 0.0, -0.0] ﻿
// ﻿﻿﻿﻿﻿﻿ targetpose_cameraspace => [0.11044108806666356, 0.11830911571745464, 0.910026423797136, -3.1957223798311967, 9.916426123374201, -1.3645222104010861] ﻿
// ﻿﻿﻿﻿﻿﻿ camerapose_targetspace => [0.05073200344402887, -0.06971129927845013, -0.9202765733102549, 3.0053509229249507, -9.975233145961003, 0.8248704510006653] ﻿
// ﻿﻿﻿﻿﻿﻿ camMode => 0.0 ﻿
// ﻿﻿﻿﻿﻿﻿ thor => 183.0 ﻿
// ﻿﻿﻿﻿﻿﻿ tlong => 180.1289825439453 ﻿
// ﻿﻿﻿﻿﻿﻿ botpose_wpired => [1.9480593870526004, 6.99418900738137, 0.5324306350112721, 0.8135504863837562, -3.147968990855259, -170.06864782043507, 101.63847351074219] ﻿
// ﻿﻿﻿﻿﻿﻿ cl => 37.619998931884766 ﻿
// ﻿﻿﻿﻿﻿﻿ ta => 2.555910587310791 ﻿
// ﻿﻿﻿﻿﻿﻿ ledMode => 0.0 ﻿
// ﻿﻿﻿﻿﻿﻿ tc => [0.9058823585510254, 0.9058823585510254, 0.9058823585510254] ﻿
// ﻿﻿﻿﻿﻿﻿ pipeline => 0.0 ﻿
// ﻿﻿﻿﻿﻿﻿ botpose_wpiblue => [14.59369854007495, 1.0195277707981631, 0.5324306350112721, 0.8135504863837562, -3.147968990855259, 9.931504219056306, 101.63847351074219] ﻿
// ﻿﻿﻿﻿﻿﻿ targetpose_robotspace => [0.11044108806666356, 0.11830911571745464, 0.910026423797136, -3.1957223798311967, 9.916426123374201, -1.3645222104010861] ﻿
// ﻿﻿﻿﻿﻿﻿ tl => 64.01847839355469 ﻿
// ﻿﻿﻿﻿﻿﻿ hb => 23498.0 ﻿
// ﻿﻿﻿﻿﻿﻿ botpose => [6.322823540074951, -2.987322229201837, 0.5324306350112721, 0.8135504863837562, -3.147968990855259, 9.931504219056306, 101.63847351074219] ﻿
// ﻿﻿﻿﻿﻿﻿ tvert => 180.0 ﻿
// ﻿﻿﻿﻿﻿﻿ tshort => 178.9037322998047 ﻿
// ﻿﻿﻿﻿﻿﻿ ts => 89.00653076171875 ﻿



    // [tv, llpython, tx, ty, tid, botpose_targetspace, tclass, stream, getpipe, json, camerapose_robotspace, targetpose_cameraspace, camerapose_targetspace, camMode, thor, tlong, botpose_wpired, cl, ta, ledMode, tc, pipeline, botpose_wpiblue, targetpose_robotspace, tl, hb, botpose, tvert, tshort, ts]

    double speed = 0.2, rot = 0.1;
    double epsilon = 0.01;


    double xSpeed = 0, zRot = 0;

    

    if (ta > epsilon && ta < 1) {
      xSpeed = speed;
    } else if (ta >= 1) {
      //  m_arm.set(0.5);
    }

    if (Math.abs(tx) > 8) {
      zRot = rot * Math.signum(tx);
    }

    // m_left.set(xSpeed - zRot);
    // m_right.set(xSpeed + zRot);



    // m_left.set(-speed * Math.signum(tx));
    // m_right.set(speed * Math.signum(tx));
 
    

    // if (time - startTime < 2) {
    //   m_left.set(speed);
    //   m_right.set(speed);
    // } else if (time - startTime < 6) {
    //   m_left.set(speed);
    //   m_right.set(-speed);
    // } else if (time - startTime < 8) {
    //   m_left.set(-speed);
    //   m_right.set(-speed);
    // } else {
    //   m_left.set(0.0);
    //   m_right.set(0.0);
    // }
  }

  private static Transform3d buildTransform(double[] nums, int roll, int pitch, int yaw) {
    var translation = new Translation3d(nums[0], nums[1], nums[2]);
    var rotation = new Rotation3d(nums[roll], nums[pitch], nums[yaw]);

    return new Transform3d(translation, rotation);
}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    if(xbox.getLeftStickButton()){
      m_myRobot.arcadeDrive(-xbox.getRightY()*0.45, xbox.getRightX()*0.5);//percision drive ¯\_(ツ)_/¯ ok listen there's a reason english is my lowest grade
    }
    else{
      m_myRobot.arcadeDrive(-xbox.getRightY()*0.7, xbox.getRightX()*0.6);// standard drive, when bumper is NOT clicked
    }

    //intake code
    if(xbox.getRightTriggerAxis() > 0.1){
      m_intake.set(ControlMode.PercentOutput, 1.0);  //thomas did this stuff so ¯\_(ツ)_/¯
    }
    else if(xbox.getRightBumper()){
      m_intake.set(ControlMode.PercentOutput, -1.0);
    }
    else{
      m_intake.set(ControlMode.PercentOutput, 0.0);
    }

    //arm code
    if(xbox.getLeftTriggerAxis() > 0.1){
      m_arm.set(0.2);
    }
    else if(xbox.getLeftBumper()){
      m_arm.set(-0.1);// reduced the drop speed a little
    }
    else{
      m_arm.set(0);
    }
    /* 
         if(xbox.getLeftTriggerAxis() > 0.1){
      m_arm.set(-0.1);
    }
    else if(xbox.getLeftBumper()){
      m_arm.set(0.2);// reduced the drop speed a little           Might wanna remap it a little so the lower trigger is down, upper bumper is up
    }
    else{
      m_arm.set(0);
    }
 */
     
     
  }

  @Override
  public void disabledInit() {
    m_leftMotor1.set(0);
    m_leftMotor2.set(0);
    m_rightMotor1.set(0);
    m_rightMotor2.set(0);

    
    entry("ledMode").setNumber(0);
  }

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
