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
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armMotor = null;
    private DcMotor extenderMotor;
    private Servo clawServo = null;
    private Servo handServo = null;
    double leftPower;
    double rightPower;
    double armPower;
    double extendorPower;
    double rotatePower;
    boolean closed = true;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        armMotor = hardwareMap.get(DcMotor.class, "arm_motor");
        extenderMotor = hardwareMap.get(DcMotor.class, "extender_motor");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        handServo = hardwareMap.get(Servo.class, "hand_servo");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extenderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();
        runtime.reset();
        clawServo.setPosition(0.01);
        handServo.setPosition(0.67);
        while (opModeIsActive()) {
            
            
            leftPower = gamepad1.left_stick_y / 5;
            rightPower = gamepad1.right_stick_y / 5;
            armPower = -gamepad2.left_stick_y / 2;
            extendorPower = -gamepad2.right_stick_y / 1.7;
            
            
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            armMotor.setPower(armPower);
            extenderMotor.setPower(extendorPower);
            
            
            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            extenderMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            
            
            
            if(gamepad2.left_trigger >= 0.5 || gamepad2.dpad_down) {
                clawServo.setPosition(0.27);
            } else if(gamepad2.right_trigger >= 0.5 || gamepad2.dpad_up) {
                clawServo.setPosition(0);
            } else if (gamepad2.left_bumper) {
                clawServo.setPosition(0.14);
            }
            
            if(gamepad2.left_stick_button) {
                handServo.setPosition(0.67);
            } else if(gamepad2.right_stick_button) {
                handServo.setPosition(0.95);
            }
            
            
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}