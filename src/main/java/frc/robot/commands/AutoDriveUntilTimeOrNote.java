package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

public class AutoDriveUntilTimeOrNote extends Command{
    //wait if time limit reached before note is obtained. still return
    /**
     * This command drives, at the given speed and turning, until it recieves a note (limit switch is pressed)
     * OR until the max amount of time passes and it does not have a note.
     * it can then return to its starting spot 
     * 
     */
    private int timeTaken, timeAllowedtoObtain, waitTime, timeEndWait, timeEndReturning;
    private boolean returnToStartingSpot, obtaining, currentlyWaiting;
    private double RPS, turn, returnSpeedMult;

//TODO change speed to rpm, and make distance a parameter instead of time
//calculate time from distance and rpm

    public AutoDriveUntilTimeOrNote(double maxSeconds, double RPS, boolean comeBack, double turn, double waitSecondsBeforeReturning, double returnSpeedMultiplier)
    {
        returnSpeedMult = returnSpeedMultiplier;
        timeAllowedtoObtain = (int)(maxSeconds * 50);
        this.turn = turn;
        this.RPS = RPS;
        returnToStartingSpot = comeBack;
        timeTaken = 0;
        obtaining = true;
        waitTime = (int)(waitSecondsBeforeReturning*50);
    }

    public AutoDriveUntilTimeOrNote(double maxSeconds, double RPS, boolean comeBack, double waitSecondsBeforeReturning, double returnSpeedMultiplier)
    {
        this(maxSeconds,  RPS, comeBack, 0, waitSecondsBeforeReturning, returnSpeedMultiplier);
    }


    public AutoDriveUntilTimeOrNote(double maxSeconds, double RPS, boolean comeBack, double waitSecondsBeforeReturning)
    {
        this(maxSeconds,  RPS, comeBack, 0, waitSecondsBeforeReturning, 1);
    }

    @Override
  public void initialize() {

    timeTaken = 0;
    obtaining = true;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timeTaken++;

    if(obtaining && timeTaken > timeAllowedtoObtain)
    {
        obtaining = false;
        if(this.returnToStartingSpot)
        {
            currentlyWaiting = true;
            timeEndWait = timeTaken + waitTime;
            timeEndReturning = timeEndWait + (int)(timeTaken*returnSpeedMult);

        }
    }
    if(obtaining && RobotContainer.intakeNoteLimitJustPressed)
    {
        obtaining = false;
        (new IntakeLift()).schedule();
        (new SpinUpShooter()).schedule();
        if(this.returnToStartingSpot)
        {
            timeEndReturning = (int)((1+returnSpeedMult)*timeTaken);
        }
    }
    if(currentlyWaiting)
    {
        if(waitTime < timeTaken)
        {
            currentlyWaiting = false;
        }
    }
    else if(obtaining)
    {
        DriveTrain.driveStraightRPS(RPS);
    }
    else
    {
        DriveTrain.driveStraightRPS(-RPS);
    }

    // if(!RobotContainer.intakePostitionUsed && !Intake.intakeUp && RobotContainer.intakeNoteLimitJustPressed) 
    // {
    //     (new IntakeLift()).schedule();
    // }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.driveStraightRPS(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if(!obtaining && !returnToStartingSpot)
    {
        return true;
    }
    if(!obtaining && timeTaken > timeEndReturning)
    {
       return true;
    }
    
    return false;
  }
    
}
