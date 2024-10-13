package org.firstinspires.ftc.teamcode.essentials;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;




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

    double vertical;
    double horizontal;
    double pivot;

    int drivetrainVelocityRate; // Converts power to ticks
    double drivetrainVelocityMulti = 1; //for slowing down robot
    public DcMotorEx leftEncoder, rightEncoder, frontEncoder;

    public ITDR1EssentialsTeleop(int drivetrainVelocityRate) {
        this.drivetrainVelocityRate = drivetrainVelocityRate;
    }

    public void initialize() {
        LF = hardwareMap.get(DcMotor.class, "LeftFront");
        LF.setDirection(DcMotorSimple.Direction.FORWARD);
        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LB = hardwareMap.get(DcMotor.class, "LeftBack");
        LB.setDirection(DcMotorSimple.Direction.FORWARD);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RF = hardwareMap.get(DcMotor.class, "RightFront");
        RF.setDirection(DcMotorSimple.Direction.REVERSE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RB = hardwareMap.get(DcMotor.class, "RightBack");
        RB.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



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

        ((DcMotorEx) LF).setVelocity(pivot + vertical + horizontal);
        ((DcMotorEx) LB).setVelocity(vertical - horizontal + pivot);
        ((DcMotorEx) RF).setVelocity(vertical - horizontal - pivot);
        ((DcMotorEx) RB).setVelocity(vertical + horizontal - pivot);

    }

    public void gamepad2Controls() {

     /*
        if (gamepad2.dpad_left){
            CS.setPosition(clawGrab);
            FBS.setPosition(FBSback);
            RS.setTargetPosition(1300);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);
            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else if (gamepad2.dpad_up){
            CS.setPosition(clawGrab);
            FBS.setPosition(0.15);
            RS.setTargetPosition(3250);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else if(gamepad2.dpad_right){
            CS.setPosition(clawGrab);
            FBS.setPosition(FBSback);
            RS.setTargetPosition(2530);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else if(gamepad2.dpad_down){
            CS.setPosition(clawGrab);
            FBS.setPosition(FBSfront);
            RS.setTargetPosition(0);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else{
            RS.setPower(0.05);
        }


        if (gamepad2.left_bumper && RS.getCurrentPosition() > 118){

            RS.setTargetPosition(RS.getCurrentPosition()-120);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);


            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}

        }
        if (gamepad2.right_bumper && RS.getCurrentPosition() < 3100){
            RS.setTargetPosition(RS.getCurrentPosition()+118);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
   */

    }


    @Override
    public void runOpMode(){}
    }
