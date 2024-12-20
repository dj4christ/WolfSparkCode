package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Teleop2p", group="Linear OpMode")

public class Teleop2p extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private Servo rotateHandServo = null;
    private DcMotor armMotor = null;
    private DcMotor extenderMotor;
    private CRServo clawServo = null;

    double leftPower;
    double rightPower;
    double armPower;
    double extendorPower;
    double clawPower;

    double normalRotation = 0.45;
    double extendedRotation = 0.81;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        rotateHandServo = hardwareMap.get(Servo.class, "rotate_servo");
        armMotor = hardwareMap.get(DcMotor.class, "arm_motor");
        extenderMotor = hardwareMap.get(DcMotor.class, "extender_motor");
        clawServo = hardwareMap.get(CRServo.class, "claw_servo");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        extenderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            extenderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            
            leftPower = gamepad1.left_stick_y / 5;
            rightPower = gamepad1.right_stick_y / 5;
            armPower = -gamepad2.left_stick_y / 3;
            extendorPower = gamepad2.right_stick_y / 3;
            clawPower = (gamepad2.right_trigger / 3) + (-gamepad2.left_trigger / 3);

            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            armMotor.setPower(armPower);
            extenderMotor.setPower(extendorPower);
            clawServo.setPower(clawPower);
            
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extenderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            
            // if(gamepad2.dpad_up) {
            //     clawServo.setPosition(0.5);
            // } else if(gamepad2.dpad_down) {
            //     clawServo.setPosition(0);
            // }

            if(gamepad2.dpad_left) {
                rotateHandServo.setPosition(extendedRotation);
            } else if(gamepad2.dpad_right) {
                rotateHandServo.setPosition(normalRotation);
            }

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
