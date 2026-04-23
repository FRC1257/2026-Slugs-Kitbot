package frc.robot.subsystems.shooter;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Volts;

public interface FlywheelIO {
    @AutoLog
    public static class FlywheelIOInputs {
        public AngularVelocity rpm=RPM.of(0); // rotations per minute
        public Voltage voltage=Volts.of(0);
    }

    public default void updateInputs(FlywheelIOInputsAutoLogged inputs) {

    }

    public default void setVelocity(AngularVelocity rpm) {}

    public default void setVoltage(Voltage voltage) {}

    public default void stop() {}

    public default void setFF() {}

    public default void setPID(double kp, double ki, double kd) {}
}
