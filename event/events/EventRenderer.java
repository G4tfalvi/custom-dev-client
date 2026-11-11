package KexTools.event.events;

import KexTools.event.Event;

public class EventRenderer extends Event{
	float partialTicks;
	
	public EventRenderer(float partialTicks) {
		this.partialTicks = partialTicks;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

	public void setPartialTicks(float partialTicks) {
		this.partialTicks = partialTicks;
	}
}
