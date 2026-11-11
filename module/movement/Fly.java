package KexTools.module.movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.component.DamageComponent;
import KexTools.event.EventTarget;
import KexTools.event.events.EventBlockCollision;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.event.events.EventReceivePacket;
import KexTools.event.events.EventSemiBlink;
import KexTools.event.events.EventServerKick;
import KexTools.event.events.EventTeleport;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import KexTools.packet.PacketUtil;
import KexTools.settings.Setting;
import KexTools.utils.MotionUtils;
import KexTools.utils.PlayerUtils;
import KexTools.utils.TimeHelper;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;

public class Fly extends KexTools.module.Module {
	private String mode = Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString();

    public Fly() {
        super("Fly", Keyboard.KEY_F, Category.MOVEMENT);
    }
    
    MotionUtils mu = new MotionUtils();

    private float rotationYaw, rotationPitch;
 
    double startY = 0;
    
    private double moveSpeed1;
    private boolean started1;
    private boolean notUnder1;
    private boolean clipped1;
    private boolean teleport1;
    
    private boolean ignoreVulcan;
    int ticks;
	static TimeHelper time = new TimeHelper();
 
	static int toggle = 0;
    
    @Override
    public void setup() {
    	super.setup();
        ArrayList<String> options = new ArrayList<>();
        options.add("Creative");
        options.add("Hypixel");
        options.add("Vulcan");
        options.add("Teleport");
        options.add("Minemora");
        options.add("Boosted Glide");
        options.add("CubeCraft");
        options.add("BlocksMC");
        options.add("BlocksMC 2");
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Fly Mode", this, "Creative", options));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Fake Damage", this, false));
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Hypixel")){
    		setDisplayName("Fly §cHypixel");
    		
            double y;
            double y1;
            mc.thePlayer.motionY = 0;
            if(mc.thePlayer.ticksExisted % 3 == 0) {
                y = mc.thePlayer.posY - 1.0E-10D;
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, y, mc.thePlayer.posZ, true));
            }
            y1 = mc.thePlayer.posY + 1.0E-10D;
            mc.thePlayer.setPosition(mc.thePlayer.posX, y1, mc.thePlayer.posZ);
        }
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Minemora")){
        	mc.timer.timerSpeed = 3.0f;
    	}
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vulcan")){
    		setDisplayName("Fly §cVulcan");
    		
    		mc.thePlayer.motionY = -1E-10D;
    		if(mu.isMoving()) {
    			mu.strafe(0.914f);
    		} else {
    			mu.strafe(0);
    		}
    	}
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Creative")){
    		setDisplayName("Fly §cCreative");
    		
        	mc.thePlayer.capabilities.isFlying = true;
        }
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("CubeCraft")){
    		setDisplayName("Fly §cCubeCraft");
    		
    		mc.gameSettings.keyBindSneak.pressed = true;
    		if (time.hasReached(300L)) {
    			mc.thePlayer.setSprinting(false);
     
    			mc.thePlayer.rotationYaw = this.rotationYaw;
    			mc.thePlayer.rotationPitch = this.rotationPitch;
     
    			mc.thePlayer.motionY -= 0.25f;
    			mc.thePlayer.motionX *= 1.8f;
    			mc.thePlayer.motionZ *= 1.8f;
     
    			double yaw = Math.toRadians(this.rotationYaw);
    			double x = -Math.sin(yaw) * 3;
    			double z = Math.cos(yaw) * 3;
    			mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY - 0.25F, mc.thePlayer.posZ + z);
    			mc.timer.timerSpeed = 0.12F;
    			if (mc.thePlayer.onGround) {
    				toggle += 1;
    				if (toggle == 2) {
    					toggle = 0;
    					toggle();
    				}
    			}
    			time.reset();
    		}
        }
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("BlocksMC")){
    		setDisplayName("Fly §cBlocksMC");
    		
    		// by: j d#6985
    		
    		final AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, 1, 0);
        	mc.timer.timerSpeed = 0.6f;
            if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()) {
                switch (mc.thePlayer.offGroundTicks) {
                    case 0:
                        mc.thePlayer.jump();
                        mu.strafe(9 - Math.random() / 100f);
                        break;

                    default:
                    	
                        break;
                }
            }
    	}
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("BlocksMC 2")){
    		setDisplayName("Fly §cBlocksMC 2");
    		
    		AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0.0, 1.0, 0.0);
            if (this.started1) { 
                mc.thePlayer.motionY += 0.025;
                mc.timer.timerSpeed = 0.55f;
                mu.strafe((double)(this.moveSpeed1 *= (double)0.935f));
                if (mc.thePlayer.motionY < -0.5 && !PlayerUtils.isBlockUnder()) {
                    this.toggle();
                }
            }
            if (mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, bb).isEmpty() && !this.started1) {
                this.started1 = true;
                mc.thePlayer.jump();
                this.moveSpeed1 = 9.0;
                mu.strafe((double)9.0);
            }
    	}
    }
    @EventTarget
    public void preMotion(EventPreMotionUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Teleport")){
    		if (mc.thePlayer.fallDistance > 0.0f && mc.thePlayer.fallDistance < 2.2) {
                final EntityPlayerSP thePlayer = mc.thePlayer;
                thePlayer.motionY += 0.14000000059604645;
            }
        	++this.ticks;
        	 switch (this.ticks) {
             case 1: {
            	 mc.timer.timerSpeed = 0.5f;
                 PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
                 PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 0.0784000015258789, mc.thePlayer.posZ, mc.thePlayer.onGround));
                 PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                 this.ignoreVulcan = true;
                 mc.thePlayer.motionY = 0.41999998688697815;
                 break;
             }
             case 2: {
            	 final EntityPlayerSP thePlayer2 = mc.thePlayer;
                 thePlayer2.motionY += 0.10000000149011612;
                 break;
             }
             case 4: {
            	 event.setGround(true);
                 mc.thePlayer.onGround = true;
                 break;
             }
        	 }
    	}
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Boosted Glide")){
    		setDisplayName("Fly §cBoosted Glide");
		
    		if (mc.thePlayer.lastPosY < startY + 0.1) {
                mc.thePlayer.jump();
                MotionUtils.setMotion(0.29f);
            } else {
                if (mc.thePlayer.ticksExisted % 2 == 0 && mc.thePlayer.motionY < 0.1) {
                    mc.thePlayer.motionY = -0.098;
                }
                if (mc.thePlayer.ticksExisted % 46 == 0) {
                	mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + 2.9, mc.thePlayer.posZ);
                	final float speed12 = 1;

                    mc.thePlayer.motionY = -1E-10D
                            + (mc.gameSettings.keyBindSneak.isKeyDown() ? speed12 : 0.0D)
                            - (mc.gameSettings.keyBindSprint.isKeyDown() ? speed12 : 0.0D);

                    if (mc.thePlayer.getDistance(mc.thePlayer.lastReportedPosX, mc.thePlayer.lastReportedPosY, mc.thePlayer.lastReportedPosZ) <= 10 - speed12 - 0.15) {
                        event.setCancelled(true);
                    }
                    
                }
            }
        	mc.timer.timerSpeed = 1.0f;
    	}
    }
    @EventTarget
    public void eventReceive(EventReceivePacket event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Minemora")){
    		if(event.getPacket() instanceof S08PacketPlayerPosLook) {
    			event.setCancelled(true);
    		}
    	}
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Teleport")){
    		if (event.getPacket() instanceof S08PacketPlayerPosLook && this.ignoreVulcan) {
                event.setCancelled(true);
                this.ignoreVulcan = false;
            }
    	}
    }
    
    @EventTarget
    public void onKick(EventServerKick event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Minemora")){
    		this.toggle();
    	}
    }
    
    @EventTarget
    public void semiBlinkEvent(EventTeleport event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("BlocksMC 2")){
    		if (this.teleport1) {
                event.setCancelled(true);
                this.teleport1 = false;
            }
    	}
    }
    
    @EventTarget
    public void semiBlinkEvent(EventSemiBlink event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vulcan")){
    		float speedVulcan = 0.934f;
    		
        	if(mc.thePlayer.getDistance(mc.thePlayer.lastReportedPosX, mc.thePlayer.lastReportedPosY, mc.thePlayer.lastReportedPosZ) <= 10 - speedVulcan - 0.15) { 
    			event.setCancelled(true);
    		}
    	}
    }
    
    @EventTarget
    public void onCollision(EventBlockCollision event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Minemora")){
    		if (event.getBlock() instanceof BlockAir) {
                if (mc.thePlayer.isSneaking())
                    return;
                double x = event.getX();
                double y = event.getY();
                double z = event.getZ();
                if (y < mc.thePlayer.posY) {
                    event.setAxisAlignedBB(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
                }
            }
    	}
    }
    
    @Override
    public void onEnable() {
    	super.onEnable();
    	this.ticks = 0;
        ignoreVulcan = false;
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fake Damage").getValBoolean()){ 
    		DamageComponent.fakeDamage();
    	}
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vulcan")){
    		PacketUtil.send(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ,
    				mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, false));
    	}
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("CubeCraft")){
    		this.rotationYaw = mc.thePlayer.rotationYaw;
    		this.rotationPitch = mc.thePlayer.rotationPitch;
    		toggle = 0;
    	}
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Boosted Glide")){
        	mc.timer.timerSpeed = 0.2f;
    	}
    	this.moveSpeed1 = 0.0;
        this.notUnder1 = false;
        this.started1 = false;
        this.clipped1 = false;
        this.teleport1 = false;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    	mc.timer.timerSpeed = 1f;
        if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Creative")){
        	mc.thePlayer.capabilities.isFlying = false;
        }
        
        if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("Vulcan") || Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("BlocksMC")){
        	mu.stop();
        }
        
        if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("CubeCraft")){
        	mc.thePlayer.capabilities.isFlying = false;
        	this.rotationYaw = 0;
        	this.rotationPitch = 0;
        	time.reset();
        	toggle = 0;
        }
        if(Kex.INSTANCE.settingsManager.getSettingByName("Fly Mode").getValString().equalsIgnoreCase("BlocksMC 2")){
        	mu.stop();
        }
    }
}
