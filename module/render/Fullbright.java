package KexTools.module.render;

import org.lwjgl.input.Keyboard;

import KexTools.event.EventTarget;
import KexTools.event.events.EventUpdate;
import KexTools.module.Category;

public class Fullbright extends KexTools.module.Module{

	public Fullbright() {
		super("FullBright", 0, Category.RENDER);
	}
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		mc.gameSettings.gammaSetting = 100f;
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = 1f;
		super.onDisable();
	}

}
