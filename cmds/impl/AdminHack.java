package KexTools.cmds.impl;

import KexTools.cmds.Cmd;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;

public class AdminHack extends Cmd{
	
	public AdminHack() {
        super("AdminHack", "AdminHack", "AdminHack", new String[] { "AdminHack" });
    }
	
	public ItemStack generateAdminHackItem(String message) {
		ItemStack itm = new ItemStack(Blocks.mob_spawner);
		NBTTagCompound base = new NBTTagCompound();
		NBTTagCompound blockEntityTag = new NBTTagCompound();
		
		blockEntityTag.setString("EntityId", "FallingSand");
		blockEntityTag.setInteger("SpawnCount", 1);
		blockEntityTag.setInteger("SpawnRange", 3);
		
		blockEntityTag.setInteger("RequiredPlayerRange", 100);
		blockEntityTag.setInteger("MinSpawnDelay", 20);
		blockEntityTag.setInteger("MaxpawnDelay", 20);
		
		blockEntityTag.setInteger("MaxNearbyEntities", 0);
		
		NBTTagCompound spawnData = new NBTTagCompound();
		spawnData.setString("Tile", "minecraft:command_block");
		spawnData.setString("Block", "minecraft:command_block");
		spawnData.setInteger("Time", 1);
		
		blockEntityTag.setTag("SpawnData", spawnData);
		base.setTag("BlockEntityTag", blockEntityTag);
		itm.setTagCompound(base);
		
		return itm;
	}
	
	public ItemStack addItemToOfen(ItemStack itm) {
		ItemStack furnance = new ItemStack(Blocks.furnace);
		NBTTagCompound base = new NBTTagCompound();
		NBTTagCompound  blockEntityTag = new NBTTagCompound();
		
		blockEntityTag.setShort("BurnTime", (short) 0);
		blockEntityTag.setShort("CookTime", (short) 0);
		blockEntityTag.setShort("CookTimeTotal", (short) 200);
		
		blockEntityTag.setString("id", "Furnance");
		blockEntityTag.setString("Lock", "");
		
		NBTTagList items = new NBTTagList();
		NBTTagCompound item = new NBTTagCompound();
		item.setByte("Count", (byte) 1);
		item.setShort("Damage", (short) itm.getItemDamage());
		item.setString("id", "minecraft:mob_spawner");
		item.setShort("Slot", (short) 0);
		
		item.setTag("tag", itm.getTagCompound());
		
		items.appendTag(item);
		
		blockEntityTag.setTag("Items", items);
		base.setTag("BlockEntityTag", blockEntityTag);
		
		furnance.setTagCompound(base);
		
		return furnance;
	}

	@Override
	public void onCommand(String[] array, String s) {
		String message = "";
		for(int i = 0; i < array.length; i++) {
			message += array[i] + " ";
		}
		Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C10PacketCreativeInventoryAction(36, addItemToOfen(generateAdminHackItem(message))));
		
	}
}
