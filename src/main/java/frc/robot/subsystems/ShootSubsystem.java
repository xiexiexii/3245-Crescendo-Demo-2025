// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Configs.ShooterConfigs;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.MotorIDConstants;
import frc.robot.Constants.MotorSpeedsConstants;

// Shooter goes biu biu
public class ShootSubsystem extends SubsystemBase {

  // Init stuff
  SparkFlex shootMotor1;
  SparkFlex shootMotor2;

  public ShootSubsystem() {

    // Motors - [NEO Vortex + SparkFLEX] x2
    shootMotor1 = new SparkFlex(MotorIDConstants.shootMotor1ID, MotorType.kBrushless);
    shootMotor2 = new SparkFlex(MotorIDConstants.shootMotor2ID, MotorType.kBrushless);

    shootMotor1.configure(ShooterConfigs.shooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    shootMotor2.configure(ShooterConfigs.shooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void periodic() {}

  public void spinUp() {
    if(SmartDashboard.getBoolean("At Amp Height", false)){
      shootMotor1.set(-MotorSpeedsConstants.shoot1MaxValAmp);
      shootMotor2.set(-MotorSpeedsConstants.shoot2MaxValAmp);
    }
    else {
      shootMotor1.set(-MotorSpeedsConstants.shoot1MaxVal);
      shootMotor2.set(MotorSpeedsConstants.shoot2MaxVal);
    }
  }

  public void driveBackward() {
    shootMotor1.set(MotorSpeedsConstants.shoot1RunBackVal);
    shootMotor2.set(-MotorSpeedsConstants.shoot2RunBackVal);
  }

  public void spinUpAuto() {
    shootMotor1.set(-MotorSpeedsConstants.shoot1MaxValAuto);
    shootMotor2.set(MotorSpeedsConstants.shoot2MaxValAuto);
  }

  public void manual(CommandXboxController controller){
    if(controller.getHID().getRawButton(ControllerConstants.spinupButton)){
      if(SmartDashboard.getBoolean("Shoulder At Amp Height", false)){
      shootMotor1.set(-MotorSpeedsConstants.shoot1MaxValAmp);
      shootMotor2.set(MotorSpeedsConstants.shoot2MaxValAmp);
      }
      else {
      shootMotor1.set(-MotorSpeedsConstants.shoot1MaxVal);
      shootMotor2.set(MotorSpeedsConstants.shoot2MaxVal);
      }
    }
    else {
      shootMotor1.set(0);
      shootMotor2.set(0);
    }
  }

  public void stop() {
    shootMotor1.set(0);
    shootMotor2.set(0);
  }

}
