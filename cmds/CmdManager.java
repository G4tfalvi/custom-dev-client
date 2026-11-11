package KexTools.cmds;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.List;

import KexTools.Wrapper;

import KexTools.Kex;
import KexTools.cmds.impl.*;
import KexTools.event.events.EventChat;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

public class CmdManager {
	
	public static void sendMessage(String message) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent("{\"text\":\"" + message + "\"}"));
	}
	
	public List<Cmd> commands;
    public static String prefix;
    
    static {
        CmdManager.prefix = "-";
    }
    
    public CmdManager() {
        this.commands = new ArrayList();
        this.setup();
    }
	
	public void setup() {
		this.commands.add(new PageBook());
		this.commands.add(new ClearChat());
		this.commands.add(new AdminHack());
		this.commands.add(new Connect());
		this.commands.add(new Coordinate());
		this.commands.add(new IP());
		this.commands.add(new Bed());
	}
	
	public void handleChat(EventChat event) {
        final String message = event.getMessage();
        	
        if (!message.startsWith(CmdManager.prefix)) 
            return;
        
        event.setCancelled(true);
        
        boolean foundCommand = false;
        
        final String substring = message.substring(CmdManager.prefix.length());
        if (substring.split(" ").length > 0) {
            String s = substring.split(" ")[0];
            
            for (Cmd cmd : this.commands) {
                if (cmd.aliases.contains(s) || cmd.name.equalsIgnoreCase(s)) {
                	cmd.onCommand(Arrays.copyOfRange(substring.split(" "), 1, substring.split(" ").length), substring);
                	foundCommand = true;
                    break;
                }
            }
        }
        if(!foundCommand) {
        	sendMessage("§4§lKexTools §8» §ccc");
        	sendMessage("§4§lKexTools §8» §ccoords");
        	sendMessage("§4§lKexTools §8» §cip");
        	sendMessage("§4§lKexTools §8» §cbook");
        	sendMessage("§4§lKexTools §8» §cadminhack");
        	sendMessage("§4§lKexTools §8» §cconnect");
        	sendMessage("§4§lKexTools §8» §crate");
        }
        
	}
        
}
