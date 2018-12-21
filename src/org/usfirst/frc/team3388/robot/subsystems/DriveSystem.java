package org.usfirst.frc.team3388.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.flash3388.flashlib.robot.Subsystem;
import edu.flash3388.flashlib.robot.systems.TankDriveSystem;
import recorder.Player;
import recorder.Frame;
import recorder.Recorder;

public class DriveSystem extends Subsystem implements TankDriveSystem,Recorder,Player{
    private final TalonSRX fr;
    private final TalonSRX fl;
    private final TalonSRX rr;
    private final TalonSRX rl;

    public DriveSystem(int fr, int fl, int rr, int rl) {
        this.fr = new TalonSRX(fr);
        this.fl = new TalonSRX(fl);
        this.rr = new TalonSRX(rr);
        this.rl = new TalonSRX(rl);
    	this.fr.setInverted(true);
    	this.rr.setInverted(true);

    }

    @Override
    public void tankDrive(double left, double right) {
    	fr.set(ControlMode.PercentOutput, right);
    	rr.set(ControlMode.PercentOutput, right);

    	fl.set(ControlMode.PercentOutput, left);
    	rl.set(ControlMode.PercentOutput, left);
    }

    @Override
    public void stop() {

    }

    @Override
    public Frame capture() {
        return new Frame(Double.toHexString(fr.getMotorOutputPercent())+","+Double.toHexString(fl.getMotorOutputPercent()));
    }

	@Override
	public void play(Frame frame) {
		String val = frame.getData();
		tankDrive(Double.parseDouble(val.split(",")[1]), Double.parseDouble(val.split(",")[0]));
	}
}
