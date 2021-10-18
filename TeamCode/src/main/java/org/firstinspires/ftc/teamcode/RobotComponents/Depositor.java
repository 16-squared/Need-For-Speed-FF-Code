package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Depositor {



    public HardwareMap hwMap;

    public MotorEx v4bMotor;

    public Servo depositorServo, capServo;


    public ArmLevel armLevel;

    ElapsedTime timer = new ElapsedTime();

    public enum ArmLevel {
        ARMLEVEL_IN,  /* not extended out */
        ARMLEVEL_1,  /* extended out at height of low goal and neutral hub */
        ARMLEVEL_2,  /* extended out at height of mid goal */
        ARMLEVEL_3,  /* extended out at height of high goal */
        ARMLEVEL_CAP   /* capping height */
    }

    public boolean armIsOut(){
        return armLevel != ArmLevel.ARMLEVEL_IN;
    }

    public int armLevel(){
        if (armLevel == ArmLevel.ARMLEVEL_IN) return 0;
        if (armLevel == ArmLevel.ARMLEVEL_1) return 1;
        if (armLevel == ArmLevel.ARMLEVEL_2) return 2;
        if (armLevel == ArmLevel.ARMLEVEL_3) return 3;
        if (armLevel == ArmLevel.ARMLEVEL_CAP) return 4;
        else return 5;
    }

    private boolean firstCapLoop = true;

    public static double armInPosition = 10, armLevelOnePosition = 100, armLevelTwoPosition = 200, armLevelThreePosition = 400, armCapingPosition = 500;

    public double capAngleOffset;

    public static double armP = 0, armD = 0, armMG = 1;

    PIDController armPID = new PIDController(armP, 0, armD);

    public void setCapAngleOffset(boolean dpadUp, boolean dpadDown){
        if(dpadUp) capAngleOffset+=1;
        if(dpadDown) capAngleOffset-=1;
    }

    public void setArmPosition(double sp){
    /*    double pv = v4bMotor.getCurrentPosition();
        double error = sp - pv; */

        double pidf = armPID.calculate(v4bMotor.getCurrentPosition(), sp) + armMG * Math.sin(Math.toRadians(ticksToArmAngle(sp)));
        v4bMotor.set(pidf);
    }

    //calculates arm angle to the vertical
    public double ticksToArmAngle(double ticks){
        return (ticks)/v4bMotor.getCPR()*.8*360;
    }

    public void updateArmPosition(){
        if (armLevel == ArmLevel.ARMLEVEL_IN) {
            setArmPosition(armInPosition);
        }
        if(armLevel == ArmLevel.ARMLEVEL_1){
            setArmPosition(armLevelOnePosition);
        }
        if(armLevel == ArmLevel.ARMLEVEL_2){
            setArmPosition(armLevelTwoPosition);
        }
        if(armLevel == ArmLevel.ARMLEVEL_3){
            setArmPosition(armLevelThreePosition);
        }
        if(armLevel == ArmLevel.ARMLEVEL_CAP){
            if (firstCapLoop) {
                capAngleOffset = 0;
                firstCapLoop = false;
            }
            setArmPosition(armCapingPosition + capAngleOffset);
        }
    }

    public void setArmLevelIn(){
        armLevel = ArmLevel.ARMLEVEL_IN;
    }
    public void setArmLevelOne(){
        armLevel = ArmLevel.ARMLEVEL_1;
    }
    public void setArmLevelTwo(){
        armLevel = ArmLevel.ARMLEVEL_2;
    }
    public void setArmLevelThree(){
        armLevel = ArmLevel.ARMLEVEL_3;
    }
    public void setArmLevelCapping(){
        firstCapLoop = true;
        armLevel = ArmLevel.ARMLEVEL_CAP;
    }





    public Depositor(){

        v4bMotor = new MotorEx(hwMap, "arm", Motor.GoBILDA.RPM_117);
        v4bMotor.setRunMode(Motor.RunMode.RawPower);
    }



}
