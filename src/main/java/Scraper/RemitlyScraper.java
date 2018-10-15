package Scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Valar Dohaeris on 10/14/18.
 */
public class RemitlyScraper {

    public double readExchangeRate()
    {
        double currentExchangeRate = 0.0;

        try {
            File file = new File("RemitlyExchangeRate");
            if (!file.isFile() && !file.createNewFile())
            {
                throw new IOException("Error creating new file: " + file.getAbsolutePath());
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            if( (line = br.readLine())!=null)
                currentExchangeRate = Double.parseDouble(line.trim());

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return currentExchangeRate;
    }

    public void writeExchangeRate(double exchangeRate)
    {
        try {
            FileWriter fr = new FileWriter("RemitlyExchangeRate");
            BufferedWriter br = new BufferedWriter(fr);

            br.write(String.valueOf(exchangeRate));
            br.newLine();

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Data> scrape()
    {

        List<Data> results = new ArrayList<Data>();
        double todaysExchangeRate = 0.0;
        double olderExchangeRate = readExchangeRate();

        try {
            Document doc = Jsoup.connect(ScrapeConfigurations.remitlyUrl)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com").get();
            if(doc!=null)
            {
                Elements element = doc.getElementsByClass("f1smo2ix");
                String stringRate = element.text().trim().split(" ")[2].substring(1);
                todaysExchangeRate = Double.parseDouble(stringRate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(todaysExchangeRate > olderExchangeRate) {
            writeExchangeRate(todaysExchangeRate);
            results.add(new Data(String.valueOf(todaysExchangeRate), ScrapeConfigurations.remitlyUrl));
            return results;
        }else
            return null;
    }
}
