package frc.robot.Subsystems.drive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.util.Units;

/** IO implementation for AHRS */
public class GyroIOAHRS implements GyroIO {

    private final AHRS gyro = new AHRS();

    public GyroIOAHRS() {
        reset();
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.connected = gyro.isConnected();
        inputs.yawPosition = gyro.getRotation2d();
        inputs.yawVelocityRadPerSec = Units.degreesToRadians(gyro.getRate());
    }

    @Override
    public void reset() {
        gyro.reset();;
    }
}