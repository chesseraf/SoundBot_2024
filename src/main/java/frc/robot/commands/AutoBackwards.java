// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.Command;

public final class AutoBackwards extends Command{
  @Override
  public void initialize() {
    
  }


  @Override
  public void execute(){
    DriveTrain.driveBoth(-0.2, 0);
  }
}
