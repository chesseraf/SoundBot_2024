// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

/** An example command that uses an example subsystem. */
public class ShootCommand extends Command {

  // Called when the command is initially scheduled.
  int timer;
  
  public static boolean currentlyShooting;
  @Override
  public void initialize() {
    if(Intake.intakeUp)
    {
      timer = 0;
      currentlyShooting = true;
      System.out.println("Initialized shoot command");
    }
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Intake.intakeUp)
    {
      Shooter.spinShooter();
      timer++;
      if(timer > Constants.DELAY_STARTING_SHOOTER_BEFORE_REVERSE_INTAKE)
      {
        Intake.intakeWheels.set(Constants.INTAKE_REVERSE_SHOOT_SPEED);
      }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.stop();
    Intake.intakeWheels.set(0);
    currentlyShooting = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(Intake.intakeUp)
    {
      return(timer > Constants.DELAY_STARTING_SHOOTER_BEFORE_REVERSE_INTAKE + 100);
    }
    else
    {
      return true;
    }
    
  }

  public double shootSpeed(int time)
  {
    return(Constants.SHOOTER_SPEED);
  }
}
