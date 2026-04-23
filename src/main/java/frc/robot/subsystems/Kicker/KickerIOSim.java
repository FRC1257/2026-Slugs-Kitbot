package frc.robot.subsystems.Kicker;

import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

public class KickerIOSim implements KickerIO {
    private SparkMax kickerMotor = 
        new SparkMax(KickerConstants.KICKER_MOTOR_ID, SparkMax.MotorType.kBrushless);
    private PIDController pidControllerSim =
      new PIDController(0, KickerConstants.kKickerConstraints);
        private SimpleMotorFeedforward feedforward;



    public KickerIOSim() {

    }


}
///