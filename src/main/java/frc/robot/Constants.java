// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


public final class Constants {
    public static double SPEED_MULTIPLIER = 1;

    //PS4 Constants
    public static class PS4 {
        public static final int X = 2, 
        CIRCLE = 3, SQUARE = 1, TRIANGLE = 4, LB = 5, RB = 6, TOUCHPAD = 9, OPTIONS = 10, L3 = 11, R3 = 12,
                LEFT_TRIGGER = 7, RIGHT_TRGGER = 8, LEFT_X = 0, LEFT_Y = 1, RIGHT_X = 2, RIGHT_Y = 5
                ;
    }
    // buttons numbers
 
    public static final int 
    SHOOT_HIGH_BUTTON = PS4.RIGHT_TRGGER,
    SHOOT_LOW_BUTTON = PS4.RB,
    SHOOT_SHUTTLE_BUTTON = PS4.CIRCLE,
    FLIP_INTAKE_BUTTON = PS4.LEFT_TRIGGER,

    LIFT_INAKE_ALWAYS = PS4.SQUARE,
    SHOOT_WITH_INTAKE_BUTTON = PS4.TRIANGLE,
    SHOOT_FROM_SAFTEY_BUTTON = PS4.X,
    STOP_SPUN_SHOOTER_BUTTON = PS4.OPTIONS;

    // public static final int 
    // SHOOT_HIGH_BUTTON = 1,
    // SHOOT_LOW_BUTTON = 3,
    // SHOOT_SHUTTLE_BUTTON = 4,
    // FLIP_INTAKE_BUTTON = 2,

    // STOP_PULSING_BUTTON = 5,
    // SHOOT_WITH_INTAKE_BUTTON = 6,
    // SHOOT_FROM_SAFTEY_BUTTON = 7;

    public static final double MAX_ACCELERATION = 0.05;
    //public static final double turnDeadBand = 0.05;
    public static final double SPEED_DEAD_BAND = 0.05;

    public static final double INTAKE_MIN_ANGLE_DOWN = 21;//.7*48; right, counter clock was -0.25
    public static final double INTAKE_MAX_ANGLE_UP = 235;//.7*48; left, away was -0.75

    public static final double EncoderOffset = -72;
    //to avoid problems going from 1 to 0, change the absolute 0 location

    public static final double SHOOTER_SPEED = 0.1,
    SHOOTER_OUTER_HIGH_SHOT_SPEED = 0.98,
     SHOOTER_INNER_HIGH_SHOT_SPEED = 0.54,
     SHOOTER_INNER_LOW_SHOT_SPEED = 0.22,
     SHOOTER_OUTER_LOW_SHOT_SPEED = 0.22,
     SHOOTER_INNER_SHUTTLE_SHOT_SPEED = 0.98,
     SHOOTER_OUTER_SHUTTLE_SHOT_SPEED = 0.6, //spit out is 0.2
     SHOOTER_INNER_SAFETY_SHOT_SPEED = 0.5, //*************** */
     SHOOTER_OUTER_SAFTEY_SHOT_SPEED = 0.5;
     ;

    public static final double DELAY_AFTER_STOPPING_INTAKE_AND_LIFTING = 0.5,
    DELAY_STARTING_SHOOTER_BEFORE_REVERSE_INTAKE = 1.3 * 50, //cycles
    DELAY_AFTER_SHOOTING_BEFORE_STOPPING_SHOOTER = 0.7*50
    ;

    public static final double AUTO_OBTAIN_SECOND_NOTE_SPEED = 0.5;
    public static final double ATUO_DRIVE_BACK_SECONDS = 3;
    // port numbers on pdp, computer, pcm...

    public static final int SHOOTER_OUTER_WHEEL_ID = 11,
    SHOOTER_INNER_WHEEL_ID = 13;
    
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
    MIN_TURN = 0.0,

    INTAKE_LOWER_SPEED = -0.25,
    INTAKE_LIFT_SPEED = .25,
    INTAKE_WHEELS_INTAKE_SPEED = 0.3,

    INTAKE_REVERSE_SHOOT_SPEED = -0.25,

    DEGREES_BEFORE_MAX_TO_END = 2;
}

