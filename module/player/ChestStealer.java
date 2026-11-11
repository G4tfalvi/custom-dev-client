package KexTools.module.player;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

import KexTools.event.EventTarget;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import KexTools.utils.TimeHelper;

public class ChestStealer extends KexTools.module.Module {
    public ChestStealer() {
        super("ChestStealer", Keyboard.KEY_V, Category.PLAYER);
    }
    
    public TimeHelper time = new TimeHelper();

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if((mc.thePlayer.openContainer != null) && ((mc.thePlayer.openContainer instanceof ContainerChest))) {
        	ContainerChest chest = (ContainerChest) mc.thePlayer.openContainer;
        	for(int i = 0; i < chest.getLowerChestInventory().getSizeInventory(); i++) {
        		if((chest.getLowerChestInventory().getStackInSlot(i) != null) && (time.hasReached(40L))) {
        			mc.playerController.windowClick(chest.windowId, i, 0, 1, mc.thePlayer);
        			time.reset();
        		}
        	}
        	if(chest.getInventory().isEmpty()) {
        		mc.thePlayer.closeScreen();
        	}
        }
    }
}
