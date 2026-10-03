package org.firstinspires.ftc.teamcode.kotlin.opmodes.teleop

import com.bylazar.telemetry.PanelsTelemetry
import dev.nextftc.robot.opmode.NextOpMode
import dev.nextftc.robot.opmode.NextTeleop
import dev.nextftc.robot.triggers.CommandGamepad
import org.firstinspires.ftc.teamcode.kotlin.Robot

@NextTeleop(name = "Shooter Tuner")
class OuttakeTuner(val robot: Robot): NextOpMode(robot) {
    private val panelsTelemetry = PanelsTelemetry.telemetry
    private val gp1 = CommandGamepad(gamepad1)

    override fun periodic() {
        gp1.a.whileTrue(robot.outtake.shoot())
        gp1.a.whileFalse(robot.outtake.stop())
        panelsTelemetry.addData("target", robot.outtake.getTargetSpeed())
        panelsTelemetry.addData("current", robot.outtake.getCurrentSpeed())
        robot.outtake.setConstants()
    }

    override fun end() {
        robot.outtake.stop()
    }
}