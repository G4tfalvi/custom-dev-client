package KexTools.module.player;

import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import KexTools.settings.Setting;

public class NoFall extends KexTools.module.Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_B, Category.PLAYER);
    }
    
    @Override
    public void setup() {
    	ArrayList<String> mode = new ArrayList<>();
    	mode.add("Vanilla");
    	mode.add("Minemora");
    	Kex.INSTANCE.settingsManager.rSetting(new Setting("Mode", this, "Vanilla", mode));    
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Mode").getValString().equalsIgnoreCase("Vanilla")){
    		setDisplayName("AntiVoid §cVanilla");
    		if(mc.thePlayer.fallDistance > 2F)
    			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
    	}
        
    }
}
