package org.firstinspires.ftc.teamcode.kotlin.mechanisms

import com.bylazar.configurables.annotations.Configurable
import dev.nextftc.hardware.actuators.NextMotor
import dev.nextftc.robot.Mechanism
import dev.nextftc.units.RotationsPerSecond
import dev.nextftc.units.radians
import dev.nextftc.units.rotationsPerSecond

@Configurable
class Outtake: Mechanism {
    companion object {
        @JvmField var kPShooter = 0.0
        @JvmField var kIShooter = 0.0
        @JvmField var kDShooter = 0.0
        @JvmField var kSShooter = 0.0
        @JvmField var kVShooter = 0.004
        @JvmField var targetRps = 175.0
    }

    val mainMotor = NextMotor("out", anglePerCount = (2.0 *Math.PI / 28).radians)
    var isShooting = false

    override fun periodic() {
        setConstants()
    }

    fun shoot() = infinite {
        mainMotor.setVelocitySetpoint(targetRps.rotationsPerSecond)
        isShooting = true
    }

    fun stop() = infinite {
        stopMotor()
    }

    fun stopMotor() {
        mainMotor.throttle = 0.0
        mainMotor.update()
        isShooting = false
    }

    fun getCurrentSpeed(): Double {
        return mainMotor.encoderVelocity.into(RotationsPerSecond)
    }

    fun getTargetSpeed(): Double {
        return if (isShooting) {
            targetRps
        } else {
            0.0
        }
    }

    fun runOpen(power: Double) {
        mainMotor.throttle = power
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
