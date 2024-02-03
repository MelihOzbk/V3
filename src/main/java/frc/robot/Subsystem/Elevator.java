package frc.robot.Subsystem;

import frc.robot.Constants.ElevatorConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;

public class Elevator extends SubsystemBase {
    private final WPI_VictorSPX m_elevatorMotor = new WPI_VictorSPX(ElevatorConstants.kElevatorMotorDeviceNumber);
    private final AnalogInput m_elevatorSensor = new AnalogInput(ElevatorConstants.kIRMzSensorPort);

    public Elevator() {
        m_elevatorMotor.setInverted(true);
    }

    public void moveElevator(double speed) {
        m_elevatorMotor.set(speed);
    }

    public double getElevatorHeight() {
        return m_elevatorSensor.getVoltage();
    }

}
