package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.data.Feed;
import au.nodelogic.coucal.workspaces.data.FeedItem;
import au.nodelogic.coucal.workspaces.data.FeedItemRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listFeedItems(@RequestParam(name = "feed", required = false) String feedUri, Model model) throws IOException {
        List<FeedItem> feedItems;
        if (feedUri != null) {
            feedItems = feedItemRepository.findAll(Example.of(
                    new FeedItem().withFeed(new Feed().withUri(feedUri))));
            Collections.reverse(feedItems);
        } else {
            feedItems = feedItemRepository.findAllByOrderByPublishedDate();
        }
        model.addAttribute("dateFormatter", new PrettyTime());
        model.addAttribute("feedItems", feedItems);
        return "feedItems/list";
    }

    @GetMapping("/{feedUri}/{feedItemUri}")
    public String getFeedItem(@PathVariable(value="feedItemUri") String feedItemUri, Model model) throws IOException {
        List<FeedItem> feedItems = feedItemRepository.findAll(Example.of(new FeedItem().withUri(feedItemUri)));
        model.addAttribute("dateFormatter", new PrettyTime());
        model.addAttribute("feedItem", feedItems.getFirst());
        return "feedItems/detail";
    }
}
