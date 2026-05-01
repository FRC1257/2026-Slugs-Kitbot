package frc.robot.subsystems.Kicker;

import edu.wpi.first.units.measure.AngularVelocity;
import static edu.wpi.first.units.Units.RadiansPerSecond;


public class KickerConstants {
    public static final int KICKER_MOTOR_ID = 2; //temp random number

    public static final double KICKER_KP = 0.0; //temp PID stuff
    public static final double KICKER_KI = 0.0; //temp
    public static final double KICKER_KD = 0.0; //temp

    public static final double KICKER_KS = 0.0; //temp FF stuff
    public static final double KICKER_KV = 0.0; //temp
    public static final double KICKER_KA = 0.0; //in the Sparkmax i think


    public static final AngularVelocity KICKER_INTAKE_VELOCITY = RadiansPerSecond.of(0.0); //idk temp (push up)
    public static final AngularVelocity KICKER_OUTTAKE_VELOCITY = RadiansPerSecond.of(0.0); // (push down)
    public static final AngularVelocity KICKER_MAX_VELOCITY = RadiansPerSecond.of(0.0); //temp

    public static final String KICKER_JAMMED_VELOCITY = RadiansPerSecond.(0.0);
}
    