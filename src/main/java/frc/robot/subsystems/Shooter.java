package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class Shooter extends Command{
    //public static TalonFX shootMotorNumFront = new TalonFX(Constants.SHOOT_MOTOR_ID_Front);
    //public static TalonFX shootMotorNumBack = new TalonFX(Constants.SHOOT_MOTOR_ID_Back);
    
   public static CANSparkMax shootMotorNumOuter = new CANSparkMax(Constants.SHOOTER_OUTER_WHEEL_ID, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);
   public static CANSparkMax shootMotorNumInner = new CANSparkMax(Constants.SHOOTER_INNER_WHEEL_ID, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);

   public static double outerCustomShooterSpeed;
   public static double innerCustomShooterSpeed;
//    private double innerSpeed;
//    private double outerSpeed;

//    public Shooter()
//    {
//      innerSpeed = Constants.SHOOTER_INNER_SPEED;
//      outerSpeed = Constants.SHOOTER_OUTER_SPEED;
//    }
//    public Shooter(double innerSpeed, double outerSpeed)
//    {
//      this.innerSpeed = innerSpeed;
//      this.outerSpeed = outerSpeed;
//    }
    public static void setShooterMotors(double outerSpeed, double innerSpeed)
    {
        shootMotorNumInner.set(innerSpeed);
        shootMotorNumOuter.set(outerSpeed);
    }
    public static void spinShooterHighShot()
    {
        setShooterMotors(Constants.SHOOTER_OUTER_HIGH_SHOT_SPEED, Constants.SHOOTER_INNER_HIGH_SHOT_SPEED);
    }

    public static void spinShooterLowShot()
    {
        setShooterMotors(Constants.SHOOTER_OUTER_LOW_SHOT_SPEED, Constants.SHOOTER_INNER_LOW_SHOT_SPEED);
    }
    public static void spinShooterShuttleShot()
    {
        setShooterMotors(Constants.SHOOTER_OUTER_SHUTTLE_SHOT_SPEED, Constants.SHOOTER_INNER_SHUTTLE_SHOT_SPEED);
    }

    public static void spinCustomSpeed()
    {
        setShooterMotors(outerCustomShooterSpeed, innerCustomShooterSpeed);
    }

    public static void stop()
    {
        setShooterMotors(0, 0);
    }

}
