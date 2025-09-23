import org.openftc.apriltag.AprilTagDetection;
import org.openftc.apriltag.AprilTagDetectionPipeline;
import org.openftc.easyopencv.*;

import java.util.ArrayList;

@Autonomous(name="AprilTag Example", group="Concept")
public class AprilTagExample extends LinearOpMode
{
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FX = 578.272; // camera intrinsics (you must calibrate for your webcam)
    static final double FY = 578.272;
    static final double CX = 402.145;
    static final double CY = 221.506;
    ArrayList<String> order = new ArrayList<>();

    double tagsize = 0.166; // meters (FTC tags are ~16.6 cm)

    @Override
    public void runOpMode()
    {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, FX, FY, CX, CY);
        camera.setPipeline(aprilTagDetectionPipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }
        });

        telemetry.setMsTransmissionInterval(50);

        waitForStart();

        while (opModeIsActive())
        {
            ArrayList<AprilTagDetection> detections = aprilTagDetectionPipeline.getLatestDetections();

            if(detections.size() != 0)
            {
                for(AprilTagDetection tag : detections)
                {
                    telemetry.addData("Detected tag ID", tag.id);
                    if (tag.id == 21) {
                        order.clear();
                        order.add("g");
                        order.add("p");
                        order.add("p");
                    } else if (tag.id == 22) {
                        order.clear();
                        order.add("p");
                        order.add("g");
                        order.add("p");
                    } else if (tag.id == 23) {
                        order.clear();
                        order.add("p");
                        order.add("p");
                        order.add("g");
                    }
                    telemetry.addData("Translation X", tag.pose.x);
                    telemetry.addData("Translation Y", tag.pose.y);
                    telemetry.addData("Translation Z", tag.pose.z);
                }
            }
            else
            {
                telemetry.addLine("No tags detected");
            }

            telemetry.update();
            sleep(20);
        }
    }
}
