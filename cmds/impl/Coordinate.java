package KexTools.cmds.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import KexTools.Wrapper;
import KexTools.cmds.Cmd;
import net.minecraft.client.Minecraft;

public class Coordinate extends Cmd{

	public Coordinate() {
        super("coords", "Coordinate", "coordinate", new String[] { "Coordinate" });
    }

	@Override
	public void onCommand(String[] p0, String p1) {
		StringSelection selection = new StringSelection(Minecraft.getMinecraft().thePlayer.posX + " " + Minecraft.getMinecraft().thePlayer.posY + " " + Minecraft.getMinecraft().thePlayer.posZ);
	    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
	    Wrapper.send("§4§lKexTools §8» §cYou Coords was copied to the clipboard");
	}

}
