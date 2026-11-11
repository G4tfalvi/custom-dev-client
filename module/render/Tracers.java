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

public class Tracers extends KexTools.module.Module {
	
	public Tracers() {
        super("Tracers", 0, Category.RENDER);
    }
	
	@EventTarget
	public void onTracer(Event3D e){
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
        
        for (final EntityPlayer player : mc.theWorld.playerEntities) {
        	if (player == mc.thePlayer || player.isDead && !player.isInvisible()) {
                continue;
            }
        	final double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * e.getPartialTicks();
            final double y = (player.lastTickPosY + (player.posY - player.lastTickPosY) * e.getPartialTicks()) + 1.62F;
            final double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * e.getPartialTicks();
            
            final Color color = ColorUtil.withAlpha(
                    ColorUtil.mixColors(Kex.INSTANCE.getAlternateClientColor(), Kex.INSTANCE.getClientColor(), Math.min(1, mc.thePlayer.getDistanceToEntity(player) / 50)),
                128);
            
            RenderUtil.drawLine(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY + mc.thePlayer.getEyeHeight(), mc.getRenderManager().renderPosZ, x, y, z, color, 1.5F);
        }
        GlStateManager.resetColor();
        GlStateManager.popMatrix();
	}
}
