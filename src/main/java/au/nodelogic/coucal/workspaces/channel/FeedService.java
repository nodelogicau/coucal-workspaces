package au.nodelogic.coucal.workspaces.channel;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FeedService {

    public SyndFeed getFeed(URL url) throws IOException, FeedException {
        return new SyndFeedInput().build(new XmlReader(url.openStream()));
    }

    public List<String> resolveFeeds(String url) throws IOException {
        List<String> feedUrls = new ArrayList<>();
        if (URI.create(url).toURL().getHost().endsWith("reddit.com")) {
            feedUrls.add(url.replaceAll("/$", "") + ".rss");
        } else {
            Document doc = Jsoup.connect(url).followRedirects(true).get();
            doc.select("link[type=application/rss+xml], link[type=application/atom+xml]").forEach(element -> {
                feedUrls.add(Objects.requireNonNull(element.attribute("href")).getValue());
            });
        }
        return feedUrls;
    }

}
