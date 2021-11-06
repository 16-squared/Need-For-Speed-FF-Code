package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotComponents.DrivetrainNoVelo;
import org.firstinspires.ftc.teamcode.RobotComponents.PowerLUT;

@Config
@TeleOp(name = "DriveTrain Power Test", group = "Linear Opmode")
public class DTPowerTest extends LinearOpMode {

    public static double power = .1, percentCorrection = 10;
    double percentoffset;
    @Override
    public void runOpMode() {
        DrivetrainNoVelo drivetrain = new DrivetrainNoVelo(hardwareMap);
        GamepadEx pad1 = new GamepadEx(gamepad1);

        FtcDashboard dashboard = FtcDashboard.getInstance();


        waitForStart();

        while (opModeIsActive()){

            if(pad1.wasJustPressed(GamepadKeys.Button.DPAD_UP)) percentCorrection+=1;
            if(pad1.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) percentCorrection-=1;

            percentoffset = (percentCorrection+100)/100;




            drivetrain.setDrivePowerAccelerationCurve(pad1.getLeftY(), pad1.getRightX(), drivetrain.leftMotorOne.motor.getPower(), drivetrain.rightMotorOne.motor.getPower());

            if (gamepad1.a){
                drivetrain.rightMotorOne.set(power);
                drivetrain.rightMotorTwo.set(power);
                drivetrain.rightMotorThree.set(power);
                drivetrain.leftMotorOne.set(power*percentoffset);
                drivetrain.leftMotorTwo.set(power*percentoffset);
                drivetrain.leftMotorThree.set(power*percentoffset);
            }
        }




        telemetry.addData("motor power", power);
        telemetry.addData("correction factor", percentCorrection);
        telemetry.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("left velocity", drivetrain.leftMotorOne.getCorrectedVelocity());
        packet.put("right velocity", drivetrain.rightMotorOne.getCorrectedVelocity());
        packet.put("velocity difference", drivetrain.leftMotorOne.getCorrectedVelocity()-drivetrain.rightMotorOne.getCorrectedVelocity());

        dashboard.sendTelemetryPacket(packet);




    }
}
