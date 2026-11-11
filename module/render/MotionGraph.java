package KexTools.module.render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import KexTools.event.EventTarget;
import KexTools.event.events.Event2D;
import KexTools.event.events.EventPreMotionUpdate;
import KexTools.module.Category;
import KexTools.utils.MotionUtils;
import KexTools.utils.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class MotionGraph extends KexTools.module.Module{
	
	public MotionGraph() {
        super("MotionGraph", 0, Category.RENDER);
    }
	private int width = 180;
	private int height = 5;
	private int yoffset = -210;
	
	private final ArrayList<Float> speeds = new ArrayList<>();
	private double lastVertices;
	private float fuckingCock;
	
	MotionUtils mu = new MotionUtils();
	
	@EventTarget
	public void onPreMotion(EventPreMotionUpdate event) {
		if (speeds.size() > 100 - 2) {
            speeds.remove(0);
        }
		speeds.add((float) mu.getSpeed() * mc.timer.timerSpeed);

		fuckingCock = -1;
        for (final float f : speeds) {
            if (f > fuckingCock) {
            	fuckingCock = f;
            }
        }
	}
	
	@EventTarget
	public void onRender2D(Event2D event) { 
		final ScaledResolution sr = new ScaledResolution(mc);
		
		GL11.glPushMatrix();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(2);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBegin(GL11.GL_LINES);
        
        if (speeds.size() > 3) {
            final float width = (float) (sr.getScaledWidth() / 2f - this.width / 2f);
            
            for (int i = 0; i < speeds.size() - 1; i++) {
            	RenderUtil.color(RenderUtil.getRainbow(-6000, 0));
            	
            	final float y = (float) (speeds.get(i) * 10 * height);
                final float y2 = (float) (speeds.get(i + 1) * 10 * height);
                final float length = (float) (this.width / (speeds.size() - 1));
                
                GL11.glVertex2f(width + (i * length), (float) (sr.getScaledHeight() / 2F - Math.min(y, 50) - this.yoffset));
                GL11.glVertex2f(width + ((i + 1) * length), (float) (sr.getScaledHeight() / 2F - Math.min(y2, 50) - this.yoffset));
            }
        }
        GL11.glEnd();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        RenderUtil.color(Color.WHITE);
        GlStateManager.resetColor();
        GL11.glPopMatrix();
	}
	
}
