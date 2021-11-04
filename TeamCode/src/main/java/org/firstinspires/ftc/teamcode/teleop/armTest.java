package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.RobotComponents.Depositor;
import org.firstinspires.ftc.teamcode.RobotComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.RobotComponents.DuckMec;
import org.firstinspires.ftc.teamcode.RobotComponents.Intake;

@TeleOp(name="Arm Test", group = "Linear Opmode")
public class armTest extends LinearOpMode {


    @Override
    public  void runOpMode() {
        Depositor depositor = new Depositor(hardwareMap);
        Drivetrain drivetrain = new Drivetrain(hardwareMap);
        GamepadEx pad1 = new GamepadEx(gamepad1);
        GamepadEx pad2 = new GamepadEx(gamepad2);
        Intake intake = new Intake(hardwareMap);
        DuckMec duckMec = new DuckMec(hardwareMap);
        double armTarget=0;

        boolean depositorDoorHasSwitched = false;
        boolean openDepositorDoor = false;

        boolean firstloop = true;
        waitForStart();

        while (opModeIsActive()){

            if(firstloop){
                //depositor.v4bMotor.resetEncoder();
                depositor.v4bMotor.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                firstloop = false;
            }

            if(pad1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER )||pad1.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                depositor.v4bMotor.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }

            if(gamepad1.dpad_up && armTarget<499) armTarget+=1;
            if (gamepad1.dpad_down && armTarget>1) armTarget-=1;

          depositor.setArmAngle(armTarget);
         if(gamepad1.a){
             intake.runIntake();
         }


            telemetry.addData("arm position", depositor.v4bMotor.getCurrentPosition());
            telemetry.addData("arm target", armTarget);
            telemetry.addData("arm power", depositor.v4bMotor.motor.getPower());
            telemetry.update();

        }
    }
}
