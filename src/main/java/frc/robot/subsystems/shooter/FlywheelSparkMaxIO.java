package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.units.measure.Voltage;

public class FlywheelSparkMaxIO implements FlywheelIO {

  private final SparkMax motor;
  private final RelativeEncoder encoder;
  private final SparkMaxConfig flywheelConfig;

  public FlywheelSparkMaxIO() {
    motor = new SparkMax(FlywheelConstants.FLYWHEEL_MOTOR_ID, SparkMax.MotorType.kBrushless);
    encoder = motor.getEncoder();
    flywheelConfig = new SparkMaxConfig();


  }

  @Override
  public void setVoltage(Voltage voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void stop() {
    motor.stopMotor();
  }
    
}
