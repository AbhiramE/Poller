import Notifier.Notify;
import Notifier.Push;
import Scraper.Data;
import Scraper.RemitlyScraper;
import Scraper.TrendingCompaniesScraper;

import java.util.List;

/**
 * Valar Dohaeris on 12/29/16.
 */
public class Initiator {

    public static void main(String[] args) {

        RemitlyScraper scraper=new RemitlyScraper();
        Notify notify=new Notify();
        int count=0;

        List<Data> results=scraper.scrape();

        if(results!=null) {
            for (Data result : results) {
                Push push = new Push();
                push.setType("link");
                push.setTitle("Remitly has a better exchange rate of " + result.getPost() + " !!");
                push.setBody("Exchange now");
                push.setUrl(result.getUrl());
                if (notify.doNotify(push))
                    count++;
            }

            if (count == results.size())
                System.out.println("All Succeeded");
            else
                System.out.println("Success: " + count + " Out of " + results.size());
        }
    }
}
