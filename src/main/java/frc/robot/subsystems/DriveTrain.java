package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Robot;


public class DriveTrain extends SubsystemBase{
    //OpenLoopRampsConfigs openLoopRampsConfigs = new OpenLoopRampsConfigs();
    public static void applyConfig() {
      
      frontLeftMotor.getConfigurator().apply(configSpeed);
      frontRightMotor.getConfigurator().apply(configSpeed);
      backLeftMotor.getConfigurator().apply(configSpeed);
      backRightMotor.getConfigurator().apply(configSpeed);

      // frontLeftMotor.setControl(new Follower(backLeftMotor.getDeviceID(), false));
      // frontRightMotor.setControl(new Follower(backRightMotor.getDeviceID(), false));
      frontLeftMotor.getConfigurator().apply(Robot.configs);
      frontRightMotor.getConfigurator().apply(Robot.configs);
      backLeftMotor.getConfigurator().apply(Robot.configs);
      backRightMotor.getConfigurator().apply(Robot.configs);
      enterBreak();


      
      
      
    }

   // TalonFX motor = new TalonFX(0); // creates a new TalonFX with ID 0

    public static final TalonFX frontLeftMotor = new TalonFX(Constants.FRONT_LEFT_ID);
    public static final TalonFX frontRightMotor = new TalonFX(Constants.FRONT_RIGHT_ID);
    public static final TalonFX backLeftMotor = new TalonFX(Constants.BACK_LEFT_ID);
    public static final TalonFX backRightMotor = new TalonFX(Constants.BACK_RIGHT_ID);
    
    

    public static final OpenLoopRampsConfigs configSpeed = new OpenLoopRampsConfigs().withDutyCycleOpenLoopRampPeriod(0.5);

    
    private static final DifferentialDrive frontDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
    private static final DifferentialDrive backDrive = new DifferentialDrive(backLeftMotor, backRightMotor);
    
    
   // private final boolean breaksOn = true;

  public static void driveBoth(double forwardPercent, double rotationPercent)
  {
    backDrive.arcadeDrive(forwardPercent, -rotationPercent);
    frontDrive.arcadeDrive(forwardPercent, -rotationPercent);
  }
  public static void driveStraightRPS(double RPS)
  {
    DriveTrain.frontLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));
    DriveTrain.frontRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(-RPS));


    DriveTrain.backLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));
    DriveTrain.backRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(-RPS));  
  }

  public static void driveTurnRPS(double speedRPS, double turnRPS)
  {
    DriveTrain.frontLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(speedRPS - turnRPS));
    DriveTrain.frontRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(-speedRPS-turnRPS));


    DriveTrain.backLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(speedRPS - turnRPS));
    DriveTrain.backRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(-speedRPS - turnRPS));  
  }

  public static void turnRPS(double RPS)
  {
    DriveTrain.frontLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));
    DriveTrain.frontRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));

    DriveTrain.backLeftMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));
    DriveTrain.backRightMotor.setControl(Robot.m_voltageVelocity.withVelocity(RPS));  
  }

  

  public static void driveBothTank(double left, double right)
  {
    backDrive.tankDrive(left, right);
    frontDrive.tankDrive(left, right);
  }

  public static void turnDrive(double forwardPercent, double rotationPercent) {
    if(forwardPercent>1){
      driveBoth(1*Constants.SPEED_MULTIPLIER, rotationPercent);
    }else if(forwardPercent<-1){
      driveBoth(-1*Constants.SPEED_MULTIPLIER, rotationPercent);
    }else{
      driveBoth(forwardPercent*Constants.SPEED_MULTIPLIER, rotationPercent);
    }
  }
  // public void tankDrive(double leftPercent, double rightPercent) {
  //   driveBoth(leftPercent, leftPercent);.tankDrive(leftPercent, rightPercent);
  // }
  public double getVelocity(){
    return (frontLeftMotor.getVelocity().getValue()+frontRightMotor.getVelocity().getValue())/2;
    // return((frontLeftMotor.getVelocity()+frontRightMotor.getVelocity()+
    //         backLeftMotor.getVelocity()+backRightMotor.getVelocity())/4);
  }


    public static void exitBreak(){
      frontLeftMotor.setNeutralMode(NeutralModeValue.Coast);
      frontRightMotor.setNeutralMode(NeutralModeValue.Coast);
      backLeftMotor.setNeutralMode(NeutralModeValue.Coast);
      backRightMotor.setNeutralMode(NeutralModeValue.Coast);
    }

    public static void enterBreak(){
       frontLeftMotor.setNeutralMode(NeutralModeValue.Brake);
       frontRightMotor.setNeutralMode(NeutralModeValue.Brake);
       backLeftMotor.setNeutralMode(NeutralModeValue.Brake);
       backRightMotor.setNeutralMode(NeutralModeValue.Brake);
    }
    public void stop(){
      driveBoth(0, 0);
    }
}
