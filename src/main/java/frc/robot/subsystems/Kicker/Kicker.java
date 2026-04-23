

package frc.robot.subsystems.Kicker;


import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;


import java.util.function.Supplier;


import org.littletonrobotics.junction.Logger;


import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Resistance;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.util.misc.LoggedTunableNumber;


package frc.robot.subsystems.Kicker;


import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;


import java.util.function.Supplier;


import org.littletonrobotics.junction.Logger;


import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.filter.Debouncer.DebounceType;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Robot;
import frc.robot.util.misc.LoggedTunableNumber;


public class Kicker extends SubsystemBase {


    private static final LoggedTunableNumber Kp = new LoggedTunableNumber("Kicker/Kp", KickerConstants.KICKER_KP);
    private static final LoggedTunableNumber Ki = new LoggedTunableNumber("Kicker/Ki", KickerConstants.KICKER_KI);
    private static final LoggedTunableNumber Kd = new LoggedTunableNumber("Kicker/Kd", KickerConstants.KICKER_KD);
   
    private static final LoggedTunableNumber Ks = new LoggedTunableNumber("Kicker/Ks", KickerConstants.KICKER_KS);
    private static final LoggedTunableNumber Kv = new LoggedTunableNumber("Kicker/Kv", KickerConstants.KICKER_KV);


    private static final LoggedTunableNumber tolerance = new LoggedTunableNumber("Kicker/Tolerance", KickerConstants.KICKER_VELOCITY_TOLERANCE);


    private static final LoggedTunableNumber kickerIntakeVelocity = new LoggedTunableNumber("Kicker/IntakeVelocity", KickerConstants.KICKER_INTAKE_VELOCITY.magnitude());
    private static final LoggedTunableNumber kickerOuttakeVelocity = new LoggedTunableNumber("Kicker/OuttakeVelocity", KickerConstants.KICKER_OUTTAKE_VELOCITY.magnitude());


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


    disconnected.set(!connectedDebouncer.calculate(inputs.kickerConnected));


    Robot.controller::GetBatteryVoltage(12, Resistance, Current);
   
    public Command runVoltageCommand(Supplier<Voltage> voltage) {
        return runEnd(() -> io.setVoltage(voltage.get()), io::stop)
            .withName(getName() + "/RunVoltageCommand");
    }
    public Command runVelocityCommand(Supplier<AngularVelocity> velocity) {
        return runEnd(() -> {
            goalVelocity = velocity.get().in(RadiansPerSecond);
            io.setVelocity(velocity.get());
        }, io::stop)
            .withName(getName() + "/RunVelocityCommand");
    }


    public Command runStatic() {
        return runVoltageCommand(() -> Volts.of(Ks.get()));
    }
    public Command stopCommand(){
        return runOnce(()->end());
    }
    public Trigger kickerTrigger = new Trigger(robotPeriodic,Supplier<disconnected>Boolean);
   
    public Trigger atGoalVelocity(){
        return new kickerTrigger(
            ()->Math.abs(inputs.kickerAngularVelocity.in(RadiansPerSecond) - goalVelocity)
            < tolerance.get()
        );
    }
}




