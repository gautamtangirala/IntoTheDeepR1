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
public class redAutoClose extends ITDR1EssentialsAuto {


    @Override

    public void runOpMode(){
        Pose2d startPose = new Pose2d(-35,-60,Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, startPose);
        Pose2d currPose = drive.pose;

        //Basket Position XY
        double DropX = -51, DropY = -51, DropAngle = Math.toRadians(45);

        Vector2d dropVector = new Vector2d(DropX, DropY);
        Pose2d dropPose = new Pose2d(DropX, DropY, DropAngle);

        Action dropPreload = drive.actionBuilder(startPose)
                .strafeToLinearHeading(dropVector, DropAngle)
                .build();
        Action bucketDropOff = drive.actionBuilder(currPose)
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

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                dropPreload,
                moveSlideMid(),
                closeClaw()
        ));
        currPose = drive.pose;
        openClaw();

        Actions.runBlocking(new ParallelAction(
                pickRightBlock,
                moveSlideBottom()
        ));
        currPose = drive.pose;
        openClaw();

        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideMid()
        ));
        currPose = drive.pose;
        openClaw();

        Actions.runBlocking(new ParallelAction(
                pickMidBlock,
                moveSlideBottom()
        ));

        currPose = drive.pose;
        openClaw();

        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideMid()
        ));
        currPose = drive.pose;
        openClaw();
        Actions.runBlocking(new ParallelAction(
                pickLeftBlock,
                moveSlideBottom()
        ));
        currPose = drive.pose;
        openClaw();
        Actions.runBlocking(new ParallelAction(
                bucketDropOff,
                moveSlideBottom()
        ));

        currPose = drive.pose;
        openClaw();

    }

}
