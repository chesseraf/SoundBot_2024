package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class TurnForTimeAtRPS extends Command{

    private double totTime, RPS;
    int timer;
    public TurnForTimeAtRPS(double seconds, double RPS)
    {
        this.totTime = seconds*50;
        this.RPS = RPS;
    }

    @Override
  public void initialize() {
    System.out.println("turning at RPS"+RPS);
        timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;

    System.out.println("turning at RPS"+RPS);

    DriveTrain.frontLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));
    DriveTrain.frontRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));


    DriveTrain.backLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));
    DriveTrain.backRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));  
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
