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
        var targetPose = Limelight.entry("targetpose_robotspace").getDoubleArray(new double[0]);
        if (targetPose.length != 6 || targetPose[2] < 1E-6) return null;
        
        return buildTransform(targetPose, 3, 4, 5);
    }

    private static Transform3d buildTransform(double[] nums, int roll, int pitch, int yaw) {
        var translation = new Translation3d(nums[0], nums[1], nums[2]);
        var rotation = new Rotation3d(nums[roll], nums[pitch], nums[yaw]);

        return new Transform3d(translation, rotation);
    }

}
