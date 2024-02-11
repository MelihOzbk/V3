// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

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
        /*
         * Operatör sürücü komutları eksik****
         * HALA EKSİK EKLE TAHA
         */
        private final CommandJoystick j_Operator = new CommandJoystick(OIConstants.kCoDriverControllerPort);

        private final Drive m_drive = new Drive();
        private final Elevator m_elevator = new Elevator();
        private final Shooter m_shooter = new Shooter();
        private final Intake m_intake = new Intake();

        public RobotContainer() {

        }

        public void setShuffleboardDisplay() {
                ShuffleboardTab AutoControl = Shuffleboard.getTab("Auto Control");
                ShuffleboardLayout AutoIntake = AutoControl.getLayout("Auto Intake", BuiltInLayouts.kList)
                                .withPosition(0, 0)
                                .withSize(2, 2);
                AutoIntake.addBoolean("OTO.INTAKE DURUMU", () -> value);
                ShuffleboardTab subTab = Shuffleboard.getTab("Subsystems");
                ShuffleboardLayout Subsystem = subTab.getLayout("Subsystem", BuiltInLayouts.kList)
                                .withPosition(2, 0)
                                .withSize(2, 2);
                Subsystem.add("Drive", m_drive);
                Subsystem.add("Elevator", m_elevator);
                // Subsystem.add("Shooter", m_shooter);
                // Subsystem.add("Intake", m_intake);

        }

        // set amp shoot speed with shuffleboard
        // Shuffleboard'da hız ayarları yapılır.
        GenericEntry ampShootspeed = Shuffleboard.getTab("Speed").add("AS-Speed", 0.4)
                        .withWidget(BuiltInWidgets.kNumberBar)
                        .getEntry();
        // set speaker shoot speed with shuffleboard
        GenericEntry speakerShootspeed = Shuffleboard.getTab("Speed").add("SP-Speed", 0.8)
                        .withWidget(BuiltInWidgets.kNumberBar)
                        .getEntry();
        // set amp slider speed with shuffleboard
        GenericEntry ampsliderspeed = Shuffleboard.getTab("Speed").add("AL-Speed", 0.4)
                        .withWidget(BuiltInWidgets.kNumberBar)
                        .getEntry();
        // set speaker slider speed with shuffleboard
        GenericEntry speakersliderspeed = Shuffleboard.getTab("Speed").add("SL-Speed", 0.4)
                        .withWidget(BuiltInWidgets.kNumberBar)
                        .getEntry();
        // set elevator speed with shuffleboard
        GenericEntry elevatorSpeed = Shuffleboard.getTab("Speed").add("E-Speed", 0.5)
                        .withWidget(BuiltInWidgets.kNumberBar)
                        .getEntry();

        public void configureBindings() {

                setShuffleboardDisplay();

                m_intake.m_intakeIRSensorTrigger.and(supplier::getAsBoolean)
                                .toggleOnTrue(m_intake.autoIntakeSequence());

                /*
                 * arcadeDrive methodu içerisindeki forward ve rotation değerlerini
                 * j_Driver.getY() ve j_Driver.getX() ile değiştirdik. Bu sayede joystick
                 * değerlerini kullanarak robotu hareket ettirebiliyoruz.
                 *
                 */
                m_drive.setDefaultCommand(m_drive.arcadeDrive(
                                () -> -j_Driver.getY(),
                                () -> -j_Driver.getX()));
                /*
                 * Auto Intake sisteminin çalışmasını açıp kapamak için j_Driver buton 1'i
                 * kullanıyoruz. Bu butona basıldığında Auto Intake sistemi açılır ve
                 * j_Driver buton 1'e tekrar basıldığında Auto Intake kapalı hale gelir.
                 */
                j_Driver.button(1)
                                .toggleOnTrue(new InstantCommand(() -> {
                                        value = !value;
                                        supplier = () -> value;
                                }));

                // Speaker Shoot Command
                // j_Driver buton 2'ye basıldığında Speaker Shoot komutu çalışır ve sonrasında
                // Slider motoru çalışır.
                // Speakera özel hızlar ayarlanabilir.

                j_Driver.button(2)
                                .whileTrue(
                                                m_shooter.setShooterSpeed(() -> speakerShootspeed.getDouble(0.8))
                                                                .alongWith(m_intake.runSlider(() -> speakersliderspeed
                                                                                .getDouble(0.4))));

                // Amp Shoot Command
                // j_Driver buton 3'e basıldığında Amp Shoot komutu çalışır ve sonrasında
                // Slider motoru çalışır.
                // Ampe özel hızlar ayarlanabilir.

                j_Driver.button(3)
                                .whileTrue(m_shooter.setShooterSpeed(() -> ampShootspeed.getDouble(0.4))
                                                .alongWith(m_intake.runSlider(() -> ampsliderspeed.getDouble(0.4))));

                // Elevator Up Command
                // j_Driver buton 4'e basıldığında Elevator yukarı hareket eder.
                // Elevator hızı ayarlanabilir.

                j_Driver.button(6).whileTrue(m_elevator.moveElevator(() -> elevatorSpeed.getDouble(0.5)));
                // Elevator Down Command
                // j_Driver buton 5'e basıldığında Elevator aşağı hareket eder.
                // Elevator hızı ayarlanabilir.

                j_Driver.button(5).whileTrue(m_elevator.moveElevator(() -> -elevatorSpeed.getDouble(0.5)));
        }

        public Command getAutonomousCommand() {
                return Commands.print("Otonom Komut Ayarlanmadı!");
        }
}
