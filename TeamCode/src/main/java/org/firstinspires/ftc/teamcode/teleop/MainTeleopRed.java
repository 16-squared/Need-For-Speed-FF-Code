package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.RobotComponents.Depositor;
import org.firstinspires.ftc.teamcode.RobotComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.RobotComponents.DuckMec;
import org.firstinspires.ftc.teamcode.RobotComponents.Intake;

@Config
@TeleOp(name = "Red TeleOP", group = "Linear Opmode")
public class MainTeleopRed extends LinearOpMode {

    @Override
    public void runOpMode(){
        Depositor depositor = new Depositor(hardwareMap);
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        GamepadEx pad1 = new GamepadEx(gamepad1);
        GamepadEx pad2 = new GamepadEx(gamepad2);
        Intake intake = new Intake(hardwareMap);
        DuckMec duckMec = new DuckMec(hardwareMap);
        FtcDashboard dashboard = FtcDashboard.getInstance();


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        BNO055IMU imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);


        boolean depositorDoorHasSwitched = false;
        boolean openDepositorDoor = false;

        double offSetAngle = 0;
        double heading;



        boolean a1DownLastLoop = false;

        boolean intakeReversedLastLoop = false;
        boolean intakeToggledLastLoop = false;


        waitForStart();

        depositor.armLevel = Depositor.ArmLevel.ARMLEVEL_IN;
        while (opModeIsActive()){


        //controller one

            //dt powers

            Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
            double ly = gamepad1.left_stick_y;
            double lx = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;

            heading = angles.firstAngle - offSetAngle + Math.toRadians(270);
            double speed = Math.hypot(ly, lx);
            double y = speed * Math.sin(Math.atan2(ly, lx) - heading);
            double x = speed * Math.cos(Math.atan2(ly, lx) - heading);
            drivetrain.driveFieldCentric(x, y, rx);




            //arm positions
            depositor.setPreviousArmLevel();


            if (gamepad1.a){
                intake.runIntake();
            }

            if(gamepad1.right_stick_button && intake.intakeState != Intake.IntakeState.STOPPED && depositor.armLevel==Depositor.ArmLevel.ARMLEVEL_IN){
                intake.intakeStopperIn();
            }


            //Bring arm in (press Right Bumper)
            if(gamepad1.right_bumper){
                depositor.setArmLevelIn();
            }


            //cap
         /*   //Bring arm to cap (press LB)
            if(pad1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                depositor.setArmLevelCapping();
            }
            //Adjust capping height (dpad up and down)
            depositor.setCapAngleOffset(pad2.isDown(GamepadKeys.Button.DPAD_UP), pad2.isDown(GamepadKeys.Button.DPAD_DOWN));

            if(depositor.armLevel != Depositor.ArmLevel.ARMLEVEL_CAP) {
                //Bring arm to low goal (press Left Trigger)
                if(pad1.gamepad.left_trigger>.05){
                    depositor.setArmLevelOne();
                }
                //Bring arm to mid goal (press Left Bumper)
                if(pad1.gamepad.left_bumper){
                    depositor.setArmLevelTwo();
                }
                //Bring arm to high goal (press Right Trigger)
                if(gamepad1.right_trigger>.05) {
                    depositor.setArmLevelThree();
                }
            }


          */
                //open depositor
            if(gamepad1.a){
                if(!depositor.door.depositorDoorIsOpen && !a1DownLastLoop && depositor.readyToDeposit()) {
                    depositor.door.openDepositor(true);
                    a1DownLastLoop = true;
                }
            }
            else a1DownLastLoop = false;


            //reset v4b encoder
            if(pad1.gamepad.back){
               offSetAngle = angles.firstAngle;
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
           // if(pad2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
             //   depositor.setArmLevelCapping();
            //}
            //Adjust capping height (dpad up and down)
            //depositor.setCapAngleOffset(pad2.isDown(GamepadKeys.Button.DPAD_UP), pad2.isDown(GamepadKeys.Button.DPAD_DOWN));


            if(gamepad2.y) depositor.setArmLevelIn();

            if(gamepad2.x)depositor.setArmLevelOne();

            if(gamepad2.a)depositor.setArmLevelTwo();

            if(gamepad2.b)depositor.setArmLevelThree();



            //duck mec
                    //toggle duck mec (hold RT) //todo
            if(gamepad2.b) duckMec.duckMotor.set(.25);
            else duckMec.duckMotor.set(0);

            //intake controls
                  //toggle intake forwards and off (press LB)
            if(gamepad2.left_bumper && !intakeToggledLastLoop) {
                intake.toggleIntakeForwards();
                intakeToggledLastLoop = true;
            }

            if (!gamepad2.left_bumper) intakeToggledLastLoop = false;

            //Bring intake stopper up (press LT)
            if(gamepad2.left_trigger>.05) intake.intakeStopperIn();


                  //intake reversal (hold Dpad down)
            if(gamepad2.dpad_down){
                intake.reverseIntake();
                intakeReversedLastLoop = true;
            }
            if(!gamepad2.dpad_down && intakeReversedLastLoop) {
                intake.returnToPreviousIntakeState();
                intakeReversedLastLoop = false;
            }

           depositor.updateArmPosition();

           depositor.door.closeDepositorDoorTimer();

          // depositor.intakeControl();

           intake.updateIntake();


           if(depositor.previousArmLevel != depositor.armLevel) intake.armStateChange = true;

           /*

                    //drop elements (press RB)
           if(pad2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
                depositor.depositorServoToggle();
            } //todo change this method so you can hold the button and it will open the door once readyToDeposit is true

*/
            //telemetry.addData("stick", (pad1.getLeftY() * drivetrain.speedMultiplier + pad1.getRightX() * drivetrain.turnMultiplier) - drivetrain.leftMotorOne.motor.getPower());
            //telemetry.addData("left motor", drivetrain.leftMotorOne.motor.getPower());
            //telemetry.addData("right motor", drivetrain.rightMotorOne.motor.getPower());
            telemetry.addData("arm position", depositor.v4bMotor.getCurrentPosition());
            telemetry.addData("arm state", depositor.armLevel);
            telemetry.addData("arm State Change", intake.armStateChange);
            telemetry.addData("intake", intake.intakeState);
            telemetry.addData("intake stopper", intake.intakeStopperIsOut);
            telemetry.update();



            TelemetryPacket packet = new TelemetryPacket();
            //packet.put("left velocity", drivetrain.leftMotorOne.getCorrectedVelocity());
            //packet.put("left power", drivetrain.leftMotorOne.motor.getPower());
            //packet.put("right velocity", drivetrain.rightMotorOne.getCorrectedVelocity());
            //packet.put("right power", drivetrain.rightMotorOne.motor.getPower());
            dashboard.sendTelemetryPacket(packet);



        }
    }
}
