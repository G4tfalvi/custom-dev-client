package KexTools.module.combat;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.ArrayList;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventSendPacket;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import KexTools.settings.Setting;
import KexTools.utils.PlayerUtils;

public class Criticals extends KexTools.module.Module {
    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("Packet");
        options.add("MiniJump");
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Criticals Mode", this, "Packet", options));
    }

    @EventTarget
    public void onSendPacket(EventSendPacket event) {
        String mode = Kex.INSTANCE.settingsManager.getSettingByName("Criticals Mode").getValString();

        if(canCrit()) {
            if (event.getPacket() instanceof C02PacketUseEntity) {
                C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
                if(packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if(mode.equalsIgnoreCase("Packet")) {
                    	setDisplayName("Criticals §cPacket");
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + .1625, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 4.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-6, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
                    }
                }
            }
            if(mode.equalsIgnoreCase("MiniJump")) {
            	setDisplayName("Criticals §cMiniJump");
                mc.thePlayer.jump();
                mc.thePlayer.motionY -= .30000001192092879;
            }
        }
    }

    private boolean canCrit() {
        return !PlayerUtils.isInLiquid() && mc.thePlayer.onGround;
    }
}
