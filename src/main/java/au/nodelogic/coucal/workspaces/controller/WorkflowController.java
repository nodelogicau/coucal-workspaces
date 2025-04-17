package au.nodelogic.coucal.workspaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workflow")
public class WorkflowController {

    private final FeedItemController feedItemController;

    public WorkflowController(@Autowired FeedItemController feedItemController) {
        this.feedItemController = feedItemController;
    }
}
