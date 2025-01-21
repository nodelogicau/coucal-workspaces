package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.CollectionManager;
import org.ical4j.connector.ObjectCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class FilterController {

    @Autowired
    private CollectionManager manager;

    @GetMapping("/filters/{id}")
    public String fixedFilters(@PathVariable(value="id") String collectionId, Model model) throws IOException {
        ObjectCollection<?> collection = manager.getCollection(collectionId);
        model.addAttribute("collection", collection);
        return "filters";
    }
}
