package justapps.ud.torrentr;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Usman-Durrani on 8/22/2015.
 */
public class Network_TorrDetFetch {
    public Interface_TorrFunc delegate;
    Context mContext;
    String[] sites;
    String[] contents;
    String[] comments;
    String[] links;
    public Network_TorrDetFetch(Context context) {

        mContext = context;

    }

    public void fetch(String url,final String cat) {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        final ArrayList<Model_TorrDetail> items = new ArrayList<>();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    response = response.getJSONObject("Details");
                    sites = new String[response.getJSONObject("Download Locations").length()];
                    contents = new String[response.getJSONObject("Contents").length()];
                    if(response.has("Comments")) {
                        comments = new String[response.getJSONObject("Comments").length()];
                    }else
                        comments = new String[0];

                    links = new String[response.getJSONObject("Download Locations").length()];

                    for (int i = 0; i < response.getJSONObject("Download Locations").length(); i++) {

                        sites[i] = response.getJSONObject("Download Locations").getJSONObject(Integer.toString(i)).getString("Site");
                        links[i] = response.getJSONObject("Download Locations").getJSONObject(Integer.toString(i)).getString("Link");
                        // Log.e("TESSST", response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Size"));

                    }
                    for (int i = 0; i < response.getJSONObject("Contents").length(); i++)
                        contents[i] = response.getJSONObject("Contents").getJSONObject(Integer.toString(i)).getString("Item");

                    if(comments.length > 0) {
                        for (int i = 0; i < response.getJSONObject("Comments").length(); i++)
                            comments[i] = response.getJSONObject("Comments").getJSONObject(Integer.toString(i)).getString("Text");
                    }
                    Log.e("Response Length", Integer.toString(response.length()));
                    //Log.e("Item Array Length", Integer.toString(items.size()));

                    delegate.TorrDetail(sites, contents, comments, links);
                    //delegate.TorrCatCurr(cat);
                } catch (JSONException e) {
                    Log.e("Torrentr", e.toString());


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Torrentr", error.toString());
            }
        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(3000,
                1,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsObjRequest);


    }


}






/*package justapps.ud.torrentr;

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
/*public class Network_TorrDetFetch extends AsyncTask<String, Void, String> {

    private final String url;
    private final Context context;
    public Interface_TorrFunc delegate;


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
       /* delegate.TorrDetail(sites, contents, comments, links);
        pDialog.dismiss();


        // glink.execute(0);

    }
}*/