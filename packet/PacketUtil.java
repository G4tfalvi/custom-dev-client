package KexTools.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public class PacketUtil {
	
	static Minecraft mc = Minecraft.getMinecraft();
	
	public static void send(Packet<?> packet) {
        mc.getNetHandler().addToSendQueue(packet);
    }

    public static void sendNoEvent(Packet<?> packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
	
}
