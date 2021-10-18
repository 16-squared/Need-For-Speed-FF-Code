package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@Config
public class Intake {

    public HardwareMap hwMap;

    MotorEx intakeMotor;

    Servo intakeStopper;

    public static double intakeSlowSpeed = .4, intakeFastSpeed = 1, intakeReversedSpeed = -.7;

    public boolean intakeStopperIsUp = true, intakeRunningForwards = false;

    public IntakeState intakeState;

    public IntakeState previousIntakeState;

    public enum IntakeState{
        STOPPED,
        REVERSED,
        FORWARDS_SLOW,
        FORWARDS_FAST
    }


    public void toggleIntakeForwards(){
        if(intakeRunningForwards) stopIntake();
        else runIntake();
    }

    public void stopIntake(){
        intakeRunningForwards = false;
        intakeState = IntakeState.STOPPED;
    }


    public void runIntake(){
        intakeRunningForwards = true;
    }

    public void reverseIntake(){
        if(intakeState != IntakeState.REVERSED)previousIntakeState = intakeState;

        intakeRunningForwards = false;
        intakeState = IntakeState.REVERSED;
    }

    public void returnToPreviousIntakeState(){
        if (previousIntakeState == IntakeState.STOPPED) stopIntake();
        if(previousIntakeState == IntakeState.FORWARDS_FAST || previousIntakeState == IntakeState.FORWARDS_SLOW) runIntake();
    }



    public void updateIntake(){

        if (intakeRunningForwards && intakeStopperIsUp) intakeState = IntakeState.FORWARDS_SLOW;
        if (intakeRunningForwards && !intakeStopperIsUp) intakeState = IntakeState.FORWARDS_FAST;

        if(intakeState == IntakeState.STOPPED) intakeMotor.set(0);
        if(intakeState == IntakeState.REVERSED) intakeMotor.set(intakeReversedSpeed);
        if(intakeState == IntakeState.FORWARDS_SLOW) intakeMotor.set(intakeSlowSpeed);
        if(intakeState == IntakeState.FORWARDS_FAST) intakeMotor.set(intakeFastSpeed);
    }





    public Intake(){
        intakeMotor = new MotorEx(hwMap, "intakeMotor", Motor.GoBILDA.RPM_435);
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);

        intakeState = IntakeState.STOPPED;

        intakeStopper = hwMap.servo.get("intakeStopper");

    }
}