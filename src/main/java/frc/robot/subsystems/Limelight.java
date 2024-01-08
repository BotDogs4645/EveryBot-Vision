package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

    public static final NetworkTable TABLE = NetworkTableInstance.getDefault().getTable("limelight");

    public NetworkTableEntry entry(String key) {
        return TABLE.getEntry(key);
    }

    public boolean hasTarget() {
        return targetPos() != null;
    }

    public Transform3d targetPos() {
        var pose = entry("targetpose_robotspace").getDoubleArray(new double[0]);
        if (pose.length != 6 || pose[2] < 1E-6) return null;
        
        return new Transform3d(
            new Translation3d(pose[0], pose[1], pose[2]),
            new Rotation3d(pose[3], pose[4], pose[5])
        );
    }

    public Transform3d calculateLocation() {
        var pos = targetPos();
        if (pos == null) return null; // Cannot calculate location if we can't see a target!
        
        // TODO
        return null;
    }

}
