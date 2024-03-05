// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

public final class AutoForward extends Command{
  @Override
  public void initialize() {
    //negative is towards the goal
    // positive speed is towards the other note
    // (new AutoShootFirstNote()).andThen
    // (new IntakeLower()).andThen
    // (new Wait(1)).andThen
    // (new AutoObtainSecondNote()).andThen
    // (new Wait(1)).andThen
    // (new ShootCommand()).andThen
    // (new Wait(0.5)).andThen
    // (new DriveForTime(2, 0.3, 0))
    // .schedule();
  }


  @Override
  public void execute(){
    //DriveTrain.frontLeftMotor.set(0.15);
    //DriveTrain.driveBoth(0.2, 0);
  }
}
