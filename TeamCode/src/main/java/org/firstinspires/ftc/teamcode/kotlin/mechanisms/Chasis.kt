package org.firstinspires.ftc.teamcode.kotlin.mechanisms

import com.pedropathing.follower.Follower
import com.pedropathing.follower.ManualDrive
import com.pedropathing.ivy.Command
import com.pedropathing.ivy.commands.Commands
import com.pedropathing.math.Pose
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism

class Chasis : Mechanism {
    lateinit var follower: Follower

    override fun periodic() {
        if (::follower.isInitialized) follower.update()
    }

    fun manualDrive(gamepad: Gamepad): Command = Commands.infinite {
        if (follower.idle()) {
            val powers = ManualDrive.fieldCentric(
                -gamepad.left_stick_y.toDouble(),
                gamepad.left_stick_x.toDouble(),
                gamepad.right_stick_x.toDouble(),
                follower.pose().heading()
            )
            follower.manual(powers)
        }
    }

    fun holdPose(pose: Pose): Command = Command.build()
        .requiring(this)
        .setStart { follower.hold(pose) }
        .setEnd { follower.stop() }
}