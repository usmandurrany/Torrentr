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
public class Network_TorrMagLinkFetch {
    public Interface_TorrFunc delegate;
    Context mContext;

    public Network_TorrMagLinkFetch(Context context) {

        mContext = context;

    }

    public void fetch(final String url) {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        final ArrayList<Model_TorrDetail> items = new ArrayList<>();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    Log.e("URL",url);
                    Log.e("Response Length", Integer.toString(response.length()));
                    String magHref = response.getJSONObject("MagLink").getString("0");


                    delegate.TorrMagLink(magHref);
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
/*public class Network_TorrMagLinkFetch extends AsyncTask<String, Void, String> {

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
*/