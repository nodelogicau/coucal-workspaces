package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.CollectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/ribbon")
public class RibbonController {

    private final CollectionManager manager;

    public RibbonController(@Autowired CollectionManager manager) {
        this.manager = manager;
    }

    @GetMapping("/{context}/{tab}")
    public String getRibbon(@PathVariable(name = "context") String context,
                            @PathVariable(name = "tab") String tab,
                            @RequestParam(name = "collection", required = false) String collection, Model model) throws IOException {
        model.addAttribute("tab", tab);
        if (collection != null) {
            model.addAttribute("collection", manager.getCollection(collection));
        }
        return context + "/ribbon";
    }
}
