package frc.robot.Commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constanst;
import frc.robot.Subsystems.drive.Drive;


public class TeleopAdvantage extends Command {    
    private Drive s_Swerve;    
    private DoubleSupplier translationSup;
    private DoubleSupplier strafeSup;
    private DoubleSupplier rotationSup;
    private BooleanSupplier slowMode;
    private BooleanSupplier middleMode;
    private boolean isFinished;
    private final double slowSpeed = 0.2;
    private final double slowRotation = 0.2;
    private final double middleSpeed = 0.65;
    private final double middleRotation = 0.65;
    double i = 0.0;
    /**
     * This is the command that dirves your swerve robot. When setting this up in robot container
     * use the {@link DriveTrain#setDefaultCommand}.
     * When putting in your paramenters use lamda code i.e. () -> {@link DoubleSupplier}, 
     * instead of just {@link DoubleSupplier}.
     * 
     * @param s_Swerve you drivetrain subsystem
     * @param translationSup the axies that controlls the foward/backwards movment of you robot
     * @param strafeSup the axies that controlls the side to side movment of you robot
     * @param rotationSup the axies that controlls the angle of your robot
     */
    public TeleopAdvantage(Drive s_Swerve, DoubleSupplier translationSup, DoubleSupplier strafeSup, 
    DoubleSupplier rotationSup) {
        this.s_Swerve = s_Swerve;
        addRequirements(s_Swerve);
        isFinished = false;
        this.translationSup = translationSup;
        this.strafeSup = strafeSup;
        this.rotationSup = rotationSup;
        this.slowMode = slowMode;
        // this.targetingUtil = targetingUtil; 
        this.middleMode = middleMode;
    }

    @Override
    public void execute() {
            double linearMagnitude =
              MathUtil.applyDeadband(
                  Math.hypot(translationSup.getAsDouble(), strafeSup.getAsDouble()), Constanst.stickDeadband);
          Rotation2d linearDirection =
              new Rotation2d(translationSup.getAsDouble(), strafeSup.getAsDouble());
          double omega = MathUtil.applyDeadband(rotationSup.getAsDouble(), Constanst.stickDeadband);

          // Square values
          linearMagnitude = linearMagnitude * linearMagnitude;
          omega = Math.copySign(omega * omega, omega);

          // Calcaulate new linear velocity
          Translation2d linearVelocity =
              new Pose2d(new Translation2d(), linearDirection)
                  .transformBy(new Transform2d(linearMagnitude, 0.0, new Rotation2d()))
                  .getTranslation();

          // Convert to field relative speeds & send command
          boolean isFlipped =
              DriverStation.getAlliance().isPresent()
                  && DriverStation.getAlliance().get() == Alliance.Red;
          s_Swerve.runVelocity(
              ChassisSpeeds.fromFieldRelativeSpeeds(
                  linearVelocity.getX() * s_Swerve.getMaxLinearSpeedMetersPerSec(),
                  linearVelocity.getY() * s_Swerve.getMaxLinearSpeedMetersPerSec(),
                  omega * s_Swerve.getMaxAngularSpeedRadPerSec(),
                  isFlipped
                      ? s_Swerve.getRotation().plus(new Rotation2d(Math.PI))
                      : s_Swerve.getRotation()));
  
    }

    @Override
    public void end(boolean interupted) {}

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}