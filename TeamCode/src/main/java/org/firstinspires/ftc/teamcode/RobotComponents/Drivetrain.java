package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

@Config
public class Drivetrain {

    public HardwareMap hwMap;

    Depositor depositor;

    public Motor rightMotorOne, rightMotorTwo, rightMotorThree, leftMotorOne, leftMotorTwo, leftMotorThree;



    public static double drivetrainSlowMultiplier = 1;

    private double speedMultiplier = 1;

    private double turnMultiplier = .7;

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

        rightMotorOne.setInverted(true);
        rightMotorTwo.setInverted(true);
        rightMotorThree.setInverted(true);


       // rightDriveMotors = new MotorGroup(rightMotorOne, rightMotorTwo, rightMotorThree);
        //rightDriveMotors.setInverted(true);

        rightMotorOne.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightMotorTwo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightMotorThree.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        //leftDriveMotors = new MotorGroup(leftMotorOne, leftMotorTwo, leftMotorThree);

        leftMotorOne.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftMotorTwo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftMotorThree.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }


    public void setDrivePowers(double leftStickY, double rightStickX) {


       // rightDriveMotors.set(leftStickY * speedMultiplier - rightStickX * turnMultiplier);
       // leftDriveMotors.set(leftStickY * speedMultiplier + rightStickX * turnMultiplier);
    }

    public void setDrivePowerAccelerationCurve(double leftStickY, double rightStickX, double leftMotorPower, double rightMotorPower){
        double rightPowerDelta = Range.clip((leftStickY * speedMultiplier - rightStickX * turnMultiplier) - rightMotorPower, -.7, .7);
        double leftPowerDelta = Range.clip((leftStickY * speedMultiplier + rightStickX * turnMultiplier) - leftMotorPower, -.7, .7);

        rightMotorOne.set(rightMotorPower + rightPowerDelta);
        rightMotorTwo.set(rightMotorPower + rightPowerDelta);
        rightMotorThree.set(rightMotorPower + rightPowerDelta);
        leftMotorOne.set(leftMotorPower + leftPowerDelta);
        leftMotorTwo.set(leftMotorPower + leftPowerDelta);
        leftMotorThree.set(leftMotorPower + leftPowerDelta);

//        rightDriveMotors.set(rightMotorPower + rightPowerDelta);
//        leftDriveMotors.set(leftMotorPower + leftPowerDelta);
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