package KexTools.utils;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import KexTools.gui.CustomMainMenu;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class AuthSystem extends GuiScreen {
	
	private CustomMainMenu guimainmenu;
	private GuiTextField loginNameField;
	private GuiTextField loginPassField;
	private String loginName;
	private String loginPass;

    public AuthSystem(CustomMainMenu guiMainMenu) {
		this.guimainmenu = guiMainMenu;
	}

    public void updateScreen()
    {
        this.loginNameField.updateCursorCounter();
        this.loginPassField.updateCursorCounter();
    }

    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 144 + 18, "Login"));
        this.loginNameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 180, 200, 20);
        this.loginNameField.setMaxStringLength(128);
        this.loginNameField.setFocused(true);
        this.loginPassField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 220, 200, 20);
        this.loginPassField.setMaxStringLength(128);
        
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 2)
            {
                this.mc.displayGuiScreen(new CustomMainMenu());
            }
            else if (button.id == 0)
            {
                this.loginName = this.loginNameField.getText();
                this.loginPass = this.loginPassField.getText();
                
                String user_acc1 = "kex";
                String pass_acc1 = "kex";
                
            	try {
            		if(loginName.equals(user_acc1) && loginPass.equals(pass_acc1))
            		{
            			this.mc.displayGuiScreen(new CustomMainMenu());
                		return;
            		}
            		else
            			try {
            				Thread.sleep(2000);
            				this.mc.shutdown();
            			} catch (InterruptedException e) {
            				System.exit(0);
            				e.printStackTrace();
            			}
            			this.mc.shutdown();
            	} catch (Exception ex) {
            		try {
            			Thread.sleep(2000);
            			this.mc.shutdown();
            		} catch (InterruptedException e) {
            			e.printStackTrace();
            			this.mc.shutdown();
            		}
            		this.mc.shutdown();
            	}
            }
        }

    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.loginNameField.textboxKeyTyped(typedChar, keyCode);
        this.loginPassField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == 15)
        {
            this.loginNameField.setFocused(!this.loginNameField.isFocused());
            this.loginPassField.setFocused(!this.loginPassField.isFocused());
        }

        if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        ((GuiButton)this.buttonList.get(0)).enabled = this.loginPassField.getText().length() > 0 && this.loginPassField.getText().split(":").length > 0 && this.loginNameField.getText().length() > 0;
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.loginPassField.mouseClicked(mouseX, mouseY, mouseButton);
        this.loginNameField.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
    	// IMPORT
    	int var3 = this.height / 4 + 48 - 30;
    	ScaledResolution scaledResolution = new ScaledResolution(mc);
    	
        int startColor = new Color(226, 55, 55).getRGB();
        int endColor = new Color(0, 0, 0).getRGB();
        
        // END OF IMPORT
    	
        this.drawDefaultBackground();
        Display.setTitle("You have to login into your Account");
        
        this.drawString(this.fontRendererObj, "Username", this.width / 2 - 100, 167, 10526880);
        this.drawString(this.fontRendererObj, "Password", this.width / 2 - 100, 208, 10526880);
        this.drawString(this.fontRendererObj, "§cKexTools §8» §fPlease §clogin §fto your account.", this.width / 2 - 100, 254, 10526880);
        this.loginNameField.drawTextBox();
        this.loginPassField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
