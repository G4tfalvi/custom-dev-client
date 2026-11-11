package KexTools.module.player;

import java.util.ArrayList;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventPostMotionUpdate;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.event.events.EventReceivePacket;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import KexTools.module.combat.KillAura;
import KexTools.packet.PacketUtil;
import KexTools.settings.Setting;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;

public class NoSlow extends KexTools.module.Module {

	private boolean attacking;
	
	public NoSlow() {
		super("NoSlow", 0, Category.PLAYER);
	}
	
	@EventTarget
	public void onPost(EventPostMotionUpdate event) {
		setDisplayName("NoSlow §cBlocksMC");
		if (mc.thePlayer.isUsingItem() && !attacking)
			PacketUtil.send(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
	}
	@EventTarget
	public void onUpdate(EventReceivePacket e) {
		if(mc.thePlayer.hurtTime >= 6) {
			attacking = true;
		}
	}
}
