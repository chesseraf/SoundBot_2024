package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class DriveForTimeAtRPS extends Command {
    private double totTime, speedRPS, turnRPS;
    private int timer;
    
    public DriveForTimeAtRPS(double seconds, double speedRPS, double turn)
    {
        this.totTime = seconds*50;
        this.speedRPS = speedRPS;
        this.turnRPS = turn;
    }

    public DriveForTimeAtRPS(double seconds, double speedRPS)
    {
        this.totTime = seconds*50;
        this.speedRPS = speedRPS;
        this.turnRPS = 0;
    }

    

    @Override
  public void initialize() {
    System.out.println("driveing at RPS"+speedRPS);
        timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;

    System.out.println("driveing at RPS"+speedRPS);

    DriveTrain.driveTurnRPS(speedRPS, turnRPS); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (totTime < timer);
  }

}
