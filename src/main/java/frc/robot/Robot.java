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
  private String autoChosen;
  private final SendableChooser<String> autoChoice = new SendableChooser<>();
  private String forwardAuto = "forwards auto";
  private String backwardAuto = "backwards auto";
  private final SendableChooser<Double> shooterSpeedControl = new SendableChooser<>();


  private Intake take;
  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
   // CameraServer.startAutomaticCapture();

    DriveTrain.applyConfig();

    //RobotContainer.intakeEncoder.setDistancePerPulse(1.0/256.0);

    autoChoice.setDefaultOption("forwards", forwardAuto);
    autoChoice.addOption("backwards", backwardAuto);
    SmartDashboard.putData("Auto choices", autoChoice);

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
    CommandScheduler.getInstance().run();
    RobotContainer.SmartBoardUpdate();
    RobotContainer.activateButton();

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


    
    autoChosen = autoChoice.getSelected();
    System.out.println("Auto selected: " + autoChosen);

    if(autoChosen.equals(forwardAuto))
    {

      autoForwardCommand.schedule();
    }
    else if(autoChosen.equals(backwardAuto))
    {
      autoBackCommand.schedule();
    }
    
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
  public void teleopPeriodic() {
/* 
    RobotContainer.UpdateJoystick();
  
    speed =   -YJoystick;
    turnRate = -XJoystick;

      // if(Math.abs(turnRate) < Constants.turnDeadBand) {
      //   turnRate = 0;
      // } else if (turnRate < 0) {
      //   turnRate = (turnRate * turnRate) * (1-Constants.MIN_TURN) + Constants.MIN_TURN;
      // } else {
      //   turnRate = -  XJoystick * XJoystick * (1-Constants.MIN_TURN) - Constants.MIN_TURN;
      // }

      // if(Math.abs(speed) < Constants.speedDeadBand){
      //   speed = 0;
      // } else if ( speed > 0){
      //   speed = speed * speed * (1-Constants.MIN_POWER) + Constants.MIN_POWER;
      // } else {
      //   speed = - speed * speed * (1-Constants.MIN_POWER) - Constants.MIN_POWER;
      // }
      
        System.out.println("Here!"+"speed"+speed+"turn"+turnRate);
      train.turnDrive(speed, turnRate );
      */
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    //RobotContainer.intakeEncoder.reset();
    RobotContainer.intakeEncoder.setDistancePerRotation(256);
    //RobotContainer.intakeEncoder = new DutyCycleEncoder(6);
    //RobotContainer.intakeEncoder.getRaw();
    //RobotContainer.intakeEncoder.se
  }

  /** This function is called periodically during test mode. */
  double  time = 0;
  @Override
  public void testPeriodic() {
    time++;
  //  System.out.println(time);
    //SmartDashboard.putNumber("encoder position", time+RobotContainer.intakeEncoder.getDistance());
    System.out.println("dist"+RobotContainer.intakeEncoder.getAbsolutePosition());

//    System.out.println("POS: "+RobotContainer.intakeEncoder.get());
    //Intake.intakeWheels.set(Constants.INTAKE_WHEELS_INTAKE_SPEED);
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
