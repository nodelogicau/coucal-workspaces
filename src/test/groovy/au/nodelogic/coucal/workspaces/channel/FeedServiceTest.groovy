/*
 *  Copyright 2025 Node Logic
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
        'https://news.google.com/topics/CAAqKggKIiRDQkFTRlFvSUwyMHZNRFZxYUdjU0JXVnVMVWRDR2dKQlZTZ0FQAQ?hl=en-AU&gl=AU&ceid=AU%3Aen' | []
    }
}
