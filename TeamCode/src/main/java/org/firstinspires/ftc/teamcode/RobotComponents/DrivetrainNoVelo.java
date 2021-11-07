package org.firstinspires.ftc.teamcode.RobotComponents;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorGroup;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

@Config
public class DrivetrainNoVelo {

    public HardwareMap hwMap;

    Depositor depositor;

    PowerLUT correctionLUT = new PowerLUT();

    public Motor rightMotorOne, rightMotorTwo, rightMotorThree, leftMotorOne, leftMotorTwo, leftMotorThree;

    public static double drivetrainSlowMultiplier = 1, percentIncreaseForwardsFast = 10, percentIncreaseBackwardsSlow = 15, percentIncreaseBackwardsFast = 20;

    public double speedMultiplier = 1;

    public double turnMultiplier = .6;

    MotorGroup rightDriveMotors, leftDriveMotors;


    public DrivetrainNoVelo(HardwareMap ahw){
        hwMap = ahw;
        depositor = new Depositor(hwMap);
        rightMotorOne = new Motor(hwMap, "rightMotorOne", Motor.GoBILDA.RPM_435);
        rightMotorTwo = new Motor(hwMap, "rightMotorTwo", Motor.GoBILDA.RPM_435);
        rightMotorThree = new Motor(hwMap, "rightMotorThree", Motor.GoBILDA.RPM_435);
        leftMotorOne = new Motor(hwMap, "leftMotorOne", Motor.GoBILDA.RPM_435);
        leftMotorTwo = new Motor(hwMap, "leftMotorTwo", Motor.GoBILDA.RPM_435);
        leftMotorThree = new Motor(hwMap, "leftMotorTwo", Motor.GoBILDA.RPM_435);

        rightMotorOne.setInverted(true);
        rightMotorTwo.setInverted(true);
        rightMotorThree.setInverted(true);


       // rightDriveMotors = new MotorGroup(rightMotorOne, rightMotorTwo, rightMotorThree);
        //rightDriveMotors.setInverted(true);

        rightMotorOne.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightMotorTwo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightMotorThree.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        //leftDriveMotors = new MotorGroup(leftMotorOne, leftMotorTwo, leftMotorThree);

        leftMotorOne.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftMotorTwo.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftMotorThree.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }


    public void setDrivePowers(double leftStickY, double rightStickX) {


       // rightDriveMotors.set(leftStickY * speedMultiplier - rightStickX * turnMultiplier);
       // leftDriveMotors.set(leftStickY * speedMultiplier + rightStickX * turnMultiplier);
    }

    public void setDrivePowerWithLUT(double leftStickY, double rightStickX, double leftMotorPower, double rightMotorPower){

        if (depositor.armIsOut()) turnMultiplier = .3;
        else turnMultiplier = .6;
        double rightPowerDelta = Range.clip((leftStickY * speedMultiplier - rightStickX * turnMultiplier) - rightMotorPower, -.1, .1);
        double leftPowerDelta = Range.clip((leftStickY * speedMultiplier + rightStickX * turnMultiplier) - leftMotorPower, -.1, .1);

        double leftInput = leftPowerDelta + leftMotorPower;
                if(Math.abs(leftMotorPower) < Math.abs(leftInput)) leftInput = (leftPowerDelta + leftMotorPower) * correctionLUT.percentScaling(leftStickY);
        double rightInput = (rightPowerDelta + rightMotorPower);










        /* if(Math.abs(rightStickX)<.1) leftInput = correctionLUT.percentScaling(leftPowerDelta + leftMotorPower)*leftInput; //robot is not turning
        else if(Math.abs(leftStickY)<.1) ;//robot is turning (in place)
        else if(Math.abs(leftStickY)>=.05) leftInput = correctionLUT.percentScaling(leftPowerDelta + leftMotorPower)*leftInput; */

        //nomalize powers to one (only works when inputs are equal, not when turning

      /*  if(leftInput>1 && (leftPowerDelta + leftMotorPower) ==  rightInput){
            rightInput = rightInput-leftInput+1;
        } */

        //set motor powers
        rightMotorOne.set(rightInput);
        rightMotorTwo.set(rightInput);
        rightMotorThree.set(rightInput);
        leftMotorOne.set(leftInput);
        leftMotorTwo.set(leftInput);
        leftMotorThree.set(leftInput);
    }

    public void setDrivePowerAccelerationCurve(double leftStickY, double rightStickX, double leftMotorPower, double rightMotorPower){
        double rightPowerDelta = Range.clip((leftStickY * speedMultiplier - rightStickX * turnMultiplier) - rightMotorPower, -.05, .05);
        double leftPowerDelta = Range.clip((leftStickY * speedMultiplier + rightStickX * turnMultiplier) - leftMotorPower, -.05, .05);

        double percentScalarForwardsFast = (percentIncreaseForwardsFast +100)/100;
        double percentScalarBackwardsSlow = (percentIncreaseBackwardsSlow +100)/100;
        double percentScalarBackwardsFast = (percentIncreaseBackwardsFast +100)/100;

        double leftInput = (leftPowerDelta + leftMotorPower);

        double rightInput = (rightPowerDelta + rightMotorPower);

        if(Math.abs(leftInput + rightInput) <= .07 && leftInput/Math.abs(leftInput) != rightInput/Math.abs(rightInput)){
            //robot is turning
        }
        else if(rightInput>0 && leftInput>0) leftInput = leftInput* percentScalarForwardsFast; //moving forwards
        else if(leftInput<0 && rightInput<0 && Math.abs(leftMotorOne.motor.getPower())<=.75) leftInput = leftInput* percentScalarBackwardsSlow; //moving backwards slow
        else if (rightInput< 0 && leftInput<0 && Math.abs(leftMotorOne.motor.getPower())>.75) leftInput = leftInput*percentScalarBackwardsFast; //moving backwards fast

        if(Math.abs(leftStickY)<.05) leftInput = (leftPowerDelta + leftMotorPower);

        if(leftInput>1 && (leftPowerDelta + leftMotorPower) ==  rightInput){
            rightInput = rightInput-leftInput+1;
        }

        rightMotorOne.set(rightInput);
        rightMotorTwo.set(rightInput);
        rightMotorThree.set(rightInput);
        leftMotorOne.set(leftInput);
        leftMotorTwo.set(leftInput);
        leftMotorThree.set(leftInput);

//        rightDriveMotors.set(rightMotorPower + rightPowerDelta);
//        leftDriveMotors.set(leftMotorPower + leftPowerDelta);
    }

/*    public void setDrivePowers(double leftStickY, double rightStickY){

        if(depositor.armIsOut()){
            speedMultiplier = drivetrainSlowMultiplier;
        }
        else speedMultiplier = 1;

        rightDriveMotors.set(rightStickY* speedMultiplier);
        leftDriveMotors.set(leftStickY* speedMultiplier);


    }
*/




}