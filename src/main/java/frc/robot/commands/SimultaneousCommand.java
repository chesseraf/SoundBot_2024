package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class SimultaneousCommand extends Command{
    Command toRun;
    public SimultaneousCommand(Command commandToRun)
    {
        toRun = commandToRun;
    }
    @Override
    public boolean isFinished()
    {
        return true;
    }
    @Override
    public void end(boolean interrupted)
    {
        toRun.schedule();
    }

}
