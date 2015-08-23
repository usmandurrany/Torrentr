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
public class Network_TorrDetFetch extends AsyncTask<String, Void, String> {

    private final String url;
    private final Context context;
    public Interface_TorrFunc delegate;
    String[] sites;
    String[] contents;
    String[] comments;
    String[] links;

    ProgressDialog pDialog;
    Document doc;


    public Network_TorrDetFetch(Context context, String url) {
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

        Elements getSites = doc.select("div.download > dl > dt > a > span.u");
        Elements getContents = doc.select("div.files > ul > li > ul > li");
        Elements getComments = doc.select("div.comments > div.comment > div.com");
        Elements getLinks = doc.select("div.download > dl > dt > a");


        sites = new String[getSites.size()];
        contents = new String[getContents.size()];
        comments = new String[getComments.size()];
        links = new String[getLinks.size()];

        for (int i = 0; i < getSites.size(); i++) {
            sites[i] = getSites.get(i).text();
            links[i] = getLinks.get(i).attr("href");
            //Log.e("Links: ",links[i].toString());

        }


        for (int i = 0; i < getContents.size(); i++)
            contents[i] = getContents.get(i).text();

        for (int i = 0; i < getComments.size(); i++)
            comments[i] = getComments.get(i).text();

            /*
        for (int i = 0; i < getVerified.size(); i++)
        for (int i = 0; i < getSeeds.size(); i++)*/
        delegate.TorrDetail(sites, contents, comments, links);
        pDialog.dismiss();


        // glink.execute(0);

    }
}
