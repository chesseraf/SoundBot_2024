package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;


public class AutoObtainSecondNote extends Command{

    int timer;

    @Override
  public void initialize() {

    timer = 0;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    DriveTrain.driveBoth(Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0);
    timer++;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.driveBoth(0, 0);
    (new Wait(0.5)).andThen(new DriveForTime(timer/50.0 ,-Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0.0)).schedule();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(RobotContainer.intakeLimitSwitch.get() || timer > 50*Constants.ATUO_DRIVE_BACK_SECONDS)
    {
        return true;
    }
    return false;
  }


    
}
