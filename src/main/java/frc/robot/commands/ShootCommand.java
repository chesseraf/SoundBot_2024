// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/** An example command that uses an example subsystem. */
public class ShootCommand extends Command {

  // Called when the command is initially scheduled.
  int timer;

  public static final int HIGH_SHOT = 0;
  public static final int LOW_SHOT = 1;
  public static final int SHUTTLE_SHOT = 2, SAFTEY_SHOT = 3;
  
  private int shotType;
  private boolean customShotInstead, shooterAlreadySpunUp;

  

  public ShootCommand(int shotType)
  {
    this.shotType = shotType;
  }
  public ShootCommand()
  {
    this(HIGH_SHOT);
  }
  
  public static boolean currentlyShooting = false;
  @Override
  public void initialize() {
    shooterAlreadySpunUp = SpinUpShooter.isSpunUp();
    timer = 0;
    if(!Intake.intakeUp)
    {}//do nothing
    else
    {
      currentlyShooting = true;
      System.out.println("Initialized shoot command");
    }
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

    SmartDashboard.putNumber( "shooting timer ",timer);
    SmartDashboard.putBoolean( "spun up already ",shooterAlreadySpunUp);
    
    if(Intake.intakeUp)
    {
      if(!shooterAlreadySpunUp)
      {
        if(customShotInstead)
        {
          Shooter.spinCustomSpeed();
        }
        else
        {
          if(shotType == HIGH_SHOT)
          {
            Shooter.spinShooterHighShot();
          }
          else if (shotType == LOW_SHOT)
          {
            Shooter.spinShooterLowShot();
          }
          else if(shotType == SHUTTLE_SHOT)
          {                
            Shooter.spinShooterShuttleShot();
          }
          else if(shotType == SAFTEY_SHOT)
          {
            Shooter.spinShooterSafteyShot();
          }
        }
        if(timer > Constants.DELAY_STARTING_SHOOTER_BEFORE_REVERSE_INTAKE)
        {
          Intake.intakeWheels.set(Constants.INTAKE_REVERSE_SHOOT_SPEED);
        }
      }
      else //shooter was already spun up
      {
          Intake.intakeWheels.set(Constants.INTAKE_REVERSE_SHOOT_SPEED);
      } 
    }
    else
    {
      SmartDashboard.putString("INTAKE IS DOWN", "INTAKE IS DOWN");
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if(!SpinUpShooter.shooterSpinningUp)
    {
      Shooter.stop();
    }
    
    Intake.intakeWheels.set(0);
    currentlyShooting = false;
    SpinUpShooter.shotJustEnded = true;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(!Intake.intakeUp)
    {
      return true;
    }
    
    if(!shooterAlreadySpunUp)
    {            
      return(timer > Constants.DELAY_STARTING_SHOOTER_BEFORE_REVERSE_INTAKE + Constants.DELAY_AFTER_SHOOTING_BEFORE_STOPPING_SHOOTER);
    }
    else
    {
      return(timer > Constants.DELAY_AFTER_SHOOTING_BEFORE_STOPPING_SHOOTER);
    }   
  }

  public double shootSpeed(int time)
  {
    return(Constants.SHOOTER_SPEED);
  }

}
