package frc.robot.subsystems.shooter;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

import java.util.function.Supplier;

public class Flywheel extends SubsystemBase {
    private final FlywheelIO io;
    private final FlywheelIOInputsAutoLogged inputs=new FlywheelIOInputsAutoLogged();

    public Flywheel(FlywheelIO io) {
        this.io=io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Flywheel",inputs);
    }

    public AngularVelocity getVelocity() {
        return inputs.rpm;
    }

    public void setVelocity(AngularVelocity rpm) {
        io.setVelocity(rpm);
    }

    public void setVoltage(Voltage voltage) {
        io.setVoltage(voltage);
    }

    public void stop() {
        io.stop();
    }

    public Command runVoltage(Supplier<Voltage> voltage) {
        return runEnd(()->setVoltage(voltage.get()), this::stop);
    }

    private Command runVelocity(Supplier<AngularVelocity> rpm) {
        return runEnd(
                ()->setVelocity(rpm.get()),
                this::stop
        ); //maybe if need the goofy goal thing in example ill put that
        //goofy
    }

    public Command commandStop() {
        return run(this::stop);
    }
}
