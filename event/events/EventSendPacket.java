package KexTools.event.events;

import KexTools.event.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event {
    public Packet<?> packet;

    public EventSendPacket(Packet<?> packet) {
        this.packet = null;
        setPacket(packet);
    }

    public Packet<?> getPacket() {
        return packet;
    }
    @SuppressWarnings("unchecked")
    public <T extends Packet> T getPacketList() {
        return (T) packet;
    }
    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }
}
