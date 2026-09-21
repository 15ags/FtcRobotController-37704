package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.OptionalDouble;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("LF");
        c.frontRightName.set("RF");
        c.backLeftName.set("LB");
        c.backRightName.set("RB");
        c.frontLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.REVERSE);
    });
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.encoderResolutionUnit.set(DistanceUnit.MM);
        c.ticksPerUnit.set(OptionalDouble.of(19.894));
        c.xPodOffset.set(-4.25);
        c.yPodOffset.set(-3.5);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.2040896970306417);
                Controller secondaryTranslationalForward = Controller.proportional(0.07540568967568115);
                Controller primaryTranslationalLateral = Controller.proportional(0.3400518113869273);
                Controller secondaryTranslationalLateral = Controller.proportional(0.12564005795572364);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.011584409758273803));
                c.brake.set(Controller.proportionalFeedforward(0.009846748294532732));

                c.headingFeedback.set(Controller.proportional(3.1344675188909403));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.029365519098316292, 0.013645222051035253));

                c.linearBrakeCoefficients.set(Matrix.diag(0.07614586522935932, 0.012802267565307598));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.001791374339896108, 0.003307506057812561));

                c.maxAchievableForwardVelocity.set(81.79419054175231);
                c.maxAchievableStrafeVelocity.set(70.4758671235489);
                c.naturalForwardDeceleration.set(50.94424291468476);
                c.naturalStrafeDeceleration.set(77.00694492146322);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}
