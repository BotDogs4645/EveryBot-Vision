package frc.robot;

import java.util.Map;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class Constants {

    public static class Ports {

        public static final int CONTROLLER = 0;

        public static final int MOTOR_LEFT1 = 3, MOTOR_LEFT2 = 4;
        public static final int MOTOR_RIGHT1 = 7, MOTOR_RIGHT2 = 2;

        public static final int ARM = 5;
        public static final int INTAKE = 6;
    }

    public static class Limelight {

        // Limelight offset relative to the center of the robot
        // Measured in meters
        public static final double OFFSET_RIGHT = 0.32;
        public static final double OFFSET_UP = 0.55;
        public static final double OFFSET_FORWARDS = 0.0;
    }

    public static class SearchForTag {

        public static final double TURN_SPEED = 0.2;

    }

    public static class MoveTowardTag {

        // Measured in portion of maximum speed
        public static final double MOVE_SPEED = 0.3;
        public static final double TURN_SPEED = 0.1;

        // Measured in degrees
        public static final double MIN_ANGLE = -12;
        public static final double MAX_ANGLE = -8;

        // Measured in meters
        public static final double SHOOT_DISTANCE = 1;

    }

    public static final Map<Integer, Transform3d> APRILTAGS = Map.ofEntries(
            tag(1, 593.68, 9.68, 53.38, 120),
            tag(2, 637.21, 34.79, 53.38, 120),
            tag(3, 652.73, 196.17, 57.13, 180),
            tag(4, 652.73, 218.42, 57.13, 180),
            tag(5, 578.77, 323.00, 53.38, 270),
            tag(6, 72.5, 323.00, 53.38, 270),
            tag(7, -1.50, 218.42, 57.13, 0),
            tag(8, -1.50, 196.17, 57.13, 0),
            tag(9, 14.02, 34.79, 53.38, 60),
            tag(10, 57.54, 9.68, 53.38, 60),
            tag(11, 468.69, 146.19, 52.00, 300),
            tag(12, 468.69, 177.10, 52.00, 60),
            tag(13, 441.74, 161.62, 52.00, 180),
            tag(14, 209.48, 161.62, 52.00, 0),
            tag(15, 182.73, 177.10, 52.00, 120),
            tag(16, 182.73, 146.19, 52.00, 240)
    );

    private static Map.Entry<Integer, Transform3d> tag(int id, double x, double y, double z, double rot) {
        final double inchesPerMeter = 39.37;

        return Map.entry(id, new Transform3d(
            new Translation3d(x / inchesPerMeter, y / inchesPerMeter, z / inchesPerMeter),
            new Rotation3d(rot, 0, 0)
        ));
    }

}
