package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class Drivetrain {

    public HardwareMap hwMap;

    Depositor depositor;

    public Motor rightMotorOne, rightMotorTwo, rightMotorThree, leftMotorOne, leftMotorTwo, leftMotorThree;



    public static double drivetrainSlowMultiplier = 1;

    private double speedMultiplier;

    MotorGroup rightDriveMotors, leftDriveMotors;


    public Drivetrain(HardwareMap ahw){
        hwMap = ahw;
        depositor = new Depositor(hwMap);
        rightMotorOne = new Motor(hwMap, "rightMotorOne", Motor.GoBILDA.RPM_435);
        rightMotorTwo = new Motor(hwMap, "rightMotorTwo", Motor.GoBILDA.RPM_435);
        rightMotorThree = new Motor(hwMap, "rightMotorThree", Motor.GoBILDA.RPM_435);
        leftMotorOne = new Motor(hwMap, "leftMotorOne", Motor.GoBILDA.RPM_435);
        leftMotorTwo = new Motor(hwMap, "leftMotorTwo", Motor.GoBILDA.RPM_435);
        leftMotorThree = new Motor(hwMap, "leftMotorTwo", Motor.GoBILDA.RPM_435);


        rightDriveMotors = new MotorGroup(rightMotorOne, rightMotorTwo, rightMotorThree);
        rightDriveMotors.setInverted(true);

        rightMotorOne.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightMotorTwo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightMotorThree.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        leftDriveMotors = new MotorGroup(leftMotorOne, leftMotorTwo, leftMotorThree);

        leftMotorOne.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftMotorTwo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftMotorThree.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }


    public void setDrivePowers(double leftStickY, double rightStickX) {


        rightDriveMotors.set(leftStickY - rightStickX);
        leftDriveMotors.set(leftStickY + rightStickX);
    }

/*    public void setDrivePowers(double leftStickY, double rightStickY){

        if(depositor.armIsOut()){
            speedMultiplier = drivetrainSlowMultiplier;
        }
        else speedMultiplier = 1;

        rightDriveMotors.set(rightStickY* speedMultiplier);
        leftDriveMotors.set(leftStickY* speedMultiplier);


    }
*/




}