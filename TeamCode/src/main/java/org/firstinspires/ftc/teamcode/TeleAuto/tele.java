package org.firstinspires.ftc.teamcode.TeleAuto;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.essentials.ITDR1EssentialsTeleop;


@TeleOp
public class tele extends ITDR1EssentialsTeleop {
    public tele(){
        super(1);
    }


    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        while (opModeIsActive()){
            gamepad1Controls();
            clawControls();
            slideControls();
            /*
            setLed();
            telemetry.addData("red", color.red());
            telemetry.addData("blue", color.blue());
            telemetry.addData("green", color.green());


            telemetry.update();
*/
        }
    }
}