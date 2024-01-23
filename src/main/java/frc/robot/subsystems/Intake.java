package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class Intake {
    public static TalonFX intakeWheels = new TalonFX(Constants.INTAKE_WHEELS_ID);
    public static TalonFX intakeLiftMotor = new TalonFX(Constants.INTAKE_LIFT_MOTORS_ID);
    public static boolean intakeUp = true;
    public void hello(){
        // intakeLiftMotor.con
        // intakeLiftMotor.configReverseSoftLimitThreshold(-170000);
        // intakeLiftMotor.configReverseSoftLimitEnable(true);
        // intakeLiftMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,70,3,1));
        // intakeLiftMotor.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,40,3,1));

        // configStatorCurrentLimit hi = new configStatorCurrentLimit()
    }
    
    
}
