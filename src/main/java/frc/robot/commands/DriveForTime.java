// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class DriveForTime extends Command {
  private double speed;
  private double turning;

  private int counter = 0;
  private int target = 0;

  /** Creates a new driveForTime. */
  public DriveForTime(double driveTime, double speed, double turning) {
    this.speed = speed;
    this.turning = turning;

    //50 cycles per second
    target = (int) (driveTime * 50);
    //target = 0;
    counter = 0;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    counter = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    DriveTrain.driveBoth(speed, turning);
    SmartDashboard.putString("Seconds left of drive for time ", ((Double)((target - counter)/50.0)).toString());
    SmartDashboard.putString("Speed, turning: ", (speed +", " + turning));
    
    counter++;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putString("Seconds left of drive for time ", "OFF");
    SmartDashboard.putString("Speed, turning: ", "OFF");

    DriveTrain.driveBoth(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(counter<target){
      return false;
    }
    else{
      return true;
    }
  }
}
