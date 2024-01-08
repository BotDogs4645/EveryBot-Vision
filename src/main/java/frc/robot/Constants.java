package frc.robot;

public interface Constants {

    interface Ports {

        int CONTROLLER = 0;

        int MOTOR_LEFT1 = 3, MOTOR_LEFT2 = 4;
        int MOTOR_RIGHT1 = 7, MOTOR_RIGHT2 = 2;

        int ARM = 5;
        int INTAKE = 6;
    }

    interface Limelight {

        // Limelight offset relative to the center of the robot
        // Measured in meters
        double OFFSET_RIGHT = 0.32;
        double OFFSET_UP = 0.55;
        double OFFSET_FORWARDS = 0.0;
    }

    interface SearchForTag {

        double TURN_SPEED = 0.2;

    }

    interface MoveTowardTag {

        // Measured in portion of maximum speed
        double MOVE_SPEED = 0.3;
        double TURN_SPEED = 0.1;

        // Measured in degrees
        double MIN_ANGLE = -12;
        double MAX_ANGLE = -8;

        // Measured in meters
        double SHOOT_DISTANCE = 1;

    }

}
