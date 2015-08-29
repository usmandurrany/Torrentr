package justapps.ud.torrentr;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
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
        final ArrayList<Model_TorrTags> tags = new ArrayList<>();

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout)((Activity)mContext).findViewById(R.id.swipe_refresh_layout);
swipe.setRefreshing(true);
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
                        int j=0;
                        if (cat.equals("movies")) {

                         // Log.e("Tags Array Length", (response.getJSONObject(Integer.toString(16)).getJSONArray("Tags").getString(j).length() == 0) ? "null" : response.getJSONObject(Integer.toString(16)).getJSONArray("Tags").getString(j));

                            String temp = "null";
                            if (response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j).length()!=0){
                                temp = response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j);
                               // Log.e("TEMP VALUE",temp);
                            }//else
                                //Log.e("TEMP VALUE", "null");

                            tags.add(new Model_TorrTags(
                                                /*(response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j).length()==0) ? "null" : response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j),
                                                (j >= response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").length()) ? "null" : response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j++),
                                                (j >= response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").length()) ? "null" : response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j++),
                                                (j >= response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").length()) ? "null" : response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j++),
                                                (j >= response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").length()) ? "null" : response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j++),
                                        (j >= response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").length()) ? "null" : response.getJSONObject(Integer.toString(i)).getJSONArray("Tags").getString(j++)
*/
                                           temp,"null","null","null","null","null"

                                        )
                                );



                        }
                       // Log.e("TESSST", response.getJSONObject(Integer.toString(i)).getJSONObject("Details").getString("Size"));

                    }
                    Log.e("Response Length", Integer.toString(response.length()));
                    Log.e("Item Array Length", Integer.toString(items.size()));
                    if (cat.equals("movies")) {
                        Log.e("Tags Array Length", Integer.toString(tags.size()));
                        delegate.TorrListWTags(items, tags);


                    }
                    else
                    delegate.TorrList(items);
                    delegate.TorrCatCurr(cat);
                    swipe.setRefreshing(false);
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
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(2000,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(jsObjRequest);


    }


}


