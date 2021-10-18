package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Drivetrain {

    public HardwareMap hwMap;

    Depositor depositor = new Depositor();

    public Motor rightMotorOne, rightMotorTwo, leftMotorOne, leftMotorTwo;



    public static double drivetrainSlowMultiplier = 1;

    private double speedMultiplier;

    MotorGroup rightDriveMotors;
    MotorGroup leftDriveMotors;


    public Drivetrain(){

        rightMotorOne = new Motor(hwMap, "rightMotorOne", Motor.GoBILDA.RPM_435);
        rightMotorTwo = new Motor(hwMap, "rightMotor", Motor.GoBILDA.RPM_435);
        leftMotorOne = new Motor(hwMap, "leftMotorOne", Motor.GoBILDA.RPM_435);
        leftMotorTwo = new Motor(hwMap, "leftMotorTwo", Motor.GoBILDA.RPM_435);


        rightDriveMotors = new MotorGroup(rightMotorOne, rightMotorTwo);
        rightDriveMotors.setInverted(true);
        rightDriveMotors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        leftDriveMotors = new MotorGroup(leftMotorOne, leftMotorTwo);
        leftDriveMotors.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void setDrivePowers(double leftStickY, double rightStickY){

        if(depositor.armIsOut()){
            speedMultiplier = drivetrainSlowMultiplier;
        }
        else speedMultiplier = 1;

        rightDriveMotors.set(rightStickY* speedMultiplier);
        leftDriveMotors.set(leftStickY* speedMultiplier);


    }


}








