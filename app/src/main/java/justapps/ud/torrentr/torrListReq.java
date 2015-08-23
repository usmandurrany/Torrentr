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
public class torrListReq {
    public Interface_TorrentFunctions delegate;
    String url = "http://torrentr-1038.appspot.com/jresp.jsp?cat=ebooks";
    String site = "null";
    Context mContext;
    ArrayList<Model_TorrentDetail> items = new ArrayList<>();

    public torrListReq(Context context) {
        mContext = context;
    }

    public void fetch() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                try {
                    response = response.getJSONObject("ebooks");
//                    Toast.makeText(mContext,response.length(),Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < response.length(); i++) {
                        items.add(new Model_TorrentDetail(response.getJSONObject(Integer.toString(i)).getString("Name"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Size"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Date"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Verif"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Seeds"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Leech"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Link")));
                        Log.e("TESSST", response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Size"));

                    }
                    Log.e("JSON", Integer.toString(response.length()));
                    Log.e("JSON", Integer.toString(items.size()));

                    response = response.getJSONObject("0");
                    site = response.getString("Name");
                    //network = response.getString("network");
                    //System.out.println("Site: " + site + "\nNetwork: " + network);
                    delegate.resultTitle(items);
                } catch (JSONException e) {
                    Log.e("Torrentr", e.toString());


                }

                Toast.makeText(mContext, site, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("Torrentr", error.toString());
            }
        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsObjRequest);


    }


}


