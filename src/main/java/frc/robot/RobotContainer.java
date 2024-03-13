// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS4Controller;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.IntakeLift;
import frc.robot.commands.IntakeLower;
import frc.robot.commands.IntakePulse;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.Wait;
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

    //public final static Joystick THRUSTMASTER = new Joystick(Constants.THRUSTMASTER_PORT);
    public final static PS4Controller ps4 = new PS4Controller(Constants.THRUSTMASTER_PORT);
    
    
    

  //  public final AHRS GYRO = new AHRS(SPI.Port.kMXP);
    DriveTrain DRIVE_TRAIN = new DriveTrain();
    public static double joystickX;
    public static double joystickY;
    public static boolean[] currentButtons = new boolean[16];
    public static boolean[] prevButtons = new boolean[16];
    public static boolean[] justPressedButtons = new boolean[16];
    public static boolean[] justReleasedButtons = new boolean[16];

    public static ShootCommand highShotCommand = new ShootCommand(ShootCommand.HIGH_SHOT) , 
    lowShotCommand = new ShootCommand(ShootCommand.LOW_SHOT) ,
    shuttleShotCommand = new ShootCommand(ShootCommand.SHUTTLE_SHOT);
    public static IntakeLower intakeLowerCommand = new IntakeLower();
    public static IntakeLift intakeLiftCommand = new IntakeLift();

    public static boolean intakePostitionUsed = false;
    public static DigitalInput intakeLimitSwitch = new DigitalInput(7);
    public static boolean pressedIntakeNoteLimitSwitch;
    public static boolean prevpressedIntakeNoteLimitSwitch = false;

    public static boolean intakeNoteLimitJustPressed = false;
    public static boolean intakeNoteLimitJustUnpressed = false;

    public static boolean sameNote = false;

    public static DutyCycleEncoder intakeEncoder = new DutyCycleEncoder(9);
    
    //public static DutyCycleEncoder intakEncoder = new DutyCycleEncoder(0)
   // private final  JoystickButton orangeButton = new JoystickButton(THRUSTMASTER, pistonTimer);
  

    public static void UpdateJoystick(){
        // DriveWithJoystick.XJoystick = THRUSTMASTER.getX();
        // DriveWithJoystick.YJoystick = THRUSTMASTER.getY();

        DriveWithJoystick.XJoystick = ps4.getRightY();
        DriveWithJoystick.YJoystick = ps4.getLeftX();
        
        prevpressedIntakeNoteLimitSwitch = pressedIntakeNoteLimitSwitch;
        pressedIntakeNoteLimitSwitch = !intakeLimitSwitch.get();
        //pressedIntakeNoteLimitSwitch = false;

        intakeNoteLimitJustPressed = pressedIntakeNoteLimitSwitch && !prevpressedIntakeNoteLimitSwitch;
        intakeNoteLimitJustUnpressed = !pressedIntakeNoteLimitSwitch && prevpressedIntakeNoteLimitSwitch;


        for(int i = 1; i<13 ; i++)
        {
            prevButtons[i] = currentButtons[i];
            //currentButtons[i] = THRUSTMASTER.getRawButtonPressed(i);
            currentButtons[i] = ps4.getRawButton(i);
            
            justPressedButtons[i] = currentButtons[i] && ! prevButtons[i];
            justReleasedButtons[i] = !currentButtons[i] && prevButtons[i];
        }
    }

    public static void activateButton()
    {
        //System.out.println("command scheduling");

        if(justPressedButtons[Constants.SHOOT_HIGH_BUTTON] && !ShootCommand.currentlyShooting)
        {
            highShotCommand.schedule();
        }
        else if(justPressedButtons[Constants.SHOOT_LOW_BUTTON] && !ShootCommand.currentlyShooting)
        {
            lowShotCommand.schedule();
        }
        else if(justPressedButtons[Constants.SHOOT_SHUTTLE_BUTTON] && !ShootCommand.currentlyShooting)
        {
            shuttleShotCommand.schedule();
        }


        if(justPressedButtons[Constants.FLIP_INTAKE_BUTTON] && !intakePostitionUsed){
            if(Intake.intakeUp)
            {                
                intakeLowerCommand.schedule();
                System.out.println("lower command scheduling");
            }
            else
            {
                System.out.println("lift command scheduling");

                intakeLiftCommand.schedule();
            }
        }

        //same note is false when the intake is lowered
        if(intakeNoteLimitJustPressed && !sameNote)
        {
            sameNote = true;
            Intake.intakeWheels.set(0);
            (new Wait(Constants.DELAY_AFTER_STOPPING_INTAKE_AND_LIFTING))
            .andThen( new IntakeLift())
            .andThen(new Wait(0.1))
            .andThen(new IntakePulse()).schedule();
            System.out.println("NOTE JUST PRESSED LIMIT!\nlift command scheduling");
        }
    }

    public static void SmartBoardUpdate()
    {

        SmartDashboard.putNumber("Encoder position", RobotContainer.intakeEncoder.getAbsolutePosition());        
        SmartDashboard.putNumber("Intake lift position", Intake.intakeLiftMotor.getPosition().getValue());
        SmartDashboard.putBoolean("Limit switch pressed: ", pressedIntakeNoteLimitSwitch);
        SmartDashboard.putBoolean("Same Note", sameNote);

    }
}

