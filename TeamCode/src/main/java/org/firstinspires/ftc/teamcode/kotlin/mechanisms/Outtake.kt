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
        @JvmField var kPShooter = 0.005
        @JvmField var kIShooter = 0.0
        @JvmField var kDShooter = 0.0
        @JvmField var kSShooter = 0.1
        @JvmField var kVShooter = 0.01
        @JvmField var kAShooter = 0.0
        @JvmField var targetRps = 0.0
        @JvmField var targetShootRps = 100.0
        @JvmField var targetFloatRps = 10.0
    }

    val mainMotor = NextMotor("out", anglePerCount = (2.0 * Math.PI * 20.0 / (28.0 * 11.0)).radians)

    override fun periodic() {
        setConstants()
    }

    fun shoot() = infinite {
        targetRps = targetShootRps
        mainMotor.setVelocitySetpoint(targetRps.rotationsPerSecond)
        mainMotor.update()
    }

    fun float() = infinite {
        targetRps = targetFloatRps
        mainMotor.setVelocitySetpoint(targetFloatRps.rotationsPerSecond)
        mainMotor.update()
    }

    fun stop() = infinite {
        stopMotor()
    }

    fun stopMotor() {
        targetRps = 0.0
        mainMotor.throttle = 0.0
        mainMotor.update()
    }

    fun getCurrentSpeed(): Double {
        return mainMotor.encoderVelocity.into(RotationsPerSecond)
    }

    fun getTargetSpeed(): Double {
        return targetRps
    }

    fun setConstants() {
        mainMotor.velocityConstants.apply {
            kP = kPShooter
            kI = kIShooter
            kD = kDShooter
            kS = kSShooter
            kA = kAShooter
            kV = kVShooter
        }
    }
}
