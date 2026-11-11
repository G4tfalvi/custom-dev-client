package KexTools.module.movement;


import org.lwjgl.input.Keyboard;

import KexTools.event.EventTarget;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;

public class Sprint extends KexTools.module.Module {
    public Sprint() {
        super("Sprint", Keyboard.KEY_M, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if(!mc.thePlayer.isCollidedHorizontally && mc.thePlayer.moveForward > 0)
            mc.thePlayer.setSprinting(true);
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.setSprinting(false);
    }
}
