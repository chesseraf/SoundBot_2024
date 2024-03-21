package frc.robot.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

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

      frontLeftMotor.setControl(new Follower(backLeftMotor.getDeviceID(), false));
      frontRightMotor.setControl(new Follower(backRightMotor.getDeviceID(), false));
      frontLeftMotor.getConfigurator().apply(Robot.configs);
      frontRightMotor.getConfigurator().apply(Robot.configs);
      
    }

   // TalonFX motor = new TalonFX(0); // creates a new TalonFX with ID 0

    public static final TalonFX frontLeftMotor = new TalonFX(Constants.FRONT_LEFT_ID);
    public static final TalonFX frontRightMotor = new TalonFX(Constants.FRONT_RIGHT_ID);
    public static final TalonFX backLeftMotor = new TalonFX(Constants.BACK_LEFT_ID);
    public static final TalonFX backRightMotor = new TalonFX(Constants.BACK_RIGHT_ID);
    
    

    public static final OpenLoopRampsConfigs configSpeed = new OpenLoopRampsConfigs().withDutyCycleOpenLoopRampPeriod(0.5);

    
    private static final DifferentialDrive frontDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
    //private static final DifferentialDrive backDrive = new DifferentialDrive(backLeftMotor, backRightMotor);
    
    
   // private final boolean breaksOn = true;

  public static void driveBoth(double forwardPercent, double rotationPercent)
  {
   // backDrive.arcadeDrive(forwardPercent, -rotationPercent);
    frontDrive.arcadeDrive(forwardPercent, -rotationPercent);
  }
  public static void driveBothTank(double left, double right)
  {
    //backDrive.tankDrive(left, right);
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


    public void exitBreak(){
    //   frontLeftMotor.setNeutralMode(NeutralMode.Coast);
    //   frontRightMotor.setNeutralMode(NeutralMode.Coast);
    //   backLeftMotor.setNeutralMode(NeutralMode.Coast);
    //   backRightMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void enterBreak(){
    //   frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    //   frontRightMotor.setNeutralMode(NeutralMode.Brake);
    //   backLeftMotor.setNeutralMode(NeutralMode.Brake);
    //   backRightMotor.setNeutralMode(NeutralMode.Brake);
    }
    public void stop(){
      driveBoth(0, 0);
    }
}
