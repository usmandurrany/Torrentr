package justapps.ud.torrentr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Usman on 7/9/2014.
 */
public class Adapter_TorrComments extends ArrayAdapter<String> {
    private final Context context;
    private final String[] comments;


    public Adapter_TorrComments(Context context, String[] comments) {
        super(context, R.layout.list_torrcomments, comments);
        this.context = context;
        this.comments = comments;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_torrcomments, parent, false);
        TextView torrComments = (TextView) rowView.findViewById(R.id.torr_comments);

        try {

            torrComments.setText(comments[position]);

        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("Error ", e.toString());
        }

        // change the icon for Windows and iPhone


        return rowView;
    }

}
