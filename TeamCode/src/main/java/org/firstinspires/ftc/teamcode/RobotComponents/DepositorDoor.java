package org.firstinspires.ftc.teamcode.RobotComponents;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DepositorDoor {

    HardwareMap hwMap;

    Depositor depositor;

    public Servo depositorServo;

    ElapsedTime timer = new ElapsedTime();

    boolean timerStarted = false;

    public static double depositorServoClosedPosition = 0, depositorServoOpenPosition = .3;

    public boolean depositorDoorIsOpen = false;

    public DepositorDoor(HardwareMap ahw){
        hwMap = ahw;

        depositor = new Depositor(ahw);

    }

    public void closeDepositor() {
        if (depositor.readyToDeposit()) {
            depositorServo.setPosition(depositorServoClosedPosition);
            depositorDoorIsOpen = false;
        }
    }

    public void openDepositor() {

        if (depositor.readyToDeposit()) {
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

    public void depositorServoToggle(){
        if(depositorDoorIsOpen) closeDepositor();
        else if(depositor.readyToDeposit()) openDepositor();
    }
}
