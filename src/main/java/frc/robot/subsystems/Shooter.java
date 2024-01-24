package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class Shooter extends Command{
    public static TalonFX shootMotor = new TalonFX(Constants.SHOOT_MOTOR_ID);
    
}
