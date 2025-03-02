package au.nodelogic.coucal.workspaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequestMapping("/create")
public class CreateController {

    @GetMapping("/")
    public String createEntry(@RequestParam(name = "concept") String concept, Model model) throws IOException {
        return "create/" + concept.replaceAll(":", "/");
    }
}
