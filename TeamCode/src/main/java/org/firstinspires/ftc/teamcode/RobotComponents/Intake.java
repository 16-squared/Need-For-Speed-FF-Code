package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Intake {

    public HardwareMap hwMap;

    MotorEx intakeMotor;

    Servo intakeStopper;

    Depositor depositor;

    public static double intakeSlowSpeed = .5, intakeFastSpeed = .8, intakeReversedSpeed = -.7;

    public static double intakeStopperDownPosition = .6, intakeStopperOutPosition = 0.05;

    public boolean intakeStopperIsOut = true, intakeRunningForwards = false, armStateChange = false;;

    public IntakeState intakeState;

    public IntakeState previousIntakeState;

    public enum IntakeState{
        STOPPED,
        REVERSED,
        FORWARDS_SLOW,
        FORWARDS_FAST
    }

    public Intake(HardwareMap awh){
        hwMap = awh;
        intakeMotor = new MotorEx(hwMap, "intakeMotor", Motor.GoBILDA.RPM_312);
        intakeMotor.setRunMode(Motor.RunMode.RawPower);
        intakeMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        intakeMotor.setInverted(true);

        intakeState = IntakeState.STOPPED;

        intakeStopper = hwMap.servo.get("intakeStopper");

        depositor = new Depositor(hwMap);

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
      //  if(intakeStopper.getPosition()==intakeStopperOutPosition) intakeStopperIsOut = true;
       // else intakeStopperIsOut = false;

        if (intakeRunningForwards && intakeStopperIsOut) intakeState = IntakeState.FORWARDS_SLOW;
        if (intakeRunningForwards && !intakeStopperIsOut) intakeState = IntakeState.FORWARDS_FAST;

        if(intakeState == IntakeState.STOPPED) intakeMotor.set(0);
        if(intakeState == IntakeState.REVERSED) intakeMotor.set(intakeReversedSpeed);
        if(intakeState == IntakeState.FORWARDS_SLOW) intakeMotor.set(intakeSlowSpeed);
        if(intakeState == IntakeState.FORWARDS_FAST) intakeMotor.set(intakeFastSpeed);


        if(armStateChange){
            if(depositor.armLevel == Depositor.ArmLevel.ARMLEVEL_IN) {
                intakeStopperOut();
                runIntake();
            }
            else {
                intakeStopperOut();
                stopIntake();
            }
            armStateChange = false;
        }
    }




    //intake stopper
    public void intakeStopperOut(){
        intakeStopper.setPosition(intakeStopperOutPosition);
        intakeStopperIsOut = true;
    }

    public void intakeStopperIn(){
        intakeStopper.setPosition(intakeStopperDownPosition);
        intakeStopperIsOut = false;
    }

    public void toggleIntakeStopper(){
        if(intakeStopperIsOut) intakeStopperIn();
        else intakeStopperOut();
    }



}
