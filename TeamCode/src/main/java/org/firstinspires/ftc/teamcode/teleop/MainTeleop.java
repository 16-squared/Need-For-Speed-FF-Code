package org.firstinspires.ftc.teamcode.teleop;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotComponents.Depositor;
import org.firstinspires.ftc.teamcode.RobotComponents.Drivetrain;
import org.firstinspires.ftc.teamcode.RobotComponents.DuckMec;
import org.firstinspires.ftc.teamcode.RobotComponents.Intake;

@TeleOp(name = "TeleOP", group = "Linear Opmode")
public class MainTeleop extends LinearOpMode {


    @Override
    public void runOpMode(){
        Depositor depositor = new Depositor();
        Drivetrain drivetrain = new Drivetrain();
        GamepadEx pad1 = new GamepadEx(gamepad1);
        GamepadEx pad2 = new GamepadEx(gamepad2);
        Intake intake = new Intake();
        DuckMec duckMec = new DuckMec();

        waitForStart();
        while (opModeIsActive()){

        //controller one

            //dt powers
            drivetrain.setDrivePowers(pad1.getLeftY(), pad1.getRightY());

            //intake controls
                //toggle intake forwards and off (press a)
            if(pad1.wasJustPressed(GamepadKeys.Button.A)) intake.toggleIntakeForwards();

                //intake reversal (hold x)
            if(pad1.isDown(GamepadKeys.Button.X)) intake.reverseIntake();
            if(pad1.wasJustReleased(GamepadKeys.Button.X)) intake.returnToPreviousIntakeState();

            //duck mec
                //toggle duck mec (press RB)
            if(pad1.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) duckMec.toggleDuckMec(false);






        //controller two

            //arm positions
                    //Bring arm in (press a)
            if(pad2.wasJustPressed(GamepadKeys.Button.A)){
                depositor.setArmLevelIn();
            }
                    //Bring arm to cap (press LB)
            if(pad2.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)){
                depositor.setArmLevelCapping();
            }
                    //Adjust capping height (dpad up and down)
            depositor.setCapAngleOffset(pad2.isDown(GamepadKeys.Button.DPAD_UP), pad2.isDown(GamepadKeys.Button.DPAD_DOWN));

           if(depositor.armLevel != Depositor.ArmLevel.ARMLEVEL_CAP) {

                    //Bring arm to low goal (press x)
            if(pad2.wasJustPressed(GamepadKeys.Button.X)){
                depositor.setArmLevelOne();
            }
                    //Bring arm to mid goal (press y)
            if(pad2.wasJustPressed(GamepadKeys.Button.Y)){
                depositor.setArmLevelTwo();
            }
                    //Bring arm to high goal (press b)
               if(pad2.wasJustPressed(GamepadKeys.Button.B)) {
                   depositor.setArmLevelThree();
               }
           }

            depositor.updateArmPosition();


                    //drop elements (press RB)
            if(pad2.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)){
                depositor.depositorServoToggle();
            }




        }
    }
}
