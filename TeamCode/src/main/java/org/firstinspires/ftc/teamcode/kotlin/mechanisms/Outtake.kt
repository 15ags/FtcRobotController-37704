package org.firstinspires.ftc.teamcode.kotlin.mechanisms

import com.bylazar.configurables.annotations.Configurable
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.units.degreesPerSecond
import dev.nextftc.units.measuretypes.AngularVelocity

@Configurable
class Outtake: Mechanism {
    var kPShooter = 0.0
    var kIShooter = 0.0
    var kDShooter = 0.0
    var kSShooter = 0.0
    var kVShooter = 0.0
    var kAShooter = 0.0
    val mainMotor = NextMotor("out")
    var isShooting = false

    val targetVelocity: AngularVelocity = 720.0.degreesPerSecond

    fun shoot() = infinite {
        mainMotor.setVelocitySetpoint(targetVelocity)
        isShooting = true
    }

    fun stop() = instant {
        mainMotor.setVelocitySetpoint(0.0.degreesPerSecond)
        isShooting = false
    }

    fun getCurrentSpeed(): Double {
        return mainMotor.encoderVelocity.toString().toDouble()
    }

    fun getTargetSpeed(): Double {
        return if (isShooting) {
            720.0
        } else {
            0.0
        }
    }

    fun setConstants() {
        mainMotor.velocityConstants.apply {
        kP = kPShooter
        kI = kIShooter
        kD = kDShooter
        kS = kSShooter
        kV = kVShooter
        kA = kAShooter
    }
    }
}