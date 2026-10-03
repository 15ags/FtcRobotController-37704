package org.firstinspires.ftc.teamcode.kotlin

import dev.nextftc.robot.NextRobot
import org.firstinspires.ftc.teamcode.kotlin.mechanisms.Chasis
import org.firstinspires.ftc.teamcode.kotlin.mechanisms.Intake
import org.firstinspires.ftc.teamcode.kotlin.mechanisms.Outtake

class Robot: NextRobot {
    val chasis = Chasis()
    val intake = Intake()

    val outtake = Outtake()

    override val mechanisms = setOf(chasis, intake, outtake)
}