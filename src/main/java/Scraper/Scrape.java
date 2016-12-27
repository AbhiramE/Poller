package Scraper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Valar Dohaeris on 12/22/16.
 */
public class Scrape {

    public static void main(String[] args)
    {
        try {
            Document doc = Jsoup.connect("https://news.ycombinator.com/jobs").get();
            if(doc!=null)
            {
                String body=doc.body().text();
                Pattern p = Pattern.compile("(.*?)ago");
                Matcher m = p.matcher(body);
                while(m.find())
                {
                    String post=m.group(1);
                    post=post.replaceAll("Hacker News new | comments | show | ask | jobs | submit login " +
                            "These are jobs at startups that were funded by Y Combinator. " +
                            "You can also get a job at a YC startup through Triplebyte.","");

                    String actualPost=post;
                    if(post.toLowerCase().contains("gitlab")
                            ||post.toLowerCase().contains("internship")
                            ||post.toLowerCase().contains("intern")) //is your string. do what you want
                    {
                        System.out.print(actualPost);
                        Elements links=Jsoup.parse(doc.toString()).select("a");
                        for (Element link:links)
                        {
                            if(actualPost.contains(link.text()))
                            {
                                String linkFinal=link.attr("href");
                                if(!linkFinal.equals("http://www.ycombinator.com"))
                                    System.out.print(linkFinal);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
