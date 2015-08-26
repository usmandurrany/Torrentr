package justapps.ud.torrentr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Usman on 7/9/2014.
 */
public class Adapter_TorrNavDrawer extends ArrayAdapter<DataType_TorrNavDrawer> {
    private final Context context;
    //private final String category;
    //private final int cat_icon;
    private DataType_TorrNavDrawer[] navdrawer;

    public Adapter_TorrNavDrawer(Context context,DataType_TorrNavDrawer[] navdrawer) {
        super(context, R.layout.list_torrdrawer, navdrawer);
        this.context = context;
        this.navdrawer = navdrawer;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_torrdrawer, parent, false);

        TextView cat = (TextView)rowView.findViewById(R.id.drawer_cat);
        ImageView icon = (ImageView)rowView.findViewById(R.id.drawer_icons);

        cat.setText(navdrawer[position].categoryName());
        icon.setImageResource(navdrawer[position].categoryIcon());


        return rowView;
    }
}
