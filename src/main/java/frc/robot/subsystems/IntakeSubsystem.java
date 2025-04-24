// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Configs.IntakeConfigs;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.MotorIDConstants;
import frc.robot.Constants.MotorSpeedsConstants;
import frc.robot.Constants.SensorConstants;

// Intake Subsystem
public class IntakeSubsystem extends SubsystemBase {

    // Init stuff
    SparkMax intakeTop1Motor;
    SparkMax intakeTop2Motor;
    DigitalInput beamBreak;
    TalonFX flipoutRun;
  
  public IntakeSubsystem() {

    // Motors - [NEO 1.1 + SparkMAX] x2, [Falcon 500 + TalonFX] x1; Sensors - [Beam Break] x1
    intakeTop1Motor = new SparkMax(MotorIDConstants.intakeTop1MotorID, MotorType.kBrushless);
    intakeTop2Motor = new SparkMax(MotorIDConstants.intakeTop2MotorID, MotorType.kBrushless);
    flipoutRun = new TalonFX(MotorIDConstants.intakeRunExtended);
    beamBreak = new DigitalInput(SensorConstants.intakeBeamBreakDIOPort);

    // Apply Configs
    intakeTop1Motor.configure(IntakeConfigs.intakeLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    intakeTop2Motor.configure(IntakeConfigs.intakeFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void periodic() {
    // SmartDashboard Shenanigans
    SmartDashboard.putBoolean("beam break tripped", !beamBreak.get());
  }

  public void stop() { 
    intakeTop1Motor.set(0);
    flipoutRun.set(0);
  }

  public void intake() {
    intakeTop1Motor.set(MotorSpeedsConstants.intakeNeoSpeed);
    flipoutRun.set(MotorSpeedsConstants.flipOutRunSpeed);
  }

  public void intakeWithoutFlipout() {
    intakeTop1Motor.set(MotorSpeedsConstants.intakeNeoSpeed);  }

  public void feed() {
    intakeTop1Motor.set(MotorSpeedsConstants.intakeNeoFeedSpeed);
  }

  public void stopBottom() {
    intakeTop1Motor.set(MotorSpeedsConstants.intakeNeoSpeed);
  }

  public void manual(CommandXboxController controller) {
    intakeTop1Motor.set(MotorSpeedsConstants.intakeNeoSpeed*controller.getHID().getRawAxis(ControllerConstants.intakeTopAxis));
  }

  public boolean getBBTripped() {
    return !beamBreak.get();
  }

  public void stopFlipoutRun() {
    flipoutRun.set(0);
  }
}
