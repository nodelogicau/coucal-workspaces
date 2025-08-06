/*
 *  Copyright 2025 Node Logic
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package au.nodelogic.coucal.workspaces;

import net.fortuna.ical4j.model.component.VEvent;
import org.mnode.ical4j.serializer.jotn.ContentMapper;
import org.springframework.boot.jackson.JsonComponent;

/**
 * EventDeserializer is a custom deserializer for VEvent components.
 * It extends ContentMapper to provide a way to deserialize JSON content into VEvent objects.
 */
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
