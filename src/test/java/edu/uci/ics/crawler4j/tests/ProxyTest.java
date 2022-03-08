package edu.uci.ics.crawler4j.tests;

import junit.framework.TestCase;
import org.junit.Test;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.ProxyConfig;
import edu.uci.ics.crawler4j.examples.proxies.ProxyCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


public class ProxyTest extends TestCase {

    private static final ProxyConfig[] PROXIES = {
        new ProxyConfig("62.210.5.43",  8080, "insidepic1",  "zOrGlHVS7Nh7diUczo9u"),
        new ProxyConfig("62.210.5.47",  8080, "insidepic2",  "49P806UezNkGy9IQMfzT"),
        new ProxyConfig("62.210.5.81",  8080, "insidepic3",  "785p5SbaMp85o61sdJwC"),
        new ProxyConfig("62.210.5.131", 8080, "insidepic4",  "6SaImtoUCsbJgjsdXQpY"),
        new ProxyConfig("62.210.5.142", 8080, "insidepic5",  "23C3LO9H6v7f1ZT4968K"),
        new ProxyConfig("62.210.5.143", 8080, "insidepic6",  "2g8rcm9w14nr799V494J"),
        new ProxyConfig("62.210.5.144", 8080, "insidepic7",  "d65FJ2qApbvcK5u90oNX"),
        new ProxyConfig("62.210.5.162", 8080, "insidepic8",  "nRg419X9T6962J6qb62h"),
        new ProxyConfig("62.210.5.203", 8080, "insidepic9",  "s756BG33QPO1R6f638tS"),
        new ProxyConfig("62.210.5.204", 8080, "insidepic10", "iatk08DGe047p25Go3OV")
    };

    @Test
    public void testProxyConfig()
    {
        boolean test;
        try {
            new ProxyConfig("104.43.166.43.52", 3128);
            test = false;
        } catch (IllegalArgumentException e) {
            test = true;
        }
        assertTrue(test);
        
        try {
            new ProxyConfig("104.43.166.43", 3128);
            test = true;
        } catch (IllegalArgumentException e) {
            test = false;
        }
        
        try {
            new ProxyConfig("invalid host", 3128);
            test = false;
        } catch (IllegalArgumentException e) {
            test = true;
        }
        
        try {
            new ProxyConfig("test.com", 3128);
            test = false;
        } catch (IllegalArgumentException e) {
            test = true;
        }
    }
    
    @Test
    public void testWithoutProxy()
    {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("frontier");
        config.setIncludeBinaryContentInCrawling(false);
        config.setResumableCrawling(false);

        //fork crawler4j and PageFetcher for proxies
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        try {
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
            controller.addSeed("http://checkip.amazonaws.com/");
            controller.start(ProxyCrawler.class, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testWithProxy()
    {   
        for (ProxyConfig proxy : PROXIES) {
            ProxyConfig[] array = {proxy};
            
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder("frontier");
            config.setIncludeBinaryContentInCrawling(false);
            config.setResumableCrawling(false);
            config.setProxies(array);
            config.setSocketTimeout(5000);
    
            //fork crawler4j and PageFetcher for proxies
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            robotstxtConfig.setEnabled(false);
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            try {
                System.err.println("Testing proxy " + proxy.getProxyHost());
               
                CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
                controller.addSeed("http://checkip.amazonaws.com/");
                controller.start(ProxyCrawler.class, 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}