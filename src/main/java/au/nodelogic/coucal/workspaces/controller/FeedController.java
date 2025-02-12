package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.data.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/feeds")
public class FeedController extends AbstractLayoutController {

    @Autowired
    private FeedRepository feedRepository;

    @GetMapping("/")
    public String listFeeds(Model model) throws IOException {
        populateModelForLayout("Coucal Feeds", model);
        model.addAttribute("feeds", feedRepository.findAll());
        return "list/feeds";
    }
}
