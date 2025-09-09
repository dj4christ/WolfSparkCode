package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
 * This OpMode contains code for driving a 4-motor Omni-Directional (or Holonomic) robot.
 * This code will work with either a Mecanum-Drive or an X-Drive train.
 * Note that a Mecanum drive must display an X roller-pattern when viewed from above.
 * Also note that it is critical to set the correct rotation direction for each motor.
 *
 * Holonomic drives provide the ability for the robot to move in three axes (directions) simultaneously.
 * Each motion axis is controlled by one Joystick axis.
 */

@TeleOp(name = "Mecanum Drive OpMode", group = "Linear OpMode")
public class MecanumWheelsTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Initialize the hardware
        // The strings here must correspond to the names of the motors in your robot config
        DcMotor leftFrontDrive = hardwareMap.dcMotor.get("left_front");
        DcMotor leftBackDrive = hardwareMap.dcMotor.get("left_back");
        DcMotor rightFrontDrive = hardwareMap.dcMotor.get("right_front");
        DcMotor rightBackDrive = hardwareMap.dcMotor.get("right_back");
        
        DcMotor armMotor = hardwareMap.get(DcMotor.class, "arm_motor");
        DcMotor extenderMotor = hardwareMap.get(DcMotor.class, "extension_motor");
        
        Servo clawServo = hardwareMap.get(Servo.class, "claw_servo");
        Servo handServo = hardwareMap.get(Servo.class, "hand_servo");

        // Set motor directions
        // You may need to adjust these for your particular robot
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extenderMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Any code before this point runs once ONCE when you press INIT
        waitForStart();

        // Any code after this point runs ONCE after you press START

        // Any code in this loop runs REPEATEDLY until the driver presses STOP
        while (opModeIsActive()) {
            double max;
            

            // Left joystick to go forward & strafe, and right joystick to rotate.
            double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x;
            double yawx = gamepad1.right_stick_x;
            double yawy = gamepad1.right_stick_y;
            double yaw = yawx;
            double multiplier = 2;
            
            if (yawy < 0) {
                yawy *= -1;
            }
            if (yawx < 0) {
                yawx *= -1;
            }
            if (yawy < yawx) {
                yaw = gamepad1.right_stick_x;
            } else {
                yaw = gamepad1.right_stick_y;
            }
            
            // Combine the joystick requests for each axis-motion to determine each wheel's power.
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;

            // Normalize the values so no wheel power exceeds 100%
            // This ensures that the robot maintains the desired motion.\
            
            // For debugging purposes
            // leftFrontDrive.setPower(0.5);
            // leftBackDrive.setPower(0.5);
            // rightFrontDrive.setPower(0.5);
            // rightBackDrive.setPower(0.5);
            
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }
            
            // Send calculated power to wheels
            leftFrontDrive.setPower(leftFrontPower/multiplier);
            rightFrontDrive.setPower(rightFrontPower/multiplier);
            leftBackDrive.setPower(leftBackPower/multiplier);
            rightBackDrive.setPower(rightBackPower/multiplier);
            
            double armPower = -gamepad2.left_stick_y / 1.5;
            double extendorPower = gamepad2.right_stick_y / 1.7;
            
            armMotor.setPower(armPower);
            extenderMotor.setPower(extendorPower);
            
            
            if(gamepad2.left_trigger >= 0.5 || gamepad2.dpad_down) {
                clawServo.setPosition(0.8);
            } else if(gamepad2.right_trigger >= 0.5 || gamepad2.dpad_up) {
                clawServo.setPosition(0.65);
            }
            
            if(gamepad2.left_stick_button) {
                handServo.setPosition(0.33);
            } else if(gamepad2.right_stick_button) {
                handServo.setPosition(0.67);
            } else if(gamepad2.left_bumper) {
                handServo.setPosition(0);
            }
            
            // Show the elapsed game time and wheel power.
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.update();
            
        }
        // Any code after the while loop will run ONCE after the driver presses STOP
    }
}
