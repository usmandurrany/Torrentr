package justapps.ud.torrentr;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Usman on 7/9/2014.
 */
public class Activity_TorrDetails extends Activity implements Interface_TorrFunc {
    Adapter_TorrDownLoc sitesAdapter;

    ListView sites;
    //ImageView magnet;

    TextView btncomments;
    TextView btncontents;
    TextView cCount;
    Bundle dialogBundle;

    String[] comments;
    String[] contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_torrdetails);
        String torrLink = getIntent().getStringExtra("torrLink");
        Network_TorrDetFetch TorrDetail = new Network_TorrDetFetch(this, "http://torrentz.eu" + torrLink);
        sites = (ListView) findViewById(R.id.dl_sites);
        cCount = (TextView) findViewById(R.id.cmntCount);


        TorrDetail.delegate = this;
        TorrDetail.execute();
        addListenerOnButton();
        dialogBundle = new Bundle();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.torrentr_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // if (id == R.id.action_settings) {
        //    return true;
        ///}
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void resultTitle(ArrayList<Model_TorrDetail> Model_TorrDetail) {

    }

    @Override
    public void TorrDetail(String[] sites, String[] contents, String[] comments, String[] links) {
        sitesAdapter = new Adapter_TorrDownLoc(Activity_TorrDetails.this, sites, links);
        cCount.setText(String.valueOf(comments.length));
        this.sites.setAdapter(sitesAdapter);
        this.comments = comments;
        this.contents = contents;


    }

    @Override
    public void TorrMagLink(String maglink) {

    }

    private void addListenerOnButton() {
        btncomments = (TextView) findViewById(R.id.btnComments);
        btncontents = (TextView) findViewById(R.id.btnContents);

        btncomments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new Dialog_TorrInfo();
                dialogBundle.putCharSequenceArray("data", comments);
                dialogBundle.putString("Title", "Comments");
                dialog.setArguments(dialogBundle);
                dialog.show(getFragmentManager(), "comments");

            }
        });
        btncontents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new Dialog_TorrInfo();
                dialogBundle.putCharSequenceArray("data", contents);
                dialogBundle.putString("Title", "Contents");
                dialog.setArguments(dialogBundle);
                dialog.show(getFragmentManager(), "contents");

            }
        });
        sites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sitesAdapter.getItem(position)));
                startActivity(browserIntent);


            }
        });


    }
}
