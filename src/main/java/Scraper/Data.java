package Scraper;

/**
 * Valar Dohaeris on 12/29/16.
 */

@lombok.Data
public class Data {

    String post;
    String url;

    Data(String post,String url)
    {
        this.post=post;
        this.url=url;
    }
}
