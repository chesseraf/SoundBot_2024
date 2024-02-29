// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoBackwards;
import frc.robot.commands.AutoForward;
import frc.robot.commands.DriveWithJoystick;
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
  private AutoForward autoForwardCommand =  new AutoForward();
  private AutoBackwards autoBackCommand = new AutoBackwards();
  private String retreatChosen, shootSequenceChosen;


  private final SendableChooser<String> autoRetreatChoice = new SendableChooser<>();
  private final SendableChooser<String> autoShootingSequence = new SendableChooser<>();
  
  private String dontRetreat = "No retreat", backRetreat = "retreat back", leftRetreat = "left retreat", rightRetreat = "right retreat";

  private String shootTwice = "shooting twice";
  private String shootOnceObtainSecond = "obtain second, dont shoot it";
  private String shootOnce = "shoot one time";
  private String dontShoot = "zero shots";


  
  
 // private final SendableChooser<Double> shooterSpeedControl = new SendableChooser<>();

  public static double intakePos;


  private Intake take = new Intake();
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
   // CameraServer.startAutomaticCapture();
    RobotContainer.intakeEncoder.setDistancePerRotation(48);

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
    RobotContainer.SmartBoardUpdate();
    RobotContainer.activateButton();
    intakePos = -RobotContainer.intakeEncoder.getAbsolutePosition();
    intakePos -= Constants.EncoderOffset;

    if(intakePos<0)
    {
      intakePos += 1;
    }

    Intake.intakeLiftMotor.setPosition(intakePos);

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

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {


    
    retreatChosen = autoRetreatChoice.getSelected();

    shootSequenceChosen = autoShootingSequence.getSelected();
    System.out.println("Auto selected: " + retreatChosen +"  and "+ shootSequenceChosen);



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
        Intake.intakeLiftMotor.set(0.1);//RobotContainer.THRUSTMASTER.getX()/10);

  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
