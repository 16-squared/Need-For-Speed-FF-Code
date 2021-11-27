package org.firstinspires.ftc.teamcode.RobotComponents;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DepositorDoor {

    HardwareMap hwMap;

    public Servo depositorServo;

    ElapsedTime timer = new ElapsedTime();
    boolean timerStarted = false;

    ElapsedTime armTimer = new ElapsedTime();
    boolean armTimerStarted = false;


    public static double depositorServoClosedPosition = 0, depositorServoOpenPosition = .3;

    public boolean depositorDoorIsOpen = false;

    public DepositorDoor(HardwareMap ahw){
        hwMap = ahw;

        depositorServo = hwMap.servo.get("depositorServo");

    }

    public void closeDepositor() {
            depositorServo.setPosition(depositorServoClosedPosition);
            depositorDoorIsOpen = false;
            armTimer.reset();
            armTimerStarted = true;
        }

    public void openDepositor(Boolean readyToDeposit) {

        if (readyToDeposit) {
            depositorServo.setPosition(depositorServoOpenPosition);
            depositorDoorIsOpen = false;
            timer.reset();
            timerStarted = true;
        }
    }

    public void closeDepositorDoorTimer(){
        if(timerStarted){
            if(timer.milliseconds()>500){
                closeDepositor();
                timerStarted = false;
            }
        }
    }

    public boolean bringInArm(){
        if(armTimerStarted && armTimer.milliseconds()>250) {
            armTimerStarted = false;
            return true;
        }
        else return false;
    }

    public void depositorServoToggle(Boolean readyToDeposit){
        if(depositorDoorIsOpen) closeDepositor();
        else if(readyToDeposit) openDepositor(true);
    }
}
