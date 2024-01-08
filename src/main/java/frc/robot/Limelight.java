package frc.robot;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    public static final NetworkTable TABLE = NetworkTableInstance.getDefault().getTable("limelight");

    public static NetworkTableEntry entry(String key) {
        return TABLE.getEntry(key);
    }

    public static boolean hasTarget() {
        return targetPos() != null;
    }

    public static Transform3d targetPos() {
        var pose = Limelight.entry("targetpose_robotspace").getDoubleArray(new double[0]);
        if (pose.length != 6 || pose[2] < 1E-6) return null;
        
        return new Transform3d(
            new Translation3d(pose[0], pose[1], pose[2]),
            new Rotation3d(pose[3], pose[4], pose[5])
        );
    }


}
