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
        @JvmField var kPShooter = 0.01
        @JvmField var kPFlowerShooter = 0.05
        @JvmField var kIShooter = 0.0
        @JvmField var kIFlowerShooter = 0.0
        @JvmField var kDShooter = 0.0
        @JvmField var kDFlowerShooter = 0.0
        @JvmField var kSShooter = 0.1
        @JvmField var kSFlowerShooter = 0.1
        @JvmField var kVShooter = 0.0097
        @JvmField var kVFlowerShooter = 0.014
        @JvmField var kAShooter = 0.0
        @JvmField var kAFlowerShooter = 0.0
        @JvmField var targetRps = 0.0
        @JvmField var targetShootRps = 47.0

        @JvmField var targetFlowerRps = 0.0
        @JvmField var targetFloatRps = 10.0
    }
    val mainMotor = NextMotor("out", anglePerCount = (2.0 * Math.PI / (28.0)).radians).apply { direction = NextMotor.Direction.REVERSE }

    var isShootingFlower = false

    init {
        mainMotor.direction = NextMotor.Direction.REVERSE
    }

    override fun periodic() {
        if (isShootingFlower) {
            setFLowerConstants()
        } else {
            setShootConstants()
        }
    }

    fun shoot() = infinite {
        isShootingFlower = false
        targetRps = targetShootRps
        mainMotor.setVelocitySetpoint(targetRps.rotationsPerSecond)
        mainMotor.update()
    }

    fun shootFlower() = infinite {
        isShootingFlower = true
        targetRps = targetFlowerRps
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

    fun runMaxOpen() = infinite {
        mainMotor.throttle = 1.0
    }

    fun getCurrentSpeed(): Double {
        return mainMotor.encoderVelocity.into(RotationsPerSecond)
    }

    fun getTargetSpeed(): Double {
        return targetRps
    }

    fun setShootConstants() {
        mainMotor.velocityConstants.apply {
            kP = kPShooter
            kI = kIShooter
            kD = kDShooter
            kS = kSShooter
            kA = kAShooter
            kV = kVShooter
        }
        mainMotor.direction = NextMotor.Direction.FORWARD
    }
    fun setFLowerConstants() {
        mainMotor.velocityConstants.apply {
            kP = kPFlowerShooter
            kI = kIFlowerShooter
            kD = kDFlowerShooter
            kS = kSFlowerShooter
            kA = kAFlowerShooter
            kV = kVFlowerShooter
        }
        mainMotor.direction = NextMotor.Direction.FORWARD
    }
}
