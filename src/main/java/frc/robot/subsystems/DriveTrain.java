package frc.robot.subsystems;

import com.ctre.phoenix6.configs.OpenLoopRampsConfigs;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class DriveTrain extends SubsystemBase{
    //OpenLoopRampsConfigs openLoopRampsConfigs = new OpenLoopRampsConfigs();
    public DriveTrain() {
      
        //backLeftMotor.setInverted(true);
        //frontLeftMotor.setInverted(true);
        // backLeftMotor openLoopRampsConfigs
        //backLeftMotor.configOpenloopRamp(0.1);
        // backRightMotor.configOpenloopRamp(0.1);
        // frontRightMotor.configOpenloopRamp(0.1);
        // frontLeftMotor.configOpenloopRamp(0.1);
    }

   // TalonFX motor = new TalonFX(0); // creates a new TalonFX with ID 0

    public static final TalonFX frontLeftMotor = new TalonFX(Constants.ports.frontLeft);
    public static final TalonFX frontRightMotor = new TalonFX(Constants.ports.frontRight);
    public static final TalonFX backLeftMotor = new TalonFX(Constants.ports.backLeft);
    public static final TalonFX backRightMotor = new TalonFX(Constants.ports.backRight);

    
    //private final PWMMotorController left = new PWMMotorController()
    //private final DifferentialDrive drive = new DifferentialDrive()

    //private final MotorController leftSpeedGroup = new MotorController()

    //private final MotorControllerGroup leftSpeedGroup = new MotorControllerGroup(frontLeftMotor, backLeftMotor);
    //private final MotorControllerGroup rightSpeedGroup = new MotorControllerGroup(frontRightMotor, backRightMotor);

    private static final DifferentialDrive frontDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);
    private static final DifferentialDrive backDrive = new DifferentialDrive(backLeftMotor, backRightMotor);
    
    
   // private final boolean breaksOn = true;

  public static void driveBoth(double forwardPercent, double rotationPercent)
  {
    frontDrive.arcadeDrive(forwardPercent, rotationPercent);
    backDrive.arcadeDrive(forwardPercent, rotationPercent);
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
    return 0;
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
