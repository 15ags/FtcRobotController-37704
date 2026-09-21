package org.firstinspires.ftc.teamcode.kotlin.opmodes.auto

import com.pedropathing.api.Paths.curve
import com.pedropathing.api.Paths.line
import com.pedropathing.api.PoseFactory
import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.groups.Groups.sequential
import com.pedropathing.ivy.pedro.PedroCommands.follow
import com.pedropathing.paths.Path
import dev.nextftc.robot.opmode.NextAutonomous
import dev.nextftc.robot.opmode.NextOpMode
import org.firstinspires.ftc.teamcode.kotlin.Robot
import org.firstinspires.ftc.teamcode.pedro.Constants

@NextAutonomous(name = "Pedro Auto 1", preselectTeleop = "Next Teleop")
class PedroAuto1(val robot: Robot) : NextOpMode(robot) {

    private val poseFactory = PoseFactory.degrees()

    private val start = poseFactory.of(72.0, 72.0, 90.0)
    private val path1 = poseFactory.of(36.0, 76.0, 0.0)
    private val path1Control1 = poseFactory.of(36.0, 130.0, 0.0)
    private val point2 = poseFactory.of(72.0, 36.0, -90.0)
    private val point2Control1 = poseFactory.of(48.0, 40.0, 0.0)
    private val point3 = poseFactory.of(72.0, 72.0, 90.0)

    init {
        Scheduler.reset()
        robot.chasis.isAuto = true
        robot.chasis.follower = Constants.create(hardwareMap)
    }

    fun path1(): Path = curve(start, path1Control1, path1).linear(start, path1)

    fun path2(): Path = curve(path1, point2Control1, point2).linear(path1, point2)

    fun path3(): Path = line(point2, point3).linear(point3, point2)

    override fun start() {
        robot.chasis.follower.setPose(start)
        robot.chasis.follower.update()

        sequential(
            follow(robot.chasis.follower, path1()),
            follow(robot.chasis.follower, path2()),
            follow(robot.chasis.follower, path3()),
        ).schedule()
    }
}