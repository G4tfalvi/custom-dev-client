package KexTools.module.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import KexTools.Kex;
import KexTools.clickgui.util.ColorUtil;
import KexTools.event.EventTarget;
import KexTools.event.events.Event3D;
import KexTools.module.Category;
import KexTools.settings.Setting;
import KexTools.utils.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class ESP extends KexTools.module.Module {
	
	public ESP() {
        super("ESP", 0, Category.RENDER);
    }
	
	@EventTarget
	public void onESP(Event3D e){
        for (Object obj : mc.theWorld.loadedEntityList) {
            Entity entity = (Entity) obj;
            if (isValidEntity(entity)) {
                RenderUtil.entityRenderer(mc, entity, () -> {
                    GlStateManager.rotate(mc.getRenderManager().playerViewY, 0.0F, -1.0F, 0.0F);
                    GL11.glLineWidth(1f);

                    final Color healthColor = new Color(ColorUtil.getHealthColor((EntityLivingBase) entity));
                    final Color rainbowColor = new Color(ColorUtil.getRainbow(-6000, 0));
                    
                    GL11.glColor3d(rainbowColor.getRed() / 255F, rainbowColor.getBlue() / 255F, rainbowColor.getGreen() / 255F);
                    GL11.glColor3d(0, 0, 1);
                    

                    RenderUtil.drawOutlinedRectNoColor(-entity.width, 0, entity.width, entity.height + 0.2F);

                    if (mc.thePlayer.getDistanceToEntity(entity) < 25) {
                        GL11.glColor3d(healthColor.getRed() / 255f, healthColor.getGreen() / 255f, healthColor.getBlue() / 255f);
                        RenderUtil.drawRectNoColor(entity.width + 0.1F, 0, entity.width + 0.075F, ((EntityLivingBase) entity).getHealth() / 10.0F);
                    }
                });
            }
        }
	}
	private boolean isValidEntity(Entity entity) {
        if (entity == mc.thePlayer) return true;
        
        if (entity.isInvisible()) return true;
        if (entity instanceof EntityPlayer) return true;
        if (entity instanceof EntityAnimal) return false;
        if (entity instanceof EntityVillager) return false;
        return false;
    }
}
