package KexTools.module.movement;

import org.lwjgl.input.Keyboard;

import KexTools.event.EventTarget;
import KexTools.event.events.EventMove;
import KexTools.module.Category;
import KexTools.utils.MotionUtils;

public class Strafe extends KexTools.module.Module{

	MotionUtils mu = new MotionUtils();
	
	public Strafe() {
		super("Strafe", 0, Category.MOVEMENT);
	}
	
	@EventTarget
	public void onMove(EventMove event) {
		mu.strafe();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		mu.stop();
	}
}
