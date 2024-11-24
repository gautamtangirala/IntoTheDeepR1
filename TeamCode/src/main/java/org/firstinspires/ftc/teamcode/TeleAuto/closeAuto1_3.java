package org.firstinspires.ftc.teamcode.TeleAuto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.RRFiles.MecanumDrive;
import org.firstinspires.ftc.teamcode.essentials.ITDR1EssentialsAuto;

@Autonomous
public class closeAuto1_3 extends ITDR1EssentialsAuto {


    @Override

    public void runOpMode(){
        Pose2d startPose = new Pose2d(-35,-60,Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);


        //Basket Position XY
        double DropX = -55.5, DropY = -55.5, DropAngle = Math.toRadians(45);

        Vector2d dropVector = new Vector2d(DropX, DropY);
        Pose2d dropPose = new Pose2d(DropX, DropY, DropAngle);

        Action dropPreload = drive.actionBuilder(startPose)
                .strafeToLinearHeading(dropVector, DropAngle)
                .build();

        Action pickRightBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-43.5,-32),Math.toRadians(90))
                .build();


        Action pickMidBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-55,-32), Math.toRadians(90))
                
                .build();
        Action pickLeftBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-40,-21), Math.toRadians(180))
                .strafeToConstantHeading(new Vector2d(-57.5,-20))
                .build();
        initDrive();
        initSlides();
        closeClaw();
        clawTilt.setPosition(0);

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                dropPreload,
                moveSlideTop(),
                tiltSlide(0.8)
        ));

        sleep(200);
        tiltClawMid();
        conciseSlideTilt(1);
        sleep(200);
        openClaw();
        sleep(200);
        conciseSlideTilt(0.8);
        clawTilt.setPosition(0.1);

        Actions.runBlocking(new ParallelAction(
                pickRightBlock,
                moveSlideBottom(),
                tiltSlide(0)
        ));


        sleep(200);
        closeClaw();
        sleep(200);

        Action bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(0.8)
        ));


        sleep(200);
        tiltClawMid();
        conciseSlideTilt(1);
        sleep(200);
        openClaw();
        sleep(200);
        conciseSlideTilt(0.8);
        clawTilt.setPosition(0.1);


        Actions.runBlocking(new ParallelAction(
                pickMidBlock,
                moveSlideBottom(),
                tiltSlide(0)
        ));



        sleep(200);
        closeClaw();
        sleep(200);


        bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(0.8)
        ));

        sleep(200);
        tiltClawMid();
        conciseSlideTilt(1);
        sleep(200);
        openClaw();
        sleep(200);
        conciseSlideTilt(0.8);
        clawTilt.setPosition(0.1);

        Actions.runBlocking(new ParallelAction(
                pickLeftBlock,
                moveSlideBottom(),
                tiltSlide(0)
        ));

        sleep(200);
        closeClaw();
        sleep(200);


        bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop()
        ));



        conciseSlideTilt(1);
        sleep(1000);
        openClaw();
        sleep(200);

        bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(DropX + 4, DropY +  4), DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideBottom(),
                tiltSlide(0)
        ));


        sleep(1000);

    }

}
