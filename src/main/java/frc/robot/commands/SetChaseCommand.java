// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.VisionConstants;
import edu.wpi.first.wpilibj2.command.Command;

// Raises Elevator
public class SetChaseCommand extends Command {

  // Side of the Reef
  String m_status;

  // Constructor
  public SetChaseCommand(String status) {
    
    // Level
    m_status = status;
  }

  // Reset timer when the command starts executing
  public void initialize() {
  }
  
  // Actual command
  public void execute() {

    // Set to Chase
    if (m_status.equals("chase")) {
      VisionConstants.k_isChasing = true;
    }

    // Set to Stop Chasing
    if(m_status.equals("stop")) {
      VisionConstants.k_isChasing = false;
    }
  }

  // Stuff that happens when command is over
  public void end(boolean interrupted) {
  }

  // Checks if the command is done
  public boolean isFinished() {

    // Am I done?  Am I done? Am I finally done?
    return true;
  }
}
