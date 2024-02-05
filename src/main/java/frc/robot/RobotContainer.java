// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.IntakeLift;
import frc.robot.commands.IntakeLower;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    public RobotContainer(){
         
    }

    public final static Joystick THRUSTMASTER = new Joystick(Constants.THRUSTMASTER_PORT);
    
    

  //  public final AHRS GYRO = new AHRS(SPI.Port.kMXP);
    DriveTrain DRIVE_TRAIN = new DriveTrain();
    public static double joystickX;
    public static double joystickY;
    public static boolean[] currentButtons = new boolean[16];
    public static boolean[] prevButtons = new boolean[16];
    public static boolean[] justPressedButtons = new boolean[16];
    public static boolean[] justReleasedButtons = new boolean[16];

    public static ShootCommand shootingCommand =  new ShootCommand();
    public static IntakeLower intakeLowerCommand = new IntakeLower();
    public static IntakeLift intakeLiftCommand = new IntakeLift();

    public static boolean intakePostitionUsed = false;
    public static DigitalInput intakeLimitSwitch = new DigitalInput(0);
    public static boolean disableIntakeWheelsLimitSwitch;
    public static boolean prevDisableIntakeWheelsLimitSwitch;

    public static boolean intakeWheelsLimitJustReached;
    public static boolean intakeWheelsLimitJustStopped;

    //public static Encoder intakeEncoder = new Encoder(0, 1);



   // private final  JoystickButton orangeButton = new JoystickButton(THRUSTMASTER, pistonTimer);
  

    public static void UpdateJoystick(){
        DriveWithJoystick.XJoystick = THRUSTMASTER.getX();
        DriveWithJoystick.YJoystick = THRUSTMASTER.getY();

        prevDisableIntakeWheelsLimitSwitch = disableIntakeWheelsLimitSwitch;
        disableIntakeWheelsLimitSwitch = intakeLimitSwitch.get();
        //nintakeWheelsLimitJustReached = 

        for(int i = 0; i<16 ; i++)
        {
            prevButtons[i] = currentButtons[i];
            currentButtons[i] = THRUSTMASTER.getRawButtonPressed(i+1);
            justPressedButtons[i] = currentButtons[i] && ! prevButtons[i];
            justReleasedButtons[i] = !currentButtons[i] && prevButtons[i];
        }
    }

    public static void activateButton()
    {
        if(justPressedButtons[Constants.SHOOT_BUTTON])
        {
            shootingCommand.schedule();
        }
        if(justPressedButtons[Constants.FLIP_INTAKE_BUTTON] && !intakePostitionUsed){
            if(Intake.intakeUp)
                intakeLowerCommand.schedule();
            else
                intakeLiftCommand.schedule();
        }
    }

    public static void SmartBoardUpdate(){

        SmartDashboard.putNumber("Intake position", Intake.intakeLiftMotor.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("front right motor speed ", Intake.intakeLiftMotor.getVelocity().getValueAsDouble());
        // SmartDashboard.putNumber("back left motor speed ", DriveTrain.backLeftMotor.get());
        // SmartDashboard.putNumber("back right motor speed ", DriveTrain.backRightMotor.get());
        // SmartDashboard.putNumber("right difference ", DriveTrain.backRightMotor.get()-DriveTrain.frontRightMotor.get());
        // SmartDashboard.putNumber("left difference ", DriveTrain.backLeftMotor.get()-DriveTrain.frontLeftMotor.get());
        
    }
}
