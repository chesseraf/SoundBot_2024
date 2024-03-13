package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class IntakePulse extends Command{

    int timer = 0;
    int timeIntoEachPulse = 30; // cycles
    int timeOutEachPulse = 10; //cycles 
    double inwardSpeedEachPulse = 0.1;
    double outSpeedEachPulse = -0.07;
    boolean inward = true;
    int lastRec = 0;
    int TOTAL_PULSES = 3;

    //boolean goingIn;
    int timeFin = 0, timeStartGoingOutMax = 0;
    int curPulseNum = 0;
    int timeInAfterLimitPress = 4; //cycles
    boolean limitHasBeenPressed = false;

  @Override
  public void initialize() {
    timer = 0;
    curPulseNum = 0;
    
    inward = false;
    timeFin = timeOutEachPulse;
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;

    // if(timer % (timeIntoEachPulse + timeOutEachPulse) < timeIntoEachPulse)
    // {
    //     Intake.intakeLiftMotor.set(inwardSpeedEachPulse);
    // }
    // else{
    //     Intake.intakeLiftMotor.set(outSpeedEachPulse);
    // }
    // if(inward && !RobotContainer.intakeLimitSwitch.get())
    // {
    //     Intake.intakeLiftMotor.set(outSpeedEachPulse);
    //     lastRec = timer;
    // }
    // else if(!inward && timer-lastRec > timeOutEachPulse)
    // {
    //     Intake.intakeLiftMotor.set(inwardSpeedEachPulse);
    // }

    if(inward && RobotContainer.pressedIntakeNoteLimitSwitch)
    {
      if(!limitHasBeenPressed)
      {
        limitHasBeenPressed = true;
      
        timeFin = timer + timeInAfterLimitPress;
      }
      
    }
    if(inward && ((limitHasBeenPressed && (timer > timeFin)) || (timer > timeStartGoingOutMax)))
    {
      inward = false;
      curPulseNum++;
      timeFin = timer + timeOutEachPulse;

    }

    if(!inward && (timeFin < timer))
    {
        inward = true;
        limitHasBeenPressed = false;
        timeStartGoingOutMax = timer + timeIntoEachPulse;
    }
    if(curPulseNum < TOTAL_PULSES)
    {
      if(inward)
      {
          Intake.intakeWheels.set(inwardSpeedEachPulse);
          SmartDashboard.putString("PULSE DIRECTION", "IN");
      }
      else
      {
          SmartDashboard.putString("PULSE DIRECTION", "OUT");
          Intake.intakeWheels.set(outSpeedEachPulse);
      }
    }
    else
    {
      Intake.intakeWheels.set(0);
    }

    
    SmartDashboard.putNumber("TIMER", timer);
    SmartDashboard.putNumber("timeFin", timeFin);
   // SmartDashboard.putNumber("TIMER", timer);
    


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(curPulseNum >= TOTAL_PULSES)
    {
      Intake.intakeWheels.set(0);
      return true;
    }
    if(!Intake.intakeUp)  return true;
    if(RobotContainer.ps4.getRawButtonPressed(Constants.STOP_PULSING_BUTTON))
        return true;
    if(ShootCommand.currentlyShooting)
        return true;
    return false;
  }
}
