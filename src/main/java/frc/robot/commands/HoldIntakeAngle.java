package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Intake;

public class HoldIntakeAngle extends Command{
    private double angle, wheelsSpeed;
    private int timer, timeEnd;
    static int holdAngleMargin = 6;
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
        if((Robot.intakePos<angle + 15) && (Robot.intakePos > angle-15))
        {
            Intake.intakeWheels.set(Constants.INTAKE_REVERSE_SHOOT_SPEED*2);
        }
        else
        {
            Intake.intakeWheels.set(0);
        }
    }

    @Override
    public boolean isFinished()
    {
        return((timer > timeEnd) || RobotContainer.emergencyLiftingIntake);
    }

    @Override
    public void end(boolean interrupted)
    {
        if(!RobotContainer.emergencyLiftingIntake)
        {
            (new IntakeLift()).schedule();
        }
        Intake.intakeWheels.set(0);
        
    }

    public double speedSetter(double curA, double targetA, double vel)
    {
        if(curA > targetA+holdAngleMargin)
        {
            return(-0.13);
        }
        else if (curA > targetA - holdAngleMargin) {
            return 0.001/holdAngleMargin*(targetA - curA);
        }
        else if(curA < targetA - holdAngleMargin)
        {
            return(0.17);
        }
        //never reaches this 0
        else{
            return 0;
        }

    }
}
