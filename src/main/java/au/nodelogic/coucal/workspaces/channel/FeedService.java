package au.nodelogic.coucal.workspaces.channel;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class FeedService {

    public SyndFeed getFeed(URL url) throws IOException, FeedException {
        return new SyndFeedInput().build(new XmlReader(url.openStream()));
    }
}
