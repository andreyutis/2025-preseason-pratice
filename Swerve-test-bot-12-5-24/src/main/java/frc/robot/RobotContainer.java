// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import org.springframework.stereotype.Component;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.DriveTrain;
import frc.robot.Commands.TelopSwerve;
import frc.robot.Constanst.JoystickConstants;
import frc.robot.Subsystems.*;

// @Component
public class RobotContainer {

  /* Test mode choosers */
    /* Initail */
      private final SendableChooser<String> TestMode = new SendableChooser<>();
      private final String Xbox = "Use Xbox controller";
      private final String Input = "Use Inputs";
      private String TestModeSelected;
    

  /* Controllers */
    private final XboxController driver = new XboxController(JoystickConstants.DRIVER_USB);
    private final Joystick operator = new Joystick(JoystickConstants.OPERATOR_USB);

  /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, JoystickConstants.BACK_BUTTON);

  /* Subsystems */
    private final LimelightSubsystem limelight = new LimelightSubsystem();
    private final DriveTrain s_swerve = new DriveTrain();
    private final TestMode test = new TestMode();

  /* Pathplanner stuff */
    private final SendableChooser<Command> autoChoosers;

  public RobotContainer() {
    
    /* Starting the Test Mode selectors*/
    if(RobotState.isTest()){
      test.start();
      TestMode.setDefaultOption("Use Xbox controller", Xbox);
      TestMode.addOption("Use Inputs", Input);
      TestModeSelected = TestMode.getSelected();
      /* Getting values for test mode from smartdashboard */
          SmartDashboard.putNumber("Translation", 0);
          SmartDashboard.putNumber("Translation", 0);
        
    }
    autoChoosers = AutoBuilder.buildAutoChooser("Tuning auto");
    double jiggle_count = SmartDashboard.getNumber("Advancer Jiggle Number Auto", 5);
      s_swerve.setDefaultCommand(
        new TelopSwerve(
          s_swerve,
          () -> -driver.getLeftY(),
          () -> -driver.getLeftX(), 
          () -> -driver.getRightX()
          )
      );
    
    
    configureBindings();
      /* Intake */
        // NamedCommands.registerCommand("Intake", new IntakeLoad(intake, advancer));
        // NamedCommands.registerCommand("Outtake", new IntakeOut(intake, advancer));
      /* Shooter Commands*/
        // NamedCommands.registerCommand("Speaker Shot", new SpeakerShoot(shooter_, advancer));
        // NamedCommands.registerCommand("Lob shot", new ShooterLob(shooter_, advancer));
        // NamedCommands.registerCommand("Rev Shooter", new AutoRevShooter(shooter_));
      /* Advancer Commands */   
        // NamedCommands.registerCommand("Advancer Jiggle", new AdvancerJiggle(advancer, jiggle_count));

        SmartDashboard.putData("Auto Chooser", autoChoosers);
  }
  
  public void testPeriodic() {
   switch (TestModeSelected) {
        case Input:
          s_swerve.setDefaultCommand(
            new TelopSwerve(
              s_swerve,
              () -> test.translate(),
              () -> test.strafe(),
              () -> test.rotate()
              )
          );


          break;
      
        default:
          s_swerve.setDefaultCommand(
            new TelopSwerve(
              s_swerve,
              () -> -driver.getLeftY(),
              () -> -driver.getLeftX(), 
              () -> -driver.getRightX()
              )
          );

          break;
      }
  }

  private void configureBindings() {
    
    /* Driver Controls */
      zeroGyro.onTrue (new InstantCommand(() -> s_swerve.ResetDrives()));

    /* Operator Controls */
      /* Shooter Controls 
        new JoystickButton (operator, JoystickConstants.GREEN_BUTTON).onTrue(new SpeakerShoot(shooter_, advancer));
        new Trigger (new JoystickButton(operator, JoystickConstants.GREEN_BUTTON)).and(
          new Trigger(() -> (advancer.LowerBeam.get()))).onTrue(new ParallelCommandGroup(new ShooterIntake(shooter_), new RetractToBottom(advancer)));
        new Trigger(() -> limelight.InRange(5)).and(new Trigger(() -> (advancer.LowerBeam.get())))
          .onTrue(new AutoRevShooter(shooter_));
        new JoystickButton (operator, JoystickConstants.BLUE_BUTTON).onTrue(new ShooterLob(shooter_, advancer));
        new JoystickButton (operator, JoystickConstants.YELLOW_BUTTON).onTrue(new ChangeShot(shooter_, advancer));
      /* Intake Controls 
        new JoystickButton (operator, JoystickConstants.RIGHT_BUMPER).onTrue(new IntakeLoad(intake, advancer));
        new JoystickButton (operator, JoystickConstants.LEFT_BUMPER).onTrue(new IntakeOut(intake, advancer));
      /* Advancer stuff */
        // new JoystickButton (operator, JoystickConstants.START_BUTTON).onTrue(new AdvancerJiggle(advancer, SmartDashboard.getNumber("Advancer Jiggle Number Teleop", 5)));
  }

  public Command getAutonomousCommand() {
    return autoChoosers.getSelected();
  }
}
