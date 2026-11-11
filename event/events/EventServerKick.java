package KexTools.event.events;

import java.util.List;

import KexTools.event.Event;

public class EventServerKick extends Event{
	public List<String> message;

    public List<String> getMessage() {
        return this.message;
    }

    public EventServerKick(List<String> message) {
        this.message = message;
    }
}
