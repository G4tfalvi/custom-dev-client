package KexTools.event.events;

import KexTools.event.Event;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;

public class EventTeleport extends Event{
	
	public EventTeleport(C06PacketPlayerPosLook c06PacketPlayerPosLook, double var3, double var5, double var7, float var9, float var10) {
    	this.response = c06PacketPlayerPosLook;
    	
    	this.posX = var3;
    	this.posY = var5;
    	this.posZ = var7;
    	
    	this.yaw = var9;
    	this.pitch = var10;
	}
	private C03PacketPlayer response;
    private double posX;
    private double posY;
    private double posZ;
    private float yaw;
    private float pitch;

}
