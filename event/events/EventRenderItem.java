package KexTools.event.events;

import KexTools.event.Event;
import net.minecraft.item.Item;

public class EventRenderItem extends Event{
	
	private final Item item;
	private final ItemUseAction useAction;
	private final float useProgress;
	private final float swingProgress;

	public EventRenderItem(Item item, ItemUseAction useAction, float useProgress, float swingProgress) {
		this.item = item;
		this.useAction = useAction;
		this.useProgress = useProgress;
		this.swingProgress = swingProgress;
	}

	public Item getItem() {
		return item;
	}

	public ItemUseAction getUseAction() {
		return useAction;
	}

	public float getUseProgress() {
		return useProgress;
	}

	public float getSwingProgress() {
		return swingProgress;
	}

	public enum ItemUseAction {
		NONE,
		EAT,
		DRINK,
		BLOCK,
		BOW
	}
}
