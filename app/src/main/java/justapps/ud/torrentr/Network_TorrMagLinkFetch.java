package justapps.ud.torrentr;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Usman on 7/9/2014.
 */
public class Network_TorrMagLinkFetch extends AsyncTask<String, Void, String> {

    private final String url;
    private final Context context;
    public Interface_TorrFunc delegate;
    String[] sites;
    String[] contents;
    String[] comments;
    String[] links;

    ProgressDialog pDialog;
    Document doc;


    public Network_TorrMagLinkFetch(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Plese wait...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();


    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            doc = Jsoup.parse(new URL(url), 5000);

        } catch (MalformedURLException e) {
            Log.e("Malformed URL", url);
        } catch (IOException e) {
            Log.e("IO Exception", e.toString());
            pDialog.dismiss();
            this.cancel(true);
        }
        return null;

    }


    protected void onPostExecute(String result) {

        Elements getMagTag = doc.select("[href*=magnet]");
        String magHref = getMagTag.attr("href");

        delegate.TorrMagLink(magHref);
        pDialog.dismiss();


        // glink.execute(0);

    }
}
