package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class HoldIntakeAngle extends Command{
    private double angle, wheelsSpeed;
    private int timer, timeEnd;
    public HoldIntakeAngle(double a, double maxSeconds, double intakeWheelsSpeed)
    {
        timeEnd =(int)( 50 * maxSeconds);
        angle = a;
        wheelsSpeed = intakeWheelsSpeed;
    }
    @Override
    public void initialize()
    {
        RobotContainer.intakePostitionUsed = true;
        timer = 0;

    }

    @Override
    public void execute() {
        timer++;
        Intake.intakeLiftMotor.set(speedSetter(Robot.intakePos, angle, Intake.intakeLiftMotor.getVelocity().getValueAsDouble()));
        if((Robot.intakePos<angle + 15) && (Robot.intakePos > angle))
        {
            Intake.intakeWheels.set(Constants.INTAKE_REVERSE_SHOOT_SPEED);
        }
        else
        {
            Intake.intakeWheels.set(0);
        }
    }

    @Override
    public boolean isFinished()
    {
        return(timer > timeEnd);
    }

    public double speedSetter(double curA, double targetA, double vel)
    {
        if(curA > targetA+3)
        {
            return(-0.15);
        }
        else if (curA > targetA - 10) {
            return 0.05;
        }
        else if(curA < targetA - 3)
        {
            return(0.15);
        }
        else{
            return 0;
        }

    }
}
