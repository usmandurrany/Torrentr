package justapps.ud.torrentr;

/**
 * Created by Usman on 7/9/2014.
 */

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
import java.util.ArrayList;


class Network_TorrListFetch_OLD extends AsyncTask<String, Void, String> {

    private final String url;
    private final Context context;
    public Interface_TorrFunc delegate;
    String[] sizes;
    String[] titles;
    String[] dates;
    String[] verified;
    String[] seeds;
    String[] leeches;
    String[] links;

    ProgressDialog pDialog;
    Document doc;


    public Network_TorrListFetch_OLD(Context context, String url) {
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

                    /*connect(url)
                    .header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .maxBodySize(0)
                    .timeout(600000)
                    .get();*/
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

        Elements getTitle = doc.select("div.results > dl > dt > a");
        Elements getSize = doc.select("div.results > dl > dd > span.s");
        Elements getDate = doc.select("div.results > dl > dd > span.a > span");
        Elements getVerified = doc.select("div.results > dl > dd > span.v");
        Elements getSeeds = doc.select("div.results > dl > dd > span.u");
        Elements getLeeches = doc.select("div.results > dl > dd > span.d");
        Elements getLinks = doc.select("div.results > dl > dt > a");


        sizes = new String[getSize.size()];
        titles = new String[getTitle.size()];
        dates = new String[getDate.size()];
        verified = new String[getVerified.size()];
        seeds = new String[getSeeds.size()];
        leeches = new String[getLeeches.size()];
        links = new String[getLinks.size()];

        ArrayList<Model_TorrDetail> items = new ArrayList<>();


        for (int i = 0; i < getTitle.size(); i++) {
           /* titles[i] = getTitle.get(i).text();
            sizes[i] = getSize.get(i).text();
            dates[i] = getDate.get(i).text();
            verified[i] = getVerified.get(i).text();
            seeds[i] = getSeeds.get(i).text();
            leeches[i] = getLeeches.get(i).text();
            links[i] = getLinks.get(i).attr("href");
            //Log.e("Links: ",links[i].toString());*/
            items.add(new Model_TorrDetail(getTitle.get(i).text(), getSize.get(i).text(), getDate.get(i).text(), getVerified.get(i).text(), getSeeds.get(i).text(), getLeeches.get(i).text(), getLinks.get(i).attr("href")));

        }

        // items.add(new Model_TorrDetail("Usman", "Size10","today","v1","2","20","www.google.com"));

        /*for (int i = 0; i < getSize.size(); i++)
        for (int i = 0; i < getDate.size(); i++)
        for (int i = 0; i < getVerified.size(); i++)
        for (int i = 0; i < getSeeds.size(); i++)*/
        delegate.resultTitle(items);
        pDialog.dismiss();


        // glink.execute(0);

    }
}
