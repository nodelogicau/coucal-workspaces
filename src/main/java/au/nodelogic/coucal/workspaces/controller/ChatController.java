package au.nodelogic.coucal.workspaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/chat")
public class ChatController extends AbstractLayoutController {

    @GetMapping("/")
    public String listTopics(Model model) throws IOException {
        populateModelForLayout("Coucal Chat", model);
//        model.addAttribute("feeds", feedRepository.findAll());
        return "list/chat";
    }
}
