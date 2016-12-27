package Notify;

import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Valar Dohaeris on 12/27/16.
 */
public class Notify {

    public static void main(String[] args) {
        try {

            String url = "https://api.pushbullet.com/v2/pushes";
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            con.setRequestProperty("Access-Token", "LOL");

            // Request parameters and other properties.
            Push push = new Push();
            push.setType("link");
            push.setTitle("New Internship Posting");
            push.setBody("Some Message");
            push.setUrl("http://google.com");

            Gson gson = new Gson();
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(gson.toJson(push));
            wr.flush();

            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK)
                System.out.print("Ok");

        } catch (UnsupportedEncodingException e) {
            System.out.print("Epic Fail");
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            System.out.print("Epic Fail");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.print("Epic Fail");
            e.printStackTrace();
        }
    }
}
