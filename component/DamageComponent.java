package KexTools.component;

import KexTools.utils.interfaces.InstanceAccess;

public class DamageComponent implements InstanceAccess{

	public static void fakeDamage() {
        mc.thePlayer.handleHealthUpdate((byte) 2);
        mc.ingameGUI.healthUpdateCounter = mc.ingameGUI.updateCounter + 20;
    }
}
