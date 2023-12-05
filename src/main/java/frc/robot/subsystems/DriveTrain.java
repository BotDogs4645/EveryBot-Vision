package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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
        right.setInverted(true);

        drive = new DifferentialDrive(left, right);
    }

    public void arcadeDrive(double xSpeed, double zRotate) {
        drive.arcadeDrive(xSpeed, zRotate, false);
    }

    public void setMotors(double leftSpeed, double rightSpeed) {
        left.set(leftSpeed);
        right.set(rightSpeed);
    }

    public void stop() {
        left.set(0);
        right.set(0);
    }

}
