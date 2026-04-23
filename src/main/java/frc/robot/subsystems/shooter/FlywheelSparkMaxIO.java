package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

public class FlywheelSparkMaxIO implements FlywheelIO {

  private final SparkMax motor;
  private final RelativeEncoder encoder;
  private final SparkMaxConfig flywheelConfig;
  private final SparkClosedLoopController controller;
  private final SimpleMotorFeedforward feedforward;

  public FlywheelSparkMaxIO() {
      motor = new SparkMax(FlywheelConstants.FLYWHEEL_MOTOR_ID, SparkMax.MotorType.kBrushless);
      encoder = motor.getEncoder();
      flywheelConfig = new SparkMaxConfig();
      feedforward = new SimpleMotorFeedforward(FlywheelConstants.FLYWHEEL_KS, FlywheelConstants.FLYWHEEL_KV);

  }
      @Override
      public void setVoltage (Voltage voltage){
          motor.setVoltage(voltage);
      }

      @Override
      public void stop () {
          motor.stopMotor();
      }

      @Override
      public void setFF ( double ks, double kv){
          feedforward = new SimpleMotorFeedforward(ks, kv);

      }


}
