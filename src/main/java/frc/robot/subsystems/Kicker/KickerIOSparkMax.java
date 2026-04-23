//KickeriOSparkmax
package frc.robot.subsystems.Kicker;


import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;


import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;
import com.ctre.phoenix6.swerve.utility.WheelForceCalculator.Feedforwards;
import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.FeedForwardConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.config.BaseConfig;
import com.revrobotics.spark.config.ClosedLoopConfig;


import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.AccelerationUnit;
import edu.wpi.first.units.measure.Acceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Velocity;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.subsystems.Kicker.KickerIO.KickerIOInputs;


public class KickerIOSparkMax implements KickerIO{
    private SparkMax kickerMotor = new SparkMax(KickerConstants.KICKER_MOTOR_ID, SparkMax.MotorType.kBrushless);
    
    public KickerIOSparkMax(){
        kickerMotor.configure(
            new SparkMaxConfig()
                .idleMode(SparkBaseConfig.IdleMode.kCoast)
                .voltageCompensation(0)
                .smartCurrentLimit(0)
                .inverted(false)
            .apply(new EncoderConfig()
                .positionConversionFactor(Math.PI * 2.0/3)
                .velocityConversionFactor((Math.PI * 2.0/3) / 60.0)
                .uvwMeasurementPeriod(10)
                .uvwAverageDepth(2))
            //Closed loop config determines how the sensors are to maintain a key output, mainly attributed to the pid controller and feedforward mechanisms
            .apply(new ClosedLoopConfig()
                .p(KickerConstants.KICKER_KP)
                .i(KickerConstants.KICKER_KI)
                .d(KickerConstants.KICKER_KD))
                .feedForward
                    .kS(Volts)//Eliminates the dead zone that pay lead to a ball being stuck because of friction, gives the perfect amount of force
                    .kV(Velocity, ClosedLoopSlot.kSlot0)
                    .kA(Acceleration),
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters;
            double last_AngV=inputs.kickerAngularVelocity;
        }

        @Override
        public void updateInputs(KickerIOInputs inputs){
            inputs.kickerConnected = kickerMotor.getLastError() == REVLibError.kOk;
            inputs.kickerVoltage = Volts.of(kickerMotor.getAppliedOutput() * 12.0);
            inputs.kickerAngularVelocity = RadiansPerSecond.of(kickerMotor.getEncoder().getVelocity());
            inputs.kickerAngle = Radians.of(kickerMotor.getEncoder().getPosition());
            inputs.kickerCurrent = Amps.of(kickerMotor.getOutputCurrent());
        }

        @Override
        public void setVoltage(Voltage voltage) {
            kickerMotor.setVoltage(voltage);
        }
        @Override
        public void setVelocity(AngularVelocity velocity) {
            kickerMotor.getClosedLoopController(). setSetpoint(
            velocity.in(RPM),
            ControlType.kVelocity,
            ClosedLoopSlot.kSlot0,        
            ClosedLoopConfig.feedForward.calculate(inputs.kickerVoltage,(inputs.kickerAngularVelocity-last_AngV)/0.002));
        }  
        @Override
        public void stop() {
            kickerMotor.stopMotor();
        }    
        @Override
        public void setPID(double kP, double kI, double kD) {
            kickerMotor.configure(
                new SparkMaxConfig()
                .apply(new ClosedLoopConfig()
                    .p(kP)
                    .i(kI)
                    .d(kD)),
            ResetMode.kNoResetSafeParameters,
            PersistMode.kPersistParameters);
        }
        @Override
        public void setFF(double kS,double kV, double kA){
            .kS(Volts)//Eliminates the dead zone that pay lead to a ball being stuck because of friction, gives the perfect amount of force
            .kV(Velocity, ClosedLoopSlot.kSlot0)
            .kA(Acceleration),
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters,
        }
    }
}
