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
public class Network_TorrListFetch {
    public Interface_TorrFunc delegate;
    Context mContext;

    public Network_TorrListFetch(Context context) {

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
                    response = response.getJSONObject(cat);
                    for (int i = 0; i < response.length(); i++) {
                        items.add(new Model_TorrDetail(response.getJSONObject(Integer.toString(i)).getString("Name"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Size"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Date"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Verif"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Seeds"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Leech"),
                                response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Link")));
                       // Log.e("TESSST", response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Size"));

                    }
                    Log.e("Response Length", Integer.toString(response.length()));
                    Log.e("Item Array Length", Integer.toString(items.size()));

                    delegate.TorrList(items);
                    delegate.TorrCatCurr(cat);
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


