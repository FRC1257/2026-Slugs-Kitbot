package frc.robot.subsystems.intake;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Volts;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.Units.UnitUtil;


public class Intake extends SubsystemBase {
    private final IntakeIO io;
    private final IntakeIOInputsAutoLogged inputs = new IntakeIOInputsAutoLogged();
    public Intake(IntakeIO inputs) {
        this.io = io;
    }
    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Intake", inputs);
    }
    public Command runVoltage(Supplier<Voltage> voltage) {
        return run(
            () -> io.setVoltage(UnitUtil.clamp(voltage.get(), Volts.of(-12.0), Volts.of(12.0)))
        );
    }
    public Command runIntake() {
        return runVoltage(
            () -> IntakeConstants.Intake_Voltage
        ).withName("Intake/On");
    }
    public Command stopIntake() {
        return runOnce(
            () -> io.stop()
        ).withName("Intake/Off");
    }
}
