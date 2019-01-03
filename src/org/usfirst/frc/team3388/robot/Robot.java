package org.usfirst.frc.team3388.robot;

import org.usfirst.frc.team3388.robot.subsystems.DriveSystem;

import edu.flash3388.flashlib.robot.InstantAction;
import edu.flash3388.flashlib.robot.RobotFactory;
import edu.flash3388.flashlib.robot.frc.IterativeFRCRobot;
import edu.flash3388.flashlib.robot.hid.XboxController;
import edu.wpi.first.wpilibj.DriverStation;
import recorder.PlayingRunner;
import recorder.RecordingRunner;

public class Robot extends IterativeFRCRobot {
	
	DriveSystem drive;
	RecordingRunner rec;
	PlayingRunner player;
	public static XboxController xbox;
	
	boolean shouldrec = false;
	
	@Override
	protected void preInit(RobotInitializer initializer) {
		
	}
	
    @Override
    protected void initRobot() {
    	RobotFactory.setHIDInterface(new FRCHIDInterface(DriverStation.getInstance()));
    	
    	drive = new DriveSystem(7,3,6,1);
    	rec = new RecordingRunner("test",drive);
    	player = new PlayingRunner("test",drive);
    	xbox = new XboxController(0);
    	
    	xbox.A.whenPressed(new InstantAction() {
			@Override
			protected void execute() {
				shouldrec = !shouldrec;
    			if(shouldrec)
    				rec.record();
    			else
    				rec.stop();
			}
		});
    	
    	xbox.X.whenPressed(new InstantAction() {
			@Override
			protected void execute() {
    			player.play();
    		}
		});
    }

    @Override
    protected void disabledInit() {

    }

    @Override
    protected void disabledPeriodic() {

    }

    @Override
    protected void teleopInit() {

    }

    @Override
    protected void teleopPeriodic() {
    	if(player.isFinished())
    		drive.tankDrive(-xbox.LeftStick.getY(), -xbox.RightStick.getY());
    }

    @Override
    protected void autonomousInit() {

    }

    @Override
    protected void autonomousPeriodic() {

    }
}
