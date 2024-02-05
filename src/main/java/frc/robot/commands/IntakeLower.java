package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class IntakeLower extends Command{

    int timer;

    @Override
    public void initialize() {
        RobotContainer.intakePostitionUsed = true;
        timer = 0;
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        Intake.intakeLiftMotor.set(Constants.INTAKE_LOWER_SPEED);
        Intake.intakeWheels.set(Constants.INTAKE_WHEELS_INTAKE_SPEED);
        timer ++;
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        RobotContainer.intakePostitionUsed = false;
        Intake.intakeLiftMotor.set(0);
        //Intake.intakeWheels.set(0);
        Intake.intakeUp = false;

        //System.out.print("lowered Intake");
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {        
        if(Intake.intakeLiftMotor.getPosition().getValue() < Constants.INTAKE_MIN_ANGLE_DOWN+Constants.DEGREES_BEFORE_MAX_TO_END)
        {
            return true;
        }
        if(timer>300)
        {
            return true;
        }
        if(timer>50 && Math.abs(Intake.intakeLiftMotor.getVelocity().getValue()) <0.1)
        {
            System.out.print("Intake motor blocked");
            return true;
        }
        if(timer > 300)
        {
            return true;
        }
        
        return false;
    }
}
