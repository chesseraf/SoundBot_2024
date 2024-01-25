package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class Shooter extends Command{
    public static TalonFX shootMotorNumFront = new TalonFX(Constants.SHOOT_MOTOR_ID_Front);
    public static TalonFX shootMotorNumBack = new TalonFX(Constants.SHOOT_MOTOR_ID_Back);

    public static void setShooterMotors(double speed)
    {
        shootMotorNumBack.set(speed);
        shootMotorNumFront.set(speed);
    }

}
