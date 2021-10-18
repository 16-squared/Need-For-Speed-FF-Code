package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
public class DuckMec {
    public HardwareMap hwMap;

    CRServo duckServo;

    public boolean duckMecIsRunning = false;

    public static double duckSpeed = 1;

    public void runDuckMec(boolean redSide){
        if(redSide) duckServo.setPower(duckSpeed);
        else duckServo.setPower(-1*duckSpeed);

        duckMecIsRunning = true;
    }

    public void stopDuckMec(){
        duckServo.setPower(0);

        duckMecIsRunning = false;
    }


    public void toggleDuckMec(boolean redSide){
        if(!duckMecIsRunning){
            runDuckMec(redSide);
        }
        else stopDuckMec();
    }



    public DuckMec(){
        duckServo = hwMap.crservo.get("duckServo");
    }
}
