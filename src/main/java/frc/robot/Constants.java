// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


public final class Constants {
    public static double SPEED_MULTIPLIER = 1;

    // PS4 Constants
    // public static class PS4 {
    //     public static final int X = 2, 
    //     CIRCLE = 3, SQUARE = 1, TRIANGLE = 4, LB = 5, RB = 6, TOUCHPAD = 9, OPTIONS = 10, L3 = 11, R3 = 12,
    //             LEFT_TRIGGER = 3, RIGHT_TRGGER = 4, LEFT_X = 0, LEFT_Y = 1, RIGHT_X = 2, RIGHT_Y = 5;
    // }


    public static final double MAX_ACCELERATION = 0.04;
    //public static final double turnDeadBand = 0.05;
    public static final double SPEED_DEAD_BAND = 0.05;

    public static final double INTAKE_MIN_ANGLE_DOWN = -0.25;//.7*48; right, counter clock
    public static final double INTAKE_MAX_ANGLE_UP = -0.75;//.7*48; left, away

    public static final double EncoderOffset = 0;
    //to avoid problems going from 1 to 0, change the absolute 0 location

    public static final double SHOOTER_SPEED = 0.1;
    public static final double SHOOTER_OUTER_SPEED = 0.2;
    public static final double SHOOTER_INNER_SPEED = -0.1;
    public static final double DELAY_AFTER_STOPPING_INTAKE_AND_LIFTING = 0.5,
    DELAY_STARTING_SHOOTER_BEFORE_REVERSE_INTAKE = 1 * 50 //cycles
    ;

    public static final double AUTO_OBTAIN_SECOND_NOTE_SPEED = 0.3;
    public static final double ATUO_DRIVE_BACK_SECONDS = 3;
    // port numbers on pdp, computer, pcm...

    public static final int SHOOTER_TOP_WHEEL_ID = 13,
    SHOOTER_LOWER_WHEEL_ID = 11;
    
    public static final int 
    FRONT_LEFT_ID = 13,//13
    BACK_LEFT_ID = 12,//12
    FRONT_RIGHT_ID = 11,
    BACK_RIGHT_ID = 14,

    INTAKE_WHEELS_ID = 2,
    INTAKE_LIFT_MOTORS_ID = 3,

    SHOOT_MOTOR_ID_Front = 4,
    SHOOT_MOTOR_ID_Back = 5,

    THRUSTMASTER_PORT = 0;
    
   
    //important values
    public static final double 
    MIN_POWER = 0.15,
    MIN_TURN = 0.1,

    INTAKE_LOWER_SPEED = 0.15,
    INTAKE_LIFT_SPEED = -0.15,
    INTAKE_WHEELS_INTAKE_SPEED = 0.25,

    INTAKE_REVERSE_SHOOT_SPEED = -0.25,

    DEGREES_BEFORE_MAX_TO_END = 0.01;

    
    //public static Button  bu= new But()
    //public static final Trigger halfSpeed = new Trigger(null)

    // buttons numbers
 
    public static final int 
    SHOOT_BUTTON = 1,
    FLIP_INTAKE_BUTTON = 2,
    NUM3_BUTTON = 3,
    NUM4_BUTTON = 4;
    
    //public static final int[] BUTTON_ARR = {SHOOT_BUTTON};
    
//     public static class OperatorConstants {
//       public static final int kDriverControllerPort = 0;
//   }
}

