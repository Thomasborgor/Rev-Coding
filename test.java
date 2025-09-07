package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="Simple Tank Drive", group="Linear Opmode")
public class SweveDrive extends LinearOpMode {
    private int thingy = 500;
    // Declare motors and servos
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private Servo leftservo;
    private Servo rightservo;

    @Override
    public void runOpMode() {
        // Initialize hardware
        leftDrive  = hardwareMap.get(DcMotor.class, "Left");
        rightDrive = hardwareMap.get(DcMotor.class, "Right");
        leftservo = hardwareMap.get(Servo.class, "leftservo");
        rightservo = hardwareMap.get(Servo.class, "rightservo");

        // Reverse motor direction if needed
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);

        // Track last angle for holding servo position
        double lastAngle = 0;

        // Wait for start
        waitForStart();

        // Main loop
        while (opModeIsActive()) {
            // Read joystick input
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;

            // Calculate magnitude (distance from center)
            double magnitude = Math.sqrt(x * x + y * y);

            // If joystick is pushed enough, update angle
            if (magnitude > 0.1) {
                lastAngle = Math.toDegrees(Math.atan2(x, -y)); // Make 0° = forward
            }

            // Clamp angle to -180 to 180
            if (lastAngle > 180) lastAngle -= 360;
            if (lastAngle < -180) lastAngle += 360;

            // Map angle to servo position
            double servopos = map(lastAngle, -180, 180, 0.28, 0.72);

            // Drive motors with right stick Y (or same stick if you want)
            double drivePower = -gamepad1.right_stick_y;
            leftDrive.setPower(magnitude);.

            // Only update servos if joystick is pushed
            if (magnitude > 0.1) {
                leftservo.setPosition(servopos);
                rightservo.setPosition(servopos);
            }

            // Debug info
            telemetry.addData("Joystick Magnitude", magnitude);
            telemetry.addData("Angle", lastAngle);
            telemetry.addData("Servo Pos", servopos);
            telemetry.addData("Drive Power", drivePower);
            telemetry.update();
        }
    }

    // Arduino-style map function
    public double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}
