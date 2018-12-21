package org.usfirst.frc.team3388.robot;

import edu.flash3388.flashlib.robot.FlashRobotUtil;
import edu.flash3388.flashlib.robot.HIDInterface;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotState;

public class FRCHIDInterface implements HIDInterface {

	private final DriverStation mDs;
	
	public FRCHIDInterface(DriverStation ds) {
		mDs = ds;
	}
	
	@Override
	public double getHIDAxis(int stick, int axis) {
		if(FlashRobotUtil.inEmergencyStop() || RobotState.isDisabled()) {
			return 0.0;
		}
		
		return mDs.getStickAxis(stick, axis);
	}

	@Override
	public boolean getHIDButton(int stick, int button) {
		if(FlashRobotUtil.inEmergencyStop() || RobotState.isDisabled()) {
			return false;
		}
		
		return mDs.getStickButton(stick, button);
	}

	@Override
	public int getHIDPOV(int stick, int pov) {
		if(FlashRobotUtil.inEmergencyStop() || RobotState.isDisabled()) {
			return 0;
		}
		
		return mDs.getStickPOV(stick, pov);
	}

	@Override
	public boolean isAxisConnected(int stick, int axis) {
		return mDs.getStickAxisCount(stick) > axis;
	}

	@Override
	public boolean isButtonConnected(int stick, int button) {
		return mDs.getStickButtonCount(stick) >= button;
	}

	@Override
	public boolean isHIDConnected(int stick) {
		return mDs.getStickAxisCount(stick) > 0;
	}

	@Override
	public boolean isPOVConnected(int stick, int pov) {
		return mDs.getStickPOVCount(stick) > pov;
	}

}
