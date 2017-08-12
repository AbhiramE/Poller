package Scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Valar Dohaeris on 8/11/17.
 */
public class TrendingCompaniesScraper {

    static String url = ScrapeConfigurations.trendingUrl;

    public List<String> readCompanies()
    {
        List<String> companies = new ArrayList<String>();

        try {
            FileReader fr = new FileReader("Companies");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while( (line = br.readLine())!=null)
                companies.add(line.trim());

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return companies;
    }

    public void writeCompanies(List<String> companies)
    {
        try {
            FileWriter fr = new FileWriter("Companies");
            BufferedWriter br = new BufferedWriter(fr);

            for (String comp: companies)
            {
                br.write(comp);
                br.newLine();
            }

            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Data> scrape()
    {

        List<Data> results=new ArrayList<Data>();
        List<String> companies = readCompanies();
        List<String> newCompanies = new ArrayList<String>();

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get();
            if(doc!=null)
            {
                Elements elements = doc.getElementsByClass("name");

                for(Element e : elements)
                {
                    String link = e.select("a").attr("href");
                    String company = e.select("a").text();
                    System.out.println(link+" "+company);

                    if(!companies.contains(company))
                        results.add(new Data(company,link));
                    newCompanies.add(company);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        writeCompanies(newCompanies);
        return results;
    }

}
