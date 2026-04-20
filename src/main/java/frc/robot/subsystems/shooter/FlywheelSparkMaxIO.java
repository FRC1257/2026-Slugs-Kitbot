package frc.robot.subsystems.shooter;

public class FlywheelSparkMaxIO implements FlywheelIO {

  private final SparkMax motor;
  private final RelativeEncoder encoder;
  private final SparkMaxConfig flywheelConfig;

  public FlywheelSparkMaxIO() {
    motor = new SparkMax(FlywheelConstants.flywheel_motor_id, SparkMax.MotorType.kBrushless);
    encoder = motor.getEncoder();
    flywheelConfig = new SparkMaxConfig();

    @Override
    public void setVoltage(Voltage voltage) {
      motor.setVoltage(voltage);
    }

    @Override
    public void stop() {
      motor.stopMotor();
    }

    
}
