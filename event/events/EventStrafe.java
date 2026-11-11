package KexTools.event.events;

import KexTools.event.Event;
import KexTools.utils.MotionUtils;
import KexTools.utils.interfaces.InstanceAccess;

public class EventStrafe extends Event implements InstanceAccess{

	private float forward;
    private float strafe;
    private float friction;
    private float yaw;
    
    MotionUtils moveUtil = new MotionUtils();
    
    public EventStrafe(float forward2, float strafe2, float friction2, float rotationYaw) {
		this.forward = forward2; this.strafe = strafe2; this.friction = friction2; this.yaw = rotationYaw;
	}

	public void setSpeed(final double speed, final double motionMultiplier) {
    	setFriction((float) (getForward() != 0 && getStrafe() != 0 ? speed * 0.98F : speed));
        mc.thePlayer.motionX *= motionMultiplier;
        mc.thePlayer.motionZ *= motionMultiplier;
    }
    
    public void setSpeed(final double speed) {
        setFriction((float) (getForward() != 0 && getStrafe() != 0 ? speed * 0.98F : speed));
		moveUtil.stop();
    }
    
    public float setFriction(float friction) {
    	this.friction = friction;
    	return this.friction;
    }
    public float getForward() {
    	return this.forward;
    }
    public float getStrafe() {
    	return this.strafe;
    }
    public float setYaw() {
    	return this.yaw;
    }
	public float getFriction() {
		return friction;
	}
	public float getYaw() {
		return yaw;
	}

	public void setForward(int i) {
		this.forward = i;
	}

	public void setStrafe(int i) {
		this.strafe = i;
	}

	public void setYaw(float x) {
		this.yaw = x;
	}
}
