package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShoulderSubsystem;

public class SetShoulderTagChaseCommand extends Command{
    //instantiate stuff
    ShoulderSubsystem m_shoulderSubsystem;
    Timer timer = new Timer();

    public SetShoulderTagChaseCommand(ShoulderSubsystem shoulderSubsystem){
        //definitions and setting parameters equal to members
        m_shoulderSubsystem = shoulderSubsystem;
        addRequirements(m_shoulderSubsystem);
    }

    public void initialize() {
        timer.start();
        timer.reset();
    }
        
    public void execute() {

        m_shoulderSubsystem.setTagChase();
    }

    public void end(boolean interrupted){}

    public boolean isFinished(){
        return timer.get() > 4;
    }

}