package KexTools.module.movement;
import org.lwjgl.input.Keyboard;

import KexTools.event.EventTarget;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;

public class Step extends KexTools.module.Module {
    public Step() {
        super("Step", Keyboard.KEY_L, Category.MOVEMENT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        mc.thePlayer.stepHeight = 1.5F;
    }

    @Override
    public void onDisable() {
        super.onDisable();

        mc.thePlayer.stepHeight = .5F;
    }
}
