package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class Shooter extends Command{
    //public static TalonFX shootMotorNumFront = new TalonFX(Constants.SHOOT_MOTOR_ID_Front);
    //public static TalonFX shootMotorNumBack = new TalonFX(Constants.SHOOT_MOTOR_ID_Back);
    
    public static CANSparkMax shootMotorNumOuter = new CANSparkMax(Constants.SHOOTER_TOP_WHEEL_ID, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);
    public static CANSparkMax shootMotorNumInner = new CANSparkMax(Constants.SHOOTER_LOWER_WHEEL_ID, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);

    public static void setShooterMotors(double outerSpeed, double innerSpeed)
    {
        shootMotorNumInner.set(innerSpeed);
        shootMotorNumOuter.set(outerSpeed);
    }
    public static void spinShooter()
    {
        setShooterMotors(Constants.SHOOTER_OUTER_SPEED, Constants.SHOOTER_INNER_SPEED);
    }
    public static void stop()
    {
        setShooterMotors(0, 0);
    }

}
