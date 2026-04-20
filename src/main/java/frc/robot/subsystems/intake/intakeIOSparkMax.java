import frc.robot.subsystems.intake.IntakeIO;


public class IntakeIOSparkMax implements KickerIO {
    private final CANSparkMax motor;
    public class IntakeIOSparkMax {
        motor = new CANSparkMax(intakeConstants.Motor_ID, CANSparkMaxx.MotorType.kBrushless);
    }
    @Override
    public void updateInputs(IntakeIOInputs inputs) {
        inputs.currentAmps = Amps.of(motor.getCurrentOutput());
        inputs.tempCelsius = Celsius.of(motor.getMotorTemperature());
        inputs.appliedVoltage = Volts.of(motor.getAppliedOutput() * motor.getBusVoltage());
    }
    @Override
    public void intake(double voltage) {
        motor.setVoltage(voltage);
    }
    @Override
    public void stop() {
        motor.setVoltage(0.0)
    }
}
