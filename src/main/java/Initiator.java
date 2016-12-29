import Notifier.Notify;
import Notifier.Push;
import Scraper.Data;
import Scraper.Scraper;

import java.util.List;

/**
 * Valar Dohaeris on 12/29/16.
 */
public class Initiator {

    static String url="https://news.ycombinator.com/";

    public static void main(String[] args) {

        Scraper scraper=new Scraper();
        Notify notify=new Notify();
        int count=0;

        List<Data> results=scraper.scrape();

        for (Data result:results)
        {
            Push push=new Push();
            push.setType("link");
            push.setTitle("New Internship Posting from "+result.getPost().split(" ")[0]);
            push.setBody(result.getPost());
            if(result.getUrl().contains("http"))
                push.setUrl(result.getUrl());
            else
                push.setUrl(url+result.getUrl());
            if(notify.doNotify(push))
                count++;
        }

        if(count==results.size())
            System.out.println("All Succeeded");
        else
            System.out.println("Success: "+count+" Out of "+results.size());
    }
}
