package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;

public class AutoObtainNextNote extends Command{
  //obtain the next planned note for the auto

    int timer;
    boolean comeBack;
   public static Command obtainSecondNote;
//CA - closer note near amp
   final static double turnRateCA = 0.268, turnTimeCA = 1.15, driveTimeCA = 4, driveSpeedCA = 0.6, initMoveSpeedCA = 0.5, initMoveTimeCA = 0.4;
   //FA - further note near amp

   final static double turnRateFA = 0.255, turnTimeFA = 1.1, driveTimeFA = 12, driveSpeedFA = 0.3, initMoveSpeedFA = 0.25, initMoveTimeFA = 4;

   // return((new DriveForTimeAtRPS(1,10)
            // .andThen(new TurnForTimeAtRPS(1, -5))
            // .andThen(new AutoDriveUntilTimeOrNote(4, 20, true, 1, 1))
            // .andThen(new TurnForTimeAtRPS(1, 5))
            // .andThen(new DriveForTimeAtRPS(1,-10)))
            // .andThen(new DriveForTimeAtRPS(0.1, 0))
            // );
   final static double sideNoteInitSpeedRPS = 15,  sideNoteTurnRateRPS = 10., sideNoteDriveSpeedRPS = 20;
   final static double sideNoteInitTime = 0.5,  sideNoteTurnRateTime = 0.52, sideNoteDriveTime = 4;
   
   public static Command getAutoObtainSecondNoteCommand(int col, int startingLoc, int noteNum)
    {
      if(startingLoc == Robot.START_NEAR_AMP)
      {
        if(noteNum == Robot.CLOSER_FIRST)
        {
          if(col == Robot.COL_BLUE)
          {
            return(
              //(new Wait(2)).andThen
              (new DriveForTime(initMoveTimeCA, initMoveSpeedCA,0)).andThen
              //(new Wait(2)).andThen
              (new DriveForTime(turnTimeCA, 0, turnRateCA)).andThen
              //(new Wait(2)).andThen
              
              (new AutoDriveUntilTimeOrNote(driveTimeCA, driveSpeedCA, true, 1, 0.9)).andThen
              //(new Wait(2)).andThen
              (new DriveForTime(turnTimeCA*1.1, 0, -turnRateCA)).andThen
              //(new Wait(2)).andThen
              (new DriveForTime(initMoveTimeCA*1.2, -initMoveSpeedCA,0))
              //.andThen(new Wait(2))
            );
          }
          else //red so the turning is reversed
          {
            // return(
            // (new Wait(2)).andThen
            // (new DriveForTime(initMoveTime, initMoveSpeed,0)).andThen
            // (new Wait(2)).andThen
            // (new DriveForTime(turnTime, 0, turnRate    *(-1))).andThen
            // (new Wait(2)).andThen
            
            // (new AutoDriveUntilTimeOrNote(driveTime, driveSpeed, true, 1)).andThen
            // (new Wait(2)).andThen
            // (new DriveForTime(turnTime, 0, -turnRate    *(-1))).andThen
            // (new Wait(2)).andThen
            // (new DriveForTime(initMoveTime, -initMoveSpeed,0)).andThen
            // (new Wait(2))
            // );
          }
        }
        else //further Note
        {
          if(col == Robot.COL_BLUE)
          {
            return(
              (new Wait(2)).andThen
              (new DriveForTime(initMoveTimeFA, initMoveSpeedFA,0)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(turnTimeFA, 0, turnRateFA)).andThen
              (new Wait(2)).andThen
              
              (new AutoDriveUntilTimeOrNote(driveTimeFA, driveSpeedFA, true, 1, 0.8)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(turnTimeFA, 0, -turnRateFA)).andThen
              (new Wait(2)).andThen
              (new DriveForTime(initMoveTimeFA, -initMoveSpeedFA,0)).andThen
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
        if(noteNum == Robot.BEHIND_NOTE)
        {
            return((new AutoDriveUntilTimeOrNote(4, 20, true, 1, 1.1)));

        }
        else if((noteNum == Robot.LEFT_NOTE && col == Robot.COL_BLUE) || (noteNum == Robot.RIGHT && col == Robot.COL_RED))
        {
            return((new DriveForTimeAtRPS(sideNoteInitTime, sideNoteInitSpeedRPS)
            .andThen(new TurnForTimeAtRPS(sideNoteTurnRateTime, sideNoteTurnRateRPS))
            .andThen(new AutoDriveUntilTimeOrNote(sideNoteDriveTime, sideNoteDriveSpeedRPS, true, 1, 1))
            .andThen(new TurnForTimeAtRPS(sideNoteTurnRateTime, -sideNoteTurnRateRPS))
            .andThen(new DriveForTimeAtRPS(sideNoteInitTime, -sideNoteInitSpeedRPS))
            .andThen(new DriveForTimeAtRPS(0.1, 0))) //stop
            );

        }
        else if((noteNum == Robot.RIGHT && col == Robot.COL_RED) || (noteNum == Robot.LEFT_NOTE && col == Robot.COL_BLUE))
        {
          return((new DriveForTimeAtRPS(sideNoteInitTime, sideNoteInitSpeedRPS)
            .andThen(new TurnForTimeAtRPS(sideNoteTurnRateTime, -sideNoteTurnRateRPS))
            .andThen(new AutoDriveUntilTimeOrNote(sideNoteDriveTime, sideNoteDriveSpeedRPS, true, 1, 1))
            .andThen(new TurnForTimeAtRPS(sideNoteTurnRateTime, sideNoteTurnRateRPS))
            .andThen(new DriveForTimeAtRPS(sideNoteInitTime, sideNoteInitSpeedRPS))
            .andThen(new DriveForTimeAtRPS(0.1, 0))) //stop
            );
            
        }
        
        
        /*
        return(
          (new AutoDriveUntilTimeOrNote(3, 0.5, true,0, 1, 1))
        );
       */

      }
      else if(startingLoc == Robot.START_FAR_FROM_AMP)
      {
        if(noteNum == Robot.CLOSER_FIRST)
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

//     public AutoObtainNextNote(boolean comeBack)
//     {
//       this.comeBack = comeBack;
//     }

//     @Override
//   public void initialize() {

//     timer = 0;

//   }

//   // Called every time the scheduler runs while the command is scheduled.
//   @Override
//   public void execute() {
//     timer++;

//     if(Robot.startLoc == Robot.START_MID)
//     {
//       DriveTrain.driveBoth(Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0);
//     }
//  //   else if(Robot.startLoc == Robot.START_NEAR_AMP)
    

//   }

//   // Called once the command ends or is interrupted.
//   @Override
//   public void end(boolean interrupted) {
//     DriveTrain.driveBoth(0, 0);
//     // if(comeBack)
//     // {
//     //     (new Wait(0.5))
//     //     .andThen(new IntakeLift())
//     //     .andThen(new DriveForTime(timer/50.0 *1.1,-Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0.0))
//     //     .andThen(new ShootCommand()).schedule();
//     // }
//     // else
//     // {
//     //   (new Wait(0.5))
//     //     .andThen(new IntakeLift()).schedule();
//     // }
    

//   }

//   // Returns true when the command should end.
//   @Override
//   public boolean isFinished() {
//     if(RobotContainer.pressedIntakeNoteLimitSwitch)// || timer > 50*Constants.ATUO_DRIVE_BACK_SECONDS)
//     {
//         return true;
//     }
//     return false;
//   }

}
