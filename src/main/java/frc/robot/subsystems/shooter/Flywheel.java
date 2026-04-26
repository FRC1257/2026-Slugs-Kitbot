package frc.robot.subsystems.shooter;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.util.misc.LoggedTunableNumber;
import org.littletonrobotics.junction.Logger;

import java.util.function.Supplier;

public class Flywheel extends SubsystemBase {
    /* imported from the flywheel.java from 2026, but bc i dont wanna write it all
    * all it does it make it so we can tune the values in real time and no need to redeploy code i think
     */
    private static final LoggedTunableNumber Kp = new LoggedTunableNumber("Flywheel/Kp", FlywheelConstants.FLYWHEEL_KP);
    private static final LoggedTunableNumber Ki = new LoggedTunableNumber("Flywheel/Ki", FlywheelConstants.FLYWHEEL_KI);
    private static final LoggedTunableNumber Kd = new LoggedTunableNumber("Flywheel/Kd", FlywheelConstants.FLYWHEEL_KD);

    private static final LoggedTunableNumber Ks = new LoggedTunableNumber("Flywheel/Ks", FlywheelConstants.FLYWHEEL_KS);
    private static final LoggedTunableNumber Kv = new LoggedTunableNumber("Flywheel/Kv", FlywheelConstants.FLYWHEEL_KV);

    private final FlywheelIO io;
    private final FlywheelIOInputsAutoLogged inputs=new FlywheelIOInputsAutoLogged();

    public Flywheel(FlywheelIO io) {
        this.io=io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Flywheel",inputs);

        if(Kp.hasChanged(hashCode()) || Ki.hasChanged(hashCode()) || Kd.hasChanged(hashCode())) {
            io.setPID(Kp.get(), Ki.get(), Kd.get());
        }

        if(Ks.hasChanged(hashCode()) || Kv.hasChanged(hashCode())) {
            io.setFF(Ks.get(), Kv.get());
        }
        /*
        if the values change, it will redo the feedforward/pid
        copy from 2026 code btw, bc i dont know why it is formatted
        pretty sure all hashcode does is return same value if nothing changed
         */


    }

    public AngularVelocity getAngularVelocity() {
        return inputs.flywheelAngularVelocity;
    }

    public void setVelocity(AngularVelocity velocityRadsPerSec) {
        io.setVelocity(velocityRadsPerSec);
    }

    public void setVoltage(Voltage voltage) {
        io.setVoltage(voltage);
    }

    public void stop() {
        io.stop();
    }

    public Command runVoltageCommand(Supplier<Voltage> voltage) {
        return runEnd(()->setVoltage(voltage.get()), this::stop);
    }

    private Command runVelocityCommand(Supplier<AngularVelocity> velocityRadsPerSec) {
        return runEnd(
                ()->setVelocity(velocityRadsPerSec.get()),
                this::stop
        ); //maybe if need the goofy goal thing in example ill put that
        //goofy
    }

    public Command stopCommand() {
        return runOnce(this::stop).withName("Flywheel/StopCommand");
    }
}
