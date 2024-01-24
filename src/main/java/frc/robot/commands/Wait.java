package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public class Wait extends Command {
    int timer;
    int target;

    public Wait(int seconds)
    {
        target = seconds*50;
    }

    @Override
  public void initialize() {
    timer  = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return timer>target;
  }

    
}
