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
public class Adapter_TorrDownLoc extends ArrayAdapter<String> implements Interface_TorrFunc {
    private final Context context;
    private final String[] sites;
    private final String[] links;


    public Adapter_TorrDownLoc(Context context, String[] sites, String[] links) {
        super(context, R.layout.list_torrdownloc, sites);
        this.context = context;
        this.sites = sites;
        this.links = links;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_torrdownloc, parent, false);
        TextView torrSites = (TextView) rowView.findViewById(R.id.torr_sites);
        ImageView magnet = (ImageView) rowView.findViewById(R.id.magnetlink);

        magnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), getItem(position).toString(), Toast.LENGTH_SHORT).show();
                Network_TorrMagLinkFetch magLink = new Network_TorrMagLinkFetch(getContext(), getItem(position).toString());

                magLink.delegate = Adapter_TorrDownLoc.this;
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
    public void resultTitle(ArrayList<Model_TorrDetail> Model_TorrDetail) {

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
