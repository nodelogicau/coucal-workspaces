package au.nodelogic.coucal.workspaces.util

import spock.lang.Specification

class HtmlParserTest extends Specification {

    def 'test getFeeds'() {
        expect: 'url resolution returns expected feed urls'
        HtmlParser.getFeeds(url) == expectedFeedUrls

        where:
        url                                     | expectedFeedUrls
        'https://slashdot.org'                  | ['https://rss.slashdot.org/Slashdot/slashdotMain']
        'https://www.youtube.com/@ChrisCowlin'  | ['https://www.youtube.com/feeds/videos.xml?channel_id=UCB9aWK0xfz2P-Ny2nsXGHrA']
        'https://feeds.feedblitz.com/baeldung'  | ['https://feeds.feedblitz.com/baeldung&x=1']
        'https://www.cloudflarestatus.com/'     | ['https://www.cloudflarestatus.com/history.atom',
                                                   'https://www.cloudflarestatus.com/history.rss',
                                                   'https://www.cloudflarestatus.com/history.atom']
        'https://news.google.com/topics/CAAqKggKIiRDQkFTRlFvSUwyMHZNRFZxYUdjU0JXVnVMVWRDR2dKQlZTZ0FQAQ?hl=en-AU&gl=AU&ceid=AU%3Aen' | ['https://news.google.com/rss/topics/CAAqKggKIiRDQkFTRlFvSUwyMHZNRFZxYUdjU0JXVnVMVWRDR2dKQlZTZ0FQAQ?hl=en-AU&gl=AU&ceid=AU%3Aen&oc=11, https://news.google.com/atom/topics/CAAqKggKIiRDQkFTRlFvSUwyMHZNRFZxYUdjU0JXVnVMVWRDR2dKQlZTZ0FQAQ?hl=en-AU&gl=AU&ceid=AU%3Aen&oc=11']

    }

    def 'test getTitle'() {
        expect: 'url resolution returns expected title'
        HtmlParser.getTitle(url) == expectedTitle

        where:
        url                                     | expectedTitle
        'https://slashdot.org'                  | 'Slashdot: News for nerds, stuff that matters'
        'https://www.youtube.com/@ChrisCowlin'  | 'Chris Cowlin: Spurs Chat Podcast & Tottenham News - YouTube'
        'https://feeds.feedblitz.com/baeldung'  | 'Baeldung'
        'https://www.cloudflarestatus.com/'     | 'Cloudflare Status'
    }

    def 'test getLinkedData'() {
        expect: 'url resolution returns expected linked data'
        HtmlParser.getLinkedData(url) == expectedLinkedData

        where:
        url                             | expectedLinkedData
        'http://www.nbcnews.com'        | '{"@context":"https://schema.org/","@type":"CollectionPage","inLanguage":"en-US","copyrightHolder":{"@id":"https://www.nbcnews.com/#publisher","@type":"NewsMediaOrganization"},"copyrightYear":2025,"publisher":{"@type":"NewsMediaOrganization","name":"NBC News","masthead":"https://www.nbcnews.com/news/us-news/nbc-news-digital-editors-n893846","url":"https://www.nbcnews.com","logo":{"@type":"ImageObject","url":"https://media-cldnry.s-nbcnews.com/image/upload/h_60/v1696280688/newsgroup-logos/nbcnews/logo/primary-black-424x45.png"},"sameAs":["https://x.com/nbcnews","https://www.pinterest.com/nbcnews/","https://www.facebook.com/NBCNews/","https://www.instagram.com/nbcnews/","https://www.youtube.com/nbcnews","https://www.snapchat.com/p/8bb879c7-45c0-499c-bb3c-7a3d0e229301/2193844248074240","https://www.tiktok.com/@nbcnews?lang=en"]},"sourceOrganization":{"@id":"https://www.nbcnews.com/#publisher","@type":"NewsMediaOrganization"}}'
        'https://moz.com/'              | '{"@context":"http://schema.org","@graph":[{"@type":"WebPage","author":{"@id":"https://moz.com#identity"},"copyrightHolder":{"@id":"https://moz.com#identity"},"copyrightYear":"2019","creator":{"@id":"https://moz.com#creator"},"dateCreated":"2016-10-06T15:11:03-07:00","dateModified":"2025-02-13T09:04:17-08:00","datePublished":"2019-06-06T10:10:00-07:00","description":"Backed by the largest community of SEOs on the planet, Moz builds the tools that make SEO, content marketing, market research, digital PR, and local SEO easy. Start your free 30-day trial today!","headline":"SEO Software for Smarter Marketing","image":{"@type":"ImageObject","url":"https://moz.com/images/cms/Moz-OG-Image-2024.jpg?w=1200&h=630&q=82&auto=format&fit=crop&dm=1705016118&s=9578fd3c293cea2bacf455a36480c7fd"},"inLanguage":"en-us","mainEntityOfPage":"https://moz.com/","name":"Moz","publisher":{"@id":"https://moz.com#creator"},"url":"https://moz.com"},{"@id":"https://moz.com#identity","@type":"Organization","address":[{"@type":"PostalAddress","addressLocality":"Seattle","addressRegion":"WA","name":"Moz Seattle","postalCode":"98107","streetAddress":"1752 NW Market Street #4073"},{"@type":"PostalAddress","addressCountry":"Canada","addressLocality":"Vancouver","addressRegion":"BC","name":"Moz Vancouver","postalCode":"V6Z 1A1","streetAddress":"400 - 720 Robson St"}],"image":{"@type":"ImageObject","height":"252","url":"https://moz.com/cms/Moz-logo-blue.jpg?mtime=20170419135148&focal=none","width":"862"},"logo":{"@type":"ImageObject","height":"60","url":"https://moz.com/images/cms/Moz-logo-blue.jpg?w=600&h=60&q=82&fm=png&fit=clip&dm=1532382338&s=d801d8360a3af30381aa8f20d49dc8d4","width":"205"},"name":"Moz","sameAs":["https://twitter.com/Moz","https://www.facebook.com/moz/","https://www.linkedin.com/company/moz/","https://www.youtube.com/channel/UCs26XZBwrSZLiTEH8wcoVXw","https://www.instagram.com/moz_hq/"],"url":"https://moz.com"},{"@id":"https://moz.com#creator","@type":"Organization","image":{"@type":"ImageObject","height":"252","url":"https://moz.com/cms/Moz-logo-blue.jpg?mtime=20170419135148&focal=none","width":"862"},"logo":{"@type":"ImageObject","height":"60","url":"https://moz.com/images/cms/Moz-logo-blue.jpg?w=600&h=60&q=82&fm=png&fit=clip&dm=1532382338&s=d801d8360a3af30381aa8f20d49dc8d4","width":"205"},"name":"Moz","url":"https://moz.com"},{"@type":"BreadcrumbList","description":"Breadcrumbs list","itemListElement":[{"@type":"ListItem","item":"https://moz.com","name":"Homepage","position":1}],"name":"Breadcrumbs"}]}'
        'https://quickbooks.intuit.com' | '{"@context":"https://schema.org","@type":"Organization","@id":"https://quickbooks.intuit.com/#organization","name":"QuickBooks","url":"https://quickbooks.intuit.com/","sameAs":["https://www.instagram.com/quickbooks/","https://en.wikipedia.org/wiki/QuickBooks","https://www.linkedin.com/company/1666/","https://www.linkedin.com/showcase/3839405/","https://twitter.com/QuickBooks","https://www.facebook.com/IntuitQuickBooks","https://www.pinterest.com/QuickBooks/","https://www.youtube.com/user/Quickbooks"],"logo":{"@type":"ImageObject","@id":"https://quickbooks.intuit.com/#logo","url":"https://quickbooks.intuit.com/cas/dam/IMAGE/A8TvQ9Cyd/quickbooks-logo-300px-square.png","caption":"QuickBooks"},"image":{"@id":"https://quickbooks.intuit.com/#logo"},"parentOrganization":{"@type":"Corporation","name":"Intuit","tickerSymbol":"INTU"},"address":{"@type":"PostalAddress","streetAddress":"2700 Coast Ave.","addressLocality":"Mountain View","addressRegion":"CA","postalCode":"94043","addressCountry":"USA"},"contactPoint":[{"@type":"ContactPoint","telephone":"+1 (877) 683-3280","contactType":"Sales","areaServed":"US","contactOption":"TollFree"},{"@type":"ContactPoint","telephone":"+1 (800) 4-INTUIT","contactType":"Customer Support","areaServed":"US","contactOption":"TollFree"},{"@type":"ContactPoint","telephone":"+1 (800) 4-INTUIT","contactType":"Technical Support","areaServed":"US","contactOption":"TollFree"}]}'
    }

    def 'test getProperties'() {
        expect: 'url resolution returns expected properties'
        HtmlParser.getProperties(url) == expectedProperties

        where:
        url                             | expectedProperties
        'http://www.nbcnews.com'        | [
                'og:image': 'https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1200-630,f_auto,q_auto:best/v1696280772/newsgroup-logos/nbcnews/social/primary-color-1680x840.png',
                'og:image:width': '1200',
                'og:type': 'website',
                'og:site_name': 'NBC News',
                'og:title': 'NBC News - Breaking News & Top Stories - Latest World, US & Local News',
                'og:locale':'en_US',
                'og:image:height':'630',
                'og:url':'https://www.nbcnews.com',
                'og:description':'Go to NBCNews.com for breaking news, videos, and the latest top stories in world news, business, politics, health and pop culture.'
            ]
        'https://moz.com/'              | [
                'og:see_also':'https://twitter.com/Moz',
                'og:image':'https://moz.com/images/cms/Moz-OG-Image-2024.jpg?w=1200&h=630&q=82&auto=format&fit=crop&dm=1705016118&s=9578fd3c293cea2bacf455a36480c7fd',
                'og:type':'website',
                'og:image:width':'1200',
                'og:site_name':'Moz',
                'og:title':'SEO Software for Smarter Marketing',
                'og:locale':'en_US',
                'og:image:height':'630',
                'og:url':'https://moz.com/',
                'og:description':'Backed by the largest community of SEOs on the planet, Moz builds the tools that make SEO, content marketing, market research, digital PR, and local SEO easy. Start your free 30-day trial today!'
        ]
        'https://quickbooks.intuit.com' | [
                'og:title':'QuickBooks®: Official Site | Smart Tools. Better Business.',
                'og:description':'Organize & manage your business with the #1 rated solution. Fast & easy setup. Sign up for a free trial to join 7 million businesses already using QuickBooks.'
        ]
    }
}
