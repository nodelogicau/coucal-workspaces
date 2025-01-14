package au.nodelogic.coucal.workspaces;

import net.fortuna.ical4j.model.component.VToDo;
import org.mnode.ical4j.serializer.jotn.ContentMapper;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class ToDoDeserializer extends ContentMapper<VToDo> {

    public ToDoDeserializer() {
        super(VToDo::new);
    }
}
