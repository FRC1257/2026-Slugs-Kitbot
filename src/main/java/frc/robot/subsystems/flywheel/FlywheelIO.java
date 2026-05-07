package frc.robot.subsystems.flywheel;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;
import org.littletonrobotics.junction.AutoLog;

import static edu.wpi.first.units.Units.RPM;

public interface FlywheelIO {
    @AutoLog
    public class FlywheelIoInputs {
        public AngularVelocity velocity=RPM.of(0);
    }

    public default void updateInputs(FlywheelIoInputs inputs) {}

    public default void setVoltage(Voltage voltage) {};

    public default void setVelocity(AngularVelocity rpm) {}

    public default AngularVelocity getVelocity() {
        return RPM.of(0);
    }
}
