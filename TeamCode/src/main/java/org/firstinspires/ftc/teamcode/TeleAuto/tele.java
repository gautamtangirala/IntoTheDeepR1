package org.firstinspires.ftc.teamcode.TeleAuto;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.essentials.ITDR1EssentialsTeleop;


@TeleOp
public class tele extends ITDR1EssentialsTeleop {
    public tele(){
        super(2000);
    }


    @Override
    public void runOpMode(){
        initLed();
        waitForStart();
        while (opModeIsActive()){
            setLed();
            telemetry.addData("red", color.red());
            telemetry.addData("blue", color.blue());
            telemetry.addData("green", color.green());


            telemetry.update();

        }
    }
}