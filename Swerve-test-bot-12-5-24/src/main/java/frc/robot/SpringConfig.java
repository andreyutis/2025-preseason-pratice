package frc.robot;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import frc.robot.Constanst.SwerveConstants.Mod0;
import frc.robot.Constanst.SwerveConstants.Mod1;
import frc.robot.Constanst.SwerveConstants.Mod2;
import frc.robot.Constanst.SwerveConstants.Mod3;
import frc.robot.Subsystems.drive.GyroIO;
import frc.robot.Subsystems.drive.GyroIOAHRS;
import frc.robot.Subsystems.drive.Module;
import frc.robot.Subsystems.drive.ModuleIOSim;
import frc.robot.Subsystems.drive.ModuleIOSparkMax;

import static frc.robot.Constanst.IS_REAL;

@Configuration
@ComponentScan(basePackages = "frc.robot")
public class SpringConfig implements InitializingBean {

    @Bean
    public Module module0() {
        return new Module(IS_REAL ? new ModuleIOSparkMax(Mod0.constants) : new ModuleIOSim(), 0, IS_REAL);
    }

    @Bean
    public Module module1() {
        return new Module(IS_REAL ? new ModuleIOSparkMax(Mod1.constants) : new ModuleIOSim(), 1, IS_REAL);
    }

    @Bean
    public Module module2() {
        return new Module(IS_REAL ? new ModuleIOSparkMax(Mod2.constants) : new ModuleIOSim(), 2, IS_REAL);
    }

    @Bean
    public Module module3() {
        return new Module(IS_REAL ? new ModuleIOSparkMax(Mod3.constants) : new ModuleIOSim(), 3, IS_REAL);
    }

    @Bean
    public GyroIO gyro() {
        return IS_REAL ? new GyroIOAHRS() : new GyroIO(){};
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Spring context is loaded");
    }

}
