package au.nodelogic.coucal.workspaces;

import net.fortuna.ical4j.model.component.VJournal;
import org.mnode.ical4j.serializer.jotn.ContentMapper;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class JournalDeserializer extends ContentMapper<VJournal> {

    public JournalDeserializer() {
        super(VJournal::new);
    }
}
