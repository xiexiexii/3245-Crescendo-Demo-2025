// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Configs.ShoulderConfigs;
import frc.robot.Constants.MotorIDConstants;
import frc.robot.Constants.PositionValueConstants;

// Shoulder (yay!)
public class ShoulderSubsystem extends SubsystemBase {

  // Init stuff
  TalonFX shoulderMaster;
  TalonFX shoulderFollower;

  CANcoder canCoder; 
  VoltageOut voltage;
    
  // Shoulder yippee
  public ShoulderSubsystem() {

    // Motors - [Falcon 500 + TalonFX] x2
    shoulderFollower = new TalonFX(MotorIDConstants.shoulder2MotorID);
    shoulderMaster = new TalonFX(MotorIDConstants.shoulder3MotorID);

    // CANCoder 
    canCoder = new CANcoder(MotorIDConstants.shoulderCANCoderID);

    // Apply Configs, Inverts, Control Requests
    shoulderMaster.getConfigurator().apply(ShoulderConfigs.shoulderFalconConfig);
    shoulderFollower.getConfigurator().apply(ShoulderConfigs.shoulderFalconConfig);

    shoulderFollower.setControl(new Follower(shoulderMaster.getDeviceID(), true));
    canCoder.getConfigurator().apply(ShoulderConfigs.shoulderCANcoderConfig);
  }

  public void periodic() {
    // SmartDashboard Shenanigans
    SmartDashboard.putNumber("Shoulder Encoder Value:", canCoder.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("Shoulder Master Value",shoulderMaster.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("Shoulder Follower Value",shoulderFollower.getPosition().getValueAsDouble());
  }

  public Angle getShoulderPosition(){
    return Units.Rotations.of(shoulderMaster.get());
  }

  public void resetEncoder() {
    canCoder.setPosition(0);
  }

  public void setHome() {
    shoulderMaster.setControl(new PositionVoltage(PositionValueConstants.k_shoulderHomePos.in(Units.Rotations)));
    SmartDashboard.putBoolean("Shoulder At Amp Height", false);
  }

  public void setAmpShot() {
    shoulderMaster.setControl(new PositionVoltage(PositionValueConstants.k_shoulderAmpShotPos.in(Units.Rotations)));
    SmartDashboard.putBoolean("Shoulder At Amp Height", true);
  }

  public void setProtShot() {
    shoulderMaster.setControl(new PositionVoltage(PositionValueConstants.k_shoulderProtShotPos.in(Units.Rotations)));
    SmartDashboard.putBoolean("Shoulder At Amp Height", false);
  }

  /*
  public void setDistShot() {
    shoulderMaster.setControl(positionDutyCycle.withPosition(
      ShoulderRegression.distanceToShoulderCounts(SmartDashboard.getNumber("Distance to target (meters)", 1.252))));
      SmartDashboard.putBoolean("Shoulder At Amp Height", false);
  }

  public void bumpUp() {
    shoulderMaster.setControl(positionDutyCycle.withPosition(
      canCoder.getPosition().getValueAsDouble() + 0.1));
  }

  public void bumpDown() {
    shoulderMaster.setControl(positionDutyCycle.withPosition(
      canCoder.getPosition().getValueAsDouble() - 0.001));
  }

  public void manual(CommandXboxController controller){
    shoulderMaster.setControl(voltage.withOutput(12*0.3*controller.getHID().getRawAxis(ControllerConstants.shoulderAxis)));
  }
  */

  public double getCurrentPositionCANcoder() {
    return canCoder.getPosition().getValueAsDouble();
  }

  public double getCurrentPositionFalcon() {
    return shoulderMaster.getPosition().getValueAsDouble();
  }
 
}
