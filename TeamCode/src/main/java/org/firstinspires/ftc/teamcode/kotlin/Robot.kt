package org.firstinspires.ftc.teamcode.kotlin

import dev.nextftc.robot.NextRobot
import org.firstinspires.ftc.teamcode.kotlin.mechanisms.Chasis
import org.firstinspires.ftc.teamcode.kotlin.mechanisms.Intake

class Robot: NextRobot {
    val chasis = Chasis()
    val intake = Intake()

    override val mechanisms = setOf(chasis, intake)
}