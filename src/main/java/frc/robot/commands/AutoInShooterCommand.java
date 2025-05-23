package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.TeleopTimeConstants;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoInShooterCommand extends Command{
    //instantiate stuff
    IntakeSubsystem m_intakeSubsystem;
    IndexerSubsystem m_indexerSubsystem;
    Timer timer = new Timer();

    public AutoInShooterCommand(IntakeSubsystem intakeSubsystem, IndexerSubsystem indexerSubsystem){
        //definitions and setting parameters equal to members
        m_intakeSubsystem = intakeSubsystem;
        m_indexerSubsystem = indexerSubsystem;
        addRequirements(m_intakeSubsystem);
        addRequirements(m_indexerSubsystem);
    }

    public void initialize() {
        timer.start();
        timer.reset();
        m_intakeSubsystem.stop();
    }
        
    @Override
    public void execute() {
        m_intakeSubsystem.stop();
       // if(timer.get()<TeleopTimeConstants.handoffIndexerReverseTime){
        m_indexerSubsystem.runBackSlow();
         //   }
          /*  else{
                m_indexerSubsystem.stop();
            }*/
    }

    @Override
    public void end(boolean interrupted){
        m_indexerSubsystem.stop();
        m_intakeSubsystem.stop();
    }

    @Override
    public boolean isFinished(){
        return timer.hasElapsed(TeleopTimeConstants.autoHandoffIndexerReverseTime);
    }

}