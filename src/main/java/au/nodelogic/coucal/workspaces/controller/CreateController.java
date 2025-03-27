package au.nodelogic.coucal.workspaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/create")
public class CreateController {

    @GetMapping("/{collectionId}")
    public String createEntry(@PathVariable(name = "collectionId") String collectionId,
                              @RequestParam(name = "concept") String concept, Model model) throws IOException {
        model.addAttribute("collection", collectionId);
        return "create/" + concept.replaceAll(":", "/");
    }
}
