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
    public DcMotorEx hang;
    public Servo clawGrab;
    public Servo clawTilt;
    public Servo leftTilt;
    public Servo rightTilt;
    public Servo twoBar;
    double vertical;
    double horizontal;
    double pivot;

    int drivetrainVelocityRate; // Converts power to ticks
    double slowedDownMulti = 0.8;
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

        hang = hardwareMap.get(DcMotorEx.class, "hang");
        hang.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        hang.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


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

        boolean slowMode = false;
        double SlowedDownMulti = 0.8;
        if(gamepad2.y) {

            slowMode = true;
        }
        else if (gamepad2.a){
            slowMode = false;
        }
        double turnMulti = 0.6;
        if (slides.getCurrentPosition() > 200) {
            SlowedDownMulti = 0.6;
        }
        if(slowMode){
            turnMulti = 0.4;
        }
        else{
            turnMulti = 0.6;
        }
        vertical = gamepad1.right_stick_y  * SlowedDownMulti;
        horizontal = -gamepad1.right_stick_x  * SlowedDownMulti;
        pivot = -gamepad1.left_stick_x  * turnMulti;

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


        if(gamepad1.left_bumper){
            hang.setPower(1);
        }
        else if(gamepad1.right_bumper){
            hang.setPower(-1);
        }
        else{
            hang.setPower(0);
        }

        if(hang.getCurrentPosition() < -20000){
            conciseSlideTilt(0.8);
            closeClaw();
            twoBarIn();
        }

        telemetry.addData("hang",hang.getCurrentPosition());


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
            tiltClawMid();
            conciseSlideTilt(0.8);

        }
        else if(gamepad2.y && slides.getCurrentPosition()<300){
            twoBarOut();
            openClaw();
            tiltClawUp();
        }
        else if(gamepad2.right_bumper){
            openClaw();
            tiltClawDown();

        }
        else if(gamepad2.left_bumper){
            tiltClawUp();

        }
    }

    public void slideControls() {


        if (gamepad2.dpad_left){
            closeClaw();
            twoBarIn();
            conciseSlideTilt(0.825);
            tiltClawMid();
            slides.setTargetPosition(200);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slides.setPower(1);

            while (slides.isBusy()){gamepad1Controls(); telemetry.addData("Slide Height",slides.getCurrentPosition()); telemetry.update(); clawControls(); }

        }
        else if (gamepad2.dpad_up){
            closeClaw();
            twoBar.setPosition(0.1);
            conciseSlideTilt(0.825);
            tiltClawMid();
            slides.setTargetPosition(645);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slides.setPower(1);

            while (slides.isBusy()){gamepad1Controls(); telemetry.addData("Slide Height",slides.getCurrentPosition()); telemetry.update(); clawControls(); }
        }
        else if(gamepad2.dpad_right){
        }
        else if(gamepad2.dpad_down){
            conciseSlideTilt(0.13); //change this
            closeClaw();

            twoBarIn();
            tiltClawUp();


            slides.setTargetPosition(10);
            slides.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slides.setPower(1);

            while (slides.isBusy()){gamepad1Controls(); telemetry.addData("Slide Height",slides.getCurrentPosition()); telemetry.update(); clawControls(); }
        }
        else{
            slides.setPower(0);
        }



   

    }


    // smaller functions

    public void closeClaw(){

        clawGrab.setPosition(1);
    }

    public void openClaw(){
         clawGrab.setPosition(0);
    }

    public void twoBarOut(){
        twoBar.setPosition(0.55);
    }

    public void twoBarIn(){
        twoBar.setPosition(0);
    }

    public void tiltClawDown(){
        clawTilt.setPosition(0.7);
    }
    public void tiltClawMid(){
        clawTilt.setPosition(0.3);
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
