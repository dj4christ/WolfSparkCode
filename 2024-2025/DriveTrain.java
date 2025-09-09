package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.Timer;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "DrivetrainAuto", group = "Robot")
public class DriveTrain extends LinearOpMode {
    @Override
    public void runOpMode() {
        // Initialize the hardware
        // The strings here must correspond to the names of the motors in your robot config
        DcMotor leftFrontDrive = hardwareMap.dcMotor.get("left_front");
        DcMotor leftBackDrive = hardwareMap.dcMotor.get("left_back");
        DcMotor rightFrontDrive = hardwareMap.dcMotor.get("right_front");
        DcMotor rightBackDrive = hardwareMap.dcMotor.get("right_back");

        // Set motor directions
        // You may need to adjust these for your particular robot
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        
        double speed = 0.7;

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        
        leftFrontDrive.setPower(speed);
        rightFrontDrive.setPower(speed);
        leftBackDrive.setPower(speed);
        rightBackDrive.setPower(speed);
        
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}
