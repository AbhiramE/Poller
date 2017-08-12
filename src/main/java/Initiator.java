import Notifier.Notify;
import Notifier.Push;
import Scraper.Data;
import Scraper.TrendingCompaniesScraper;

import java.util.List;

/**
 * Valar Dohaeris on 12/29/16.
 */
public class Initiator {

    //static String url="https://news.ycombinator.com/";

    public static void main(String[] args) {

        TrendingCompaniesScraper scraper=new TrendingCompaniesScraper();
        Notify notify=new Notify();
        int count=0;

        List<Data> results=scraper.scrape();

        for (Data result:results)
        {
            Push push=new Push();
            push.setType("link");
            push.setTitle("New Trending Company "+result.getPost());
            push.setBody("Check this new company out");
            push.setUrl(result.getUrl());
            if(notify.doNotify(push))
                count++;
        }

        if(count==results.size())
            System.out.println("All Succeeded");
        else
            System.out.println("Success: "+count+" Out of "+results.size());
    }
}
