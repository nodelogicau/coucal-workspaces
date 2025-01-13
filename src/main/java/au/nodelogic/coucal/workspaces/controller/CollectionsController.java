package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.CollectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.IOException;

@Controller
public class CollectionsController {

    /**
     * Return a list of available collections.
     * @return
     */
    public String list() {
        return "";
    }

    /**
     * Add a new collection.
     * @param collection
     * @return
     */
    @PostMapping("/addCollection")
    public String add(@ModelAttribute("displayName") String collection, Model model) throws IOException {
        CollectionManager manager = new CollectionManager(new File(System.getProperty("user.dir"), "build/collections"));
        manager.addCollection(collection);
        model.addAttribute("collections", manager.getCollections());
        return "collection-list";
    }

    /**
     * Update an existing collection.
     * @param collection
     * @return
     */
    public String update(String collection) {
        return "";
    }

    /**
     * Delete a collection.
     * @param collection
     * @return
     */
    public String delete(String collection) {
        return "";
    }
}
