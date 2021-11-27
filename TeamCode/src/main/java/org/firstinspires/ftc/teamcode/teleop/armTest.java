package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotComponents.Depositor;
import org.firstinspires.ftc.teamcode.RobotComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.RobotComponents.DuckMec;
import org.firstinspires.ftc.teamcode.RobotComponents.Intake;

@TeleOp(name = "Arm Test", group = "Linear Opmode")
public class armTest extends LinearOpMode {


    @Override
    public void runOpMode() {
        Depositor depositor = new Depositor(hardwareMap);
       Drivetrain drivetrain = new Drivetrain(hardwareMap);
        GamepadEx pad1 = new GamepadEx(gamepad1);
      GamepadEx pad2 = new GamepadEx(gamepad2);
        Intake intake = new Intake(hardwareMap);
       DuckMec duckMec = new DuckMec(hardwareMap);
        double armTarget = 0;
        boolean runMotor = true;

        boolean depositorDoorHasSwitched = false;
        boolean openDepositorDoor = false;


        waitForStart();

          depositor.v4bMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           depositor.v4bMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {

            if(gamepad1.a || gamepad1.b || gamepad1.y || gamepad1.x) runMotor = true;
            if(depositor.v4bMotor.getCurrentPosition()<340 && depositor.v4bMotor.getCurrentPosition()>-450 && gamepad1.a && runMotor){
                depositor.setArmLevelIn();
            }

            if(depositor.v4bMotor.getCurrentPosition()<340 && depositor.v4bMotor.getCurrentPosition()>-450 && gamepad1.b && runMotor){
                depositor.setArmLevelOne();
            }

            if(depositor.v4bMotor.getCurrentPosition()<340 && depositor.v4bMotor.getCurrentPosition()>-450 && gamepad1.x && runMotor){
                depositor.setArmLevelTwo();
            }

            if(depositor.v4bMotor.getCurrentPosition()<340 && depositor.v4bMotor.getCurrentPosition()>-450 && gamepad1.y && runMotor){
                depositor.setArmLevelThree();
            }

            if(depositor.v4bMotor.getCurrentPosition()>340 || depositor.v4bMotor.getCurrentPosition()<-450){
                depositor.v4bMotor.setPower(0);
            }
            else depositor.updateArmPosition();

            if (gamepad1.dpad_up){
                depositor.v4bMotor.setPower(0);

                runMotor = false;
            }

          //  depositor.v4bMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         //   depositor.v4bMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
/*
            if(pad1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER )||pad1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                depositor.v4bMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            } */

        //    if (gamepad1.dpad_up && armTarget < 499) armTarget += 1;
         //   if (gamepad1.dpad_down && armTarget > 1) armTarget -= 1;

            //depositor.v4bMotor.setPower(1);


           // depositor.setArmAngle(armTarget);



            telemetry.addData("arm position", depositor.v4bMotor.getCurrentPosition());
            telemetry.addData("arm target", armTarget);
            telemetry.addData("arm power", depositor.v4bMotor.getPower());
            telemetry.update();

        }
    }
}
