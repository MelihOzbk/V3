package frc.robot;

import edu.wpi.first.util.sendable.Sendable;

public final class Constants {
    public static final class DriveConstants {
        public static final int kLeftMotor1DeviceNumber = 5;
        public static final int kLeftMotor2DeviceNumber = 6;
        public static final int kRightMotor1DeviceNumber = 7;
        public static final int kRightMotor2DeviceNumber = 9;
    }

    public static final class OIConstants {
        public static final int kDriverControllerPort = 0;
        public static final int kCoDriverControllerPort = 1;
        public static Sendable kCameraServer;
    }

    public static final class ElevatorConstants {
        public static final int kElevatorMotorDeviceNumber = 10;
        public static final int kElevatorLimitSwitchPort = 0;
        public static final int kIRMzSensorPort = 3;
    }

    public static final class ShooterConstants {
        public static final int kShooterMotorDeviceNumber = 3;
        public static final int kShooterMotor2DeviceNumber = 4;

    }

    public static final class IntakeConstants {
        public static final int kIntakeMotorDeviceNumber = 1;
        public static final int kIntakeSliderMotorDeviceNumber = 2;
        public static final int kIntakeIRSensorPort = 1;
        public static final int kSliderIRSensorPort = 2;
    }

    /*
     * En son ayarlanan shooter hızı slider hızları ve elevator hızları burada
     * tutulur.
     */
    public static final class SpeedConstants {
        public static final String kAmpShootNameSpeed = "AS-Speed";
        public static final String kSpeakerShootNameSpeed = "SP-Speed";
        public static final String kAmpSliderNameSpeed = "AL-Speed";
        public static final String kSpeakerSliderNameSpeed = "SL-Speed";
        public static final String kElevatorNameSpeed = "E-Speed";
        public static final int kAmpShootSpeed = 0;
        public static final int kSpeakerShootSpeed = 0;
        public static final int kAmpSliderSpeed = 0;
        public static final int kSpeakerSliderSpeed = 0;
        public static final int kElevatorSpeed = 0;
    }

}
