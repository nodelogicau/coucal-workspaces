package au.nodelogic.coucal.workspaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/inbox")
public class InboxController extends AbstractLayoutController {

    @GetMapping("/")
    public String listInbox(Model model) throws IOException {
        populateModelForLayout(model);
        return "list/inbox";
    }
}
