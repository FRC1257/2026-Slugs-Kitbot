package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Flywheel extends SubsystemBase {
    private final FlywheelIO io;
    private final FlywheelIOInputsAutoLogged inputs=new FlywheelIOInputsAutoLogged();

    public Flywheel(FlywheelIO io) {
        this.io=io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
    }
}
