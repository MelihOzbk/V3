// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.time.Instant;
import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.OIConstants;
import frc.robot.Subsystem.Drive;
import frc.robot.Subsystem.Elevator;
import frc.robot.Subsystem.Shooter;
import frc.robot.Subsystem.Intake;

public class RobotContainer {
  private boolean value = false;
  private BooleanSupplier supplier = () -> value;
  private final CommandJoystick j_Driver = new CommandJoystick(OIConstants.kDriverControllerPort);
  private final CommandJoystick j_Operator = new CommandJoystick(OIConstants.kCoDriverControllerPort);

  private final Drive m_drive = new Drive();
  private final Elevator m_elevator = new Elevator();
  private final Shooter m_shooter = new Shooter();
  private final Intake m_intake = new Intake();

  public RobotContainer() {

  }

  private GenericEntry shuffleboardValue = Shuffleboard.getTab("Tab Name")
      .add("Otomatik Intake Sistemi Durumu : ", false)
      .withWidget(BuiltInWidgets.kBooleanBox)
      .getEntry();

  public boolean getValue() {
    return value;
  }

  public void configureBindings() {
    ShuffleboardTab AutoControl = Shuffleboard.getTab("Auto Control");
    ShuffleboardLayout AutoIntake = AutoControl.getLayout("Auto Intake", BuiltInLayouts.kList)
        .withPosition(0, 0)
        .withSize(2, 2);
    AutoIntake.addBoolean("OTO.INTAKE DURUMU", () -> value);
    // new ConditionalCommand(m_intake.autoIntakeSequence(supplier),
    // getAutonomousCommand(), supplier);
    m_intake.m_intakeIRSensorTrigger.and(supplier::getAsBoolean).toggleOnTrue(m_intake.autoIntakeSequence());
    // m_intake.m_intakeIRSensorTrigger.onTrue(m_intake.autoIntakeSequence(() ->
    // shuffleboardValue.getBoolean(false)));
    // autoIntak();

    m_drive.setDefaultCommand(m_drive.arcadeDrive(
        () -> -j_Driver.getY(),
        () -> -j_Driver.getX()));
    j_Driver.button(1)
        .toggleOnTrue(new InstantCommand(() -> {
          value = !value;
          supplier = () -> value;
          System.out.println("Button 1 pressed: " + value);
        }));
    // Speaker Shoot Command
    j_Driver.button(2).whileTrue(m_shooter.speakerShoot(() -> 0.8).alongWith(m_intake.runSlider(() -> 0.4)));
    // j_Driver.button(3).toggleOnTrue(new InstantCommand(() -> {
    // boolean value = shuffleboardValue.getBoolean(false);
    // System.out.println("Shuffleboard değeri: " + value);

    // }));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
