package Notifier;

import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Valar Dohaeris on 12/27/16.
 */
public class Notify {

    public boolean doNotify(Push push)
    {
        try {
            String url =Configurations.url;
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", Configurations.contentType);
            con.setRequestProperty("Accept", Configurations.contentType);
            con.setRequestMethod("POST");
            con.setRequestProperty("Access-Token", Configurations.accessToken);

            //Post
            Gson gson = new Gson();
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(gson.toJson(push));
            wr.flush();

            int HttpResult = con.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK)
                return true;

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
        return false;
    }
}
