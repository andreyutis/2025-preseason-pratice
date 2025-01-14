// Copyright 2021-2024 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;

/** IO implementation for Pigeon2 */
public class GyroIOPigeon2 implements GyroIO {
  private final AHRS pigeon;
  
 

  public GyroIOPigeon2(boolean phoenixDrive) {
    pigeon = new AHRS(SPI.Port.kMXP);
  }
  

  @Override
  public void updateInputs(GyroIOInputs inputs) {
    inputs.yawPosition = Rotation2d.fromDegrees(pigeon.getYaw());
    inputs.yawVelocityRadPerSec = Units.degreesToRadians(pigeon.getAngleAdjustment());

    
  }
}
