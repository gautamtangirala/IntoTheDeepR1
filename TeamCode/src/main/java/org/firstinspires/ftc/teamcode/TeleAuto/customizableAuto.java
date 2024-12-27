package org.firstinspires.ftc.teamcode.TeleAuto;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.RRFiles.MecanumDrive;
import org.firstinspires.ftc.teamcode.essentials.ITDR1EssentialsAuto;


@Disabled
@Autonomous
public class customizableAuto extends ITDR1EssentialsAuto {

    private boolean parkAfterDrop = false;
    private boolean pickUpAnotherBlock = false;
    private boolean doNothing = true;
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

        // Initialization stage
        telemetry.addLine("Press A to toggle Park After Drop");
        telemetry.addLine("Press B to toggle Pick Up Another Block");
        telemetry.addLine("Press X to toggle Do Nothing");
        telemetry.addLine("Press START to finalize choices.");
        telemetry.update();

        // Wait for user input during initialization
        while (!isStarted() && !isStopRequested()) {
            if (gamepad1.a) {
                parkAfterDrop = true;
                pickUpAnotherBlock = false;
                doNothing = false;
                sleep(200); // Debounce input
            }
            if (gamepad1.b) {
                parkAfterDrop = false;
                pickUpAnotherBlock = true;
                doNothing = false;
                sleep(200); // Debounce input
            }
            if (gamepad1.x) {
                parkAfterDrop = false;
                pickUpAnotherBlock = false;
                doNothing = true;
                sleep(200); // Debounce input
            }

            // Update telemetry
            telemetry.addData("Park After Drop", parkAfterDrop ? "Yes" : "No");
            telemetry.addData("Pick Up Another Block", pickUpAnotherBlock ? "Yes" : "No");
            telemetry.addData("Do Nothing", doNothing ? "Yes" : "No");
            telemetry.update();
        }
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



        if(doNothing) {
            bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(DropX + 4, DropY + 4), DropAngle).build();
            Actions.runBlocking(new ParallelAction(
                    bucketDropOff,
                    moveSlideBottom(),
                    tiltSlide(0)
            ));
        }
        else if(parkAfterDrop){
            Action parking = drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(0,-30), Math.toRadians(90)).strafeToConstantHeading(new Vector2d(50,-30)).strafeToConstantHeading(new Vector2d(50,-53)).build();
            Actions.runBlocking(new ParallelAction(
                    parking,
                    moveSlideBottom(),
                    tiltSlide(0)
            ));
        }
        else if(pickUpAnotherBlock){
            bucketDropOff = drive.actionBuilder(drive.pose).strafeToLinearHeading(new Vector2d(DropX + 4, DropY + 4), DropAngle).build();
            Actions.runBlocking(new ParallelAction(
                    bucketDropOff,
                    moveSlideBottom(),
                    tiltSlide(0)
            ));
        }

        sleep(1000);

    }

}
