package KexTools.cmds.impl;

import KexTools.cmds.Cmd;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.util.IChatComponent;
 
public class PageBook extends Cmd{
	
	public static void sendMessage(String message) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent("{\"text\":\"" + message + "\"}"));
 
	}

	public PageBook() {
        super("book", "PageBook", "book", new String[] { "PageBook" });
    }

	@Override
	public void onCommand(String[] array, String s) {
		if(Minecraft.getMinecraft().playerController.isInCreativeMode()) {
        	try {
        		int pagescount = Integer.valueOf(array[0]);
        		String message = "";
        		for(int i = 1; i < array.length; i++) {
                    message += array[i];
                    if(i != array.length - 0) {
                        message += " ";
                    }
                }
        		ItemStack itm = new ItemStack(Items.written_book);
     
        		NBTTagCompound base = new NBTTagCompound();
        		NBTTagCompound display = new NBTTagCompound();
        		NBTTagList lore = new NBTTagList();
        		lore.appendTag(new NBTTagString("Lapozz át!"));
        		display.setTag("Lore", lore);
         
        		base.setString("author", "Lowsyn_");
        		base.setByte("resolved", (byte) 1);
        		base.setString("title", "Lapozz át!");
         
        		NBTTagList pages = new NBTTagList();
        		for(int i = 0; i < pagescount; i++) {
        			pages.appendTag(new NBTTagString("{\"extra\":[\"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\",{\"clickEvent\":{\"action\":\"run_command\", \"value\":\"" + message + "\"}, \"text\":\"                                \"}], \"text\":\"\"}")) ;
        		}
        		base.setTag("pages", pages);
         
        		itm.setTagCompound(base);
         
        		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, itm));
        		sendMessage("§4§lKexTools §8» §cYou got the item!");
        	} catch(Exception e) {
        		sendMessage("§8(§c!§8) §fUsage: -book <page> <message>");
        	}
        }else{
            sendMessage("§4§lKexTools §8» §cYou can use it only creative!");
        }
	}
}