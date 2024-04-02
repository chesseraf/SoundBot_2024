// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.AutoObtainNextNote;
import frc.robot.commands.AutoShootFirstNote;
import frc.robot.commands.DriveForTime;
import frc.robot.commands.DriveForTimeAtRPS;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.IntakeLower;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.SimultaneousCommand;
import frc.robot.commands.SpinUpShooter;
import frc.robot.commands.TurnForTimeAtRPS;
import frc.robot.commands.Wait;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.HttpCamera;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //private RobotContainer m_robotContainer;
  //private DriveTrain train=new DriveTrain();

  private DriveWithJoystick driveCommand =  new DriveWithJoystick();
  
  private String retreatChosen, shootSequenceChosen;


  private final SendableChooser<String> autoRetreatChoice = new SendableChooser<>();
  private final SendableChooser<String> autoShootingSequence = new SendableChooser<>();

public static final SendableChooser<Boolean> useAlternativeShootingSpeeds = new SendableChooser<>();
public static final SendableChooser<Integer> alternativeOuterShootingSpeedTenths = new SendableChooser<>();
public static final SendableChooser<Integer> alternativeOuterShootingSpeedHundreths = new SendableChooser<>();

public static final SendableChooser<Integer> alternativeInnerShootingSpeedTenths = new SendableChooser<>();
public static final SendableChooser<Integer> alternativeInnerShootingSpeedHundreths = new SendableChooser<>();


public static final VelocityVoltage m_voltageVelocity = new VelocityVoltage(0, 0, true, 0, 0, false, false, false);
  /* Start at velocity 0, no feed forward, use slot 1 */
  private final VelocityTorqueCurrentFOC m_torqueVelocity = new VelocityTorqueCurrentFOC(0, 0, 0, 1, false, false, false);

  


  private String dontRetreat = "No retreat", backRetreat = "move behind line", leftRetreat = "left retreat", rightRetreat = "right retreat";

  private String shootTwice = "shooting twice";
  private String shootOnceObtainSecond = "obtain second, dont shoot it";
  private String shootOnce = "shoot one time";
  private String dontShoot = "zero shots", shootTwiceObtainThird = "shooting twice, obtaining third",
  shootThreeTimes = "shoot 3 times", shootFourTimes = "Shoot 4 times", shootThreeTimesObtainFourth = "Shoot 3 times, obtain 4th";


  private Command retreatCommand, shootingSequenceCommand;

  public static int START_NEAR_AMP = 0, START_MID = 1, START_FAR_FROM_AMP = 2, COL_RED = 0, COL_BLUE = 1, CLOSER_FIRST = 0, FURTHER_FIRST = 1, BEHIND_NOTE = 2, LEFT_NOTE = 3, RIGHT_NOTE = 4;
  public static final String neamAmp = "nearAmp", farFromAmp = "FAR FROM AMP", mid = "MID", red = "RED", blue = "BLUE";
  public static int startLoc, teamCol, secondNote;
  public SendableChooser<Integer> startLocChooser = new SendableChooser<Integer>();
  public SendableChooser<Integer> colorChooser = new SendableChooser<Integer>();
  public SendableChooser<Integer> secondNoteChooser = new SendableChooser<Integer>();
  
  
 // private final SendableChooser<Double> shooterSpeedControl = new SendableChooser<>();

  public static double intakePos;
  public static boolean inTeleop = false;


  private Intake take = new Intake();
  //private UsbCamera limeLight = new Limelight();
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

//  VideoSource limelightFeed = new VideoSource()
  HttpCamera limelightFeed = new HttpCamera("limelight", "http://10.43.11.47:5800/stream.mjpg", HttpCameraKind.kMJPGStreamer);
//CameraServer.getInstance().startAutomaticCapture(limelightFeed);

  public static TalonFXConfiguration configs;
  @Override
  public void robotInit() {



    //Copied from 
    //https://github.com/CrossTheRoadElec/Phoenix6-Examples/blob/main/java/VelocityClosedLoop/src/main/java/frc/robot/Robot.java

    configs = new TalonFXConfiguration();

    /* Voltage-based velocity requires a feed forward to account for the back-emf of the motor */
    configs.Slot0.kP = 0.11; // An error of 1 rotation per second results in 2V output
    configs.Slot0.kI = 0.5; // An error of 1 rotation per second increases output by 0.5V every second
    configs.Slot0.kD = 0.0001; // A change of 1 rotation per second squared results in 0.01 volts output
    configs.Slot0.kV = 0.12; // Falcon 500 is a 500kV motor, 500rpm per V = 8.333 rps per V, 1/8.33 = 0.12 volts / Rotation per second
    // Peak output of 8 volts
    configs.Voltage.PeakForwardVoltage = 8;
    configs.Voltage.PeakReverseVoltage = -8;
    
    /* Torque-based velocity does not require a feed forward, as torque will accelerate the rotor up to the desired velocity by itself */
    configs.Slot1.kP = 5; // An error of 1 rotation per second results in 5 amps output
    configs.Slot1.kI = 0.1; // An error of 1 rotation per second increases output by 0.1 amps every second
    configs.Slot1.kD = 0.001; // A change of 1000 rotation per second squared results in 1 amp output

    // Peak output of 40 amps
    configs.TorqueCurrent.PeakForwardTorqueCurrent = 40;
    configs.CurrentLimits.SupplyCurrentLimit = 40;
    configs.CurrentLimits.SupplyCurrentLimitEnable = true;
    configs.TorqueCurrent.PeakReverseTorqueCurrent = -40;

    //CameraServer.getInstance().startAutomaticCapture(limelightFeed);


    CameraServer.startAutomaticCapture();

    for(int i=0; i<16; i++)
    {
      RobotContainer.prevButtons[i] = false;
    }

    DriveTrain.applyConfig();
    Intake.intakeLiftMotor.setNeutralMode(NeutralModeValue.Brake);

    startLocChooser.setDefaultOption(neamAmp, START_NEAR_AMP);
    startLocChooser.addOption(farFromAmp, START_FAR_FROM_AMP);
    startLocChooser.addOption(mid, START_MID);

    colorChooser.setDefaultOption(blue, COL_BLUE);
    colorChooser.addOption(red, COL_RED);

    secondNoteChooser.setDefaultOption("Closer or Left note first", CLOSER_FIRST);
    secondNoteChooser.addOption("Further or right note first", FURTHER_FIRST);

    autoShootingSequence.setDefaultOption(dontShoot, dontShoot);
    autoShootingSequence.addOption(shootOnce, shootOnce);
   // autoShootingSequence.addOption(shootOnceObtainSecond, shootOnceObtainSecond);
    autoShootingSequence.addOption(shootTwice, shootTwice);
   // autoShootingSequence.addOption(shootTwiceObtainThird, shootTwiceObtainThird);
    autoShootingSequence.addOption(shootThreeTimes, shootThreeTimes);
  //  autoShootingSequence.addOption(shootThreeTimesObtainFourth, shootThreeTimesObtainFourth);
    autoShootingSequence.addOption(shootFourTimes, shootFourTimes);
    

    autoRetreatChoice.setDefaultOption(dontRetreat, dontRetreat);
    autoRetreatChoice.addOption(backRetreat, backRetreat);
    //autoRetreatChoice.addOption(leftRetreat, leftRetreat);
    //autoRetreatChoice.addOption(rightRetreat, rightRetreat);

    useAlternativeShootingSpeeds.setDefaultOption("False", false);
    useAlternativeShootingSpeeds.addOption("True", true);

    alternativeInnerShootingSpeedHundreths.setDefaultOption("0 (0.01 inner)", 0);
    alternativeInnerShootingSpeedTenths.setDefaultOption("0 (0.1 inner)", 0);
    alternativeOuterShootingSpeedHundreths.setDefaultOption("0 (0.01 outer)", 0);
    alternativeOuterShootingSpeedTenths.setDefaultOption("0 (0.1 outer)", 0);
    for(int i=1; i<=9; i++)
    {
      alternativeInnerShootingSpeedHundreths.addOption(((Integer)i).toString(), i);
      alternativeInnerShootingSpeedTenths.addOption(((Integer)i).toString(), i);
      alternativeOuterShootingSpeedHundreths.addOption(((Integer)i).toString(), i);
      alternativeOuterShootingSpeedTenths.addOption(((Integer)i).toString(), i);
    }

    SmartDashboard.putData(alternativeInnerShootingSpeedHundreths);
    SmartDashboard.putData(alternativeOuterShootingSpeedHundreths);
    SmartDashboard.putData(alternativeInnerShootingSpeedTenths);
    SmartDashboard.putData(alternativeOuterShootingSpeedTenths);
    SmartDashboard.putData(useAlternativeShootingSpeeds);
    SmartDashboard.putData(colorChooser);
    SmartDashboard.putData(startLocChooser);
    SmartDashboard.putData(secondNoteChooser);
    

    SmartDashboard.putData("Retreat choices", autoRetreatChoice);
    SmartDashboard.putData("Shooting sequence", autoShootingSequence);

    take = new Intake();

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    RobotContainer.UpdateJoystick();

    CommandScheduler.getInstance().run();
   
    intakePos = -RobotContainer.intakeEncoder.getAbsolutePosition() * 360;
    intakePos -= Constants.EncoderOffset;

    if(intakePos<0)
    {
      intakePos += 360;
    }

    Intake.intakeLiftMotor.setPosition(intakePos);

    RobotContainer.SmartBoardUpdate();
    // if(useAlternativeShootingSpeeds.getSelected())
    // {
    //   Shooter.innerCustomShooterSpeed = 0.1*alternativeInnerShootingSpeedTenths.getSelected() + 0.01 * alternativeInnerShootingSpeedHundreths.getSelected();
    //   Shooter.outerCustomShooterSpeed = 0.1*alternativeOuterShootingSpeedTenths.getSelected() + 0.01 * alternativeOuterShootingSpeedHundreths.getSelected();
       
    // }

    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    //CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    Intake.intakeLiftMotor.setNeutralMode(NeutralModeValue.Coast);
    DriveTrain.exitBreak();
  }

  @Override
  public void disabledPeriodic() {}

  public static int commandsUntilRetreat;
  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
 int noteTwo;// = LEFT_NOTE;
      int noteThree;// = RIGHT;
      double timeWaitWhenIntakeLowering = 0.7;
 int sign = 1;

  @Override
  public void autonomousInit() {

      retreatChosen = autoRetreatChoice.getSelected();
      startLoc = startLocChooser.getSelected();
      teamCol = colorChooser.getSelected();

      shootSequenceChosen = autoShootingSequence.getSelected();
      System.out.println("Auto selected: " + retreatChosen +"  and "+ shootSequenceChosen);

      // if(retreatChosen == rightRetreat)
      // {
      //   retreatCommand = new DriveForTime(3, 0.3, -0.3);
      // }
      // else if (retreatChosen == backRetreat)
      // {
      //   retreatCommand = new DriveForTime(3, 0.3, 0);
      // }
      // else if(retreatChosen == leftRetreat)
      // {
      //   retreatCommand = new DriveForTime(3, 0.3, 0.3);

      // }

      if (retreatChosen == backRetreat)
      {
        if(((teamCol == COL_BLUE ) && (startLoc == START_NEAR_AMP)) || ((teamCol == COL_RED ) && (startLoc == START_FAR_FROM_AMP)))
        {
          sign = 1;
        }
        else
        {
          sign = -1;
        }
        if(startLoc == START_FAR_FROM_AMP)
        {
          retreatCommand = (new DriveForTimeAtRPS(1.5, 15)
          .andThen(new TurnForTimeAtRPS(0.6, 10*sign))
          .andThen(new DriveForTimeAtRPS(2.5, 20))
          );
        }
        else
        {
          retreatCommand = (new DriveForTimeAtRPS(1, 15)
          .andThen(new TurnForTimeAtRPS(0.6, 10*sign))
          .andThen(new DriveForTimeAtRPS(2.5, 20))
          );
        }
        

      }
      else
      {
        retreatCommand = new DriveForTime(0, 0, 0);
      }

      System.out.println(shootSequenceChosen);
      SmartDashboard.putString("INTAKE  is up", ((Boolean)(Intake.intakeUp)).toString());
      noteTwo = LEFT_NOTE;
      noteThree = RIGHT_NOTE;
      timeWaitWhenIntakeLowering = 0.7;
      if(secondNoteChooser.getSelected() != CLOSER_FIRST)//closer first means left first, as seen on the dashboard
      {
        noteTwo = RIGHT_NOTE;
        noteThree = LEFT_NOTE;
      }
      if(shootSequenceChosen == shootFourTimes || shootSequenceChosen == shootThreeTimesObtainFourth)
      {   
        SmartDashboard.putString("AUTO RUNNING", "4 note!");

        shootingSequenceCommand =  (new ShootCommand()).andThen(new SimultaneousCommand(new IntakeLower(Constants.INTAKE_LOWER_SPEED*1.4)))
        .andThen(new Wait(timeWaitWhenIntakeLowering))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, BEHIND_NOTE))
        .andThen(new Wait(0.5))
        .andThen(new ShootCommand()).andThen(new SimultaneousCommand(new IntakeLower(Constants.INTAKE_LOWER_SPEED*1.4)))
        .andThen(new Wait(timeWaitWhenIntakeLowering/2))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, noteTwo))
        .andThen(new ShootCommand())
        .andThen(new Wait(timeWaitWhenIntakeLowering/2))
        .andThen(new SimultaneousCommand(new IntakeLower(Constants.INTAKE_LOWER_SPEED*1.4)))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, noteThree, true))
        .andThen(new ShootCommand())
        ;
        
        
        //(new Wait(5).andThen(new SpinUpShooter())).schedule();
      }
      else if(shootSequenceChosen == shootThreeTimes)
      {
        shootingSequenceCommand =  (new ShootCommand()).andThen(new SimultaneousCommand(new IntakeLower()))
        .andThen(new Wait(timeWaitWhenIntakeLowering))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, BEHIND_NOTE))
        .andThen(new Wait(0.1))
        .andThen(new ShootCommand()).andThen(new SimultaneousCommand(new IntakeLower()))
        .andThen(new Wait(timeWaitWhenIntakeLowering))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, noteTwo))
        .andThen(new ShootCommand())
        ;
      }
      else if(shootSequenceChosen == shootTwiceObtainThird)
      {
        shootingSequenceCommand =  (new ShootCommand()).andThen(new SimultaneousCommand(new IntakeLower()))
        .andThen(new Wait(timeWaitWhenIntakeLowering))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, BEHIND_NOTE))
        .andThen(new Wait(0.1))
        .andThen(new ShootCommand()).andThen(new SimultaneousCommand(new IntakeLower()))
        .andThen(new Wait(timeWaitWhenIntakeLowering))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, noteTwo))
        .andThen(new ShootCommand())
        ;
      }
      
      else if(shootSequenceChosen == shootTwice || shootSequenceChosen == shootOnceObtainSecond)
      {
        shootingSequenceCommand =  (new ShootCommand()).andThen(new SimultaneousCommand(new IntakeLower()))
        .andThen(new Wait(timeWaitWhenIntakeLowering))
        .andThen(AutoObtainNextNote.getAutoObtainSecondNoteCommand(teamCol, startLoc, BEHIND_NOTE))
        .andThen(new Wait(0.1))
        .andThen(new ShootCommand())
        ;
      }
      else if(shootSequenceChosen == shootOnce)
      {
        commandsUntilRetreat = 1;

        shootingSequenceCommand = (new ShootCommand()).andThen(new Wait(6));
      }
      else //no shooting
      {
        commandsUntilRetreat = 0;
        shootingSequenceCommand = new DriveForTime(0, 0,  0);
      }

      (new SpinUpShooter()).schedule();
      (new Wait(1.5)).andThen(shootingSequenceCommand).andThen(retreatCommand).schedule();
  }
//  private String dontRetreat = "No retreat", backRetreat = "retreat back", leftRetreat = "left retreat", rightRetreat = "right retreat";


  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}


  @Override
  public void teleopInit() {
    inTeleop = true;
    driveCommand.schedule();
    if(shootingSequenceCommand != null)
    {
      shootingSequenceCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
        RobotContainer.activateButton();

    }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {  
        for(int i=0; i<20; i++)
    {
      System.out.println(RobotContainer.ps4.getPOV());
      if(RobotContainer.ps4.getRawButton(i))
        System.out.println(i+" pressed!");
    }
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    for(int i=0; i<20; i++)
    {
      if(RobotContainer.ps4.getRawButton(i))
        System.out.println(i+" pressed!");
    }
  }
}
