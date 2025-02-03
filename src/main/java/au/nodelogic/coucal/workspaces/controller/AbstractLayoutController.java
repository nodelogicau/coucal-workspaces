package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.CollectionManager;
import au.nodelogic.coucal.workspaces.EntityManager;
import au.nodelogic.coucal.workspaces.InboxManager;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.io.IOException;

/**
 * A layout controller serves complete html pages based on a common layout.
 */
public abstract class AbstractLayoutController {

    @Autowired
    private CollectionManager collectionManager;

    @Autowired
    private EntityManager entityManager;

    @Autowired
//    IMAPService imapService;
    private InboxManager inboxManager;

    protected void populateModelForLayout(Model model) throws IOException {
        this("Coucal Workspaces", model);
    }

    /**
     * Update model to support common layout rendering.
     * @param model
     */
    protected void populateModelForLayout(String title, Model model) throws IOException {
        model.addAttribute("title", title);
        model.addAttribute("collections", collectionManager.getCollections());
        model.addAttribute("entities", entityManager.getEntityCollection().getAll());
        model.addAttribute("dateFormatter", new PrettyTime());
//        model.addAttribute("inboxMessageCount", -1); //imapService.getMessageCount());
        model.addAttribute("inbox", inboxManager.getInboxCollection());
    }
}
