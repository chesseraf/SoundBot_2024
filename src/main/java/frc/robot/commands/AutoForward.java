// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;

public final class AutoForward extends Command{
  @Override
  public void initialize() {
    (new DriveForTime(2, 0.1, 0)).andThen
    (new ShootCommand()).andThen
    (new IntakeLower()).andThen
    (new Wait(1)).andThen
    (new DriveForTime(2, -0.2, 0)).andThen
    (new Wait(1)).andThen
    (new DriveForTime(2, 0.2, 0)).andThen
    (new ShootCommand())
    .schedule();
  }


  @Override
  public void execute(){
    //DriveTrain.frontLeftMotor.set(0.15);
    //DriveTrain.driveBoth(0.2, 0);
  }
}
