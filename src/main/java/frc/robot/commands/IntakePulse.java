package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class IntakePulse extends Command{

    int timer = 0;
    int timeIntoEachPulse = 30; // cycles
    int timeOutEachPulse = 30; //cycles 
    double inwardSpeedEachPulse = 0.1;
    double outSpeedEachPulse = -0.1;

    boolean goingIn;


  @Override
  public void initialize() {
    timer = 0;
    goingIn = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;
    if(timer % (timeIntoEachPulse + timeOutEachPulse) < timeIntoEachPulse)
    {
        Intake.intakeLiftMotor.set(inwardSpeedEachPulse);
    }
    else{
        Intake.intakeLiftMotor.set(outSpeedEachPulse);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(!Intake.intakeUp)  return true;
    if(RobotContainer.THRUSTMASTER.getRawButtonPressed(Constants.STOP_PULSING_BUTTON))
        return true;
    if(ShootCommand.currentlyShooting)
        return true;
    return false;
  }
}
