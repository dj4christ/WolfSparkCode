package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Autonomous", group="Linear OpMode")

public class autonomous extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private CRServo continuousRotationServo = null;
    private Servo rotateHandServo = null;
    private DcMotor armMotor = null;
    private DcMotor extenderMotor;
    private Servo clawServo = null;

    double normalRotation = 0.55;
    double extendedRotation = 0.81;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        continuousRotationServo = hardwareMap.get(CRServo.class, "spinning_servo");
        rotateHandServo = hardwareMap.get(Servo.class, "rotate_servo");
        armMotor = hardwareMap.get(DcMotor.class, "arm_motor");
        extenderMotor = hardwareMap.get(DcMotor.class, "extender_motor");
        clawServo = hardwareMap.get(Servo.class, "hand_servo");
        
        
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setPower(0.5);
        extenderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extenderMotor.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            clawServo.setPosition(0);
            rotateHandServo.setPosition(normalRotation);

            armMotor.setPosition(600);

            leftDrive.setPower(0.3);
            rightDrive.setPower(0.3);

            sleep(2000);

            leftDrive.setPower(0);
            rightDrive.setPower(0);

            armMotor.setMode(RUN_WITHOUT_ENCODER);
            armMotor.setPower(0.1);

            leftDrive.setPower(-0.4);
            rightDrive.setPower(-0.4);

            extenderMotor.setPower(-0.5);

            sleep(3000);

            armMotor.setPower(-0.5);

            leftDrive.setPower(0);
            rightDrive.setPower(0);

            extenderMotor.setMode(RUN_TO_POSITION);
            extenderMotor.setPosition(0);

            sleep(3000);
            destroy();






            // leftPower = gamepad1.left_stick_y / 5;
            // rightPower = gamepad1.right_stick_y / 5;

            // extendorPower = gamepad2.left_stick_y / 20;
            // armPower = gamepad2.right_stick_y / 20;

            // extenderMotor.setPower(extendorPower);
            // armMotor.setPower(armPower);


            // leftDrive.setPower(leftPower);
            // rightDrive.setPower(rightPower);
            
            // // Claw code
            // if(gamepad2.dpad_up) {
            //     clawServo.setPosition(0);
            // } else if(gamepad2.dpad_down) {
            //     clawServo.setPosition(0.33);
            // }
            // if(gamepad2.dpad_left) {
            //     extenderMotor.setTargetPosition(1400);
            //     extenderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // } else if(gamepad2.dpad_right) {
            //     extenderMotor.setTargetPosition(0);
            //     extenderMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // }

            // if(gamepad2.left_trigger > 0.5) {
            //     rotateHandServo.setPosition(extendedRotation);
            // } else if(gamepad2.right_trigger > 0.5) {
            //     rotateHandServo.setPosition(normalRotation);
            // }

            // if(gamepad1.a) {
            //     armMotor.setTargetPosition(0);
            //     armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // } else if(gamepad1.b) {
            //     armMotor.setTargetPosition(500);
            //     armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // } else if(gamepad1.x) {
            //     armMotor.setTargetPosition(2000);
            //     armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // } else if(gamepad1.y) {
            //     armMotor.setTargetPosition(1800);
            //     armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // }
        }
    }
}
