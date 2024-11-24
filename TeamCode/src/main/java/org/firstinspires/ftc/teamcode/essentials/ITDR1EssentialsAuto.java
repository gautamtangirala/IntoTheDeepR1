package org.firstinspires.ftc.teamcode.essentials;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorColor;
import org.openftc.easyopencv.OpenCvWebcam;

/*
 * This is meant to be a library!
 * Do not copy and edit the code
 * Import it in a new java class!
 *
 * DO NOT MODIFY
 */

public class ITDR1EssentialsAuto extends LinearOpMode {
    public DcMotorEx LF; // LeftFront Motor
    public DcMotorEx LB; // LeftBack Motor
    public DcMotorEx RF; // RightFront Motor
    public DcMotorEx RB; // RightBack Motor

    public SensorColor color;
    public DcMotorEx slides;
    public Servo clawGrab;
    public Servo clawTilt;
    public Servo leftTilt;
    public Servo rightTilt;
    public Servo twoBar;

    public int tickRotation;
    public int tickAcceptance;

    public OpenCvWebcam webcam;




    public void initDrive(){
        LF = hardwareMap.get(DcMotorEx.class, "leftFront");
        LF.setDirection(DcMotorSimple.Direction.REVERSE);
        LF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) LF).setTargetPositionTolerance(tickAcceptance);

        LB = hardwareMap.get(DcMotorEx.class, "leftBack");
        LB.setDirection(DcMotorSimple.Direction.REVERSE);
        LB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) LB).setTargetPositionTolerance(tickAcceptance);

        RF = hardwareMap.get(DcMotorEx.class, "rightFront");
        RF.setDirection(DcMotorSimple.Direction.FORWARD);
        RF.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) RF).setTargetPositionTolerance(tickAcceptance);

        RB = hardwareMap.get(DcMotorEx.class, "rightBack");
        RB.setDirection(DcMotorSimple.Direction.FORWARD);
        RB.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) RB).setTargetPositionTolerance(tickAcceptance);

    }

    public void initSensors(){
        color = hardwareMap.get(SensorColor.class, "color");
    }

    public void initSlides(){
        slides = hardwareMap.get(DcMotorEx.class, "slideMotor");
        slides.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slides.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        
        clawGrab = hardwareMap.get(Servo.class, "clawGrab");
        clawTilt = hardwareMap.get(Servo.class, "clawTilt");
        leftTilt = hardwareMap.get(Servo.class,"leftTilt");
        rightTilt = hardwareMap.get(Servo.class,"rightTilt");
        rightTilt.setDirection(Servo.Direction.REVERSE);
        twoBar = hardwareMap.get(Servo.class, "twoBar");

    }

    
    public Action closeClawAction(){

        return new InstantAction(() -> { clawGrab.setPosition(0.5); } );
    }

    public Action openClawAction(){
        return new InstantAction(() -> { clawGrab.setPosition(0); } );
    }

    public void openClaw(){
        clawGrab.setPosition(0);
    }

    public void closeClaw(){
        clawGrab.setPosition(0.5);
    }

    public Action tiltClawMidAction(){
        return new InstantAction(() -> { clawTilt.setPosition(0.8); } );
    }

    public void tiltClawMid(){
        clawTilt.setPosition(0.2);
    }



    public void conciseSlideTilt(double pos){
        leftTilt.setPosition(pos);
        rightTilt.setPosition(pos);
    }

    public Action tiltSlide(double pos){

        //Down 0
        //Up 1

        return new InstantAction(() -> {conciseSlideTilt(pos);});
    }

    public Action twoBarIn(){
        return new InstantAction(() -> {twoBar.setPosition(0);});
    }

    public Action twoBarOut(){
        return new InstantAction(() -> {twoBar.setPosition(1);});
    }



    public Action moveSlideTop(){
        return new InstantAction(() -> {slides.setTargetPosition(800);
        slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        slides.setPower(1);  });
    }

    public Action moveSlideMid(){
        return new InstantAction(() -> {slides.setTargetPosition(250);
            slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            slides.setPower(1); });
    }

    public Action moveSlideBottom(){
        return new InstantAction(() -> {
            slides.setTargetPosition(10);
            slides.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            slides.setPower(1); });
    }


/*
    public void runCamera(){
        final String webcamName = "Webcam 1";
        final int length = 320;
        final int width = 240;
        final int squareSize = 30;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);

        detector = new PPR1SleeveDetector(telemetry, length, width, squareSize, 30, -56);
        webcam.setPipeline(detector);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(length, width, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

    }
    public void stopCamera(){webcam.stopStreaming();}
*/





    @Override
    public void runOpMode(){
    }
}

