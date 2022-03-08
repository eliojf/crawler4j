package edu.uci.ics.crawler4j.tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.junit.Test;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.ProxyConfig;
import edu.uci.ics.crawler4j.examples.proxies.ProxyDelayCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


public class ProxyDelayTest extends TestCase {

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
    public void testWithProxy()
    {   
        this.createTest(true);
    }
    
    @Test
    public void testWithoutProxy()
    {   
        //this.createTest(false);
    }
    
    private void createTest(boolean useProxy)
    {
        Logger.getLogger("edu.uci.ics.crawler4j.crawler.WebCrawler").getParent().setLevel(Level.OFF);
        Logger.getLogger("edu.uci.ics.crawler4j.crawler.WebCrawler").setLevel(Level.OFF);
        
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("frontier");
        config.setIncludeBinaryContentInCrawling(false);
        config.setResumableCrawling(false);
        config.setSocketTimeout(30000);
        config.setPolitenessDelay(5000);
        config.setMaxPagesToFetch(100);
        if (useProxy) {
            config.setProxies(PROXIES);
        }

        //fork crawler4j and PageFetcher for proxies
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        long startTime = System.currentTimeMillis();
        try {
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
            controller.addSeed("http://www.lemonde.fr/");
            controller.start(ProxyDelayCrawler.class, PROXIES.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.err.println("time elapsed = " + (endTime - startTime));
        System.err.println("expected = " + (config.getPolitenessDelay() / PROXIES.length) * config.getMaxPagesToFetch());
    }
}