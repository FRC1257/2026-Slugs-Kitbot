//KickerIOSparkMax.java 

package frc.robot.subsystems.Kicker;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RevolutionsPerMinute;
import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.EncoderConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage; 
public class KickerIOSparkMax implements KickerIO {

 private SparkMax kickerMotor = new SparkMax(KickerConstants.KICKER_MOTOR_ID, SparkMax.MotorType.kBrushless);
 private SimpleMotorFeedforward feedforward;


/*
Implement the following: 
1. A new motor class 
2. Sparkmax Encoder and Closed loop config 
3.Update Inputs 
4.Set PID values 
5. Set Feedforward Mechanism Reseach Revlib API to figure out how to include feedforward mechanism in the closed loop config.  
*/

private final Spark kicker_motor=new Spark(0) 
private SimpleMotorFeedforward feedforward;   

 public KickerIOSparkMax() {
feedforward = new SimpleMotorFeedforward(KickerConstants.KICKER_KS, KickerConstants.KICKER_KV);




}

//copy from tanish 