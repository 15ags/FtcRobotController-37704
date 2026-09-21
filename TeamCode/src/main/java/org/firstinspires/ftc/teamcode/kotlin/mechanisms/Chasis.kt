package org.firstinspires.ftc.teamcode.kotlin.mechanisms

import com.pedropathing.follower.Follower
import com.pedropathing.follower.ManualDrive
import com.pedropathing.ivy.Command
import com.pedropathing.math.Pose
import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.robot.Mechanism

class Chasis : Mechanism {
    lateinit var follower: Follower

    var isAuto = false;

    var isFieldCentric = true;
    var gamepad: Gamepad? = null

    override val defaultCommand: Command
        get() {
            if (!isAuto && gamepad != null && ::follower.isInitialized) {
                return infinite {
                    if (isFieldCentric) {
                        val powers = ManualDrive.fieldCentric(
                            -gamepad!!.left_stick_y.toDouble(),
                            -gamepad!!.left_stick_x.toDouble(),
                            -gamepad!!.right_stick_x.toDouble(),
                            follower.pose().heading()
                        )
                        follower.manual(powers)

                    } else {
                        follower.manual(
                            -gamepad!!.left_stick_y.toDouble(),
                            -gamepad!!.left_stick_x.toDouble(),
                            -gamepad!!.right_stick_x.toDouble(),
                        )
                    }
                }
            } else {
                return infinite {}
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