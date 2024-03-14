// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.commands.AutoObtainSecondNote;
import frc.robot.commands.AutoShootFirstNote;
import frc.robot.commands.DriveForTime;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.IntakeLower;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.cameraserver.CameraServer;


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

  
  private String dontRetreat = "No retreat", backRetreat = "retreat back", leftRetreat = "left retreat", rightRetreat = "right retreat";

  private String shootTwice = "shooting twice";
  private String shootOnceObtainSecond = "obtain second, dont shoot it";
  private String shootOnce = "shoot one time";
  private String dontShoot = "zero shots";

  private Command retreatCommand, shootingSequenceCommand;
  
 // private final SendableChooser<Double> shooterSpeedControl = new SendableChooser<>();

  public static double intakePos;


  private Intake take = new Intake();
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    CameraServer.startAutomaticCapture();

    for(int i=0; i<16; i++)
    {
      RobotContainer.prevButtons[i] = false;
    }

    DriveTrain.applyConfig();

    //RobotContainer.intakeEncoder.setDistancePerPulse(1.0/256.0);


    autoShootingSequence.setDefaultOption(shootTwice, shootTwice);
    autoShootingSequence.addOption(shootOnceObtainSecond, shootOnceObtainSecond);
    autoShootingSequence.addOption(shootOnce, shootOnce);
    autoShootingSequence.addOption(dontShoot, dontShoot);

    autoRetreatChoice.setDefaultOption(dontRetreat, dontRetreat);
    autoRetreatChoice.addOption(backRetreat, backRetreat);
    autoRetreatChoice.addOption(leftRetreat, leftRetreat);
    autoRetreatChoice.addOption(rightRetreat, rightRetreat);

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
   
    RobotContainer.activateButton();
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
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  public static int commandsUntilRetreat;
  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    retreatChosen = autoRetreatChoice.getSelected();

    shootSequenceChosen = autoShootingSequence.getSelected();
    System.out.println("Auto selected: " + retreatChosen +"  and "+ shootSequenceChosen);

    if(retreatChosen == rightRetreat)
    {
      retreatCommand = new DriveForTime(3, 0.3, -0.3);
    }
    else if (retreatChosen == backRetreat)
    {
      retreatCommand = new DriveForTime(3, 0.3, 0);
    }
    else if(retreatChosen == leftRetreat)
    {
      retreatCommand = new DriveForTime(3, 0.3, 0.3);

    }
    else
    {
      retreatCommand = new DriveForTime(0, 0, 0);
    }

    System.out.println(shootSequenceChosen);
    if(shootSequenceChosen == shootTwice)
    {
      shootingSequenceCommand = new AutoShootFirstNote().andThen(new IntakeLower()).andThen(new AutoObtainSecondNote(true));
    }
    else if(shootSequenceChosen == shootOnce)
    {
      commandsUntilRetreat = 1;
      shootingSequenceCommand = new AutoShootFirstNote();
    }
    else if(shootSequenceChosen == shootOnceObtainSecond)
    {
      commandsUntilRetreat = 2;
      shootingSequenceCommand = new AutoShootFirstNote().andThen(new IntakeLower()).andThen(new AutoObtainSecondNote(false));
    }
    else{
      commandsUntilRetreat = 0;
      shootingSequenceCommand = new DriveForTime(0, 0,  0);
    }

    shootingSequenceCommand.andThen(retreatCommand).schedule();
    
//  private String dontRetreat = "No retreat", backRetreat = "retreat back", leftRetreat = "left retreat", rightRetreat = "right retreat";


    // if(autoChosen.equals(forwardAuto))
    // {

    //   autoForwardCommand.schedule();
    // }
    // else if(autoChosen.equals(backwardAuto))
    // {
    //   autoBackCommand.schedule();
    // }
    
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.cancel();
    // }
    driveCommand.schedule();
    //Shooter.setShooterMotors(1);
    // Shooter.shootMotorNumBack.set(0.6);
    // Shooter.shootMotorNumFront.set(0.6);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {  
        for(int i=0; i<20; i++)
    {
      if(RobotContainer.THRUSTMASTER.getRawButton(i))
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
      if(RobotContainer.THRUSTMASTER.getRawButton(i))
        System.out.println(i+" pressed!");
    }
  }
}
