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

package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.CollectionManager;
import au.nodelogic.coucal.workspaces.EventDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.util.Calendars;
import net.fortuna.ical4j.util.RandomUidGenerator;
import org.ical4j.connector.ObjectCollection;
import org.ical4j.connector.ObjectStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ContentController {

    @Autowired
    private CollectionManager manager;

    @Autowired
    private ObjectMapper mapper;

    /**
     * List collection content.
     * @return
     */
    @GetMapping("/collections/{id}")
    public String list(@PathVariable(value="id") String collectionId, Model model) throws IOException {
        ObjectCollection<?> collection = manager.getCollection(collectionId);
        List<?> content = collection.getAll(collection.listObjectUIDs().toArray(new String[0]));
        model.addAttribute("content", content);
        model.addAttribute("collection", collection);
        return "content-list";
    }

    /**
     * Save new content.
     * @return
     */
    @PostMapping("/collections/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@PathVariable(value="id") String collectionId,
                         @RequestBody MultiValueMap<String, String> data,
                         Model model) throws IOException, ObjectStoreException {
        ObjectCollection<Calendar> collection = manager.getCollection(collectionId);
        VEvent event = mapper.convertValue(data, VEvent.class);
        event.add(new RandomUidGenerator().generateUid());
        collection.add(Calendars.wrap(event));
        return list(collectionId, model);
    }

    /**
     * Update existing content.
     * @return
     */
    public String update(String collection, String uid) {
        return "";
    }

    /**
     * Remove content with the given UID.
     * @param uid
     * @return
     */
    public String delete(String collection, String uid) {
        return "";
    }
}