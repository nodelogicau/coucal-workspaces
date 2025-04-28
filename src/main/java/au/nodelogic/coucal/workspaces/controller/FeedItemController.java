package au.nodelogic.coucal.workspaces.controller;

import au.nodelogic.coucal.workspaces.data.FeedItem;
import au.nodelogic.coucal.workspaces.data.FeedItemRepository;
import au.nodelogic.coucal.workspaces.workflow.FeedUpdater;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/feedItems")
public class FeedItemController {

    private final FeedItemRepository feedItemRepository;

    private final FeedUpdater feedUpdater;

    public FeedItemController(@Autowired FeedItemRepository feedItemRepository, @Autowired FeedUpdater feedUpdater) {
        this.feedItemRepository = feedItemRepository;
        this.feedUpdater = feedUpdater;
    }

    @GetMapping("/")
    public String listFeedItems(@RequestParam(name = "feed", required = false) String feedUri,
                                @RequestParam(name = "refresh", required = false) Boolean refreshFeeds,
                                Model model) {

        if (refreshFeeds != null && refreshFeeds) {
            feedUpdater.refreshFeeds();
        }

//        List<FeedItem> feedItems;
//        if (feedUri != null) {
//            feedItems = feedItemRepository.findAll(Example.of(
//                    new FeedItem().withFeed(new Feed().withUri(feedUri))));
//            Collections.reverse(feedItems);
//        } else {
//            feedItems = feedItemRepository.findAllByOrderByPublishedDate();
//        }
        model.addAttribute("dateFormatter", new PrettyTime());
//        model.addAttribute("feedItems", feedItems);
        return "feedItems/index";
    }

    @GetMapping("/{filter}")
    public String listFeedItemsFiltered(@PathVariable(value = "filter") String filter,
                                        @RequestParam(name = "feed", required = false) String feedUri,
                                        @RequestParam(name = "refresh", required = false) Boolean refreshFeeds,
                                Model model) {

        if (refreshFeeds != null && refreshFeeds) {
            feedUpdater.refreshFeeds();
        }

        List<FeedItem> feedItems;
        LocalDate today, yesterday;
        feedItems = switch (filter) {
            case "today" -> {
                today = LocalDate.now();
                yield feedItemRepository.findByPublishedDateBetweenOrderByPublishedDateDesc(
                        Date.from(today.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(today.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            }
            case "yesterday" -> {
                yesterday = LocalDate.now().minusDays(1);
                yield feedItemRepository.findByPublishedDateBetweenOrderByPublishedDateDesc(
                        Date.from(yesterday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
                        Date.from(yesterday.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            }
            case "beforeYesterday" -> {
                yesterday = LocalDate.now().minusDays(1);
                yield feedItemRepository.findByPublishedDateBeforeOrderByPublishedDateDesc(
                        Date.from(yesterday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            }
            default -> feedItemRepository.findAll(Sort.by(Sort.Order.desc("publishedDate")));
        };
        model.addAttribute("dateFormatter", new PrettyTime());
        model.addAttribute("feedItems", feedItems);
        return "feedItems/list";
    }

    @GetMapping("/{feedUri}/{feedItemUri}")
    public String getFeedItem(@PathVariable(value="feedItemUri") String feedItemUri, Model model) throws IOException {
        List<FeedItem> feedItems = feedItemRepository.findAll(Example.of(new FeedItem().withUri(feedItemUri)));
        model.addAttribute("dateFormatter", new PrettyTime());
        model.addAttribute("feedItem", feedItems.get(0));
        return "feedItems/detail";
    }
}
