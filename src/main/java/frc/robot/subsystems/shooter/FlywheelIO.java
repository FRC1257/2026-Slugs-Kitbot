package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface FlywheelIO {
    @AutoLog
    public static class FlywheelIOInputs {
        
    }

    public default void updateInputs(FlywheelIOInputsAutoLogged inputs) {

    }
}
