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

    @Autowired
    private CollectionManager manager;

    @Autowired
    private ObjectMapper mapper;

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
        return "list/entries";
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
        return "list/entries";
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
