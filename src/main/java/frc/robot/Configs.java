package frc.robot;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.units.Units;
import frc.robot.Constants.ModuleConstants;
import frc.robot.Constants.MotorIDConstants;
import frc.robot.Constants.MotorSpeedsConstants;
import frc.robot.Constants.PIDConstants;

public final class Configs {

  // Configs for Driving And Turning
  public static final class MAXSwerveModule {

    // Init
    public static final SparkFlexConfig drivingConfig = new SparkFlexConfig();
    public static final SparkMaxConfig turningConfig = new SparkMaxConfig();

    static {
      // Use module constants to calculate conversion factors and feed forward gain.
      double drivingFactor = ModuleConstants.kWheelDiameterMeters * Math.PI
        / ModuleConstants.kDrivingMotorReduction;
      double turningFactor = 2 * Math.PI;
      double drivingVelocityFeedForward = 1 / ModuleConstants.kDriveWheelFreeSpeedRps;

      drivingConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(45);
      drivingConfig.encoder
        .positionConversionFactor(drivingFactor) // meters
        .velocityConversionFactor(drivingFactor / 60.0); // meters per second
      drivingConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        // These are example gains you may need to tune them for your own robot!
        .pid(0.04, 0, 0)
        .velocityFF(drivingVelocityFeedForward)
        .outputRange(-1, 1);

      turningConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(25);
      turningConfig.absoluteEncoder
        // Invert the turning encoder, since the output shaft rotates in the opposite
        // direction of the steering motor in the MAXSwerve Module.
        .inverted(true)
        .positionConversionFactor(turningFactor) // radians
        .velocityConversionFactor(turningFactor / 60.0); // radians per second
      turningConfig.closedLoop
        .feedbackSensor(FeedbackSensor.kAbsoluteEncoder)
        // These are example gains you may need to them for your own robot!
        .pid(1, 0, 0)
        .outputRange(-1, 1)
        // Enable PID wrap around for the turning motor. This will allow the PID
        // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
        // to 10 degrees will go through 0 rather than the other direction which is a
        // longer route.
        .positionWrappingEnabled(true)
        .positionWrappingInputRange(0, turningFactor);
    }
  }

  // Shooter Configs
  public static final class ShooterConfigs {

    // Init
    public static final SparkFlexConfig shooterConfig = new SparkFlexConfig();

    static {
      // Brake/Coast & Current Limit
      shooterConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(60);
      // Ramping
      shooterConfig
        .openLoopRampRate(0.5);
    }
  }

  // Intake Configs
  public static final class IntakeConfigs {

    // Init
    public static final SparkMaxConfig intakeLeaderConfig = new SparkMaxConfig();
    public static final SparkMaxConfig intakeFollowerConfig = new SparkMaxConfig();

    static {
      // Brake/Coast & Current Limit
      intakeLeaderConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(60);
      // Ramping
      intakeLeaderConfig
        .openLoopRampRate(0.4);

      // Brake/Coast & Current Limit
      intakeFollowerConfig
        .idleMode(IdleMode.kBrake)
        .smartCurrentLimit(60);
      // Ramping
      intakeFollowerConfig
        .openLoopRampRate(0.4)
        .follow(MotorIDConstants.intakeTop1MotorID, true);
    }
  }

  // Intake Configs
  public static final class ShoulderConfigs {

    // Init
    public static final TalonFXConfiguration shoulderFalconConfig = new TalonFXConfiguration();
    public static final CANcoderConfiguration shoulderCANcoderConfig = new CANcoderConfiguration();

    static {
      // Brake/Coast & Current Limit
      shoulderFalconConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;
      shoulderFalconConfig.CurrentLimits.SupplyCurrentLimit = 60;

      // Duty Cycle
      shoulderFalconConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = MotorSpeedsConstants.shoulderRampRate;
      shoulderFalconConfig.MotorOutput.PeakForwardDutyCycle = MotorSpeedsConstants.shoulderClosedMaxSpeed;
      shoulderFalconConfig.MotorOutput.PeakReverseDutyCycle = -MotorSpeedsConstants.shoulderClosedMaxSpeed;
      
      // Feedback
      shoulderFalconConfig.Feedback.FeedbackRemoteSensorID = MotorIDConstants.shoulderCANCoderID;
      shoulderFalconConfig.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;

      // PID
      shoulderFalconConfig.Slot0.kP = PIDConstants.shoulderkP;
      shoulderFalconConfig.Slot0.kI = PIDConstants.shoulderkI;
      shoulderFalconConfig.Slot0.kD = PIDConstants.shoulderkP;
      shoulderFalconConfig.Slot0.kS = PIDConstants.shoulderkS;
      shoulderFalconConfig.Slot0.kV = PIDConstants.shoulderkV;
      shoulderFalconConfig.Slot0.kA = PIDConstants.shoulderkA;
      shoulderFalconConfig.Slot0.kG = PIDConstants.shoulderkG;

      // Gravity Type
      // shoulderFalconConfig.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
      
      // Software Limits
      shoulderFalconConfig.SoftwareLimitSwitch.ForwardSoftLimitEnable = true; 
      shoulderFalconConfig.SoftwareLimitSwitch.ForwardSoftLimitThreshold = Units.Rotations.of(0.65).in(Units.Rotations); // TODO: CHECK
      shoulderFalconConfig.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
      shoulderFalconConfig.SoftwareLimitSwitch.ReverseSoftLimitThreshold = Units.Rotations.of(0).in(Units.Rotations);

      // Inverts
      shoulderFalconConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive; // TODO: CHECK!

      // CANCoder invert
      shoulderCANcoderConfig.MagnetSensor.SensorDirection = SensorDirectionValue.Clockwise_Positive; // TODO: Check!
    }
  }
}