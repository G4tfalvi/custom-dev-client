package KexTools.module.player;

import org.lwjgl.input.Keyboard;

import KexTools.event.EventTarget;
import KexTools.event.events.EventFastUse;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class FastEat extends KexTools.module.Module {
    public FastEat() {
        super("FastEat", Keyboard.KEY_B, Category.PLAYER);
    }

    @EventTarget
    public void onUpdate(EventFastUse event) {
    	if (event.getEntity() instanceof EntityPlayerSP && mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemFood && !mc.thePlayer.isMoving()) {
			if (mc.thePlayer.isUsingItem()) {
				BlockPos pos = mc.thePlayer.getPosition().add(0.0, -1.5, 0.0);
				if (event.getHeight() > 0.5F) {
					mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new ItemStack(Blocks.stone.getItem(mc.theWorld, pos))));
					mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.41999998688698, mc.thePlayer.posZ, false));
            		mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.7531999805212, mc.thePlayer.posZ, false));
            		mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ, true));
				}
			}
			
		}
    }
}
