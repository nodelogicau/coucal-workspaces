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
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import net.fortuna.ical4j.filter.FilterExpression;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VAvailability;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VJournal;
import net.fortuna.ical4j.model.component.VToDo;
import net.fortuna.ical4j.util.Calendars;
import net.fortuna.ical4j.util.RandomUidGenerator;
import org.ical4j.connector.ObjectCollection;
import org.ical4j.connector.ObjectStoreException;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@Controller
@RequestMapping("/entries/{collectionId}/")
public class EntriesController {

    private final CollectionManager manager;

    private final ObjectMapper mapper;

    public EntriesController(@Autowired CollectionManager manager, @Autowired ObjectMapper mapper) {
        this.manager = manager;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String listEntries(@PathVariable(name = "collectionId") String collectionId,
                              @RequestParam(name = "concept", required = false) String[] concept,
                              Model model) throws IOException {
        ObjectCollection<Calendar> collection = manager.getCollection(collectionId);
        if (concept != null && concept.length > 0) {
            model.addAttribute("content",
                    collection.query(FilterExpression.parse(
                            String.format("concept in [%s]", String.join(",", concept)))));
        } else {
            model.addAttribute("content",
                    collection.getAll(collection.listObjectUIDs().toArray(new String[0])));
        }
        model.addAttribute("collection", collection);
        model.addAttribute("dateFormatter", new PrettyTime());
        return "entries/list";
    }

    /**
     * Save new content.
     * @return
     */
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public String addEntry(@PathVariable(name = "collectionId") String collectionId,
                           @RequestBody MultiValueMap<String, String> data,
                           Model model, HttpServletResponse response) throws IOException, ObjectStoreException {
        ObjectCollection<Calendar> collection = manager.getCollection(collectionId);
        switch (Objects.requireNonNull(data.getFirst("concept"))) {
            case "semcal:concept:action":
            case "semcal:concept:issue":
            case "semcal:concept:request":
                VToDo action = mapper.convertValue(data, VToDo.class);
                action.add(new RandomUidGenerator().generateUid());
                collection.add(Calendars.wrap(action));
                break;
            case "semcal:concept:event":
            case "semcal:concept:observance":
                VEvent event = mapper.convertValue(data, VEvent.class);
                event.add(new RandomUidGenerator().generateUid());
                collection.add(Calendars.wrap(event));
                break;
            case "semcal:concept:note":
            case "semcal:concept:report":
                VJournal note = mapper.convertValue(data, VJournal.class);
                note.add(new RandomUidGenerator().generateUid());
                collection.add(Calendars.wrap(note));
                break;
            case "semcal:concept:availability":
                VAvailability availability = mapper.convertValue(data, VAvailability.class);
                availability.add(new RandomUidGenerator().generateUid());
                collection.add(Calendars.wrap(availability));
                break;
        }
        // apply strategy
//        event = new Meeting().withPrototype(event).get();
        response.addHeader("HX-Trigger", "entitiesRefresh");
        model.addAttribute("content",
                collection.getAll(collection.listObjectUIDs().toArray(new String[0])));
        model.addAttribute("collection", collection);
        model.addAttribute("dateFormatter", new PrettyTime());
        return "entries/list";
    }

    /**
     * Update existing content.
     * @return
     */
    @PostMapping("/{uid}")
    public String updateEntry(String collection, String uid) {
        return "";
    }

    /**
     * Remove content with the given UID.
     * @param uid
     * @return
     */
    @DeleteMapping("/{uid}")
    public String deleteEntry(String collection, String uid) {
        return "";
    }
}
