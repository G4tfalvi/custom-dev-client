package KexTools.module.render;

import java.util.ArrayList;

import KexTools.Kex;
import KexTools.event.EventTarget;
import KexTools.event.events.EventRenderItem;
import KexTools.module.Category;
import KexTools.settings.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.util.MathHelper;

public class Animations extends KexTools.module.Module {
	
	private String mode = Kex.INSTANCE.settingsManager.getSettingByName("Animation Mode").getValString();

	public Animations() {
		super("Animations", 0, Category.RENDER);
	}
	
	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("Exhibition");
		options.add("Exhibition 2");
		options.add("1.7");
        Kex.INSTANCE.settingsManager.rSetting(new Setting("Animation Mode", this, "Exhibition", options));
	}

	@EventTarget
	public void onRenderItem(EventRenderItem event) {
		final float var = MathHelper.sin((float) (MathHelper.sqrt_float(event.getSwingProgress()) * Math.PI));
        ItemRenderer itemRenderer = mc.getItemRenderer();
        final float swingProgress = event.getSwingProgress();
        final float convertedProgress = MathHelper.sin(MathHelper.sqrt_float(swingProgress) * (float) Math.PI);
        
        if (event.getUseAction() == EventRenderItem.ItemUseAction.BLOCK) {
        	if(Kex.INSTANCE.settingsManager.getSettingByName("Animation Mode").getValString().equalsIgnoreCase("Exhibition")){
        		itemRenderer.transformFirstPersonItem(event.getUseProgress() - 0.125F, 0);
                GlStateManager.rotate(-var * 55 / 2.0F, -8.0F, 0.4f, 9.0F);
                GlStateManager.rotate(-var * 45, 1.0F, var / 2, -0.0F);
                GlStateManager.translate(0.0f, 0.1F, 0.0f);
                itemRenderer.doBlockTransformations();
        	}
        	if(Kex.INSTANCE.settingsManager.getSettingByName("Animation Mode").getValString().equalsIgnoreCase("1.7")){
        		itemRenderer.transformFirstPersonItem(event.getUseProgress(), swingProgress);
        		itemRenderer.doBlockTransformations();
        	}
        	if(Kex.INSTANCE.settingsManager.getSettingByName("Animation Mode").getValString().equalsIgnoreCase("Exhibition 2")){
        		itemRenderer.transformFirstPersonItem(event.getUseProgress() / 2.0F, 0.0F);
                GlStateManager.translate(0.0F, 0.3F, -0.0F);
                GlStateManager.rotate(-convertedProgress * 31.0F, 1.0F, 0.0F, 2.0F);
                GlStateManager.rotate(-convertedProgress * 33.0F, 1.5F, (convertedProgress / 1.1F), 0.0F);
                itemRenderer.doBlockTransformations();
        	}
        	event.setCancelled(true);
        }
	}
	
}
