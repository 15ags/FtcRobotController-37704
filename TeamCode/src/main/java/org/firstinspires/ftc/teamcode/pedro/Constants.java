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
        c.xPodOffset.set(1.3915252685546875);
        c.yPodOffset.set(-0.7292152014304335);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.24233073284641962);
                Controller secondaryTranslationalForward = Controller.proportional(0.089534730590315);
                Controller primaryTranslationalLateral = Controller.proportional(0.32607380429048266);
                Controller secondaryTranslationalLateral = Controller.proportional(0.1204755578328158);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.013234708906348754));
                c.brake.set(Controller.proportionalFeedforward(0.011249502570396442));

                c.headingFeedback.set(Controller.proportional(3.136831189561424));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.034759839134174755, 0.010972276490502964));

                c.linearBrakeCoefficients.set(Matrix.diag(0.026134195218077957, 0.0410384360066085));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.002794232683709751, 0.002250816827231718));

                c.maxAchievableForwardVelocity.set(75.97383194194114);
                c.maxAchievableStrafeVelocity.set(61.46632498752579);
                c.naturalForwardDeceleration.set(54.20874037871031);
                c.naturalStrafeDeceleration.set(87.05183707004822);
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
