package KexTools.module.combat;

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
import java.util.concurrent.ThreadLocalRandom;

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
import KexTools.utils.PlayerUtils;
import KexTools.utils.Rotation;
import KexTools.utils.time.Stopwatch;

public class KillAura extends KexTools.module.Module {
	public static EntityLivingBase target;
    private long current, last;
    private int delay = 8;
    private float yaw, pitch;
    private boolean others;

    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.COMBAT);
    }

    @Override
    public void setup() {
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Crack Size", this, 2, 0, 15, true));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Existed", this, 30, 0, 500, true));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("FOV", this, 180, 0, 360, true));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("AutoBlock", this, true));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Invisibles", this, false));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Players", this, true));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Animals", this, false));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Monsters", this, false));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Villagers", this, false));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Teams", this, false));
    }

    @EventTarget
    public void onPre(EventPreMotionUpdate event) {
        target = getClosest(mc.playerController.getBlockReachDistance());
        if(target == null)
            return;
        updateTime();
        yaw = mc.thePlayer.rotationYaw;
        pitch = mc.thePlayer.rotationPitch;
        boolean block = target != null && Kex.INSTANCE.settingsManager.getSettingByName("AutoBlock").getValBoolean() && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemSword;
        if(block && target.getDistanceToEntity(mc.thePlayer) < 2)
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
        if(current - last > 1000 / delay) {
            attack(target, 98);
            resetTime();
        }
    }
    /*
    @EventTarget
    public void onPost(EventPostMotionUpdate event) {
        if(target == null)
            return;
        mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }
    */

    private void attack(Entity entity, double chance) {
        for(int i = 0; i < Kex.INSTANCE.settingsManager.getSettingByName("Crack Size").getValDouble(); i++)
            mc.thePlayer.onCriticalHit(entity);
        
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        double randomXZ = threadLocalRandom.nextDouble(-0.05, 0.1);
        double randomY = threadLocalRandom.nextDouble(-0.05, 0.1);
        
        mc.thePlayer.rotationYawHead = getRotations(entity.posX + randomXZ, entity.posY + (entity.getEyeHeight() / 2.05) + randomY, entity.posZ + randomXZ)[0];
        mc.thePlayer.renderYawOffset = getRotations(entity.posX + randomXZ, entity.posY + (entity.getEyeHeight() / 2.05) + randomY, entity.posZ + randomXZ)[0];
        mc.thePlayer.rotationPitchHead = getRotations(entity.posX + randomXZ, entity.posY + (entity.getEyeHeight() / 2.05) + randomY, entity.posZ + randomXZ)[1];
        
        if(FightUtil.canHit(chance / 100)) {
        	mc.thePlayer.swingItem();
        	PacketUtil.send(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        } else {
        	mc.thePlayer.swingItemNoEvent();
        }
        
    }

    private void updateTime() {
        current = (System.nanoTime() / 1000000L);
    }

    private void resetTime() {
        last = (System.nanoTime() / 1000000L);
    }

    private EntityLivingBase getClosest(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Object object : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) object;
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase player = (EntityLivingBase) entity;
                if (canAttack(player)) {
                    double currentDist = mc.thePlayer.getDistanceToEntity(player);
                    if (currentDist <= dist) {
                        dist = currentDist;
                        target = player;
                    }
                }
            }
        }
        return target;
    }

    private boolean canAttack(EntityLivingBase player) {
        if(player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !Kex.INSTANCE.settingsManager.getSettingByName("Players").getValBoolean())
                return false;
            if (player instanceof EntityAnimal && !Kex.INSTANCE.settingsManager.getSettingByName("Animals").getValBoolean())
                return false;
            if (player instanceof EntityMob && !Kex.INSTANCE.settingsManager.getSettingByName("Monsters").getValBoolean())
                return false;
            if (player instanceof EntityVillager && !Kex.INSTANCE.settingsManager.getSettingByName("Villagers").getValBoolean())
                return false;
        }
        if(player.isOnSameTeam(mc.thePlayer) && Kex.INSTANCE.settingsManager.getSettingByName("Teams").getValBoolean())
            return false;
        if(player.isInvisible() && !Kex.INSTANCE.settingsManager.getSettingByName("Invisibles").getValBoolean())
            return false;
        if(!isInFOV(player, Kex.INSTANCE.settingsManager.getSettingByName("FOV").getValDouble()))
            return false;
        return player != mc.thePlayer && player.isEntityAlive() && mc.thePlayer.getDistanceToEntity(player) <= mc.playerController.getBlockReachDistance() && player.ticksExisted > Kex.INSTANCE.settingsManager.getSettingByName("Existed").getValDouble();
    }

    private boolean isInFOV(EntityLivingBase entity, double angle) {
        angle *= .5D;
        double angleDiff = getAngleDifference(mc.thePlayer.rotationYaw, getRotations(entity.posX, entity.posY, entity.posZ)[0]);
        return (angleDiff > 0 && angleDiff < angle) || (-angle < angleDiff && angleDiff < 0);
    }

    private float getAngleDifference(float dir, float yaw) {
        float f = Math.abs(yaw - dir) % 360F;
        float dist = f > 180F ? 360F - f : f;
        return dist;
    }
    
    private float[] getRotations(double x, double y, double z) {
        double diffX = x + .5D - mc.thePlayer.posX;
        double diffY = (y + .5D) / 2D - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double diffZ = z + .5D - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);

        return new float[] { yaw, pitch };
    }
    
    
}
