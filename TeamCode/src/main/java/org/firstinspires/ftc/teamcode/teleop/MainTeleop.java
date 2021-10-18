package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.RobotComponents.Depositor;
import org.firstinspires.ftc.teamcode.RobotComponents.Drivetrain;

@TeleOp(name = "TeleOP", group = "Linear Opmode")
public class MainTeleop extends LinearOpMode {


    @Override
    public void runOpMode(){
        Depositor depositor = new Depositor();
        Drivetrain drivetrain = new Drivetrain();
        GamepadEx pad1 = new GamepadEx(gamepad1);
        GamepadEx pad2 = new GamepadEx(gamepad2);

        waitForStart();
        while (opModeIsActive()){
            drivetrain.setDrivePowers(pad1.getLeftY(), pad1.getRightY());

            depositor.setCapAngleOffset(pad2.isDown(GamepadKeys.Button.DPAD_UP), pad2.isDown(GamepadKeys.Button.DPAD_DOWN));

            depositor.updateArmPosition();






        }
    }
}
