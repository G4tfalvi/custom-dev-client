package KexTools.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import KexTools.utils.interfaces.InstanceAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

import static org.lwjgl.opengl.GL11.glBlendFunc;

public class RenderUtil implements InstanceAccess{
	
	public static void color(int color) {
        color(color, (float) (color >> 24 & 255) / 255.0F);
    }
	public static void color(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }
	public static void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }
	public static void color(final double red, final double green, final double blue, final double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }
	public static int getRainbow(final int speed, final int timeOffset) {
		final float hue = (System.currentTimeMillis() + timeOffset) % speed;
		
		return Color.HSBtoRGB(hue / speed, 1F, 1F);
	}
	public static void drawLine(double x, double y, double z, double x1, double y1, double z1, final Color color, final float width) {
        x = x - mc.getRenderManager().renderPosX;
        x1 = x1 - mc.getRenderManager().renderPosX;
        y = y - mc.getRenderManager().renderPosY;
        y1 = y1 - mc.getRenderManager().renderPosY;
        z = z - mc.getRenderManager().renderPosZ;
        z1 = z1 - mc.getRenderManager().renderPosZ;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(width);

        color(color);
        GL11.glBegin(2);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glEnd();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
	public static void entityRenderer(Minecraft mc, Entity entity, Runnable runnable) {
        final double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosX;
        final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosY;
        final double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - mc.getRenderManager().renderPosZ;
        GL11.glPushMatrix();
        glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.translate(x, y, z);
        runnable.run();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor3f(1, 1, 1);
        GL11.glPopMatrix();
    }
	
	public static void drawOutlinedRectNoColor(float left, float top, float right, float bottom) {
        float var5;

        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }

        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }

        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        worldRenderer.startDrawing(2);
        worldRenderer.pos(left, bottom, 0.0D);
        worldRenderer.pos(right, bottom, 0.0D);
        worldRenderer.pos(right, top, 0.0D);
        worldRenderer.pos(left, top, 0.0D);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	
	public static void drawRectNoColor(double left, double top, double right, double bottom) {
        double var5;

        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }

        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        worldRenderer.startDrawingQuads();
        worldRenderer.pos(left, bottom, 0.0D);
        worldRenderer.pos(right, bottom, 0.0D);
        worldRenderer.pos(right, top, 0.0D);
        worldRenderer.pos(left, top, 0.0D);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
