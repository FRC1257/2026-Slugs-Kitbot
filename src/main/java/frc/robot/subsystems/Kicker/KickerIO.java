package frc.robot.subsystems.Kicker;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;
import edu.wpi.first.units.measure.Current;


public interface KickerIO {
    @AutoLog
    public static class KickerIOInputs {
        public boolean kickerLoaded = false;// this was kicker connected i think thats what it means
        public Voltage kickerVoltage = Volts.of(0.0); //Wrapper class for voltage is like double tho
        public AngularVelocity kickerAngularVelocity = RadiansPerSecond.of(0.0); //idk
        public Angle kickerAngle = Radians.of(0.0); 
        public Current kickerCurrent =  Amps.of(0.0); //
    }
    public default void updateInputs(KickerIOInputs inputs) {}
    public default void setVoltage(Voltage voltage) {}
    public default void setVelocity(AngularVelocity velocity) {}
    public default void stop() {}
    public default void setPID(double kP, double kI, double kD) {}//PID
    public default void setFF(double kS, double kV, double kA) {}
    public default void setBreakMode(boolean enabled) {} //

}

