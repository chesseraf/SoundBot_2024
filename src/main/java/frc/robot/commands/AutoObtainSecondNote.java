package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;


public class AutoObtainSecondNote extends Command{

    int timer;
    boolean comeBack;

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

    DriveTrain.driveBoth(Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0);
    timer++;

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveTrain.driveBoth(0, 0);
    if(comeBack)
    {
        (new Wait(0.5))
        .andThen(new IntakeLift())
        .andThen(new DriveForTime(timer/50.0 *0.8,-Constants.AUTO_OBTAIN_SECOND_NOTE_SPEED, 0.0))
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
