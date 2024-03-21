package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;


public class DriveWithJoystick extends Command{
  //public static double slowDown;
 // private final DriveTrain driveTrain;

  public static double XJoystick;
  public static double YJoystick;

  public static Double leftJoy;
  public static Double rightJoy;

  private Double newLeft;
  private Double newRight;
  

  private double newSpeed;
  private double turnRate;
  private double prevSpeed = 0;

  private double prevLeft = 0.0;
  private double prevRight = 0.0;

  public DriveWithJoystick() {
    
  }
  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    
  
    turnRate =   -YJoystick;
    newSpeed = XJoystick;

    // newLeft = leftJoy;
    // newRight = rightJoy;

    // applyDeadBand(newLeft, Constants.SPEED_DEAD_BAND);
    // applyDeadBand(newRight, Constants.SPEED_DEAD_BAND);

    // affectMax(newLeft, prevLeft, Constants.MAX_ACCELERATION);
    // affectMax(newRight, prevRight, Constants.MAX_ACCELERATION);

    // DriveTrain.driveBothTank(newLeft, newRight);

    // prevLeft = newLeft;
    // prevRight = newRight;


      // if (turnRate > 0) {
      //   turnRate = (turnRate * turnRate) * (1-Constants.MIN_TURN) + Constants.MIN_TURN;
      // } else {
      //   turnRate = -turnRate * turnRate * (1-Constants.MIN_TURN) - Constants.MIN_TURN;
      // }

      turnRate = turnRate *Math.abs(turnRate)*0.9;

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
  
  public void affectMax(Double next, double prev, double maxChange)
  {
    if(next - prev > maxChange)
      next = prev + maxChange;
    
    if(prev - next > maxChange)
      next = prev - maxChange;
  }
  public void applyDeadBand(Double cur, double deadBand)
  {
    if(Math.abs(cur)<deadBand)
    {
      cur = 0.0;
    }
  }
}