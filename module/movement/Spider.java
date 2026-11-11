package KexTools.module.movement;

import java.util.ArrayList; 

import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.module.Category;
import KexTools.packet.PacketUtil;
import KexTools.settings.Setting;
import KexTools.utils.MotionUtils;
import net.minecraft.util.MathHelper;

public class Spider extends KexTools.module.Module {

    public Spider() {
        super("Spider", 0, Category.MOVEMENT);
    }
    MotionUtils mu = new MotionUtils();
    
    @Override
    public void setup() {
    	super.setup();
        ArrayList<String> options = new ArrayList<>();
        options.add("Vulcan");
        options.add("Verus");
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Mode", this, "Vulcan", options));
    }

    @Override
	public void onDisable() {
		super.onDisable();
    }
    
    @EventTarget
    public void preMotion(EventPreMotionUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Mode").getValString().equalsIgnoreCase("Vulcan")){
    		setDisplayName("Spider §cVulcan");
    		
    		if (mc.thePlayer.isCollidedHorizontally) {
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    event.setGround(true);
                    mc.thePlayer.motionY = mu.jumpMotion();
                }

                final double yaw = mu.getDirection();
                event.setX(event.getPosX() - -MathHelper.sin((float) yaw) * 0.1f);
                event.setZ(event.getPosZ() - MathHelper.cos((float) yaw) * 0.1f);
            }
    	};
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Mode").getValString().equalsIgnoreCase("Verus")){
    		setDisplayName("Spider §cVerus");
    		if (mc.thePlayer.isCollidedHorizontally) {
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    mc.thePlayer.jump();
                }
            }
    	}	
    }
}
