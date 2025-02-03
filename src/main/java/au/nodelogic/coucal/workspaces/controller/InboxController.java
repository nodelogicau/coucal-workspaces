package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.InboxManager;
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
//    IMAPService imapService;
    InboxManager inboxManager;

    @GetMapping("/")
    public String listInbox(Model model) throws IOException {
        populateModelForLayout("Coucal Inbox", model);
        model.addAttribute("inboxMessages", inboxManager.getInboxCollection().getAll());
        return "list/inbox";
    }
}
