package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

@Config
public class Drivetrain {

    public HardwareMap hwMap;

    Depositor depositor;


    public Motor rightFrontMotor, rightBackMotor, leftFrontMotor, leftBackMotor;



    public static double drivetrainSlowMultiplier = 1;

    public static double kP = 1, kI = 0, kD = 0, kV = 1, kA =0;

    private double speedMultiplier = 1;

    private double turnMultiplier = .3;


    public Drivetrain(HardwareMap ahw){
        hwMap = ahw;
        depositor = new Depositor(hwMap);
        rightFrontMotor = new Motor(hwMap, "rightFrontMotor", Motor.GoBILDA.RPM_312);
        rightBackMotor = new Motor(hwMap, "rightBackMotor", Motor.GoBILDA.RPM_312);
        leftFrontMotor = new Motor(hwMap, "leftFrontMotor", Motor.GoBILDA.RPM_312);
        leftBackMotor = new Motor(hwMap, "leftBackMotor", Motor.GoBILDA.RPM_312);

        rightFrontMotor.setInverted(true);
        rightBackMotor.setInverted(true);

        rightFrontMotor.setRunMode(Motor.RunMode.RawPower);
        rightBackMotor.setRunMode(Motor.RunMode.RawPower);

        leftFrontMotor.setRunMode(Motor.RunMode.RawPower);
        leftBackMotor.setRunMode(Motor.RunMode.RawPower);





        rightFrontMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        leftFrontMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }



    public void driveFieldCentric(double x, double y, double rx) {
       rightFrontMotor.motor.setPower(1 * (y + x + rx));
        leftFrontMotor.motor.setPower(1 * (y - x - rx));
        leftBackMotor.motor.setPower(1 * (y - x + rx));
        rightBackMotor.motor.setPower(1 * (y + x - rx));
    }



}