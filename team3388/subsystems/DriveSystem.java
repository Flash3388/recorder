package edu.frc.team3388.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;
import Recorder.Recordable;
import edu.flash3388.flashlib.robot.systems.VoltageScalable;

import java.util.function.BinaryOperator;

public class DriveSystem extends Subsystem implements TankDriveSystem,Recordable{
    TalonSRX fr;
    TalonSRX fl;
    TalonSRX rr;
    TalonSRX rl;

    public DriveSystem(int fr, int fl, int rr, int rl) {
        this.fr = new TalonSRX(fr);
        this.fl = new TalonSRX(fl);
        this.rr = new TalonSRX(rr);
        this.rl = new TalonSRX(rl);

    }

    @Override
    public void tankDrive(double v, double v1) {

    }

    @Override
    public void stop() {

    }

    @Override
    public String writeScript() {


        return Double.toHexString(fr.getMotorOutputPercent())+","+Double.toHexString(fl.getMotorOutputPercent());
    }

    @Override
    public void readScript(String val) {
        fr.set(ControlMode.PercentOutput,Double.parseDouble(val.split(",")[0]));
        fl.set(ControlMode.PercentOutput,Double.parseDouble(val.split(",")[1]));
        rr.set(ControlMode.PercentOutput,Double.parseDouble(val.split(",")[0]));
        rl.set(ControlMode.PercentOutput,Double.parseDouble(val.split(",")[1]));
    }
}
