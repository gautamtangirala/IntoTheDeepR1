package com.example.meepmeeptesting;


import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;



public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 11.69)
                .build();

        Pose2d startPose = new Pose2d(9,-60,Math.toRadians(270));
        
        double speciY = -33;
        myBot.runAction(myBot.getDrive().actionBuilder(startPose)
                        .strafeToLinearHeading(new Vector2d(9,speciY), Math.toRadians(270))
                        .waitSeconds(2)
                        .splineToLinearHeading(new Pose2d(35, -30, Math.toRadians(90)), Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(46,-10,Math.toRadians(90)),Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(46,-50,Math.toRadians(90)),Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(48,-10,Math.toRadians(90)),Math.toRadians(270))

                        .splineToLinearHeading(new Pose2d(58,-10,Math.toRadians(90)),Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(59,-50,Math.toRadians(90)),Math.toRadians(270))
//                        .strafeToLinearHeading(new Vector2d(57,-10),Math.toRadians(90))
//                        .strafeToLinearHeading(new Vector2d(58,-50),Math.toRadians(90))
//                        .strafeToLinearHeading(new Vector2d(57,-10),Math.toRadians(90))
//                        .strafeToLinearHeading(new Vector2d(66,-10),Math.toRadians(90))
//                        .strafeToLinearHeading(new Vector2d(66,-50),Math.toRadians(90))
                        .strafeToLinearHeading(new Vector2d(35,-57), Math.toRadians(90))
                         .waitSeconds(1)
                        .strafeToLinearHeading(new Vector2d(2,speciY), Math.toRadians(270))
                        .waitSeconds(2)
                        .splineToLinearHeading(new Pose2d(35,-57,Math.toRadians(90)),Math.toRadians(270))
                        .waitSeconds(1)
                        .strafeToLinearHeading(new Vector2d(4,speciY), Math.toRadians(270))
                        .waitSeconds(2)

                        .strafeToLinearHeading(new Vector2d(35,-57), Math.toRadians(90))
                       // .splineToLinearHeading(new Pose2d(35,-57,Math.toRadians(90)),Math.toRadians(270))
                        .waitSeconds(1)
                        .strafeToLinearHeading(new Vector2d(6,speciY), Math.toRadians(270))
                        .waitSeconds(2)
//                        .splineToLinearHeading(new Pose2d(35,-57,Math.toRadians(90)),Math.toRadians(270))
//                        .waitSeconds(1)
//                        .strafeToLinearHeading(new Vector2d(12,speciY), Math.toRadians(270))
//                        .waitSeconds(2)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}