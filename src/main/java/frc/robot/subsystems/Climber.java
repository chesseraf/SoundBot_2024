package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import frc.robot.Constants;

public class Climber {
    final static int leftMotorClimbID = 5, rightMotorClimbID = 4;
    public static CANSparkMax leftClimbMoter = new CANSparkMax(leftMotorClimbID, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);

    public static CANSparkMax rightClimbMoter = new CANSparkMax(leftMotorClimbID, com.revrobotics.CANSparkLowLevel.MotorType.kBrushed);
    


    public static void spinClimbersUp()
    {
        leftClimbMoter.set(Constants.CLIMBER_LIFT_SPEED);
        rightClimbMoter.set(Constants.CLIMBER_LIFT_SPEED);
    }

    public static void spinClimbersDown()
    {
        leftClimbMoter.set(Constants.CLIMBER_LOWER_SPEED);
        rightClimbMoter.set(Constants.CLIMBER_LOWER_SPEED);
    }    

    public static void stopClimbers()
    {
        leftClimbMoter.set(0);
        rightClimbMoter.set(0);
    }    

}
