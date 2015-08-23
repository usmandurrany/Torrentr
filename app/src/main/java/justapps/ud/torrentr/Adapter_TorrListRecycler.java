package justapps.ud.torrentr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Usman-Durrani on 8/14/2015.
 */
public class Adapter_TorrListRecycler extends RecyclerView.Adapter<Adapter_TorrListRecycler.ViewHolder> {
    // Store a member variable for the users
    private ArrayList<Model_TorrDetail> modeltorrents;
    // Store the context for later use
    private Context context;

    // Pass in the context and users array into the constructor
    public Adapter_TorrListRecycler(Context context, ArrayList<Model_TorrDetail> Model_TorrDetail) {
        this.modeltorrents = Model_TorrDetail;
        this.context = context;
    }

    @Override
    public Adapter_TorrListRecycler.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).
                inflate(R.layout.list_torrmain, viewGroup, false);
        // Return a new holder instance
        return new Adapter_TorrListRecycler.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Adapter_TorrListRecycler.ViewHolder viewHolder, final int i) {
// Get the data model based on position
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent TorrDetail = new Intent(context, Activity_TorrDetails.class);
                TorrDetail.putExtra("torrLink", modeltorrents.get(i).links);
                //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context, (View) torrcard, "torrselected");
                context.startActivity(TorrDetail);
            }
        });
        // Set item views based on the data model
        viewHolder.title.setText(modeltorrents.get(i).title);
        viewHolder.size.setText(modeltorrents.get(i).size);
        viewHolder.date.setText(modeltorrents.get(i).date);
        viewHolder.verify.setText(modeltorrents.get(i).verify);
        viewHolder.seeds.setText(modeltorrents.get(i).seeds);
        viewHolder.leeches.setText(modeltorrents.get(i).leeches);
    }

    @Override
    public int getItemCount() {
        return modeltorrents.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView title;
        public TextView size;
        public TextView date;
        public TextView verify;
        public TextView seeds;
        public TextView leeches;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.size = (TextView) itemView.findViewById(R.id.size);
            this.date = (TextView) itemView.findViewById(R.id.added);
            this.verify = (TextView) itemView.findViewById(R.id.verified);
            this.seeds = (TextView) itemView.findViewById(R.id.seeds);
            this.leeches = (TextView) itemView.findViewById(R.id.leeches);
        }
    }

}
