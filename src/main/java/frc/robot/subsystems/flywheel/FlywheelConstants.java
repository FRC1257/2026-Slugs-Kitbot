package frc.robot.subsystems.flywheel;

import edu.wpi.first.units.measure.AngularVelocity;

import static edu.wpi.first.units.Units.RPM;

public class FlywheelConstants {
    public static final AngularVelocity IDLE_VELOCITY=RPM.of(1000);
    public static final AngularVelocity SHOOTING_VELOCITY=RPM.of(3000);
    public static final AngularVelocity UNJAMMING_VELOCITY_ABS=RPM.of(2000);

    public static final double UNJAMMING_SWITCH_FREQUENCY=2; //time between switching forward and back
}
