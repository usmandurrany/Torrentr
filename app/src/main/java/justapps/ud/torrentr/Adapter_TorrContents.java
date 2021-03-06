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
public class Adapter_TorrContents extends ArrayAdapter<String> {
    private final Context context;
    private final String[] contents;


    public Adapter_TorrContents(Context context, String[] contents) {
        super(context, R.layout.list_torrcontents, contents);
        this.context = context;
        this.contents = contents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_torrcontents, parent, false);
        TextView torrContents = (TextView) rowView.findViewById(R.id.torr_file);

        try {

            torrContents.setText(contents[position]);

        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e("Error ", e.toString());
        }

        // change the icon for Windows and iPhone


        return rowView;
    }
}
