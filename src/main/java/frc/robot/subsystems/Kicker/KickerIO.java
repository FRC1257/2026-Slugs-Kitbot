public class KickerIO {
    public static class KickerIOInputs {
        public boolean kickerLoaded = false;// this was kicker connected i think thats what it means
        public Voltage voltage = Voltage.of(0.0); //Wrapper class for voltage is like double tho
        public AngularVelocity angularVelocity = RadiansPerSecond.of(0.0); //idk
        public Angle angle = Radians.of(0.0); 
        public Currrent kickerCurrent =  Amps.of(0.0); //
    }
    public default void updateInputs(KickerIOInputs inputs) {}
    public default void setVoltage(Voltage voltage) {}
    public default void setVelocity(AngularVelocity velocity) {}
    public default void stop() {}
    public default void setVelocity(KickerIOInputs inputs) {}
    public default void setVelocity(KickerIOInputs inputs) {}
    public default void setVelocity(KickerIOInputs inputs) {}

}
