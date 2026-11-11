package KexTools.event.events;

import KexTools.event.Event;

public class EventPreMotionUpdate extends Event {
    private float yaw, pitch;
    private boolean ground;
    public double x, y, z;

    private final boolean isPre;
    
    public EventPreMotionUpdate(float yaw, float pitch, boolean ground, double x, double y, double z) {
    	this(true);
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public EventPreMotionUpdate(boolean isPre) {
        this.isPre = isPre;
    }
    
    public double setY() {
    	return y;
    }
    
    public double setX() {
    	return x;
    }
    
    public double setZ() {
    	return z;
    }
    
    public double getPosX() {
        return x;
    }

    public double getPosY() {
        return y;
    }

    public double getPosZ() {
        return z;
    }
    
    public float getYaw() {
        return yaw;
    }
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean onGround() {
        return ground;
    }
    public void setGround(boolean ground) {
        this.ground = ground;
    }

	public void setY(double d) {
		this.y = d;
	}

	public void setX(double d) {
		this.x = d;
	}

	public void setZ(double d) {
		this.z = d;
	}

	public boolean isPre() {
        return isPre;
    }
}
