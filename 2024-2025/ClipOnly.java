package org.firstinspires.ftc;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="ClipOnly", group="Robot")

public class ClipOnly extends LinearOpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor armMotor = null;
    private Servo clawServo = null;
    private DcMotor extendMotor = null;
    private Servo handServo = null;

    private ElapsedTime runtime = new ElapsedTime();
    
    static final double COUNTS_PER_MOTOR_REV    = 1440;
    static final double DRIVE_GEAR_REDUCTION    = 1.0;
    static final double WHEEL_DIAMETER_INCHES   = 4.0;
    static final double COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode() {
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        armMotor = hardwareMap.get(DcMotor.class, "arm_motor");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        extendMotor = hardwareMap.get(DcMotor.class, "extender_motor");
        handServo = hardwareMap.get(Servo.class, "hand_servo");
        
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        extendMotor.setDirection(DcMotor.Direction.REVERSE);
        
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        telemetry.addData("Starting at",  "%7d :%7d :%7d",
                          leftDrive.getCurrentPosition(),
                          rightDrive.getCurrentPosition(),
                          armMotor.getCurrentPosition());
        telemetry.update();
        
        clawServo.setPosition(0.04);
        
        waitForStart();
        
        // Hand Servo moves hand vertically
        handServo.setPosition(0.65);
        
        // Moves arm to horizontal bar height
        encoderDrive(0.06, 0.2, 0.2, 0.4);
        moveArm(0.88, 13.5, 1.55);
        //Moves drive-train
        encoderDrive(0.06, 1.8, 1.8, 1.7);
        sleep(1000);
        // Following section of code for adjusting claw height before clipping. Fiddle
        //extendMotor.setPower(0.3);
        //sleep(200);
        //extendMotor.setPower(0);
        moveArm(0.9, -7, 1.5);
        // Opens claw, 0 being base
        clawServo.setPosition(0.23);
        // Moves robot back(Slightly)
        encoderDrive(0.04, -1, -1, 1.2);
        sleep(1500);
        moveArm(0.6, -5, 1.55);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
    public void extendArm(double speed,double extendInches,double timeoutS) {
        int newExtendTarget;

        if (opModeIsActive()) {
            newExtendTarget = extendMotor.getCurrentPosition() + (int)(extendInches * COUNTS_PER_INCH);
            extendMotor.setTargetPosition(newExtendTarget);
            extendMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            runtime.reset();
            
            extendMotor.setPower(Math.abs(speed));
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && extendMotor.isBusy()) {
                telemetry.addData("Currently at",  " at %7d", extendMotor.getCurrentPosition());
                telemetry.update();
            }

            extendMotor.setPower(0);

            extendMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
    public void moveArm(double speed,double liftInches,double timeoutS) {
        int newArmTarget;

        if (opModeIsActive()) {
            newArmTarget = armMotor.getCurrentPosition() + (int)(liftInches * COUNTS_PER_INCH);
            armMotor.setTargetPosition(newArmTarget);
            armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            runtime.reset();
            
            armMotor.setPower(Math.abs(speed));
            while (opModeIsActive() && (runtime.seconds() < timeoutS) && armMotor.isBusy()) {
                telemetry.addData("Currently at",  " at %7d", armMotor.getCurrentPosition());
                telemetry.update();
            }

            armMotor.setPower(0);

            armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
    public void encoderDrive(double speed,double leftInches,double rightInches,double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        if (opModeIsActive()) {
            newLeftTarget = leftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = rightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            
            leftDrive.setTargetPosition(newLeftTarget);
            rightDrive.setTargetPosition(newRightTarget);
            
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            
            runtime.reset();
            
            leftDrive.setPower(Math.abs(speed));
            rightDrive.setPower(Math.abs(speed));
            while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (leftDrive.isBusy() && rightDrive.isBusy())) {

                telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                                            leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition());
                telemetry.update();
            }

            leftDrive.setPower(0);
            rightDrive.setPower(0);

            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}
