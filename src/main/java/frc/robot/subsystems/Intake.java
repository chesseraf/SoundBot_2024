package frc.robot.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class Intake {
    public static TalonFX intakeWheels = new TalonFX(Constants.INTAKE_WHEELS_ID);
    public static TalonFX intakeLiftMotor = new TalonFX(Constants.INTAKE_LIFT_MOTORS_ID);
    public static boolean intakeUp = true;
    public static final OpenLoopRampsConfigs configIntakeLift = new OpenLoopRampsConfigs().withDutyCycleOpenLoopRampPeriod(0.5);

    public Intake(){
        // final double startingPositionTalonUnits = 0;
        // intakeLiftMotor.setSelectedSensorPosition(startingPositionTalonUnits);
        // intakeLiftMotor.getConfigurator().apply(configIntakeLift);
        // intakeLiftMotor.configForwardSoftLimitThreshold(0);
        // intakeLiftMotor.configReverseSoftLimitThreshold(-170000);
        // intakeLiftMotor.configReverseSoftLimitEnable(true);
        // intakeLiftMotor.configForwardSoftLimitEnable(true);
    }

    
    
}
