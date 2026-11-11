package KexTools.cmds.impl;

import KexTools.alt.GuiAltLogin; 
import KexTools.cmds.Cmd;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import KexTools.Wrapper;

public class Connect extends Cmd{
	
	public Connect() {
        super("Connect", "Connect", "Connect", new String[] { "Connect" });
    }

	@Override
	public void onCommand(String[] array, String s) {
		if (array.length == 1) {
            final ServerData serverData = new ServerData("", array[0], true);
            if (!Connect.mc.isSingleplayer()) {
                final Minecraft mc = Connect.mc;
                mc.theWorld.sendQuittingDisconnectingPacket();
            }
            Connect.mc.displayGuiScreen(new GuiConnecting(null, Connect.mc, serverData));
        }
        else {
            Wrapper.send("§8(§c!§8) §fUsage: -connect <ip>");
        }
	}

}
