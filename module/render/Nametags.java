package KexTools.module.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import KexTools.Kex;
import KexTools.clickgui.util.ColorUtil;
import KexTools.event.EventTarget;
import KexTools.event.events.Event3D;
import KexTools.module.Category;
import KexTools.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;

public class Nametags extends KexTools.module.Module {
	
	public Nametags() {
        super("Nametags", 0, Category.RENDER);
    }
	
	@EventTarget
	public void onESP(Event3D e){
        
	}
}
