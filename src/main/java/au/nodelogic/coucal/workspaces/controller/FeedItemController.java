package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.data.Feed;
import au.nodelogic.coucal.workspaces.data.FeedItem;
import au.nodelogic.coucal.workspaces.data.FeedItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/feedItems")
public class FeedItemController {

    private final FeedItemRepository feedItemRepository;

    public FeedItemController(@Autowired FeedItemRepository feedItemRepository) {
        this.feedItemRepository = feedItemRepository;
    }

    @GetMapping("/")
    public String listFeedItems(Model model) throws IOException {
        List<FeedItem> feedItems = feedItemRepository.findAll(Example.of(new FeedItem()));
        Collections.reverse(feedItems);
        model.addAttribute("feedItems", feedItems);
        return "feedItems/list";
    }

    @GetMapping("/{feedUri}")
    public String listFeedItems(@PathVariable(value="feedUri") String feedUri, Model model) throws IOException {
        List<FeedItem> feedItems = feedItemRepository.findAll(Example.of(
                new FeedItem().withFeed(new Feed().withUri(feedUri))));
        Collections.reverse(feedItems);
        model.addAttribute("feedItems", feedItems);
        return "feedItems/list";
    }
}
