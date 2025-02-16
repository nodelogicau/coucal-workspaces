package au.nodelogic.coucal.workspaces.channel

import spock.lang.Specification

class FeedServiceTest extends Specification {

    def 'test feed resolver'() {
        given: 'a feed service'
        def feedService = new FeedService()

        expect: 'url resolution returns expected feed urls'
        feedService.resolveFeeds(url) == expectedFeedUrls

        where:
        url                                     | expectedFeedUrls
        'https://slashdot.org'                  | ['https://rss.slashdot.org/Slashdot/slashdotMain']
        'https://www.youtube.com/@ChrisCowlin'  | ['https://www.youtube.com/feeds/videos.xml?channel_id=UCB9aWK0xfz2P-Ny2nsXGHrA']
        'https://www.reddit.com/r/coys/'        | ['https://www.reddit.com/r/coys.rss']
        'https://feeds.feedblitz.com/baeldung'  | ['https://feeds.feedblitz.com/baeldung&x=1']
        'https://www.cloudflarestatus.com/'     | ['https://www.cloudflarestatus.com/history.atom',
                                                   'https://www.cloudflarestatus.com/history.rss',
                                                   'https://www.cloudflarestatus.com/history.atom']
        'https://www.linkedin.com/in/benfortuna/' | []
    }
}
