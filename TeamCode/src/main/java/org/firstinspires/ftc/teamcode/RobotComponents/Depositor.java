package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Config
public class Depositor {




    public HardwareMap hwMap;

   // Intake intake;

   public DepositorDoor door;

    public DcMotor v4bMotor;

    public Servo depositorServo, depositorLid, capServo;


    public ArmLevel armLevel;

    public ArmLevel previousArmLevel;

    ElapsedTime timer = new ElapsedTime();

    boolean timerStarted = false;

    boolean firstIntegralLoop = true;

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


    private boolean firstCapLoop = true;

    public static double armPositionThreshold = 5, armIntegralThreshold = 5;

    public static double armInPosition = 0, armLevelOnePosition = -1475.0, armLevelTwoPosition = 5150.0, armLevelThreePosition = 4000.0, armCapingPosition = 500, servoLidOpenPosition = 1, servoLidClosePosition = 0;

    public double capAngleOffset;

    public static double armP = 0.0001, armD = 0, armI=0, armMG = 0;

    private double I = 0;

    double lastTime = 0;

    public double totalError;

    public Depositor(HardwareMap ahw){
        hwMap = ahw;

        door = new DepositorDoor(hwMap);
       // intake = new Intake(hwMap);
       // v4bMotor = new MotorEx(hwMap, "arm", Motor.GoBILDA.RPM_117);
        v4bMotor = hwMap.get(DcMotor.class, "arm");
        v4bMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        v4bMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        depositorServo = hwMap.servo.get("depositorServo");

      //  capServo = hwMap.servo.get("capServo");

        depositorLid = hwMap.servo.get("lid");

        totalError=0;

    }

    public void setCapAngleOffset(boolean dpadUp, boolean dpadDown){
        if(dpadUp) capAngleOffset+=1;
        if(dpadDown) capAngleOffset-=1;
    }


    PIDController armPID = new PIDController(armP, 0, armD);

    private void setArmPosition(double sp){
    /*    double pv = v4bMotor.getCurrentPosition();
        double error = sp - pv; */

        double pidf = armPID.calculate(v4bMotor.getCurrentPosition(), sp) + armMG * Math.sin(Math.toRadians(ticksToArmAngle(sp/10))) + I;
        v4bMotor.setPower(Range.clip(pidf, -.6, .6));
          //      v4bMotor.set(pidf);

    }


    public void updatePIDCoeff(){
        armPID = new PIDController(armP, I, armD);
    }


    public double ticksToArmAngle(double ticks){  //calculates arm angle to the vertical
        return ((ticks-35)/1425.1*.8*360);
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
            closeDepositorLid();
            setArmPosition(armCapingPosition + capAngleOffset);

        }
                   /*if(turnOnI()){
                I = armI;
            }
            else I = 0;
*/
        updatePIDCoeff();
        updateInt();
        depositorLidControl();

        if(door.bringInArm()) armLevel = ArmLevel.ARMLEVEL_IN;
    }

    public void updateInt(){

        double sp;
        double time;
        double deltaTime;


        while (true){
        if(armLevel==ArmLevel.ARMLEVEL_IN) sp = armInPosition;
        else if(armLevel==ArmLevel.ARMLEVEL_1) sp = armLevelOnePosition;
        else if(armLevel==ArmLevel.ARMLEVEL_2) sp = armLevelTwoPosition;
        else if (armLevel==ArmLevel.ARMLEVEL_3) sp =armLevelThreePosition;
        else {
            I = 0;
            totalError = 0;
            firstIntegralLoop = true;
            break;
        }
       if(firstIntegralLoop) {
           lastTime = System.nanoTime() / 1e9d;
           firstIntegralLoop = false;
       }
        time = System.nanoTime() / 1e9d;
        deltaTime = time - lastTime;
        lastTime = time;

        double error = sp-v4bMotor.getCurrentPosition();

        if (Math.abs(error) < armIntegralThreshold) totalError += error * deltaTime;
        else {
            totalError = 0;
            firstIntegralLoop = true;
        }
        I = totalError*armI;

            break;
    }
    }



    //setting arm lvls
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

    public void setPreviousArmLevel() {
        previousArmLevel = armLevel;
    }



    //checks if arm is out enough to be ready to deposit
    public boolean readyToDeposit(){
        if (armLevel== ArmLevel.ARMLEVEL_CAP) return true;
        else if (armLevel == ArmLevel.ARMLEVEL_1 && Math.abs(armLevelOnePosition-v4bMotor.getCurrentPosition())<armPositionThreshold) return true;
        else if (armLevel == ArmLevel.ARMLEVEL_2 && Math.abs(armLevelTwoPosition-v4bMotor.getCurrentPosition())<armPositionThreshold) return true;
        else if (armLevel == ArmLevel.ARMLEVEL_3 && Math.abs(armLevelThreePosition-v4bMotor.getCurrentPosition())<armPositionThreshold) return true;
        else return false;
    }

    public void setArmAngle(double ticks){
        setArmPosition(ticks);
    }



    /*
    public boolean turnOnI(){
        if (armLevel == ArmLevel.ARMLEVEL_1 && Math.abs(armLevelOnePosition-v4bMotor.getCurrentPosition())<armIntegralThreshold) return true;
        else if (armLevel == ArmLevel.ARMLEVEL_2 && Math.abs(armLevelTwoPosition-v4bMotor.getCurrentPosition())<armIntegralThreshold) return true;
        else if (armLevel == ArmLevel.ARMLEVEL_3 && Math.abs(armLevelThreePosition-v4bMotor.getCurrentPosition())<armIntegralThreshold) return true;
        else return false;
    }

     */



















    //depositor lid control

    public void closeDepositorLid(){
        depositorLid.setPosition(servoLidClosePosition);
    }

    public void openDepositorLid(){
        depositorLid.setPosition(servoLidOpenPosition);
    }

    public void depositorLidControl(){
        if(armLevel==ArmLevel.ARMLEVEL_IN && Math.abs(armInPosition-v4bMotor.getCurrentPosition()) < armPositionThreshold) openDepositorLid();
        if(armIsOut()) closeDepositorLid();
    }


}
