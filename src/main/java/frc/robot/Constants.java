// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


public final class Constants {
    public static double SPEED_MULTIPLIER = 1;

    // PS4 Constants
    public static class PS4 {
        public static final int X = 2, 
        CIRCLE = 3, SQUARE = 1, TRIANGLE = 4, LB = 5, RB = 6, TOUCHPAD = 9, OPTIONS = 10, L3 = 11, R3 = 12,
                LEFT_TRIGGER = 3, RIGHT_TRGGER = 4, LEFT_X = 0, LEFT_Y = 1, RIGHT_X = 2, RIGHT_Y = 5;
    }


    public static final double MAX_ACCELERATION = 0.04;
    public static final double turnDeadBand = 0.05;
    public static final double speedDeadBand = 0.05;
    // port numbers on pdp, computer, pcm...
    public static class ports{
        public static final int 
        frontLeft = 13,//13
        backLeft = 12,//12
        frontRight = 11,
        backRight = 14,

        thrusty = 0,
        Joy2 = 1,

        commpressor = 0,

        leftWingRetract = 3,
        leftWingExtend = 2,

        rightWingRetract = 4,
        rightWingExtend = 5,

        armConeRetract = 1,
        armConeExtend = 0,

        arm= 15   //make that motor number 5 in peonix tuner
    ;
    }
   
    //important values
    public static final double MIN_POWER = 0.15;
    public static final double MIN_TURN = 0.1;

    public static final double MIN_GYRO_MOVE = 0.05;
    public static final double MIN_BALANCE_MOVE = 0.15;

    public static final double ARM_SPEED = 0.15;
    //public static Button  bu= new But()
    //public static final Trigger halfSpeed = new Trigger(null)

    // buttons numbers
    public static class buttons{
        public static final int 
        SHOOT_BUTTON = 1,
        NUM2_BUTTON = 2,
        NUM3_BUTTON = 3,
        NUM4_BUTTON = 4
        ;
        public static final int[] BUTTON_ARR = {SHOOT_BUTTON};
    }
    public static class OperatorConstants {
      public static final int kDriverControllerPort = 0;
  }
}

