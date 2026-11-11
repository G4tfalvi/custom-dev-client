package KexTools.gui;

import java.awt.Color;
import java.io.IOException;

import KexTools.alt.GuiAltLogin;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class CustomMainMenu extends GuiScreen{
	
	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(1, this.width / 2 - 65, this.height / 2 - 60, 120, 20, "Singleplayer"));
		this.buttonList.add(new GuiButton(2, this.width / 2 - 65, this.height / 2 - 30, 120, 20, "Multiplayer"));
		this.buttonList.add(new GuiButton(3, this.width / 2 - 65, this.height / 2 + 0, 120, 20, "Options"));
		this.buttonList.add(new GuiButton(4, this.width / 2 - 65, this.height / 2 + 30, 120, 20, "Exit game"));
		this.buttonList.add(new GuiButton(5, 5, 5, 50, 20, "Alts"));
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		mc.getTextureManager().bindTexture(new ResourceLocation("kex/bg/background1.jpg"));
		this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}
		if(button.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}
		if(button.id == 3) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
		}
		if(button.id == 4) {
			this.mc.shutdown();
		}
		if(button.id == 5) {
			mc.displayGuiScreen(new GuiAltLogin(this));
		}
	}

}
