package frc.robot.subsystems.intake;
import org.littletonrobotics.junction.AutoLog;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Volts;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;

public interface IntakeIO {
    @AutoLog
    public class IntakeIOInputs {
        public Voltage appliedVoltage = Volts.of(0.0);
        public Current currentAmps = Amps.of(0.0);
        public Temperature tempCelsius = Celsius.of(0.0);
    }
    public default void updateInputs(IntakeIOInputs inputs) {};
    public default void setVoltage(Voltage voltage) {};
    public default void stop() {};
}


