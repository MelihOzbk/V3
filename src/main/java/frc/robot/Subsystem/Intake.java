package frc.robot.Subsystem;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import frc.robot.Constants.IntakeConstants;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/*
 * Kullanılmayan komutları kaldır ve düzenle
 */
public class Intake extends SubsystemBase {
    private final WPI_VictorSPX m_intakeMotor = new WPI_VictorSPX(IntakeConstants.kIntakeMotorDeviceNumber);
    private final WPI_VictorSPX m_slideMotor = new WPI_VictorSPX(IntakeConstants.kIntakeSliderMotorDeviceNumber);
    public final DigitalInput m_intakeSensor = new DigitalInput(IntakeConstants.kIntakeIRSensorPort);
    public final DigitalInput m_sliderSensor = new DigitalInput(IntakeConstants.kSliderIRSensorPort);
    // public InstantCommand setAutoIntakeFalse = new InstantCommand(() -> value[0]
    // = false);
    public final Trigger m_intakeIRSensorTrigger = new Trigger(() -> m_intakeSensor.get());
    public final Trigger m_sliderIRSensorTrigger = new Trigger(() -> m_sliderSensor.get());

    public Intake() {
        ShuffleboardTab Intake = Shuffleboard.getTab("Intake");
        ShuffleboardLayout IRSensors = Intake.getLayout("IR Sensors", "List Layout").withPosition(0, 0).withSize(2, 2);
        ShuffleboardLayout MotorsLayout = Intake.getLayout("Motors", "List Layout").withPosition(2, 0).withSize(2, 2);
        MotorsLayout.add("Intake Motor", m_intakeMotor);
        MotorsLayout.add("Slider Motor", m_slideMotor);
        IRSensors.add("Intake IR Sensor", m_intakeSensor);
        IRSensors.add("Slider IR Sensor", m_sliderSensor);
        SendableRegistry.addChild(this, m_intakeSensor);

        Runnable disable = () -> {
            m_intakeMotor.set(0);
            m_slideMotor.set(0);
        };
        setDefaultCommand(runOnce(disable).andThen(disable).withName("IDLE"));
        m_intakeMotor.setInverted(true);
    }

    // public Command getAutoIntakeStatus() {
    // return Commands.print("Auto Intake is " + supplier.getAsBoolean());
    // }

    public Command autoIntakeSequence() {
        return run(() -> {
            m_intakeMotor.set(0.5);
            m_slideMotor.set(0.5);
        }).until(() -> m_sliderSensor.get()).finallyDo(interrupted -> {
            m_intakeMotor.set(0);
            m_slideMotor.set(0);
        });

    }

    public Command runIntake(DoubleSupplier speed) {
        return run(() -> m_intakeMotor.set(speed.getAsDouble()));
    }

    public Command runSlider(DoubleSupplier speed) {
        return run(() -> m_slideMotor.set(speed.getAsDouble()));
    }

    public boolean getIntakeSensor() {
        return m_intakeSensor.get();
    }

}
