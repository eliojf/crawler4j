/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.uci.ics.crawler4j.examples.proxies;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar
 */
public class ProxyDelayCrawler extends WebCrawler
{

    public static long time = 0;

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url)
    {
        return url.getSubDomain().equals("www") && url.getDomain().equals("lemonde.fr");
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page)
    {
        if (time == 0) {
            time = System.currentTimeMillis();
        }
        long startTime = System.currentTimeMillis();
        System.err.println("Current page = " + page.getWebURL().getURL());
        System.err.println("Current time = " + startTime);
        System.err.println("Current proxy = " + this.getMyController().getPageFetcher().currentProxy());
        System.err.println("Time elapsed since beginning " + (startTime - time) + "ms");
        System.err.println();
    }
}