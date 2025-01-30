package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.IMAPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/inbox")
public class InboxController extends AbstractLayoutController {

    @Autowired
    IMAPService imapService;

    @GetMapping("/")
    public String listInbox(Model model) throws IOException {
        populateModelForLayout(model);
        model.addAttribute("inboxMessages", imapService.fetchMessages());
        return "list/inbox";
    }
}
