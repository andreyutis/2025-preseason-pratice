package frc.robot.Subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constanst;

public class FlagsSubsystem extends SubsystemBase {
    CANSparkMax GreenFlag;
    CANSparkMax RedFlag;
    CANSparkMax YellowFlag;
  /** Creates a new ExampleSubsystem. */
  public FlagsSubsystem() {
    GreenFlag = new CANSparkMax(Constanst.FlagsConstants.GreenFlagMotor, MotorType.kBrushless);
    RedFlag = new CANSparkMax(Constanst.FlagsConstants.RedFlagMotor, MotorType.kBrushless);
    YellowFlag = new CANSparkMax(Constanst.FlagsConstants.YellowFlagMotor, MotorType.kBrushless);
  }
  
  public void RunGreenFlag() {
    GreenFlag.set(Constanst.FlagsConstants.GreenFlagMotor);
  }

  public void RunRedFlag() {
    RedFlag.set(Constanst.FlagsConstants.RedFlagMotor);
  }

  public void YellowFlag() {
    YellowFlag.set(Constanst.FlagsConstants.YellowFlagMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  
}