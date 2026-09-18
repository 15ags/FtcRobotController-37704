package org.firstinspires.ftc.teamcode.kotlin.mechanisms

import com.qualcomm.robotcore.hardware.Gamepad
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism

class Intake : Mechanism {
    val motor = NextMotor("intake").apply { direction = NextMotor.Direction.REVERSE }

    fun setPower(gp: Gamepad) = infinite {
        motor.throttle = gp.right_stick_y.toDouble()
    }
}