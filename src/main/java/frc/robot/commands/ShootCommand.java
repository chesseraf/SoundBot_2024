// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

/** An example command that uses an example subsystem. */
public class ShootCommand extends Command {

  // Called when the command is initially scheduled.
  int timer;
  @Override
  public void initialize() {
    timer = 0;
    System.out.println("Initialized shoot command");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Shooter.setShooterMotors(shootSpeed(timer));
    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Shooter.setShooterMotors(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public double shootSpeed(int time)
  {
    return(Constants.SHOOTER_SPEED);
  }
}
