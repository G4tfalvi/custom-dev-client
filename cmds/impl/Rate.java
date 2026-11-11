package KexTools.cmds.impl;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.apache.commons.lang3.RandomUtils;

import KexTools.Wrapper;
import KexTools.cmds.Cmd;
import net.minecraft.client.Minecraft;

public class Rate extends Cmd{

	public Rate() {
        super("rate", "Rate", "rate", new String[] { "Rate" });
    }

	@Override
	public void onCommand(String[] arrays, String s) {
		String message = "";
		for(int i = 2; i < arrays.length; i++) {
			message += arrays[i];
			if(i != arrays.length - 2) {
				message += " ";
			}
		}
		
		try {
			if(arrays[1].equalsIgnoreCase("mad")) {
				Wrapper.send("§4§lKexTools §8» §c" +arrays[2] + " is " + RandomUtils.nextInt(0, 100) + "% mad.");
			}
			else if(arrays[1].equalsIgnoreCase("gay")) {
				Wrapper.send("§4§lKexTools §8» §c" +arrays[2] + " is " + RandomUtils.nextInt(0, 100) + "% gay.");
			}
		}catch (Exception e) {
			Wrapper.send("§8(§c!§8) §f-rate <mad/gay> <player>");
		}
	}

}
