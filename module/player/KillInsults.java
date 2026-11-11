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
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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
import KexTools.event.events.EventSendPacket;
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
import io.netty.util.internal.ThreadLocalRandom;

public class KillInsults extends KexTools.module.Module {
	
	private final List<String> insults = new ArrayList<>();
    
    public KillInsults() {
        super("KillInsults", 0, Category.PLAYER);
        
        File file = new File("kex/dw-killsults.txt");
        if (!file.exists() || file.getTotalSpace() == 0 /*in case something goes wrong */) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(KillInsults.class.getResourceAsStream("/dw-killsults.txt")));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    this.insults.add(line);
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for (String str : insults) {
                    try {
                        writer.write(str + "\n");
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
                writer.close();
                bufferedReader.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    insults.add(line);
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }
    
    private String randomMessage() {
        return insults.get(ThreadLocalRandom.current().nextInt(0, insults.size() - 1));
    } 
    
    @EventTarget
    public void onPacket(EventSendPacket event) {
    	if (mc.thePlayer != null && event.getPacket() instanceof S02PacketChat) {
            final String look = "killed by " + mc.thePlayer.getName();
            final String look33 = mc.thePlayer.getName() + " killed ";
            final String look2 = "slain by " + mc.thePlayer.getName();
            final String look3 = "void while escaping " + mc.thePlayer.getName();
            final String look67 = "You have killed ";
            final String look4 = "was killed with magic while fighting " + mc.thePlayer.getName();
            final String look5 = "couldn't fly while escaping " + mc.thePlayer.getName();
            final String look6 = "fell to their death while escaping " + mc.thePlayer.getName();
            final String look7 = "You recieved a reward for killing ";
            final String look8 = "was thrown into the void by " + mc.thePlayer.getName();
            final String look9 = "foi morto por " + mc.thePlayer.getName();
            final String look10 = "was thrown off a cliff by " + mc.thePlayer.getName();
            final S02PacketChat cp = (S02PacketChat) event.getPacket();
            final String cp21 = cp.getChatComponent().getUnformattedText();
            if (cp21.startsWith(look67) || cp21.startsWith(look33) || (cp21.startsWith(mc.thePlayer.getName() + "(") && cp21.contains("asesino ha")) || cp21.startsWith("Has ganado ") || cp21.contains(look)
                    || cp21.contains(look2) || cp21.contains(look3)
                    || cp21.contains("You have been rewarded $50 and 2 point(s)!") || cp21.contains(look4) || cp21.contains(look5) || cp21.contains(look6) || cp21.contains(look7) || cp21.contains(look8) || cp21.contains(look9) || cp21.contains(look10)) {
                mc.thePlayer.sendChatMessage(randomMessage());
            }
        }
    }
}
