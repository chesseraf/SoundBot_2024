package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveTrain;


public class DriveWithJoystick extends Command{
  //public static double slowDown;
 // private final DriveTrain driveTrain;

  public static double XJoystick;
  public static double YJoystick;

  private double newSpeed;
  private double turnRate;
  private double prevSpeed = 0;
  public DriveWithJoystick() {
    
  }
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    RobotContainer.UpdateJoystick();
  
    newSpeed =   -YJoystick;
    turnRate = XJoystick;

      if (turnRate > 0) {
        turnRate = (turnRate * turnRate) * (1-Constants.MIN_TURN) + Constants.MIN_TURN;
      } else {
        turnRate = -  XJoystick * XJoystick * (1-Constants.MIN_TURN) - Constants.MIN_TURN;
      }

      if(Math.abs(newSpeed) < Constants.SPEED_DEAD_BAND){
        newSpeed = 0;
        } else if ( newSpeed > 0){
          newSpeed = newSpeed * newSpeed * (1-Constants.MIN_POWER) + Constants.MIN_POWER;
        } else {
          newSpeed = - newSpeed * newSpeed * (1-Constants.MIN_POWER) - Constants.MIN_POWER;
        }

      if(newSpeed-prevSpeed > Constants.MAX_ACCELERATION)
      {
        newSpeed = prevSpeed + Constants.MAX_ACCELERATION;
      }
      else if(prevSpeed - newSpeed > Constants.MAX_ACCELERATION)
      {
        newSpeed = prevSpeed - Constants.MAX_ACCELERATION;
      }
      prevSpeed=newSpeed;

        
      DriveTrain.turnDrive(newSpeed, turnRate );
  }// end of execute

  @Override
  public boolean isFinished() {
          return false;
      }
}