package frc.robot.Subsystem;

import frc.robot.Constants.ElevatorConstants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class Elevator extends SubsystemBase {
    private final WPI_VictorSPX m_elevatorMotor = new WPI_VictorSPX(ElevatorConstants.kElevatorMotorDeviceNumber);
    private final AnalogInput m_elevatorSensor = new AnalogInput(ElevatorConstants.kIRMzSensorPort);

    public Elevator() {
        Runnable disable = () -> {
            m_elevatorMotor.set(0);
        };
        setDefaultCommand(runOnce(disable).andThen(disable).withName("IDLE"));
        ShuffleboardTab elevatorTab = Shuffleboard.getTab("Elevator");
        ShuffleboardLayout MotorsLayout = elevatorTab.getLayout("Motors", "List Layout").withPosition(0, 0).withSize(2,
                2);
        MotorsLayout.add("Elevator Motor", m_elevatorMotor);
        ShuffleboardLayout SensorsLayout = elevatorTab.getLayout("Sensors", "List Layout").withPosition(2, 0)
                .withSize(2, 2);
        SensorsLayout.add("Elevator IR Sensor", m_elevatorSensor);
        m_elevatorMotor.setInverted(true);
    }

    public Command moveElevator(DoubleSupplier speed) {
        return run(() -> {
            m_elevatorMotor.set(speed.getAsDouble());
        }).withName("Move Elevator!");
    }

    public double getElevatorHeight() {
        return m_elevatorSensor.getVoltage();
    }

}
