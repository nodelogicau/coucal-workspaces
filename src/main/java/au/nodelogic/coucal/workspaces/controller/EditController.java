package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.data.CollectionManager;
import au.nodelogic.coucal.workspaces.data.EntityManager;
import net.fortuna.ical4j.filter.FilterExpression;
import org.ical4j.connector.ObjectCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/edit")
public class EditController extends AbstractLayoutController {

    @Autowired
    private CollectionManager manager;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/collection/{id}")
    public String editCollection(@PathVariable(value="id") String collectionId,
                                 @RequestParam(name = "filter", required = false) String filter,
                                 Model model) throws IOException {
        ObjectCollection<?> collection = manager.getCollection(collectionId);
        List<?> content;
        if (filter != null) {
            content = collection.query(FilterExpression.parse(filter));
        } else {
            content = collection.getAll(collection.listObjectUIDs().toArray(new String[0]));
        }
        model.addAttribute("content", content);
        model.addAttribute("collection", collection);
        populateModelForLayout(model);
        return "edit/collection";
    }

    @GetMapping("/{concept}/{uid}")
    public String editEntry(@PathVariable(name = "concept") String concept,
                            @PathVariable(name = "uid") String uid, Model model) throws IOException {
        populateModelForLayout(model);
        ObjectCollection<?> collection = manager.getCollectionForUid(uid);
        model.addAttribute("collection", collection);
        model.addAttribute("entry", collection.get(uid).orElseThrow());
        return "edit/" + concept.replaceAll(":", "/");
    }
}
