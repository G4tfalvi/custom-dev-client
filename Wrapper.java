package KexTools;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Wrapper {
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	public static void send(String s) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(s));
	}

}
