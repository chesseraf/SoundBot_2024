package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;


public class AutoObtainSecondNote extends Command{
  //obtain the next planned note for the auto

    int timer;
    boolean comeBack;
   public static Command obtainSecondNote;

   final static double turnRate = 0.255, turnTime = 1, driveTime = 5, driveSpeed = 0.3, initMoveSpeed = 0.25, initMoveTime = 1;
    public static Command getAutoObtainSecondNoteCommand(int col, int startingLoc, boolean closerNote)
    {
      if(startingLoc == Robot.START_NEAR_AMP)
      {
        if(closerNote)
        {
          if(col == Robot.COL_BLUE)
          {
            return(
              (new Wait(2)).andThen
              (new DriveForTime(initMoveTime, initMoveSpeed,0)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(turnTime, 0, turnRate)).andThen
              (new Wait(2)).andThen
              
              (new AutoDriveUntilTimeOrNote(driveTime, driveSpeed, true, 1)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(turnTime, 0, -turnRate)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(initMoveTime, -initMoveSpeed,0)).andThen
              (new Wait(2))
            );
          }
          else //red so the turning is reversed
          {
            return(
            (new Wait(2)).andThen
            (new DriveForTime(initMoveTime, initMoveSpeed,0)).andThen
            (new Wait(2)).andThen
            (new DriveForTime(turnTime, 0, turnRate    *(-1))).andThen
            (new Wait(2)).andThen
            
            (new AutoDriveUntilTimeOrNote(driveTime, driveSpeed, true, 1)).andThen
            (new Wait(2)).andThen
            (new DriveForTime(turnTime, 0, -turnRate    *(-1))).andThen
            (new Wait(2)).andThen
            (new DriveForTime(initMoveTime, -initMoveSpeed,0)).andThen
            (new Wait(2))
            );
          }
        }
        else //further Note
        {
          if(col == Robot.COL_BLUE)
          {
            return(
              (new Wait(2)).andThen
              (new DriveForTime(initMoveTime, initMoveSpeed,0)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(turnTime, 0, turnRate)).andThen
              (new Wait(2)).andThen
              
              (new AutoDriveUntilTimeOrNote(driveTime, driveSpeed, true, 1, 0.8)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(turnTime, 0, -turnRate)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(initMoveTime, -initMoveSpeed,0)).andThen
              (new Wait(2))
            );
          }
          else //further note, red
          {

          }
        }
      }
      else if(startingLoc == Robot.START_MID)
      {
        return(
          (new AutoDriveUntilTimeOrNote(3, 0.5, true,0, 1, 1))
        );
      }
      else if(startingLoc == Robot.START_FAR_FROM_AMP)
      {
        if(closerNote)
        {
          if(col == Robot.COL_BLUE)
          {

          }
          else //red closer note
          {

          }
        }
        else //further note
        {
          if(col == Robot.COL_BLUE)
          {

          }
          else //red closer note
          {

          }
        }
      }
      return obtainSecondNote;
    }

    public AutoObtainSecondNote(boolean comeBack)
    {
      this.comeBack = comeBack;
    }

    @Override
  public void initialize() {

    timer = 0;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;

    if(Robot.startLoc == Robot.START_MID)
    {
      DriveTrain.driveBoth(Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0);
    }
 //   else if(Robot.startLoc == Robot.START_NEAR_AMP)
    

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.driveBoth(0, 0);
    if(comeBack)
    {
        (new Wait(0.5))
        .andThen(new IntakeLift())
        .andThen(new DriveForTime(timer/50.0 *1.1,-Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0.0))
        .andThen(new ShootCommand()).schedule();
    }
    else
    {
      (new Wait(0.5))
        .andThen(new IntakeLift()).schedule();
    }
    

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(RobotContainer.pressedIntakeNoteLimitSwitch)// || timer > 50*Constants.ATUO_DRIVE_BACK_SECONDS)
    {
        return true;
    }
    return false;
  }

}
