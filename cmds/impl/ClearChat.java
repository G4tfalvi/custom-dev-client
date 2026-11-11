package KexTools.cmds.impl;

import KexTools.Wrapper;
import KexTools.cmds.Cmd;
import net.minecraft.client.Minecraft;

public class ClearChat extends Cmd {

	public ClearChat() {
        super("clearchat", "ClearChat", "ClearChat", new String[] { "cc" });
    }

	@Override
	public void onCommand(String[] array, String s) {
		Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages();
	}

}
