package frc.robot.Subsystem;

import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Drive extends SubsystemBase {
    private final WPI_VictorSPX m_leftMotor = new WPI_VictorSPX(DriveConstants.kLeftMotor1DeviceNumber);
    private final WPI_VictorSPX m_rightMotor = new WPI_VictorSPX(DriveConstants.kRightMotor1DeviceNumber);
    private final WPI_VictorSPX m_leftMotor2 = new WPI_VictorSPX(DriveConstants.kLeftMotor2DeviceNumber);
    private final WPI_VictorSPX m_rightMotor2 = new WPI_VictorSPX(DriveConstants.kRightMotor2DeviceNumber);
    private final DifferentialDrive m_Robot = new DifferentialDrive(m_leftMotor, m_rightMotor);

    public Drive() {
        ShuffleboardTab driveBaseTab = Shuffleboard.getTab("Drivebase");
        driveBaseTab.add("Tank Drive", m_Robot);
        m_leftMotor2.follow(m_leftMotor);
        m_rightMotor2.follow(m_rightMotor);
        m_rightMotor.setInverted(true);
    }

    public Command arcadeDrive(DoubleSupplier forward, DoubleSupplier rotation) {
        return run(() -> m_Robot.arcadeDrive(forward.getAsDouble(), rotation.getAsDouble()));
    }
}
