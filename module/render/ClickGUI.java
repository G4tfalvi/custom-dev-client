package KexTools.module.render;

import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.module.Category;
import KexTools.settings.Setting;

import java.util.ArrayList;

public class ClickGUI extends KexTools.module.Module {
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void setup() {
        ArrayList<String> options = new ArrayList<>();
        options.add("New");
        options.add("JellyLike");
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Design", this, "New", options));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Sound", this, false));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("GuiRed", this, 255, 0, 255, true));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("GuiGreen", this, 0, 0, 255, true));
        Kex.INSTANCE.settingsManager.rSetting(new Setting("GuiBlue", this, 0, 0, 255, true));
    }

    @Override
    public void onEnable() {
        super.onEnable();

        mc.displayGuiScreen(Kex.INSTANCE.clickGui);
        toggle();
    }
}
