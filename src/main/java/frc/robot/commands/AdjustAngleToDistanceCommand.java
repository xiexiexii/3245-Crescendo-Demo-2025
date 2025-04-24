package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShoulderSubsystem;

public class AdjustAngleToDistanceCommand extends Command{
    //instantiate stuff
    ShoulderSubsystem m_shoulderSubsystem;

    public AdjustAngleToDistanceCommand(ShoulderSubsystem shoulderSubsystem){
        //definitions and setting parameters equal to members
        m_shoulderSubsystem = shoulderSubsystem;
        addRequirements(m_shoulderSubsystem);
    }

    @Override
    public void initialize() {
    }
        
    @Override
    public void execute() {
        // m_shoulderSubsystem.setDistShot();
    }

    @Override
    public void end(boolean interrupted){
       
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}