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
        double DropX = -51, DropY = -51, DropAngle = Math.toRadians(45);

        Vector2d dropVector = new Vector2d(DropX, DropY);
        Pose2d dropPose = new Pose2d(DropX, DropY, DropAngle);

        Action dropPreload = drive.actionBuilder(startPose)
                .strafeToLinearHeading(dropVector, DropAngle)
                .build();

        Action pickRightBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-44,-35),Math.toRadians(90))
                .build();


        Action pickMidBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-53,-35), Math.toRadians(90))
                
                .build();
        Action pickLeftBlock = drive.actionBuilder(dropPose)
                .strafeToLinearHeading(new Vector2d(-50,-35), Math.toRadians(135))
                
                .build();
        initDrive();
        initSlides();
        closeClaw();

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                dropPreload,
                moveSlideTop(),
                tiltSlide(1),
                twoBarIn()
        ));

       sleep(1000);
       openClaw();
       sleep(1000);

        Actions.runBlocking(new ParallelAction(
                pickRightBlock,
                moveSlideBottom(),
                tiltSlide(0)
        ));

        sleep(1000);
        closeClaw();
        sleep(1000);

        Action bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(1)
        ));

        sleep(1000);
        openClaw();
        sleep(1000);

        Actions.runBlocking(new ParallelAction(
                pickMidBlock,
                moveSlideBottom(),
                tiltSlide(0)
        ));


        sleep(1000);
        closeClaw();
        sleep(1000);


        bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(1)
        ));

        sleep(1000);
        openClaw();
        sleep(1000);
        Actions.runBlocking(new ParallelAction(
                pickLeftBlock,
                moveSlideBottom(),
                tiltSlide(0)
        ));

        sleep(1000);
        closeClaw();
        sleep(1000);


        bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(dropVector, DropAngle).build();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideTop(),
                tiltSlide(1)
        ));



        sleep(1000);
        openClaw();
        sleep(1000);


        Actions.runBlocking(new ParallelAction(
                moveSlideBottom(),
                tiltSlide(0)
        ));

        sleep(1000);
    }

}
