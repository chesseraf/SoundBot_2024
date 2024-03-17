package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class ShootIntakeLow extends  Command {

    public static int stage;
    //public static boolean shootingWithIntakeNow = false;
    public static final int POSITIONING_INTAKE = 1, WAITING = 2, SHOOTING = 3, LIFTING_INTAKE = 4, ALL_FINISHED = 5;

    public static final int INTAKE_SHOOTING_ANGLE = 145, ANGLE_MARGIN = 7, REPOSITION_MARGIN = 30;
    public static final double LOWER_INTAKE_TO_SHOOT_SPEED = -0.08, LIFT_INTAKE_TO_SHOOT_SPEED = 0.1,
    INTAKE_SPEED_AT_CONSTANT_POSITION = 0, INTAKE_SHOOTING_SPEED = -0.4;

    final double WAITING_TIME = 0.5, SHOOTING_TIME = 2;//seconds
    int timer;
    int timeToEndWait;
    int timeToEndShooting;
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
    ShootCommand.currentlyShooting = true;
    Intake.intakeUp = false;
    stage = POSITIONING_INTAKE;
    Intake.intakeWheels.set(0);
    Intake.intakeLiftMotor.set(0);
    timer = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    timer++;
    if(RobotContainer.intakePostitionUsed)
    {
        stage = ALL_FINISHED;
    }


    if(stage == POSITIONING_INTAKE)
    {
        if(Robot.intakePos > INTAKE_SHOOTING_ANGLE + ANGLE_MARGIN)
        {
            Intake.intakeLiftMotor.set(LOWER_INTAKE_TO_SHOOT_SPEED);
        }
        else if(Robot.intakePos < INTAKE_SHOOTING_ANGLE - ANGLE_MARGIN)
        {
            Intake.intakeLiftMotor.set(LIFT_INTAKE_TO_SHOOT_SPEED);
        }
        else
        {
            stage = WAITING;
            timeToEndWait = (int)(timer + WAITING_TIME * 50);
        }
    }
    if(stage == WAITING)
    {
        Intake.intakeLiftMotor.set(INTAKE_SPEED_AT_CONSTANT_POSITION);
        if(timer > timeToEndWait)
        {
            stage = SHOOTING;
            timeToEndShooting = (int)(SHOOTING_TIME*50 + timer);

        }
        if(Robot.intakePos < INTAKE_SHOOTING_ANGLE - REPOSITION_MARGIN || Robot.intakePos > INTAKE_SHOOTING_ANGLE + REPOSITION_MARGIN)
        {
            stage = POSITIONING_INTAKE;
        }
    }
    if(stage == SHOOTING)
    {
        Intake.intakeWheels.set(INTAKE_SHOOTING_SPEED);
        Intake.intakeLiftMotor.set(INTAKE_SPEED_AT_CONSTANT_POSITION);

        if(timer > timeToEndShooting || Robot.intakePos < INTAKE_SHOOTING_ANGLE - REPOSITION_MARGIN)
        {
            stage = LIFTING_INTAKE;
            Intake.intakeWheels.set(0);
        }
    }
    if(stage == LIFTING_INTAKE)
    {
        if(Robot.intakePos > Constants.INTAKE_MAX_ANGLE_UP - Constants.DEGREES_BEFORE_MAX_TO_END)
        {
            stage = ALL_FINISHED;
            Intake.intakeLiftMotor.set(0);
        }
        Intake.intakeLiftMotor.set(Constants.INTAKE_LIFT_SPEED);
    }


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Intake.intakeWheels.set(0);
    ShootCommand.currentlyShooting = false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (stage == ALL_FINISHED);
  }
}
