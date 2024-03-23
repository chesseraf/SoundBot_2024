package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class SpinUpShooter extends Command{
  private int shotType;
  private boolean customShotInstead;
  public static boolean shotJustEnded;
  public static int timer;
  public static boolean shooterSpinningUp = false;
  public static boolean disableSpinUpShooter = false;

  

  public SpinUpShooter(int shotType)
  {
    this.shotType = shotType;
  }
  public SpinUpShooter()
  {
    this(ShootCommand.HIGH_SHOT);
  }
  public static boolean isSpunUp()
  {
    return timer > 60;
  }
  
  @Override
  public void initialize() {
    disableSpinUpShooter = false;
    shotJustEnded = false;
    shooterSpinningUp = true;
    timer = 0;

    if(Robot.useAlternativeShootingSpeeds.getSelected())
    {
      Shooter.innerCustomShooterSpeed = 0.1*Robot.alternativeInnerShootingSpeedTenths.getSelected() + 0.01 * Robot.alternativeInnerShootingSpeedHundreths.getSelected();
      Shooter.outerCustomShooterSpeed = 0.1*Robot.alternativeOuterShootingSpeedTenths.getSelected() + 0.01 * Robot.alternativeOuterShootingSpeedHundreths.getSelected();
      customShotInstead = true;
    }
    else
    {
      customShotInstead =false;
    }
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {          
    timer++;

      if(customShotInstead)
      {
        Shooter.spinCustomSpeed();
      }
      else
      {
        if(shotType == ShootCommand.HIGH_SHOT)
        {
          Shooter.spinShooterHighShot();
        }
        else if (shotType == ShootCommand.LOW_SHOT)
        {
          Shooter.spinShooterLowShot();
        }
        else if(shotType == ShootCommand.SHUTTLE_SHOT)
        {                
          Shooter.spinShooterShuttleShot();
        }
        else if(shotType == ShootCommand.SAFTEY_SHOT)
        {
          Shooter.spinShooterSafteyShot();
        }
      }
    }
  

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.stop();
    shooterSpinningUp = false;
    timer = 0;
    disableSpinUpShooter = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() 
  {  
    if(Robot.inTeleop && (shotJustEnded || RobotContainer.justPressedButtons[Constants.STOP_SPUN_SHOOTER_BUTTON]))
    {
        return true;
    }
    if(disableSpinUpShooter)
    {
      return true;
    }

    return false;
  }
}

