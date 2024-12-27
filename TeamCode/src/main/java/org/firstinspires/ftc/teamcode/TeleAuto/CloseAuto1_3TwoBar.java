package org.firstinspires.ftc.teamcode.TeleAuto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RRFiles.MecanumDrive;
import org.firstinspires.ftc.teamcode.essentials.ITDR1EssentialsAuto;

@Autonomous(preselectTeleOp = "tele")
public class CloseAuto1_3TwoBar extends ITDR1EssentialsAuto {


    @Override

    public void runOpMode(){
        Pose2d startPose = new Pose2d(-35,-60,Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);


        //Vars
        double reachOutSlideTiltPos = 0.1275;
        //Basket Position XY
        double DropX = -56, DropY = -56, DropAngle = Math.toRadians(45);

        Vector2d dropVector = new Vector2d(DropX, DropY);
        Pose2d dropPose = new Pose2d(DropX, DropY, DropAngle);

        Action dropPreload = drive.actionBuilder(startPose)
                .strafeToLinearHeading(dropVector, DropAngle)
                .build();

        Action pickRightBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-45.5,-47.5),Math.toRadians(90))
                .build();


        Action pickMidBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-57.5,-47.5), Math.toRadians(90))

                .build();
        Action pickLeftBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-37.75,-21), Math.toRadians(180))
                .build();
        initDrive();
        initSlides();
        closeClaw();
     //   clawTilt.setPosition(0);
        twoBarIn();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                dropPreload,
                moveSlideTop(),
                tiltSlide(0.5)
        ));

        sleep(200);
        tiltClawMid();
        conciseSlideTilt(0.85);
        sleep(1000);
        openClaw();
        sleep(200);
        conciseSlideTilt(0.5);
        sleep(200);


        Actions.runBlocking(new ParallelAction(
                pickRightBlock,
                moveSlideBottom(),
                tiltSlide(reachOutSlideTiltPos)
        ));


        sleep(200);
        openClaw();
        tiltClawDown();
        sleep(200);
        twoBarOut();
        sleep(1000);
        closeClaw();
        sleep(200);
        twoBarIn();
        sleep(200);


        Action bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(0.8)
        ));


        sleep(200);
        tiltClawMid();
        conciseSlideTilt(0.825);
        sleep(200);
        openClaw();
        sleep(200);
        conciseSlideTilt(0.5);
        sleep(200);

        Actions.runBlocking(new ParallelAction(
                pickMidBlock,
                moveSlideBottom(),
                tiltSlide(reachOutSlideTiltPos)
        ));



        sleep(200);
        openClaw();
        tiltClawDown();
        sleep(200);
        twoBarOut();
        sleep(1000);
        closeClaw();
        sleep(200);
        twoBarIn();
        sleep(200);



        bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(0.8)
        ));

        sleep(200);
        tiltClawMid();
        conciseSlideTilt(0.85);
        sleep(200);
        openClaw();
        sleep(200);
        conciseSlideTilt(0.5);
        sleep(200);


        Actions.runBlocking(new ParallelAction(
                pickLeftBlock,
                moveSlideBottom(),
                tiltSlide(reachOutSlideTiltPos)
        ));

        sleep(200);
        openClaw();
        tiltClawDown();
        sleep(1000);
        twoBarOut();
        sleep(1000);
        clawGrab.setPosition(0.85);
        sleep(1000);
        twoBarIn();
        sleep(200);


        

        bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(reachOutSlideTiltPos-0.025)
        ));


        sleep(200);
        tiltClawMid();
        conciseSlideTilt(0.85);
        sleep(1000);
        openClaw();
        sleep(200);
        conciseSlideTilt(0.5);
        sleep(200);

        Action end = drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(DropX + 10, 0), Math.toRadians(0)).strafeToLinearHeading(new Vector2d(-20, 0), Math.toRadians(0)).build();
        Actions.runBlocking(new ParallelAction(
                end,
                moveSlideBottom(),
                tiltSlide(reachOutSlideTiltPos)
        ));


        sleep(1000);

        

    }

}
