package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class DriveDistanceAtSpeed extends Command {
    private double totTime, RPM;
    int timer;
    public DriveDistanceAtSpeed(double time, double RPM)
    {
        this.totTime = time;
        this.RPM = RPM;
    }

    @Override
  public void initialize() {
        timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;
    DriveTrain.frontLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPM));
    DriveTrain.frontRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPM));
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
