// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
/* 
package frc.robot.subsystems;

import frc.robot.Constants.PIDConstants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Constants.MotorIDConstants;
import frc.robot.Constants.MotorSpeedsConstants;
import frc.robot.Constants.PositionValueConstants;

public class IntakeFlipoutSubsystem extends SubsystemBase {
    //init stuff
    SparkFlex flipOut;
    RelativeEncoder flipOutEncoder;
    SparkPIDController flipoutPID;
    
  public IntakeFlipoutSubsystem() {
    //motors/encoders
    flipOut = new SparkFlex(MotorIDConstants.intakeFlipoutMotorID, MotorType.kBrushless);
    flipOutEncoder = flipOut.getEncoder();

    flipoutPID = flipOut.getPIDController();
    flipoutPID.setFeedbackDevice(flipOutEncoder);

    flipoutPID.setFF(PIDConstants.flipoutkF);
    flipoutPID.setP(PIDConstants.flipoutkP);
    flipoutPID.setI(PIDConstants.flipoutkI);
    flipoutPID.setD(PIDConstants.flipoutkD);

    flipoutPID.setOutputRange(-MotorSpeedsConstants.flipOutClosedMaxSpeed, MotorSpeedsConstants.flipoutOpenMaxSpeed);
    
    flipOut.setClosedLoopRampRate(MotorSpeedsConstants.flipOutRamp);
    flipOut.setOpenLoopRampRate(MotorSpeedsConstants.flipOutRamp);

    flipOut.setInverted(true);
  }

  public void periodic() {
    // SmartDashboard Shenanigans
    SmartDashboard.putNumber("flipout encoder value:", flipOutEncoder.getPosition());
    SmartDashboard.putBoolean("retracted", flipOutEncoder.getPosition() < 4);
  }

  public void setOut(){
    flipoutPID.setReference(PositionValueConstants.flipoutOutPos, ControlType.kPosition);
  }

  public void setIn(){
    flipoutPID.setReference(PositionValueConstants.flipoutInPos, ControlType.kPosition);
  }

  public void manual(CommandXboxController controller){
    flipOut.set(MotorSpeedsConstants.flipoutOpenMaxSpeed * controller.getRawAxis(ControllerConstants.flipOutManualAxis));
  }

}
*/
