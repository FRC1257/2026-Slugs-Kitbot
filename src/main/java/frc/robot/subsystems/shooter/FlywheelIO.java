package frc.robot.subsystems.shooter;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.units.measure.Current;
import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Volts;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;

public interface FlywheelIO {
    @AutoLog
    public static class FlywheelIOInputs {
        public AngularVelocity flywheelRPM=RPM.of(0); // rotations per minute; change rpm to angular vel in rad?
        public Temperature flywheelTemperature = Celsius.of(0.0);
        public Current flywheelCurrent = Amps.of(0.0);
    }

    public default void updateInputs(FlywheelIOInputs inputs) {}

    public default void setVelocity(AngularVelocity rpm) {}

    public default void setVoltage(Voltage voltage) {}

    public default void stop() {}

    public default void setPID(double kp, double ki, double kd) {}

    public default void setFF(double ks, double kv) {}
}
