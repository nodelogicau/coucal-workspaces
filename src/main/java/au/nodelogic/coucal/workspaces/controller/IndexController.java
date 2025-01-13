package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.CollectionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.io.IOException;

/**
 * Application entry controller.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) throws IOException {
        CollectionManager manager = new CollectionManager(new File(System.getProperty("user.dir"), "build/collections"));
        model.addAttribute("collections", manager.getCollections());
        return "index";
    }
}
