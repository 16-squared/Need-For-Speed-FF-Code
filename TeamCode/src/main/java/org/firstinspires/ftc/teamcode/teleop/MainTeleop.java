package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotComponents.Depositor;
import org.firstinspires.ftc.teamcode.RobotComponents.DrivetrainNoVelo;
import org.firstinspires.ftc.teamcode.RobotComponents.DuckMec;
import org.firstinspires.ftc.teamcode.RobotComponents.Intake;
import org.firstinspires.ftc.teamcode.old.Drivetrain;

@Config
@TeleOp(name = "TeleOP", group = "Linear Opmode")
public class MainTeleop extends LinearOpMode {

    @Override
    public void runOpMode(){
        Depositor depositor = new Depositor(hardwareMap);
        DrivetrainNoVelo drivetrain = new DrivetrainNoVelo(hardwareMap);
        GamepadEx pad1 = new GamepadEx(gamepad1);
        GamepadEx pad2 = new GamepadEx(gamepad2);
        Intake intake = new Intake(hardwareMap);
        DuckMec duckMec = new DuckMec(hardwareMap);
        FtcDashboard dashboard = FtcDashboard.getInstance();

        boolean depositorDoorHasSwitched = false;
        boolean openDepositorDoor = false;


        boolean rightTriggerDownLastLoop = false;

        waitForStart();

        depositor.armLevel = Depositor.ArmLevel.ARMLEVEL_IN;
        while (opModeIsActive()){


        //controller one

            //dt powers
            //drivetrain.setDrivePowers(pad1.getLeftY(), pad1.getRightX());
            //drivetrain.setDrivePowerAccelerationCurve(pad1.getLeftY(), pad1.getRightX(), drivetrain.leftMotorOne.motor.getPower(), drivetrain.rightMotorOne.motor.getPower());
            drivetrain.setDrivePowerWithLUT(pad1.getLeftY(), pad1.getRightX(), drivetrain.leftMotorOne.motor.getPower(), drivetrain.rightMotorOne.motor.getPower());




            //arm positions
            depositor.setPreviousArmLevel();


            if (pad1.gamepad.left_bumper){
                intake.runIntake();
            }

            if(pad1.gamepad.right_bumper && intake.intakeState != Intake.IntakeState.STOPPED && depositor.armLevel==Depositor.ArmLevel.ARMLEVEL_IN){
                intake.intakeStopperIn();
            }


            //Bring arm in (press y)
            if(pad1.gamepad.y){
                depositor.setArmLevelIn();
            }
         /*   //Bring arm to cap (press LB)
            if(pad1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                depositor.setArmLevelCapping();
            }
            //Adjust capping height (dpad up and down)
            depositor.setCapAngleOffset(pad2.isDown(GamepadKeys.Button.DPAD_UP), pad2.isDown(GamepadKeys.Button.DPAD_DOWN));
*/
            if(depositor.armLevel != Depositor.ArmLevel.ARMLEVEL_CAP) {
                //Bring arm to low goal (press B)
                if(pad1.gamepad.b){
                    depositor.setArmLevelOne();
                }
                //Bring arm to mid goal (press A)
                if(pad1.gamepad.a){
                    depositor.setArmLevelTwo();
                }
                //Bring arm to high goal (press X)
                if(pad1.gamepad.x) {
                    depositor.setArmLevelThree();
                }
            }

                //open depositor
            if(pad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>.05){
                if(!depositor.depositorDoorIsOpen && !rightTriggerDownLastLoop && depositor.readyToDeposit()) {
                    depositor.openDepositor();
                    rightTriggerDownLastLoop = true;
                }
            }
            else rightTriggerDownLastLoop = false;


            //reset v4b encoder
            if(pad1.gamepad.back){
                depositor.v4bMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                depositor.v4bMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

           // if(pad1.gamepad.left_bumper) intake.intakeStopperIn();

            /*
            //code for toggling the depositor door with gamepad

            if(pad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>.05 && !depositorDoorHasSwitched){
                openDepositorDoor = true;
            }
            if(pad1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)<=.05){
                openDepositorDoor = false;
                depositorDoorHasSwitched = false;
            }

            if(openDepositorDoor && !depositorDoorHasSwitched){
                depositor.depositorServoToggle();
                depositorDoorHasSwitched = true;
            }
*/






        //controller two


                    //Bring arm to cap (press LB)
            if(pad2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                depositor.setArmLevelCapping();
            }
            //Adjust capping height (dpad up and down)
            depositor.setCapAngleOffset(pad2.isDown(GamepadKeys.Button.DPAD_UP), pad2.isDown(GamepadKeys.Button.DPAD_DOWN));



            //duck mec
                    //toggle duck mec (press RB)
            if(pad2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) duckMec.toggleDuckMec(false);

            //intake controls
                  //toggle intake forwards and off (press a)
            if(pad2.wasJustPressed(GamepadKeys.Button.A)) intake.toggleIntakeForwards();

                  //intake reversal (hold x)
            if(pad2.isDown(GamepadKeys.Button.X)) intake.reverseIntake();
            if(pad2.wasJustReleased(GamepadKeys.Button.X)) intake.returnToPreviousIntakeState();


           depositor.updateArmPosition();

           depositor.closeDepositorDoorTimer();

          // depositor.intakeControl();

           intake.updateIntake();


           if(depositor.previousArmLevel != depositor.armLevel) intake.armStateChange = true;

           /*

                    //drop elements (press RB)
           if(pad2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
                depositor.depositorServoToggle();
            } //todo change this method so you can hold the button and it will open the door once readyToDeposit is true

*/

            telemetry.addData("left motor", drivetrain.leftMotorOne.motor.getPower());
            telemetry.addData("right motor", drivetrain.rightMotorOne.motor.getPower());
            telemetry.addData("arm position", depositor.v4bMotor.getCurrentPosition());
            telemetry.addData("arm state", depositor.armLevel);
            telemetry.addData("arm State Change", intake.armStateChange);
            telemetry.addData("intake", intake.intakeState);
            telemetry.addData("intake stopper", intake.intakeStopperIsOut);
            telemetry.update();



            TelemetryPacket packet = new TelemetryPacket();
            packet.put("left velocity", drivetrain.leftMotorOne.getCorrectedVelocity());
            packet.put("left power", drivetrain.leftMotorOne.motor.getPower());
            packet.put("right velocity", drivetrain.rightMotorOne.getCorrectedVelocity());
            packet.put("right power", drivetrain.rightMotorOne.motor.getPower());
            dashboard.sendTelemetryPacket(packet);



        }
    }
}
