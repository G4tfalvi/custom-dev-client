package KexTools.event.events;

import KexTools.event.Event;
import net.minecraft.entity.Entity;

public class EventFastUse extends Event{
	 private float height;

	 private final boolean pre;

	 private final Entity entity;
	    
	 public EventFastUse(float height, boolean pre, Entity entity) {
		 this.height = height;
	     this.pre = pre;
	     this.entity = entity;
	 }
	 public void setHeight(float height) {
	        this.height = height;
	    }

	    public float getHeight() {
	        return height;
	    }

	    public boolean isPre() {
	        return pre;
	    }

	    public Entity getEntity() {
	        return entity;
	    }
}
