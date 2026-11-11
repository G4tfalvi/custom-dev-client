package KexTools.module.movement;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventMove;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.event.events.EventSendPacket;
import KexTools.event.events.EventStrafe;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import KexTools.packet.PacketRunnableWrapper;
import KexTools.packet.PacketRunnableWrapper.PacketRunnable;
import KexTools.packet.PacketUtil;
import KexTools.settings.Setting;
import KexTools.utils.MotionUtils;
import KexTools.utils.enums.ACType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class Bhop extends KexTools.module.Module {
	
	MotionUtils mu = new MotionUtils();
	private String mode = Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString();
	
	private boolean reset1;
    private double speed1;
	
    public Bhop() {
        super("BunnyHop", Keyboard.KEY_G, Category.MOVEMENT);
    }
    
    @Override
    public void setup() {
    	super.setup();
        ArrayList<String> options = new ArrayList<>();
        options.add("AAC");
        options.add("Minemora");
        options.add("Minemora Low");
        options.add("BlocksMC");
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Bhop Mode", this, "AAC", options));
    }
    
    @EventTarget
    public void onUpdate(EventMove event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("AAC")){
    		setDisplayName("BunnyHop §cAAC");
    		
    		double speed = 0.89d;
        	
        	float hDist = (float) ((MotionUtils.getBaseSpeed(ACType.AAC) + 0.2f) * 1 + speed / 5.0f);
        	
        	if (mc.thePlayer.isMovingOnGround()) {
                event.setMotionY(mc.thePlayer.motionY = 0.42D);
                hDist += 0.125;
            } else {
                hDist -= 0.36 * (hDist - MotionUtils.getMotion(ACType.AAC));
            }
        	MotionUtils.setMotion(event, hDist);
        	
    	}
    }
    
    @EventTarget
    public void eventUpdate(EventUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("Minemora")){
    		setDisplayName("BunnyHop §cMinemora");
    		if(Math.abs(mc.thePlayer.movementInput.moveStrafe) < 0.1f) {
        		mc.thePlayer.jumpMovementFactor = 0.026499F;
        	} else {
        		mc.thePlayer.jumpMovementFactor = 0.0244F;
        	}
        	if(mu.getSpeed() < 0.215f && !mc.thePlayer.onGround) {
        		mu.strafe(0.215F);
        	}
        	if(mc.thePlayer.onGround) {
        		mc.thePlayer.jump();
        		if(mc.thePlayer.isAirBorne) {
        			mu.strafe(mu.getSpeed());
        			if(mu.getSpeed() < 0.5F) {
        				mu.strafe(0.4849F);
        			}
        		}
        	}
    	}
    }
    
    @EventTarget
    public void preMotion(EventPreMotionUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("Minemora Low")){
    		if (!mu.isMoving()) return;
    		
        	if (mc.thePlayer.onGround) {
                event.setY(0.42F);
                mu.strafe(0.5102F + mu.speedPotionAmp(0.1));
                mc.timer.timerSpeed = 1.003f;
                mc.thePlayer.motionY = 0.123F;
            }
    	}
    }
    
    @EventTarget
    public void onStrafe(EventStrafe event) {
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("BlocksMC")){
    		setDisplayName("BunnyHop §cBlocksMC");
    		final double base = mu.getAllowedHorizontalDistance();
    		final boolean potionActive = mc.thePlayer.isPotionActive(Potion.moveSpeed);
    	
    		if (mu.isMoving2()) {
    			switch (mc.thePlayer.offGroundTicks) {
    			case 0:
    				mc.thePlayer.motionY = mu.jumpBoostMotion(0.42f);
    				speed1 = base * (potionActive ? 2 : 2.15);
    				break;
    			case 1:
    				speed1 -= 0.8 * (speed1 - base);
    				break;

    			default:
    				speed1 -= speed1 / MotionUtils.BUNNY_FRICTION;
    				break;
    			}
    			reset1 = false;
        	
    		} else if (!reset1) {
    			speed1 = 0;

    			reset1 = true;
    			speed1 = mu.getAllowedHorizontalDistance();
    		}
        
    		if (mc.thePlayer.isCollidedHorizontally) {
    			speed1 = mu.getAllowedHorizontalDistance();
    		}

    		event.setSpeed(Math.max(speed1, base), Math.random() / 2000);
        }
    }
    
    @EventTarget
    public void onPreMotion(EventPreMotionUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("BlocksMC")){
    		setDisplayName("BunnyHop §cBlocksMC");
    		
    		if (!mu.isMoving2()) {
                event.setX(event.getPosX() + (Math.random() - 0.5) / 100);
                event.setZ(event.getPosZ() + (Math.random() - 0.5) / 100);
            }

            PacketUtil.sendNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
    	}
    }

	@Override
    public void onDisable() {
    	super.onDisable();
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("BlocksMC")){
    		speed1 = 0;
    	}
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("AAC")){
    		PacketRunnableWrapper.preventLagback();
    	}
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("Minemora Low")){
    		mu.stop();
    	}
    	
    }
    
	@Override
    public void onEnable() {
		super.onEnable();
	}
	
    @EventTarget
    public void onPacket(EventSendPacket event) {
    	final Packet<?> p = event.getPacketList();
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("BlocksMC")){
    		if (event.getPacketList() instanceof C0BPacketEntityAction) {
                event.setCancelled(true);
            }
    	}
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Bhop Mode").getValString().equalsIgnoreCase("AAC")){
    		if (mc.thePlayer.ticksExisted <= 1)
    			return;
    		PacketRunnableWrapper.aacExploit.accept(event);
    	}
    }
}
	