import frc.robot.subsystems.intake.IntakeIO;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Volts;


public class IntakeIOSparkMax implements IntakeIO {
    private final SparkMax motor;
    public class IntakeIOSparkMax {
        motor = new SparkMax(intakeConstants.Motor_ID, SparkMax.MotorType.kBrushless);
    }
    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.currentAmps = Amps.of(motor.getCurrentOutput());
        inputs.tempCelsius = Celsius.of(motor.getMotorTemperature());
        inputs.appliedVoltage = Volts.of(motor.getAppliedOutput() * motor.getBusVoltage());
    }
    @Override
    public void setVoltage(Voltage voltage) {
        motor.setVoltage(voltage);
    }
    @Override
    public void stop() {
        motor.setVoltage(0.0)
    }
}
