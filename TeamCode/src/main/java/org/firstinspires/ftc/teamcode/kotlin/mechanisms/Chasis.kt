package org.firstinspires.ftc.teamcode.kotlin.mechanisms

import com.pedropathing.follower.Follower
import com.pedropathing.follower.ManualDrive
import com.pedropathing.ivy.Command
import com.pedropathing.math.Pose
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.robot.Mechanism

class Chasis : Mechanism {
    lateinit var follower: Follower
    var gamepad: Gamepad? = null

    override val defaultCommand: Command
        get() {
            val gp = gamepad ?: return infinite {}
            return infinite {
                val powers = ManualDrive.fieldCentric(
                    -gp.left_stick_y.toDouble(),
                    -gp.left_stick_x.toDouble(),
                    -gp.right_stick_x.toDouble(),
                    follower.pose().heading()
                )
                follower.manual(powers)
            }
        }

    override fun periodic() {
        if (!::follower.isInitialized) return
        follower.update()
    }

    fun holdPose(pose: Pose): Command = infinite {
        follower.hold(pose)
    }.setEnd { follower.stop() }
}