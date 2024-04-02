// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ClimbUp extends Command {
  //@SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  //private final ExampleSubsystem m_subsystem;

  /**
   * Creates a new ExampleCommand.
   *
   
   */
  int timer;
  public static double climbValue;

  

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer = 0;
    

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(climbValue == 0)
    {
        Climber.spinClimbersUp();
    }
    else if(climbValue == 180)
    {
        Climber.spinClimbersDown();
    }
    else
    {
        Climber.stopClimbers();
    }

    
    
    timer++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
