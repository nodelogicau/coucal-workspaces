package au.nodelogic.coucal.workspaces;

import net.fortuna.ical4j.model.component.VAvailability;
import org.mnode.ical4j.serializer.jotn.ContentMapper;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class AvailabilityDeserializer extends ContentMapper<VAvailability> {

    public AvailabilityDeserializer() {
        super(VAvailability::new);
    }
}
