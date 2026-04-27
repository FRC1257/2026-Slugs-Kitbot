package frc.robot.subsystems.shooter;

import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;

import static edu.wpi.first.units.Units.*;

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
      controller = motor.getClosedLoopController();

      flywheelConfig.closedLoop.pid(FlywheelConstants.FLYWHEEL_KP, FlywheelConstants.FLYWHEEL_KI, FlywheelConstants.FLYWHEEL_KD);
      motor.configure(flywheelConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }


      @Override
      public void setVoltage (Voltage voltage){
          motor.setVoltage(voltage);
      }

      @Override
      public void setVelocity (AngularVelocity RPM) {
        double feedforwardVolts = feedforward.calculate(RPM);
        controller.setSetpoint(RPM), SparkBase.ControlType.kVelocity, ClosedLoopSlot.kSlot0, feedforwardVolts);
        // controltype.kvelocity --> set control type to velocity, closedloopslot.kslot0 --> use pid in slot 0 (maybe needs to be diff idk where pid is stored)
      }

      @Override
      public void stop () {
          motor.stopMotor();
      }

      @Override
      public void setPID(double kp, double ki, double kd) {
        motor.configure(new SparkMaxConfig().apply(ClosedLoopConfig().pid(kp, ki, kd)), ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        /* resetmode makes sure that previous config will not be deleted before this one is applied --> good for small changes or smth
            persistmode makes sure that config is saved if battery is unplugged/robot lose power (so that the pid config will still be there)
         */
      }
      @Override
      public void setFF ( double ks, double kv) {
          feedforward = new SimpleMotorFeedforward(ks, kv);

      }

      @Override
      public void updateInputs(FlywheelIOInputs inputs) {
        inputs.flywheelAngularVelocity = RPM.of(encoder.getVelocity());
        inputs.flywheelVoltage = Volts.of(motor.getAppliedOutput() * motor.getBusVoltage());
        inputs.flywheelCurrent = Amps.of(motor.getOutputCurrent());
        inputs.flywheelTemperature = Celsius.of(motor.getMotorTemperature());
      }


}
