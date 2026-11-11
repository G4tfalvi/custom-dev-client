package KexTools.packet;

import java.util.function.Consumer;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventReceivePacket;
import KexTools.event.events.EventSendPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class PacketRunnableWrapper {
	
	 private final PacketRunnable runnable;
	
	 public PacketRunnableWrapper(PacketRunnable runnable, long stop_after) {
	        this.runnable = runnable;

	        new Thread(() -> {
	            Kex.INSTANCE.eventManager.register(PacketRunnableWrapper.this);
	            try {
	                Thread.sleep(stop_after);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            Kex.INSTANCE.eventManager.unregister(PacketRunnableWrapper.this);
	        }).start();
	  }
	 
	 public static final Consumer<EventSendPacket> aacExploit = packetEvent -> {
		 Minecraft mc = Minecraft.getMinecraft();
		 
		 if (packetEvent.getPacket() instanceof S08PacketPlayerPosLook) {
	            S08PacketPlayerPosLook packetPlayerPosLook = packetEvent.getPacketList();
	            double x = packetPlayerPosLook.getX() - mc.thePlayer.posX;
	            double y = packetPlayerPosLook.getY() - mc.thePlayer.posY;
	            double z = packetPlayerPosLook.getZ() - mc.thePlayer.posZ;
	            double diff = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
	            double distance = 22.5D;
	            if (diff <= distance) { // 1p
	                packetEvent.setCancelled(true);
	                
	                PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(packetPlayerPosLook.getX(), packetPlayerPosLook.getY(), packetPlayerPosLook.getZ(), packetPlayerPosLook.getYaw(), packetPlayerPosLook.getPitch(), true));
	            } else if (packetEvent.getPacket() instanceof C03PacketPlayer) {
	            	packetEvent.setCancelled(true);
	            } else if (packetEvent.getPacket() instanceof C0BPacketEntityAction) {
	                packetEvent.setCancelled(true);
	            }
		 }
	 };
	 
	 public static void preventLagback() {
		 new PacketRunnableWrapper(aacExploit::accept, 300L);
	 }

	 @EventTarget
	 public void onPacket(EventSendPacket event) {
		 this.getAction().run(event);
	 }
	 
	 public PacketRunnable getAction() {
	        return runnable;
	 }
	 
	 @FunctionalInterface
	    public interface PacketRunnable {
	        void run(EventSendPacket packet);
	 }
}
