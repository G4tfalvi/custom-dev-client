package KexTools;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import KexTools.clickgui.ClickGUI;
import KexTools.cmds.Cmd;
import KexTools.cmds.CmdManager;
import KexTools.event.Event;
import KexTools.event.EventManager;
import KexTools.event.EventTarget;
import KexTools.event.events.EventChat;
import KexTools.event.events.EventKey;
import KexTools.module.Category;
import KexTools.module.Module;
import KexTools.module.ModuleManager;
import KexTools.module.movement.Fly;
import KexTools.settings.SettingsManager;
import KexTools.utils.AuthSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ChatComponentText;

public class Kex {
	
	public static Kex INSTANCE = new Kex();
	public Minecraft mc = Minecraft.getMinecraft();
	
	public SettingsManager settingsManager;
	public EventManager eventManager;
	public ModuleManager moduleManager;
    public static CmdManager cmdManager;
    public static List<Module> modules = new ArrayList<Module>();
    
    public ClickGUI clickGui;

	
	public void start() {
		settingsManager = new SettingsManager();
		eventManager = new EventManager();
		moduleManager = new ModuleManager();
		cmdManager = new CmdManager();
		clickGui = new ClickGUI();
		
		eventManager.register(this);
		
		mc.displayGuiScreen(new AuthSystem(null));
	}
	
	public void stop() {
		eventManager.unregister(this);
	}
	
	@EventTarget
    public void onKey(EventKey event) {
        moduleManager.getModules().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());
    }
	
	public static void onEvent(Event event) {
        if (event instanceof EventChat) {
            Kex.cmdManager.handleChat((EventChat)event);
        }
    }
	
	public static void KeyPress(int n) {
        onEvent(new EventKey(n));
        for (final Module module : Kex.modules) {
            if (module.getKey() == n) {
                module.toggle();
            }
        }
    }
	
    public static Module getModuleByName(String s) {	
        for (Module m : Kex.modules) {
            if (m.getName().equalsIgnoreCase(s)) {
                return m;
            }
        }
        return null;
    }
    
    public static List getModuleByCategory(Category category) {
        final ArrayList<Module> list = new ArrayList<Module>();
        for (Module m : Kex.modules) {
            if (m.category == category) {
                list.add(m);
            }
        }
        return list;
    }

	public final static Color getClientColor() {
        return new Color(236, 133, 209);
    }

	public final static Color getAlternateClientColor() {
        return new Color(28, 167, 222);
    }
}
