package KexTools.module.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventPostMotionUpdate;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.event.events.EventStrafe;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;
import KexTools.packet.PacketUtil;
import KexTools.settings.Setting;
import KexTools.utils.AimUtil;
import KexTools.utils.FightUtil;
import KexTools.utils.MotionUtils;
import KexTools.utils.PlayerUtils;
import KexTools.utils.Rotation;
import KexTools.utils.time.Stopwatch;

public class AntiVoid extends KexTools.module.Module {
	
    
    public AntiVoid() {
        super("AntiVoid", 0, Category.PLAYER);
    }

    @Override
    public void setup() {
    	ArrayList<String> mode = new ArrayList<>();
    	mode.add("BlocksMC");
    	mode.add("Minemora");
    	Kex.INSTANCE.settingsManager.rSetting(new Setting("Mode", this, "BlocksMC", mode));    
    }
    
    @EventTarget
    public void onUpdate(EventPreMotionUpdate event) {
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Mode").getValString().equalsIgnoreCase("BlocksMC")){
    		setDisplayName("AntiVoid §cBlocksMC");
    		
    		if (!MotionUtils.isBlockUnder() && mc.thePlayer.fallDistance > 10) {
                mc.thePlayer.fallDistance = 0;
                mc.thePlayer.motionY = 2.15;
            }
    	}
    	
    	if(Kex.INSTANCE.settingsManager.getSettingByName("Mode").getValString().equalsIgnoreCase("Minemora")){
    		setDisplayName("AntiVoid §cMinemora");
    		
    		if (!MotionUtils.isBlockUnder() && mc.thePlayer.fallDistance > 0) {
                mc.thePlayer.fallDistance = 0;
                mc.thePlayer.motionY = 0;
            }
    	}
    }  
}
