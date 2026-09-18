package org.firstinspires.ftc.teamcode.kotlin.opmodes.teleop

import com.pedropathing.math.Pose
import dev.nextftc.robot.Telemetry
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.teamcode.kotlin.Robot
import org.firstinspires.ftc.teamcode.pedro.Constants

@NextTeleop(name = "Next Teleop")
public class NextTeleop(val robot: Robot): NextOpMode(robot) {
    val driver = CommandGamepad(gamepad1)
    val coDriver = CommandGamepad(gamepad2)

    override fun start() {
        robot.chasis.follower = Constants.create(hardwareMap)
        robot.chasis.follower.setPose(Pose(0.0, 0.0, 0.0))

        robot.chasis.manualDrive(gamepad1).schedule()

        driver.a.whileTrue(robot.chasis.holdPose(Pose(72.0, 72.0, 0.0)))
        driver.b.whileTrue(robot.chasis.holdPose(Pose(120.0, 120.0, Math.toRadians(90.0))))

        Telemetry.log("Started with pedro pathing")
    }

    override fun periodic() {
        robot.intake.setPower(coDriver.rightStickY.value)
    }
}