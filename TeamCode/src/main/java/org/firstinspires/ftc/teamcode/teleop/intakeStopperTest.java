package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotComponents.Intake;

@TeleOp(name = "Stopper Test", group = "Linear Opmode")
public class intakeStopperTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Intake intake = new Intake(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.x) intake.runIntake();
            if (gamepad1.y) intake.stopIntake();


            if (gamepad1.a) intake.intakeStopperIn();
            if(gamepad1.b) intake.intakeStopperOut();

            intake.updateIntake();

            telemetry.addData("state", intake.intakeState);
            telemetry.addData("running forwards", intake.intakeRunningForwards);
            telemetry.update();
        }
    }
}