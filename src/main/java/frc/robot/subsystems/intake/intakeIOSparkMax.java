import frc.robot.subsystems.intake.IntakeIO;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Celsius;
import static edu.wpi.first.units.Units.Volts;

public class IntakeIOSparkMax implements IntakeIO {
    private SparkMax motor;
    private RelativeEncoder encoder;

    public class IntakeIOSparkMax {
        motor = new SparkMax(intakeConstants.Motor_ID, SparkMax.MotorType.kBrushless);
        encoder = motor.getEncoder();
        //configurations copied from 2026 code
        SparkFlexConfig config = new SparkFlexConfig();
        config.idleMode(IdleMode.kCoast);
        config.voltageCompensation(12);
        config.smartCurrentLimit(60);
        config.inverted(true);
        config.encoder
            .positionConversionFactor(Math.PI * 2.0)
            .velocityConversionFactor(Math.PI * 2.0 / 60.0);
        motor.configure(config, com.revrobotics.ResetMode.kResetSafeParameters, com.revrobotics.PersistMode.kPersistParameters); 
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
