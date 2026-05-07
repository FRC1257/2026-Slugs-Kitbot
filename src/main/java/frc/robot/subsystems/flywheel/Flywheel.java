package frc.robot.subsystems.flywheel;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.AutoLogOutput;

import java.util.function.Supplier;

import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Volt;
import static frc.robot.subsystems.flywheel.FlywheelConstants.*;

public class Flywheel extends SubsystemBase {
    private FlywheelIO io;
    private final FlywheelIoInputsAutoLogged inputs=new FlywheelIoInputsAutoLogged();

    @AutoLogOutput
    private State currentState =State.STOP;

    public Flywheel(FlywheelIO io) {
        this.io=io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
    }

    public Command runVoltage(Supplier<Voltage> voltage) {
        return runEnd(
                ()->io.setVoltage(voltage.get()),
                ()->io.setVoltage(Volt.of(0))
        ).withName("Running with voltage");
    }

    public Command runVelocity(Supplier<AngularVelocity> rpm) {
        return run(
                ()->io.setVelocity(rpm.get())
        ).withName("Running velocity");
    }

    public Command stopCommand() {
        return run(()->io.setVelocity(RPM.of(0))).withName("Stopping");
    }

    private Command jammedCommand() {
        return run(
                ()->io.setVelocity(UNJAMMING_VELOCITY_ABS)
        ).withTimeout(UNJAMMING_SWITCH_FREQUENCY)
                .andThen(run(
                        ()->io.setVelocity(UNJAMMING_VELOCITY_ABS.times(-1))
                ).withTimeout(UNJAMMING_SWITCH_FREQUENCY)).withName("Unjamming for "+(UNJAMMING_SWITCH_FREQUENCY*2+" seconds"));
    }

    public Command setState(State newState) {
        if (newState.equals(currentState)) {
            return Commands.none();
        } else {
            currentState=newState;
            return switch (currentState) {
                case IDLE -> runVelocity(()->IDLE_VELOCITY);
                case SHOOTING -> runVelocity(()->SHOOTING_VELOCITY);
                case JAMMED -> jammedCommand().repeatedly();
                case STOP -> stopCommand();
            };
        }
    }

    public enum State {
        IDLE,
        SHOOTING,
        JAMMED,
        STOP
    }
}
