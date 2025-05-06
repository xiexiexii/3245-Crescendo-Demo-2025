package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.VisionConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.vision.LimelightHelpers;

// Positions Robot at the Nearest Valid Target
public class TagChaseCommand extends Command {
    
  // Instantiate Stuff
  DriveSubsystem m_swerveSubsystem;

  // PID Controller stuff (woah so many so scary) 
  PIDController m_aimController = new PIDController(VisionConstants.kP_aim, VisionConstants.kI_aim, VisionConstants.kD_aim);
  PIDController m_rangeController = new PIDController(VisionConstants.kP_range, VisionConstants.kI_range, VisionConstants.kD_range);

  // Bot Pose Target Space Relativse (TX, TY)
  private double m_tx;
  private double m_ty;

  // Constants
  private double m_rangeTarget;
  private double m_aimTarget;

  // Constructor
  public TagChaseCommand(DriveSubsystem driveSubsystem) {
        
    // Definitions and setting parameters are equal to members!
    m_swerveSubsystem = driveSubsystem;
    addRequirements(driveSubsystem);
  }

  // What we do to set up the command 
  public void initialize() {

    // Update TX/TY Values
    m_tx = LimelightHelpers.getTX(VisionConstants.k_limelightName);
    m_ty = LimelightHelpers.getTY(VisionConstants.k_limelightName);

    // Set Constants
    m_rangeTarget = VisionConstants.k_rangeRetroTarget;
    m_aimTarget = VisionConstants.k_aimRetroTarget;
  }
    
  // The actual control!
  public void execute() {

    VisionConstants.k_positioning = true;

    // Update the pose from NetworkTables (Limelight Readings)
    m_tx = LimelightHelpers.getTX(VisionConstants.k_limelightName);
    m_ty = LimelightHelpers.getTY(VisionConstants.k_limelightName);

    m_swerveSubsystem.drive(limelight_range_PID(), 0, limelight_aim_PID(), false);
  }

  // Add stuff we do after to reset here (a.k.a tag filters)
  public void end(boolean interrupted) {
    VisionConstants.k_positioning = false;
  }

  // Are we done yet? Finishes when threshold is reached or if no tag in view or if timer is reached 
  public boolean isFinished() {
    return !VisionConstants.k_isChasing;
  }

  // Advanced PID-assisted ranging control with Limelight's TZ value from target-relative data
  private double limelight_range_PID() {

    // Limelight Z Axis Range in meters
    m_rangeController.enableContinuousInput(-25, 25);
    
    // Calculates response based on difference in distance from tag to robot
    double targetingForwardSpeed = m_rangeController.calculate(m_ty - m_rangeTarget);

    // Value scale up to robot max speed and invert (double cannot exceed 1.0)
    targetingForwardSpeed *= 0.1 * DriveConstants.kMaxSpeedMetersPerSecond;

    // Hooray
    return targetingForwardSpeed;
  }

  // Advanced PID-assisted ranging control with Limelight's Yaw value from target-relative data
  private double limelight_aim_PID() {

    // Limelight Yaw Angle in Degrees
    m_aimController.enableContinuousInput(-30, 30);
    
    // Calculates response based on difference in angle from tag to robot
    double targetingAngularVelocity = m_aimController.calculate(m_tx - m_aimTarget);

    // Multiply by 1 because robot is CCW Positive. Multiply by a reduction 
    // multiplier to reduce speed. Scale TX up with robot speed.
    targetingAngularVelocity *= 0.1 * DriveConstants.kMaxAngularSpeed;

    // Hooray
    return targetingAngularVelocity;
  }
}
