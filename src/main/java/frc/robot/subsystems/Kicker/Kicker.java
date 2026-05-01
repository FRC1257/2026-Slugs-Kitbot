//Kicker.java          
package frc.robot.subsystems.Kicker;


import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;


import java.util.function.DoubleSupplier;
import java.util.function.Supplier;


import org.littletonrobotics.junction.Logger;


import com.revrobotics.spark.SparkClosedLoopController;


import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.util.misc.LoggedTunableNumber;




public class Kicker extends SubsystemBase {
   
SparkClosedLoopController pid_controller = KickerConstants.KICKER_MOTOR_ID.getClosedLoopController();
PowerDistribution Pd = new PowerDistribution();
int MotorThreshold= 0; //add this to the constants file


    private final KickerIO io;
    private KickerIOInputsAutoLogged inputs = new KickerIOInputsAutoLogged();


    private final Debouncer connectedDebouncer =
        new Debouncer(0.5, DebounceType.kFalling);
   
    private final Alert disconnected;


    private double goalVelocity = 0.0;


    public Kicker(KickerIO io) {
        this.io = io;


        disconnected = new Alert("KICKER MOTOR DISCONNECTED", AlertType.kError);
       
        if(disconnected){
             Logger.recordOutput("",disconnected);


    }  
}
   @Override
   public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs(getName(), inputs);

        if(Kp.hasChanged(hashCode()) || Ki.hasChanged(hashCode()) || Kd.hasChanged(hashCode())) {
            io.setPID(Kp.get(), Ki.get(), Kd.get());
        }

        if(Ks.hasChanged(hashCode()) || Kv.hasChanged(hashCode())||Ka.hasChanged(hashCode())) {
            io.setFF(Ks.get(), Kv.get(),Ka.get());
        }

        disconnected.set(!connectedDebouncer.calculate(inputs.kickerLoaded));
   }

    public void getBatteryVoltage(){
       double BatteryVoltage=RobotController.getBatteryVoltage();
       LoggerrecordOutput("Battery Voltage", BatteryVoltage);//used wrong type of logger.output here need to change later
    }
    public void getTotalCurrent(){  //don't really know if this will have use but I don't think it'll hurt to put it here
        double Current = Pd.getTotalCurrent();
        Logger.recordOutput("Current", DoubleSupplier<Current>Current);
    }
    public Command runVoltageCommand(Supplier<Voltage> voltage) {
        return runEnd(() -> io.setVoltage(voltage.get()), io::stop)
            .withName(getName() + "/RunVoltageCommand");
    }
    public Command runVelocityCommand(Supplier<AngularVelocity> velocity) {
        return runEnd(() -> {
            goalVelocity = velocity.get().in(RPM);
            io.setVelocity(goalVelocity);
        }, io::stop)
            .withName(getName() + "/RunVelocityCommand");
    }


    public Command runStatic() {
        return runVoltageCommand(() -> Volts.of(Ks.get()));
    }
    public Command stopCommand() {
        return runOnce(io::stop)
            .withName(getName() + "/StopCommand");
    }
    public Trigger atGoalVelocity() {
        return new Trigger(()-> Math.abs(KickerConstants.KICKER_KA.in(RadiansPerSecond)-goalVelocity<tolerance.get());
        )
    }
   
    public Trigger isJammed() {
      return new Trigger(
       () -> inputs.kickerAngularVelocity.lte(KickerConstants.KICKER_JAMMED_VELOCITY) && inputs.kickerCurrent.gte(KickerConstants.KICKER_JAMMED_CURRENT)
        ).debounce(KickerConstants.KICKER_JAMMED_TIME, DebounceType.kRising);
     
    }  


}
}