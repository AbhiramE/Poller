package Scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Valar Dohaeris on 12/22/16.
 */
public class Scraper {

    private boolean validate(String post)
    {
        return post.toLowerCase().contains(ScrapeConfigurations.keyword1)
                ||post.toLowerCase().contains(ScrapeConfigurations.keyword2)
                ||post.toLowerCase().contains(ScrapeConfigurations.keyword3)
                ||post.toLowerCase().contains(ScrapeConfigurations.testKeyword);
    }

    public List<Data> scrape()
    {

        List<Data> results=new ArrayList<Data>();

        try {
            Document doc = Jsoup.connect(ScrapeConfigurations.mainUrl).get();
            if(doc!=null)
            {
                String body=doc.body().text();
                Pattern p = Pattern.compile("(.*?)ago");
                Matcher m = p.matcher(body);
                while(m.find())
                {
                    String post=m.group(1);
                    post=post.replaceAll(ScrapeConfigurations.headers,"");

                    String actualPost=post;
                    if(validate(post))
                    {
                        Elements links=Jsoup.parse(doc.toString()).select("a");
                        for (Element link:links)
                        {
                            if(actualPost.contains(link.text()))
                            {
                                String linkFinal=link.attr("href");
                                if(!linkFinal.equals(ScrapeConfigurations.skipUrl)) {
                                    System.out.println(linkFinal);
                                    results.add(new Data(post,linkFinal));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }
}
