package frc.robot.Subsystem;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final WPI_VictorSPX m_shooterMotor = new WPI_VictorSPX(ShooterConstants.kShooterMotorDeviceNumber);
    private final WPI_VictorSPX m_shooterMotor2 = new WPI_VictorSPX(ShooterConstants.kShooterMotor2DeviceNumber);

    public Shooter() {
        ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
        ShuffleboardLayout MotorsLayout = shooterTab.getLayout("Motors", "List Layout").withPosition(0, 0).withSize(2,
                2);
        MotorsLayout.add("Shooter Motor", m_shooterMotor);
        MotorsLayout.add("Shooter Motor 2", m_shooterMotor2);
        Runnable disable = () -> {
            m_shooterMotor.set(0);
            m_shooterMotor2.set(0);
        };
        setDefaultCommand(runOnce(disable).andThen(disable).withName("IDLE"));
    }

    public Command speakerShoot(DoubleSupplier speed) {
        return run(() -> {
            m_shooterMotor.set(speed.getAsDouble());
            m_shooterMotor2.set(speed.getAsDouble());
        }).withName("Speaker Shoot!");

    }

    public Command ampShoot(DoubleSupplier speed) {
        return run(() -> {
            m_shooterMotor.set(speed.getAsDouble());
            m_shooterMotor2.set(speed.getAsDouble());
        }).withName("Amp Shoot!");
    }

}
