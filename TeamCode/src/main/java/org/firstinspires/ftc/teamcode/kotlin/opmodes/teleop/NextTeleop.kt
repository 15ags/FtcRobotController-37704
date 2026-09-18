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
        robot.intake.setPower(gamepad2)
        robot.chasis.follower = Constants.create(hardwareMap)
        robot.chasis.follower.setPose(Pose(0.0, 0.0, 0.0))
        robot.chasis.gamepad = gamepad1

        driver.a.whileTrue(robot.chasis.holdPose(Pose(0.0, 0.0, 0.0)))
        driver.b.whileTrue(robot.chasis.holdPose(Pose(12.0, 12.0, Math.toRadians(90.0))))

        Telemetry.log("Started with pedro pathing")
    }

    override fun periodic() {
        val robotPose = robot.chasis.follower.pose()
        Telemetry.log("Robot x = ${robotPose.x()}")
    }
}