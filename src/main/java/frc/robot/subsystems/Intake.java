package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private CANSparkMax arm;
    private VictorSPX intake;

    public Intake() {
        arm = new CANSparkMax(5, MotorType.kBrushless);
        intake = new VictorSPX(6);

        // arm.
        intake.setNeutralMode(NeutralMode.Brake);
    }

    public void setArm(double speed) {
        arm.set(speed);
    }

    public void setIntake(double speed) {
        intake.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        arm.set(0);
        intake.set(ControlMode.Disabled, 0);
    }

}
