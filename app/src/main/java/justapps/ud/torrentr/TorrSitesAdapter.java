package justapps.ud.torrentr;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Usman on 7/9/2014.
 */
public class TorrSitesAdapter extends ArrayAdapter<String> implements Interface_TorrentFunctions {
    private final Context context;
    private final String[] sites;
    private final String[] links;


    public TorrSitesAdapter(Context context, String[] sites, String[] links) {
        super(context, R.layout.torr_sites_list, sites);
        this.context = context;
        this.sites = sites;
        this.links = links;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.torr_sites_list, parent, false);
        TextView torrSites = (TextView) rowView.findViewById(R.id.torr_sites);
        ImageView magnet = (ImageView) rowView.findViewById(R.id.magnetlink);

        magnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), getItem(position).toString(), Toast.LENGTH_SHORT).show();
                getTorrMagLink magLink = new getTorrMagLink(getContext(), getItem(position).toString());

                magLink.delegate = TorrSitesAdapter.this;
                magLink.execute();
            }
        });
        try {

            torrSites.setText(sites[position]);
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("Error ", e.toString());
        }

        // change the icon for Windows and iPhone


        return rowView;
    }

    @Override
    public String getItem(int position) {
        return links[position];
    }



    @Override
    public void resultTitle(ArrayList<Model_TorrentDetail> Model_TorrentDetail) {

    }

    @Override
    public void TorrDetail(String[] sites, String[] contents, String[] comments, String[] links) {

    }

    @Override
    public void TorrMagLink(String maglink) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(maglink));
        context.startActivity(browserIntent);
    }
}
