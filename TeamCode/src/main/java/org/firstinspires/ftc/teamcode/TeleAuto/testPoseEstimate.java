package org.firstinspires.ftc.teamcode.TeleAuto;


import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RRFiles.MecanumDrive;
import org.firstinspires.ftc.teamcode.essentials.ITDR1EssentialsAuto;

@TeleOp
public class testPoseEstimate extends ITDR1EssentialsAuto {


    @Override

    public void runOpMode(){
        Pose2d startPose = new Pose2d(-35,-60,Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);

        initDrive();


        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Current Pose", drive.pose);
            telemetry.addData("getPoseEstimate", drive.updatePoseEstimate());
            telemetry.update();
        }


    }

}
