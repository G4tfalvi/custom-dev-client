package KexTools.module;

import KexTools.Kex;
import KexTools.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;

public class Module {
    protected Minecraft mc = Minecraft.getMinecraft();

    private String name, displayName;
    private int key;
    public Category category;
    public boolean toggled;

    public Module(String name, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        toggled = false;

        setup();
    }

    public void onEnable() {
        Kex.INSTANCE.eventManager.register(this);
    }
    public void onDisable() {
    	Kex.INSTANCE.eventManager.unregister(this);
    }
    public void onToggle() {}
    public void toggle() {
        toggled = !toggled;
        onToggle();
        if(toggled)
            onEnable();
        else
            onDisable();
    }
    
    public void onEvent(Event e) {}
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    } 
    
    protected EntityPlayerSP player() {
    	return mc.thePlayer;
    }
    
    protected PlayerControllerMP playerController() {
    	return mc.playerController;
    }
    
    protected WorldClient world() {
    	return mc.theWorld;
    }
    
    @SuppressWarnings("rawtypes")
    protected void sendPacket(Packet p) {
    	player().sendQueue.addToSendQueue(p);
    }
    
    public boolean isToggled() {
        return toggled;
    }
    public String getDisplayName() {
        return displayName == null ? name : displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setup() {}
}
