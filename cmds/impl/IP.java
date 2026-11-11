package KexTools.cmds.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import KexTools.Wrapper;
import KexTools.cmds.Cmd;
import net.minecraft.client.Minecraft;

public class IP extends Cmd{

	public IP() {
        super("ip", "IP", "ip", new String[] { "copyip" });
    }

	@Override
	public void onCommand(String[] arrays, String s) {
		if (!Minecraft.getMinecraft().isSingleplayer()) {
			StringSelection selection = new StringSelection((Minecraft.getMinecraft().getCurrentServerData()).serverIP);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
			Wrapper.send("§4§lKexTools §8» §cThe server IP was copied to the clipboard");
		} else {
			Wrapper.send("§4§lKexTools §8» §cYou are in singleplayer!");
			Wrapper.send("§4§lKexTools §8» §cWhy are you retard?");
		}
	}

}
