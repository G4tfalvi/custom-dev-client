package KexTools.module.player;

import java.awt.Color;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.clickgui.util.ColorUtil;
import KexTools.event.EventTarget;
import KexTools.event.events.Event3D;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.module.Category;
import KexTools.packet.PacketUtil;
import KexTools.utils.PlayerUtils;
import KexTools.utils.RenderUtil;
import KexTools.utils.TimeUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.*;

public class AutoSoup extends KexTools.module.Module {
	
	private final TimeUtil timer = new TimeUtil();
    private boolean switchBack;
    private long decidedTimer;
    private int soup = -37;
	
	public AutoSoup() {
        super("AutoSoup", 0, Category.PLAYER);
    }
	
	@EventTarget
	public void onAuto(EventPreMotionUpdate e){
		if (switchBack) {
            PacketUtil.send(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            
            PacketUtil.send(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            switchBack = false;
            return;
        }
		if (timer.hasReached(decidedTimer)) {
            if (mc.thePlayer.ticksExisted > 10 && mc.thePlayer.getHealth() < 15) {
                soup = PlayerUtils.findSoup() - 36;

                if (soup != -37) {
                    PacketUtil.send(new C09PacketHeldItemChange(soup));
                    PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getStackInSlot(soup)));
                    switchBack = true;
                }

                final int delayFirst = (int) Math.floor(Math.min(300, 500));
                final int delaySecond = (int) Math.ceil(Math.max(300, 500));

                decidedTimer = RandomUtils.nextInt(delayFirst, delaySecond);
                timer.reset();
            }
        }
	}
}
