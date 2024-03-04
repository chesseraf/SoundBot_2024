package frc.robot.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class Intake {
    public static TalonFX intakeWheels = new TalonFX(Constants.INTAKE_WHEELS_ID);
    public static TalonFX intakeLiftMotor = new TalonFX(Constants.INTAKE_LIFT_MOTORS_ID);
    public static boolean intakeUp = true;
    public static final OpenLoopRampsConfigs configIntakeLift = new OpenLoopRampsConfigs().withDutyCycleOpenLoopRampPeriod(0.5);

    SoftwareLimitSwitchConfigs limitConfig = new SoftwareLimitSwitchConfigs();
    // limitConfig.ForwardSoftLimitEnable(true);

    public Intake(){
        limitConfig.ForwardSoftLimitEnable = true;
        limitConfig.ForwardSoftLimitThreshold = Constants.INTAKE_MAX_ANGLE_UP;
        limitConfig.ReverseSoftLimitEnable = true;
        limitConfig.ReverseSoftLimitThreshold = Constants.INTAKE_MIN_ANGLE_DOWN;
        Intake.intakeLiftMotor.getConfigurator().apply(limitConfig);
        System.out.print("Configured Intake lift motor");
    }

    
    
}
