package frc.robot.Subsystem;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax; // import the CANSparkMax class
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    public final CANSparkMax m_shooterMotor = new CANSparkMax(ShooterConstants.kShooterMotorDeviceNumber,
            MotorType.kBrushed);
    public final CANSparkMax m_shooterMotor2 = new CANSparkMax(ShooterConstants.kShooterMotor2DeviceNumber,
            MotorType.kBrushed);

    public Shooter() {
        ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");

        ShuffleboardLayout MotorsLayout = shooterTab.getLayout("Motors", "List Layout").withPosition(0, 0).withSize(2,
                2);
        MotorsLayout.addDouble("Shooter Motor Speed", () -> m_shooterMotor.get());
        MotorsLayout.addDouble("Shooter Motor 2 Speed", () -> m_shooterMotor2.get());

        Runnable disable = () -> {
            m_shooterMotor.set(0);
            m_shooterMotor2.set(0);
        };
        setDefaultCommand(runOnce(disable).andThen(disable).withName("IDLE"));
    }

    public Command setShooterSpeed(DoubleSupplier speed) {
        return run(() -> {
            m_shooterMotor.set(speed.getAsDouble());
            m_shooterMotor2.set(speed.getAsDouble());
        });
    }

}
