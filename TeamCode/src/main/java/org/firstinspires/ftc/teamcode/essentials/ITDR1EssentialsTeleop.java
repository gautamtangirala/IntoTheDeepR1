package org.firstinspires.ftc.teamcode.essentials;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RRFiles.MecanumDrive;




/*
* This is meant to be a library!
* Do not copy and edit the code
* Import it in a new java class!
*
* DO NOT MODIFY
*/

public class ITDR1EssentialsTeleop extends LinearOpMode {
    public DcMotor LF; // LeftFront Motor
    public DcMotor LB; // LeftBack Motor
    public DcMotor RF; // RightFront Motor
    public DcMotor RB; // RightBack Motor

    public RevBlinkinLedDriver led;
    public ColorSensor color;
    
    public DcMotorEx slides;
    public Servo clawGrab;
    public Servo clawTilt;
    public Servo leftTilt;
    public Servo rightTilt;
    public Servo twoBar;
    double vertical;
    double horizontal;
    double pivot;

    int drivetrainVelocityRate; // Converts power to ticks
    double drivetrainVelocityMulti = 1; //for slowing down robot
    public DcMotorEx leftEncoder, rightEncoder, frontEncoder;
    //
    public ITDR1EssentialsTeleop(int drivetrainVelocityRate) {
        this.drivetrainVelocityRate = drivetrainVelocityRate;
    }

    public void initialize() {
        LF = hardwareMap.get(DcMotor.class, "leftFront");
        LF.setDirection(DcMotorSimple.Direction.FORWARD);
        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LB = hardwareMap.get(DcMotor.class, "leftBack");
        LB.setDirection(DcMotorSimple.Direction.FORWARD);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RF = hardwareMap.get(DcMotor.class, "rightFront");
        RF.setDirection(DcMotorSimple.Direction.REVERSE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RB = hardwareMap.get(DcMotor.class, "rightBack");
        RB.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        slides = hardwareMap.get(DcMotorEx.class, "slideMotor");
        slides.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slides.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        clawGrab = hardwareMap.get(Servo.class, "clawGrab");
        twoBar = hardwareMap.get(Servo.class, "twoBar");
        clawTilt = hardwareMap.get(Servo.class, "clawTilt");
        leftTilt = hardwareMap.get(Servo.class,"leftTilt");
        rightTilt = hardwareMap.get(Servo.class,"rightTilt");
        rightTilt.setDirection(Servo.Direction.REVERSE);

    }


    
    
    public void initLed(){
        color = hardwareMap.get(ColorSensor.class, "color");

        led = hardwareMap.get(RevBlinkinLedDriver.class, "led");
    }

    public void setLed(){


        if(color.green() > 500){
            led.setPattern(RevBlinkinLedDriver.BlinkinPattern.GOLD);
        }
        else if(color.red() > 500){
            led.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        }
         else if(color.blue() > 500) {
            led.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
        }

        else{
            led.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
        }


    }


    public void gamepad1Controls() {
        // Initiating Holonomic Drive

        double SlowedDownMulti = 0.6;
        if (gamepad1.left_bumper){
            drivetrainVelocityMulti = SlowedDownMulti;
        }
        else if (gamepad1.right_bumper){
            drivetrainVelocityMulti = 1;
        }
        vertical = gamepad1.right_stick_y * drivetrainVelocityRate * drivetrainVelocityMulti;
        horizontal = -gamepad1.right_stick_x * drivetrainVelocityRate * drivetrainVelocityMulti;
        pivot = -gamepad1.left_stick_x * drivetrainVelocityRate * drivetrainVelocityMulti;

        LF.setPower(pivot + vertical + horizontal);
        LB.setPower(pivot + vertical - horizontal );
        RF.setPower(0 - pivot + vertical - horizontal);
        RB.setPower(0 - pivot + vertical + horizontal);
/*
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ),
                -gamepad1.right_stick_x
        ));
*/

    }

    public void clawControls(){
        if(gamepad2.x){
            closeClaw();
        }
        else if(gamepad2.b){
            openClaw();
        }
        else if(gamepad2.a){
            twoBarIn();

        }
        else if(gamepad2.y){
            twoBarOut();
        }
    }

    public void slideControls() {


        if (gamepad2.dpad_left){
            conciseSlideTilt(0);
            twoBarIn();
        }
        else if (gamepad2.dpad_up){
            closeClaw();
            twoBarIn();
            conciseSlideTilt(1);
            tiltClawMid();
            slides.setTargetPosition(680);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slides.setPower(1);

            while (slides.isBusy()){gamepad1Controls(); telemetry.addData("Slide Height",slides.getCurrentPosition()); telemetry.update(); clawControls(); }
        }
        else if(gamepad2.dpad_right){
           conciseSlideTilt(1);
           twoBarIn();
        }
        else if(gamepad2.dpad_down){
            closeClaw();
            twoBarIn();
            conciseSlideTilt(0.25);
            tiltClawDown();

            slides.setTargetPosition(0);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slides.setPower(1);

            while (slides.isBusy()){gamepad1Controls(); telemetry.addData("Slide Height",slides.getCurrentPosition()); telemetry.update(); clawControls(); }
        }
        else{
            slides.setPower(0.01);
        }


        if (gamepad2.left_bumper && slides.getCurrentPosition() > 105){

            slides.setTargetPosition(slides.getCurrentPosition()-100);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slides.setPower(1);


            while (slides.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",slides.getCurrentPosition()); telemetry.update(); }

        }
        if (gamepad2.right_bumper && slides.getCurrentPosition() < 700){
            slides.setTargetPosition(slides.getCurrentPosition()+100);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slides.setPower(1);

            while (slides.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",slides.getCurrentPosition()); telemetry.update(); }
        }
   

    }


    // smaller functions

    public void closeClaw(){

        clawGrab.setPosition(0.75);
    }

    public void openClaw(){
         clawGrab.setPosition(0);
    }

    public void twoBarOut(){
        twoBar.setPosition(1);
    }

    public void twoBarIn(){
        twoBar.setPosition(0);
    }

    public void tiltClawDown(){
        clawTilt.setPosition(0);
    }
    public void tiltClawMid(){
        clawTilt.setPosition(0.5);
    }
    public void tiltClawUp(){
        clawTilt.setPosition(0);
    }

    public void conciseSlideTilt(double pos){
        leftTilt.setPosition(pos);
        rightTilt.setPosition(pos);
    }
    @Override
    public void runOpMode(){}
    }
