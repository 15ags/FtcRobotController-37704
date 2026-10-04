package org.firstinspires.ftc.teamcode.kotlin.mechanisms

import com.bylazar.configurables.annotations.Configurable
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.units.DegreesPerSecond
import dev.nextftc.units.degreesPerSecond

@Configurable
class Outtake: Mechanism {
    companion object {
        @JvmField var kPShooter = 0.0
        @JvmField var kIShooter = 0.0
        @JvmField var kDShooter = 0.0
        @JvmField var kSShooter = 0.0
        @JvmField var kVShooter = 0.0
        @JvmField var targetDps = 720.0
    }

    val mainMotor = NextMotor("out")
    var isShooting = false

    fun shoot() = infinite {
        mainMotor.setVelocitySetpoint(targetDps.degreesPerSecond)
        isShooting = true
    }

    fun stop() = instant {
        stopMotor()
    }

    fun stopMotor() {
        mainMotor.throttle = 0.0
        mainMotor.update()
        isShooting = false
    }

    fun getCurrentSpeed(): Double {
        return mainMotor.encoderVelocity.into(DegreesPerSecond)
    }

    fun getTargetSpeed(): Double {
        return if (isShooting) {
            targetDps
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
        }
    }
}
