package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class DuckMec {
    public HardwareMap hwMap;

    public Motor duckMotor;

    public boolean duckMecIsRunning = false;

    public static double duckSpeed = .25;

    public DuckMec(HardwareMap ahw){
        hwMap = ahw;
        duckMotor = new Motor(ahw, "duckMotor", Motor.GoBILDA.RPM_312);
    }

    public void runDuckMec(boolean redSide){
        if(redSide) duckMotor.set(duckSpeed);
        else duckMotor.set(-1*duckSpeed);

        duckMecIsRunning = true;
    }

    public void stopDuckMec(){
        duckMotor.set(0);

        duckMecIsRunning = false;
    }


    public void toggleDuckMec(boolean redSide){
        if(!duckMecIsRunning){
            runDuckMec(redSide);
        }
        else stopDuckMec();
    }




}