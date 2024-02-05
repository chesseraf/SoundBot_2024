package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class Shooter extends Command{
    //public static TalonFX shootMotorNumFront = new TalonFX(Constants.SHOOT_MOTOR_ID_Front);
    //public static TalonFX shootMotorNumBack = new TalonFX(Constants.SHOOT_MOTOR_ID_Back);
    
    public static CANSparkMax shootMotorNumFront = new CANSparkMax(13, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);
    public static CANSparkMax shootMotorNumBack = new CANSparkMax(11, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);

    public static void setShooterMotors(double speed)
    {
        shootMotorNumBack.set(speed);
        shootMotorNumFront.set(speed);
    }

}
