package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class DepositorLid {

    public HardwareMap hwMap;

    Depositor depositor;

    public Servo depositorLid;

    public static double servoLidOpenPosition = 1, servoLidClosePosition = 0;

    public DepositorLid(HardwareMap ahw){
        hwMap = ahw;

        depositor = new Depositor(ahw);

        depositorLid = hwMap.servo.get("lidServo");

    }

    public void closeDepositorLid(){
        depositorLid.setPosition(servoLidClosePosition);
    }

    public void openDepositorLid(){
        depositorLid.setPosition(servoLidOpenPosition);
    }

    public void depositorLidControl(){
        if(depositor.armLevel== Depositor.ArmLevel.ARMLEVEL_IN && Math.abs(depositor.armInPosition-depositor.v4bMotor.getCurrentPosition()) < depositor.armPositionThreshold) openDepositorLid();
        if(depositor.armIsOut()) closeDepositorLid();
    }


}
