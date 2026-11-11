package KexTools.module.combat;

import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;

import KexTools.event.EventTarget;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;

public class AntiBot extends KexTools.module.Module {
    public AntiBot() {
        super("AntiBot", Keyboard.KEY_M, Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        for(Object entity : mc.theWorld.loadedEntityList)
            if(((Entity)entity).isInvisible() && entity != mc.thePlayer)
                mc.theWorld.removeEntity((Entity)entity);
    }
}
