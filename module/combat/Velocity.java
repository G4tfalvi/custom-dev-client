package KexTools.module.combat;

import java.util.ArrayList;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.event.events.EventReceivePacket;
import KexTools.module.Category;
import KexTools.settings.Setting;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends KexTools.module.Module {
	
	private int amount;
	
    public Velocity() {
        super("Velocity", 0, Category.COMBAT);
    }

    @Override
    public void setup() { 
    	ArrayList<String> mode = new ArrayList<>();
    	mode.add("BlocksMC");
    	
    	Kex.INSTANCE.settingsManager.rSetting(new Setting("Mode", this, "BlocksMC", mode)); 
    }
    
    @EventTarget
    public void onUpdate(EventReceivePacket event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Mode").getValString().equalsIgnoreCase("BlocksMC")){
    		setDisplayName("Velocity §cBlocksMC");
    		final Packet<?> p = event.getPacket();
    	
    		if (p instanceof S12PacketEntityVelocity) {
    			final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;
	
	            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
	            	if (amount < 1) {
	                    event.setCancelled(true);
	                    amount++;
	                    return;
	                }
	                wrapper.motionX *= 1d / 100;
	                wrapper.motionY *= 1d / 100;
	                wrapper.motionZ *= 1d / 100;
	
	                event.setPacket(wrapper);
	                amount = 0;
	            }
    		}
    		if (p instanceof S27PacketExplosion) {
                final S27PacketExplosion wrapper = (S27PacketExplosion) p;

                if (amount < 1) {
                    event.setCancelled(true);
                    amount++;
                    return;
                }

                wrapper.posX *= 1d / 100;
                wrapper.posY *= 1d / 100;
                wrapper.posZ *= 1d / 100;

                event.setPacket(wrapper);
                amount = 0;
            }
    	}
    } 
}
