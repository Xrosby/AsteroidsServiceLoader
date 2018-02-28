package dk.sdu.mmmi.cbse.common.events;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.Serializable;

/**
 *
 * @author Mads
 */
public class Event implements Serializable{
    private final Entity source;
    private EventType type;

    public Event(Entity source, EventType type) {
        this.source = source;
        this.type = type;
    }

    public Entity getSource() {
        return source;
    }
    
    public EventType getEventType() {
        return type;
    }
}
