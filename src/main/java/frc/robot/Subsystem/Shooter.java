package frc.robot.Subsystem;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final CANSparkMax m_shooterMotor = new CANSparkMax(ShooterConstants.kShooterMotorDeviceNumber,
            MotorType.kBrushed);
    private final CANSparkMax m_shooterMotor2 = new CANSparkMax(ShooterConstants.kShooterMotor2DeviceNumber,
            MotorType.kBrushed);

    public Shooter() {
        m_shooterMotor.setIdleMode(IdleMode.kCoast);
        m_shooterMotor2.setIdleMode(IdleMode.kCoast);
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
