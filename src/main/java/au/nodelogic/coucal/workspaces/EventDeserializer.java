package au.nodelogic.coucal.workspaces;

import net.fortuna.ical4j.model.component.VEvent;
import org.mnode.ical4j.serializer.jotn.ContentMapper;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class EventDeserializer extends ContentMapper<VEvent> {

    public EventDeserializer() {
        super(VEvent::new);
    }

//    @Bean
//    @Primary
//    public ObjectMapper objectMapper() {
//        SimpleModule module = new SimpleModule();
//        module.addDeserializer(VEvent.class, new EventDeserializer());
//        return new ObjectMapper()
////                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
//                .registerModule(module);
//    }
}
