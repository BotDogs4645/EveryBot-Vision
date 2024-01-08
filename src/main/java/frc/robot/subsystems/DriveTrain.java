package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Ports.*;

public class DriveTrain extends SubsystemBase {

    private CANSparkMax left1, left2, right1, right2;
    private MotorControllerGroup left, right;
    private DifferentialDrive drive;

    public DriveTrain() {
        left1 = new CANSparkMax(MOTOR_LEFT1, MotorType.kBrushed);
        left2 = new CANSparkMax(MOTOR_LEFT2, MotorType.kBrushed);
        right1 = new CANSparkMax(MOTOR_RIGHT1, MotorType.kBrushed);
        right2 = new CANSparkMax(MOTOR_RIGHT2, MotorType.kBrushed);

        left = new MotorControllerGroup(left1, left2);
        right = new MotorControllerGroup(right1, right2);

        drive = new DifferentialDrive(left, right);
    }

    // The Problem: Left1 needs to be inverted but the motor locks when inverted.
    //              Inverting it on the software side fixes it, but requires a
    //              significant amount of customized behaviour.

    public void arcadeDrive(double xSpeed, double zRotation) {
        // This is just the source code from DifferentialDrive#arcadeDrive except
        // that it calls #setMotors so we can properly invert 
        System.out.printf("arcade: %.3f, %.3f\n", xSpeed, zRotation);

        xSpeed *= -1;
        
        var m_deadband = RobotDriveBase.kDefaultDeadband;
        var m_maxOutput = RobotDriveBase.kDefaultMaxOutput;

        xSpeed = MathUtil.applyDeadband(xSpeed, m_deadband);
        zRotation = MathUtil.applyDeadband(zRotation, m_deadband);
    
        var speeds = drive.arcadeDriveIK(xSpeed, zRotation, false);
    
        setMotors(speeds.left * m_maxOutput, speeds.right * m_maxOutput);
    }

    public void setMotors(double leftSpeed, double rightSpeed) {
        left.set(leftSpeed);

        right1.set(-rightSpeed);
        right2.set(rightSpeed);
    }

    public void stop() {
        left.set(0);
        right.set(0);
    }

}
